package usp.br.jandisson.gitresearch.step.common;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;
import usp.br.jandisson.gitresearch.model.Project;

public class ProjectSetMapper implements FieldSetMapper<Project> {

    @Override
    public Project mapFieldSet(FieldSet fieldSet) throws BindException {
        Project project =  new Project();
        project.setId(fieldSet.readLong(0));
        project.setUrl(fieldSet.readString(1));
        return project;
    }
}
