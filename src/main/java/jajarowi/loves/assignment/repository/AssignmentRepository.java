package jajarowi.loves.assignment.repository;

import jajarowi.loves.assignee.entity.Assignee;
import jajarowi.loves.assignment.entity.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    List<Assignment> findAllByAssignee(Assignee assignee);
    List<Assignment> findAllByProjectId(Long id);
    Optional<Assignment> findByProjectIdAndId(Long projectId, Long id);
    List<Assignment> findAllByAssigneeLogin(String login);
}
