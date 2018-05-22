package usp.br.jandisson.gitresearch.step.common;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;
import usp.br.jandisson.gitresearch.model.Project;

@Component
public class FileProjectReader extends FlatFileItemReader<Project> {




    public FileProjectReader(@Value("${gitresearch.projects.file}") String projectsFile)
    {
        this.setLinesToSkip(1);
        this.setResource(new FileSystemResource(projectsFile));
        DefaultLineMapper<Project> lineMapper = new DefaultLineMapper<Project>();
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setDelimiter("\t");
        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(new ProjectSetMapper());
        this.setLineMapper(lineMapper);
    }



}
