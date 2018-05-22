package usp.br.jandisson.gitresearch.repositories;

        import org.springframework.data.repository.CrudRepository;
        import org.springframework.data.repository.PagingAndSortingRepository;
        import usp.br.jandisson.gitresearch.model.Project;
        import usp.br.jandisson.gitresearch.model.ProjectDomain;

public interface ProjectDomainRepository extends PagingAndSortingRepository<ProjectDomain,String> {
}

