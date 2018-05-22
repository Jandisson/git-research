package usp.br.jandisson.gitresearch.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import usp.br.jandisson.gitresearch.model.Project;

public interface ProjectRepository extends PagingAndSortingRepository<Project,Long> {
}
