package usp.br.jandisson.gitresearch.step.normalization;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import usp.br.jandisson.gitresearch.model.Information;
import usp.br.jandisson.gitresearch.model.Project;
import usp.br.jandisson.gitresearch.repositories.ProjectRepository;
import usp.br.jandisson.gitresearch.text.normalization.DescriptionNormalizer;
import usp.br.jandisson.gitresearch.text.normalization.IDescriptionNormalizer;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class NormalizationProcessor implements ItemProcessor<Project,Project> {

    IDescriptionNormalizer normalizer = new DescriptionNormalizer();

    private String defaultReadmeList[] ={
            "readme.md",
            "readme.html",
            "readme.txt"
    };

    @Override
    public Project process(Project project) throws Exception {

        String normalizedDescription = "";
        File readme = this.findReadmeFile(project);

        if(readme!= null) {
            normalizedDescription = normalizer.normalize(readme);
        }
        project.addInformation("normalized_description",normalizedDescription);
        return project;
    }

    private File findReadmeFile(Project project)
    {
        for(String readmeFileName: defaultReadmeList)
        {
            File file = new File(String.format("gitRepositoryPath%s%s",File.pathSeparator,readmeFileName));
            if(file.exists())
            {
                return file;
            }
        }

        String gitRepositoryPath = project.getInformation("git_repository_path");
        Collection<File> files = FileUtils.listFiles(new File(gitRepositoryPath), FileFilterUtils.prefixFileFilter("README"),null);
        for(File file : files)
        {
            return file;
        }
        return null;


    }



}
