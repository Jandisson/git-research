package usp.br.jandisson.gitresearch.configuration.lda;

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
import usp.br.jandisson.gitresearch.model.Project;
import usp.br.jandisson.gitresearch.step.lda.CorpusPreparatorProcessor;

@Configuration
@EnableBatchProcessing
public class CorpusPreparatorStep {

    @Autowired
    private StepBuilderFactory steps;

    @Bean
    protected ItemProcessor<Project,Project> getCorpusPreparatorItemProcessor(){
        return new CorpusPreparatorProcessor();
    }

    @Bean
    protected Step prepareCorpus(@Qualifier("getRepositoryItemReader") ItemReader<Project> reader,
                                                   @Qualifier("getCorpusPreparatorItemProcessor")ItemProcessor<Project, Project> processor,
                                                   ItemWriter<Project> writer) {
        return steps.get("corpus_preparator")
                .<Project, Project> chunk(10)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }
}
