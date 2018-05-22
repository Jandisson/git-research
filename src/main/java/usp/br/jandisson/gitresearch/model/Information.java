package usp.br.jandisson.gitresearch.model;

import javax.persistence.*;

@Entity
public class Information {

    @Id @GeneratedValue(strategy=GenerationType.TABLE)
    private Long id;
    @Column(columnDefinition="clob")
    @Lob
    private String value;
    private String name;
    @ManyToOne
    @JoinColumn(name="project_id")
    private Project project;

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
