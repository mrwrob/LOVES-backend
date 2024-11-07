package jajarowi.loves.videoSet.repository;

import jajarowi.loves.assignment.entity.Assignment;
import jajarowi.loves.project.entity.Project;
import jajarowi.loves.videoSet.entity.VideoSet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VideoSetRepository extends JpaRepository<VideoSet, Long> {
    List<VideoSet> findAllByProject(Project project);
    List<VideoSet> findAllByProjectId(long id);
    Optional<VideoSet> findByProjectIdAndId(long projectId, long id);
    Optional<VideoSet> findByAssignmentListId(long assignmentId);
}
