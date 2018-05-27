package usp.br.jandisson.gitresearch.configuration.sonar;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import usp.br.jandisson.gitresearch.model.Project;
import usp.br.jandisson.gitresearch.step.sonar.ReadSonarDataProcessor;
import usp.br.jandisson.gitresearch.step.sonar.RunSonarProcessor;


@Configuration
@EnableBatchProcessing
public class ReadSonarData {

    @Autowired
    private StepBuilderFactory steps;

    @Autowired
    ReadSonarDataProcessor processor;



    @Bean
    protected Step readSonar(@Qualifier("getRepositoryItemReader") ItemReader<Project> reader,

                                                   ItemWriter<Project> writer) {
        return steps.get("read_sonar")
                .<Project, Project> chunk(4)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }
}
