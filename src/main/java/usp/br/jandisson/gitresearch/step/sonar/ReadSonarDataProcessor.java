package usp.br.jandisson.gitresearch.step.sonar;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpHost;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonarsource.scanner.api.EmbeddedScanner;
import org.sonarsource.scanner.api.LogOutput;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import usp.br.jandisson.gitresearch.model.Project;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Component
public class ReadSonarDataProcessor implements ItemProcessor<Project,Project> {



    @Value("${gitresearch.working.sonar.url}")
    private String sonarUrl;


    Logger logger = LoggerFactory.getLogger(ReadSonarDataProcessor.class);


    @Override
    public Project process(Project project) throws Exception {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        //map.add("component",String.format("Project%s",project.getId()));
        map.add("component",String.format("Project"));
        map.add("metrics",String.format(String.join(",",metricts)));
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        try {
            ResponseEntity<Measures> response
                    = restTemplate.exchange(sonarUrl + "/api/measures/search_history", HttpMethod.POST, request, Measures.class);

            for (Measure measure : response.getBody().getMeasures()) {
                int i = 1;
                for (History history : measure.getHistory()) {
                    project.addInformation(measure.getMetric() + i, history.getValue());
                    i++;
                }
            }
        }catch(Exception e)
        {
            logger.error(String.format("Error in reading information from project %s. Error: %s",project.getId(),e.getMessage()));
        }

        return project;
    }

    private String metricts[] = {
            "sqale_debt_ratio",
            "ncloc",
            "code_smells",
            "sqale_rating",
            "duplicated_lines_density",
            "duplicated_lines",
            "duplicated_blocks",
            "duplicated_files",
            "statements",
            "functions",
            "classes",
            "files",
            "directories",
            "comment_lines",
            "comment_lines_density",
            "complexity",
            "cognitive_complexity",
            "violations"
    };




}
