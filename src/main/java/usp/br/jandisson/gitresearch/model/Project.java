package usp.br.jandisson.gitresearch.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Project {

    @Id
    private Long id;
    private String url;


    @ManyToOne
    @JoinColumn(name="domain1_name")
    private ProjectDomain domain1;

    @ManyToOne
    @JoinColumn(name="domain2_name")
    private ProjectDomain domain2;

    public ProjectDomain getDomain2() {
        return domain2;
    }

    public void setDomain2(ProjectDomain domain2) {
        this.domain2 = domain2;
    }



    public ProjectDomain getDomain1() {
        return domain1;
    }

    public void setDomain1(ProjectDomain domain1) {
        this.domain1 = domain1;
    }

    public List<Information> getInformations() {
        if(informations == null)
             informations =  new ArrayList<Information>();

        return informations;
    }

    public void addInformation(String name, String value)
    {
        Information information = new Information();
        information.setName(name);
        information.setValue(value);
        information.setProject(this);
        this.getInformations().add(information);
    }


    public String getInformation(String informationName)
    {
        for(Information information: this.getInformations())
        {
            if(information.getName().equals(informationName))
                return information.getValue();
        }
        return null;
    }

    public void setInformations(List<Information> informations) {
        this.informations = informations;
    }



    @OneToMany(mappedBy = "project", targetEntity = Information.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Information> informations;





    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
