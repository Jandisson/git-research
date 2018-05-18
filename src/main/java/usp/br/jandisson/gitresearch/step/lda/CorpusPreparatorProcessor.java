package usp.br.jandisson.gitresearch.step.lda;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import usp.br.jandisson.gitresearch.model.Project;

import java.io.File;
import java.nio.charset.Charset;

public class CorpusPreparatorProcessor implements ItemProcessor<Project,Project> {


    Logger logger = LoggerFactory.getLogger(CorpusPreparatorProcessor.class);
    final int  MINIMUM_DESCRIPTION_LENGH = 300;

    @Value("${gitresearch.working.folder}")
    private String workDirectory;

    @Override
    public Project process(Project project) throws Exception {
        String normalizedDescription = project.getInformation("normalized_description");

        if(normalizedDescription.trim().length() < MINIMUM_DESCRIPTION_LENGH)
        {
            return project;
        }

        File file = new File(String.format("%s%s%s",generateFolderName(project),File.separator,project.getId()));
        createFolder(project);
        logger.info(String.format("Writing corpus file to : %s",file.getAbsolutePath()));
        FileUtils.writeStringToFile(file, normalizedDescription, Charset.defaultCharset());
        return project;
    }

    private void createFolder(Project project)
    {

        File folder = new File(generateFolderName(project));
        if(!folder.exists())
            folder.mkdir();


    }

    private String generateFolderName(Project project)
    {
        String folderName = String.format("%s%s%s",workDirectory,File.separator,"normalized_descriptions");
        return folderName;
    }
}
