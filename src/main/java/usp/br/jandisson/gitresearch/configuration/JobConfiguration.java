package usp.br.jandisson.gitresearch.configuration;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
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

@Configuration
@EnableBatchProcessing
public class JobConfiguration {

    @Autowired
    private JobBuilderFactory jobs;

    @Bean
    public Job extractDataJob(
            @Qualifier("downloadProjectRepository") Step download,
            @Qualifier("normalizeProjectDescriptionText") Step normalize,
            @Qualifier("prepareCorpus") Step prepareCorpus,
            @Qualifier("runLda") Step runLda,
            @Qualifier("runSonar") Step runSonar,
            @Qualifier("readSonar") Step readSonar


            ) {
        return this.jobs.get("extractDataJob")
                .start(readSonar)
                //.start(prepareCorpus)
               // .next(normalize)
                .build();
    }





}
