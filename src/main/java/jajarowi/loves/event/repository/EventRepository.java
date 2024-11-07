package jajarowi.loves.event.repository;

import jajarowi.loves.assignment.entity.Assignment;
import jajarowi.loves.event.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findAllByAssignment(Assignment assignment);
    List<Event> findAllByAssignmentId(long assignmentId);
    List<Event> findAllByAssignmentProjectIdAndAssignmentId(long projectId, long assignmentId);
    Optional<Event> findByAssignmentIdAndId(long assignmentId, long id);
    Optional<Event> findByAssignmentProjectIdAndAssignmentIdAndId(long projectId, long assignmentId, long id);
}
