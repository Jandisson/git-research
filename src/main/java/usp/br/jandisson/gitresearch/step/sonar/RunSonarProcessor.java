package usp.br.jandisson.gitresearch.step.sonar;

import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.errors.TransportException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.TextProgressMonitor;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonarsource.scanner.api.EmbeddedScanner;
import org.sonarsource.scanner.api.LogOutput;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import usp.br.jandisson.gitresearch.model.Project;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;

@Component
public class RunSonarProcessor implements ItemProcessor<Project,Project> {


    @Value("${gitresearch.working.folder}")
    private String workDirectory;

    @Value("${gitresearch.working.sonar.url}")
    private String sonarUrl;

    @Value("${gitresearch.tempdir}")
    private String tempdir;

    private int frameNumber = 10;

    private LogOutput outPutlogger;

    Logger logger = LoggerFactory.getLogger(RunSonarProcessor.class);


    public RunSonarProcessor(){
        super();
        this.outPutlogger = new LogOutput() {

            @Override
            public void log(String s, Level level) {
                logger.info(s);
            }
        };
    }

    @Override
    public Project process(Project project) throws Exception {

        int numberOfCommits;
        int frameSize;

        String projectDirectory = makeRepositoryCopy(project);

        File gitDirectory = new File(String.format("%s%s%s",projectDirectory,File.separator,".git"));
        String defaultBranchName = getDefaultBranch(gitDirectory);
        Git git  = Git.open(gitDirectory);
        numberOfCommits = countNumberOfCommits(git.getRepository(),defaultBranchName);
        frameSize = numberOfCommits /this.frameNumber;
        for(int i =0 ; i < this.frameNumber ; i++)
        {
            git.checkout().setCreateBranch(false).setName(defaultBranchName).setForce(true).call();
            /** Avoid Sonnar Bug */
            Thread.sleep(1000);
            ObjectId Myhead = git.getRepository().resolve("HEAD~" + (frameSize * (this.frameNumber - i - 1)));
            try {
                executeSonarScanner(project,projectDirectory, i + 1);
            }catch(Exception e)
            {
                logger.error("Fail to execute Sonar: "+e.getMessage());
            }
        }
         removeRepositoryCopy(project);
        return project;
    }

    private String  makeRepositoryCopy(Project project) throws IOException {

        File srcDir = new File(project.getInformation("git_repository_path"));

        String destination = this.tempdir+File.separator+project.getId();
        File destDir = new File(destination);


        FileUtils.copyDirectory(srcDir, destDir);

        return destination;
    }

    private void removeRepositoryCopy(Project project) throws IOException {
        FileUtils.deleteDirectory(new File(this.tempdir+File.separator+project.getId()));
    }

    private void executeSonarScanner(Project project,String temporaryProjectFolder,int version){

        HashMap prop = new HashMap();

        prop.put("sonar.projectKey", String.format("Project%s",project.getId()));
        prop.put("sonar.projectName", String.format("Project%s",project.getId()));
        prop.put("sonar.projectVersion", String.valueOf(version));
        prop.put("sonar.projectBaseDir",temporaryProjectFolder);
        prop.put("sonar.sources",temporaryProjectFolder + File.separator +".");
        prop.put("sonar.java.binaries", temporaryProjectFolder);
        prop.put("sonar.host.url", this.sonarUrl);


        EmbeddedScanner scanner = EmbeddedScanner.create("", "", this.outPutlogger);
        scanner.start();
        scanner.execute(prop);
    }

    private int countNumberOfCommits(Repository repository,String defaultBranchName) throws IOException {

        int numberOfCommits = 0 ;
        RevWalk walk = new RevWalk(repository);
        RevCommit head = walk.parseCommit(repository.findRef(defaultBranchName).getObjectId());

        while (head != null) {
            numberOfCommits++;
            RevCommit[] parents = head.getParents();
            if (parents != null && parents.length > 0) {
                head = walk.parseCommit(parents[0]);
            } else {
                head = null;
            }
        }

        return numberOfCommits;

    }

    private static String getDefaultBranch(File gitFolder) throws IOException, GitAPIException {
        Git git = Git.open(gitFolder  );
        List<Ref> branchs= git.branchList().call();
        return branchs.get(0).getName();

    }



}
