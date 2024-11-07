package jajarowi.loves.project.repository;


import jajarowi.loves.project.entity.Project;
import jajarowi.loves.scientist.entity.Scientist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findAllByScientistLogin(String login);
}
