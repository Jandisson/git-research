package usp.br.jandisson.gitresearch.configuration.lda;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import usp.br.jandisson.gitresearch.step.lda.RunLdaTasklet;

@Configuration
@EnableBatchProcessing
public class RunLdaStep {

    @Autowired
    private StepBuilderFactory steps;

    @Autowired
    private RunLdaTasklet runLdaTasklet;


   @Bean
    protected Step runLda() {
        return steps.get("run_lda")
                .tasklet(runLdaTasklet)
                .build();
    }


}
