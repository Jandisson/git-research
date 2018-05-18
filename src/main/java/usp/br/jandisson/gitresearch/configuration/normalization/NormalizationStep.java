package usp.br.jandisson.gitresearch.configuration.normalization;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import usp.br.jandisson.gitresearch.model.Project;
import usp.br.jandisson.gitresearch.repositories.ProjectRepository;
import usp.br.jandisson.gitresearch.step.normalization.NormalizationProcessor;
import usp.br.jandisson.gitresearch.step.common.ProjectWriter;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableBatchProcessing
public class NormalizationStep {


    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    private StepBuilderFactory steps;

    @Bean
    protected ItemWriter<Project> getNormalizedDescriptionWriter(){
            return new ProjectWriter();
    }

    @Bean
    protected ItemReader<Project> getRepositoryItemReader() {
        RepositoryItemReader<Project> reader = new RepositoryItemReader<Project>();
        reader.setRepository(projectRepository);
        reader.setMethodName("findAll");
        Map<String,Sort.Direction> sorts = new HashMap<String,Sort.Direction>();
        sorts.put("id", Sort.Direction.ASC);

        reader.setSort(sorts);
        return reader;
    }

    @Bean
    protected ItemProcessor<Project,Project> getNormalizationItemProcessor(){
            return new NormalizationProcessor();
    }

    @Bean
    protected Step normalizeProjectDescriptionText(@Qualifier("getRepositoryItemReader") ItemReader<Project> reader,
                                                   @Qualifier("getNormalizationItemProcessor")ItemProcessor<Project, Project> processor,
                                                   ItemWriter<Project> writer) {
        return steps.get("readme_normalization")
                .<Project, Project> chunk(10)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }
}
