package usp.br.jandisson.gitresearch.configuration.download;

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
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import usp.br.jandisson.gitresearch.model.Project;
import usp.br.jandisson.gitresearch.step.download.DownloadProcessor;


@Configuration
@EnableBatchProcessing
public class DownloadStep {

    @Autowired
    private StepBuilderFactory steps;

    @Autowired
    @Qualifier("getDownloadProcessor")
    ItemProcessor<Project, Project> processor;

    @Bean
    public TaskExecutor taskExecutor(){
        SimpleAsyncTaskExecutor executor= new SimpleAsyncTaskExecutor("GIT RESEARCH ");
        executor.setConcurrencyLimit(30);
        return executor;
    }

    @Bean
    public ItemProcessor<Project,Project> getDownloadProcessor(){
        return new DownloadProcessor();
    }

    @Bean
    protected Step downloadProjectRepository(@Qualifier("fileProjectReader") ItemReader<Project> reader,
                                             TaskExecutor taskExecutor,
                                                   ItemWriter<Project> writer) {
        return steps.get("download_repository")
                .<Project, Project> chunk(10)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .taskExecutor(taskExecutor)
                .throttleLimit(30)
                .build();
    }
}
