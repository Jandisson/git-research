package usp.br.jandisson.gitresearch.configuration.sonar;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import usp.br.jandisson.gitresearch.model.Project;
import usp.br.jandisson.gitresearch.step.download.DownloadProcessor;
import usp.br.jandisson.gitresearch.step.sonar.RunSonarProcessor;


@Configuration
@EnableBatchProcessing
public class RunSonarStep {

    @Autowired
    private StepBuilderFactory steps;

    @Autowired
    RunSonarProcessor processor;

    @Bean
    public TaskExecutor taskExecutor(){
        
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
    taskExecutor.setCorePoolSize(16);
    taskExecutor.setThreadNamePrefix("GIT RESEARCH-");
    return taskExecutor;

    }

    @Bean
    protected Step runSonar(@Qualifier("getRepositoryItemReader") ItemReader<Project> reader,
                                             TaskExecutor taskExecutor,
                                                   ItemWriter<Project> writer) {
        return steps.get("run_sonar")
                .<Project, Project> chunk(1000)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .taskExecutor(taskExecutor)
                .throttleLimit(16)
                .build();
    }
}
