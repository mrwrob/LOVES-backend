package jajarowi.loves.label.repository;


import jajarowi.loves.assignment.entity.Assignment;
import jajarowi.loves.label.entity.Label;
import jajarowi.loves.project.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LabelRepository extends JpaRepository<Label, Long> {
    List<Label> findAllByProjectId(Long projectId);
    Optional<Label> findByProjectIdAndId(Long projectId, Long id);
    List<Label> findAllByProjectAssignmentListId(Long assignmentId);
    List<Label> findAllByShortcutAndProjectId(String shortcut, long projectId);
}
