package usp.br.jandisson.gitresearch.step.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import usp.br.jandisson.gitresearch.model.Project;
import usp.br.jandisson.gitresearch.repositories.ProjectRepository;

import java.util.List;

public class ProjectWriter implements ItemWriter<Project> {

    Logger logger = LoggerFactory.getLogger(ProjectWriter.class);

    @Autowired
    ProjectRepository projectRepository;

    @Override
    public void write(List<? extends Project> list) throws Exception {
        for(Project project: list)
        {
            projectRepository.save(project);
            logger.info("Writing project: "+project.getId());
        }
    }
}
