package jajarowi.loves.video.repository;

import jajarowi.loves.video.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {
    Video findByName(String name);
    List<Video> findAllByVideoSetId(long videoSetId);
    List<Video> findAllByVideoSetIdAndVideoSetProjectId(long videoSetId, long projectId);
    Optional<Video> findByVideoSetIdAndVideoSetProjectIdAndId(long videoSetId, long projectId, long id);
    List<Video> findAllByVideoSetAssignmentListIdAndVideoSetId(long assignmentId, long videoSetId);
    Optional<Video> findByVideoSetAssignmentListIdAndVideoSetIdAndId(long assignmentId, long videoSetId, long id);
    Boolean existsByName(String name);
}
