package jajarowi.loves.event.service;

import jajarowi.loves.assignment.entity.Assignment;
import jajarowi.loves.assignment.service.AssignmentService;
import jajarowi.loves.event.entity.Event;
import jajarowi.loves.event.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    private final EventRepository repository;
    private final AssignmentService assignmentService;

    @Autowired
    public EventService(EventRepository repository, AssignmentService assignmentService) {
        this.repository = repository;
        this.assignmentService = assignmentService;
    }

    public Optional<Event> find(long id) {
        return repository.findById(id);
    }

    public List<Event> findAll() {
        return repository.findAll();
    }

    public List<Event> findAllByAssignmentId(long assignmentId) {
        return repository.findAllByAssignmentId(assignmentId);
    }

    public Optional<Event> findByAssignmentId(long assignmentId, long id){
        return repository.findByAssignmentIdAndId(assignmentId, id);
    }

    public List<Event> findAllByAssignmentProjectIdAndAssignmentId(long projectId, long assignmentId){
        return repository.findAllByAssignmentProjectIdAndAssignmentId(projectId, assignmentId);
    }

    public Optional<Event> findByAssignmentProjectIdAndAssignmentIdAndId(long projectId, long assignmentId, long id){
        return repository.findByAssignmentProjectIdAndAssignmentIdAndId(projectId, assignmentId, id);
    }

    @Transactional
    public Event create(Event event) {
        return repository.save(event);
    }

    @Transactional
    public void update(Event event) {
        repository.save(event);
    }

    public void updateByAssignment(List<Event> events, Long assignmentId) {
        Optional<Assignment> assignment = assignmentService.find(assignmentId);
        assignment.ifPresent(value -> value.setEventList(events));
    }

    @Transactional
    public void delete(Event event) {
        repository.delete(event);
    }

    @Transactional
    public void delete(long id) {
        repository.deleteById(id);
    }
}
