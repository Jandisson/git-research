package usp.br.jandisson.gitresearch.step.lda;


import com.github.chen0040.data.utils.TupleTwo;
import com.github.chen0040.lda.Doc;
import com.github.chen0040.lda.Lda;
import com.github.chen0040.lda.LdaResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import usp.br.jandisson.gitresearch.model.Project;
import usp.br.jandisson.gitresearch.model.ProjectDomain;
import usp.br.jandisson.gitresearch.repositories.ProjectDomainRepository;
import usp.br.jandisson.gitresearch.repositories.ProjectRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


@JobScope
@Component
public class RunLdaTasklet implements Tasklet {




    @Value("${gitresearch.working.folder}")
    private String workDirectory;

    @Autowired
    ProjectDomainRepository projectDomainRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Value("${gitresearch.lda.number_of_topics}")
    private int numberoOfTopics ;

    @Value("${gitresearch.lda.interactions}")
    private int numberOfLdaInteractions ;

    private Map<Integer,String> documentIndex =  new HashMap<Integer,String>();

    Logger logger = LoggerFactory.getLogger(RunLdaTasklet.class);



    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        logger.info("Getting the documents from disk.");
        List<String> docs = this.loadDocuments();
        LdaResult result = this.runLda(docs);

        logger.info("Getting the topic list.");
        for(int topicIndex = 0; topicIndex < numberoOfTopics; ++topicIndex){
            String topicSummary = result.topicSummary(topicIndex);
            this.createTopics(String.valueOf(topicIndex+1),topicSummary);
        }

        logger.info("Associating the documents with the topics.");
        int i=0;
        for(Doc doc : result.documents()) {
            List<TupleTwo<Integer, Double>> topTopics = doc.topTopics(2);
            this.setProjectDomain(documentIndex.get(i),topTopics.get(0)._1()+1,topTopics.get(1)._1()+1);
            i++;
        }

        return RepeatStatus.FINISHED;
    }






    private List<String> loadDocuments() throws IOException {
        List<String> docs = new ArrayList<String>();

        final File folder = new File(String.format("%s%s%s",workDirectory,File.separator,"normalized_descriptions"));
        int i=0;
        for (final File fileEntry : folder.listFiles()) {


            byte[] encoded = Files.readAllBytes(Paths.get(fileEntry.getAbsolutePath()));
            String string = new String(encoded);
            docs.add(string);
            documentIndex.put(i, fileEntry.getName());
            i++;

        }

        return docs;
    }

    private LdaResult runLda( List<String> docs)  {


        Lda method = new Lda();
        method.setTopicCount(numberoOfTopics);
        method.setMaxVocabularySize(200000);
        method.setStemmerEnabled(true);
        method.setMaxSweepCount(numberOfLdaInteractions);

        return  method.fit(docs);
    }

    private void setProjectDomain(String projectId, int idDomain1, int idDomain2) {

        Optional<Project> project= projectRepository.findById(Long.parseLong(projectId));
        Optional<ProjectDomain>  domain1=projectDomainRepository.findById(String.valueOf(idDomain1));
        Optional<ProjectDomain>  domain2=projectDomainRepository.findById(String.valueOf(idDomain2));

        project.get().setDomain1(domain1.get());
        project.get().setDomain2(domain2.get());

        projectRepository.save(project.get());


    }

    private void createTopics(String id, String words){
        ProjectDomain  domain =  new ProjectDomain();
        domain.setId(id);
        domain.setWords(words);
        domain.setGivenName(String.format("topic %s",id));
        projectDomainRepository.save(domain);

    }



}
