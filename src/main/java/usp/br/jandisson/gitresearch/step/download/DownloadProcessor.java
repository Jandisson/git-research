package usp.br.jandisson.gitresearch.step.download;

import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.errors.TransportException;
import org.eclipse.jgit.lib.TextProgressMonitor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import usp.br.jandisson.gitresearch.model.Project;
import usp.br.jandisson.gitresearch.repositories.ProjectRepository;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;


public class DownloadProcessor implements ItemProcessor<Project,Project> {


    @Value("${gitresearch.working.folder}")
    private String workDirectory;

    @Override
    public Project process(Project project) throws Exception {

        this.createProjectFolder(project);
        this.cloneProject(project);
        project.addInformation("git_repository_path",workDirectory+File.separator+"projects"+File.separator+project.getId());
        return project;
    }

    private void createProjectFolder(Project project) throws IOException
    {

        this.createProjectsFolder();
        File file = new File(workDirectory+File.separator+"projects"+File.separator+project.getId());
        FileUtils.forceMkdir(file);

    }

    private void cloneProject(Project project) throws IOException, InterruptedException, InvalidRemoteException, TransportException, GitAPIException {


        Git git = Git.cloneRepository()
                .setProgressMonitor(new TextProgressMonitor(new PrintWriter(System.out)))
                .setURI( project.getUrl() )
                .setDirectory(new File(workDirectory+File.separator+"projects"+File.separator+project.getId()))
                .setTimeout(1_200_000)
                .call();




    }

    private void createProjectsFolder() throws IOException {
        File projectFoler = new File(workDirectory+File.separator+"projects");
        if(!Files.exists(projectFoler.toPath()))
        {
            FileUtils.forceMkdir(projectFoler);
        }
    }

}
