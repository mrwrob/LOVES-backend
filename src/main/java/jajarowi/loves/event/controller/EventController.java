package jajarowi.loves.event.controller;

//import com.auth0.jwt.exceptions.JWTDecodeException;

import jajarowi.loves.assignment.entity.Assignment;
import jajarowi.loves.assignment.service.AssignmentService;
import jajarowi.loves.event.dto.*;
import jajarowi.loves.event.entity.Event;
import jajarowi.loves.event.service.EventService;
import jajarowi.loves.label.service.LabelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping()
public class EventController {

    private final EventService eventService;
    private final LabelService labelService;
    private final AssignmentService assignmentService;


    public EventController(EventService eventService, LabelService labelService, AssignmentService assignmentService) {
        this.eventService = eventService;
        this.labelService = labelService;
        this.assignmentService = assignmentService;
    }

    @GetMapping("/projects/{projectId}/assignments/{assignmentId}/events")
    ResponseEntity<GetEventsResponse> getEventsByProjectIdAndAssignmentId(@PathVariable long projectId, @PathVariable long assignmentId){
        return ResponseEntity.ok(GetEventsResponse.entityToDtoMapper().apply(eventService.findAllByAssignmentProjectIdAndAssignmentId(projectId, assignmentId)));
    }


    @GetMapping("/assignments/{assignmentId}/events")
    public ResponseEntity<GetEventsResponse> getEventsByAssignmentId(@PathVariable long assignmentId) {
        return ResponseEntity.ok(GetEventsResponse.entityToDtoMapper().apply(eventService.findAllByAssignmentId(assignmentId)));
    }

    @PostMapping("/assignments/{assignmentId}/events")
    public ResponseEntity<Void> create(@RequestBody CreateEventsRequest request, @PathVariable long assignmentId) {
        request.setAssignmentId(assignmentId);
        Optional<Assignment> assignment = assignmentService.find(request.getAssignmentId());
        if (assignment.isPresent()) {
            assignment.get().getEventList().forEach(event -> {
                if(request.getEventList().stream().map(CreateEventsRequest.Event::getId).noneMatch(x -> x.equals(event.getId()))) {
                    eventService.delete(event.getId());
                }
            });

            request.getEventList().forEach(event -> {
                event.setAssignmentId(assignmentId);
                if(event.getId() == 0) {
                    eventService.create(CreateEventRequest.dtoToEntityMapper(id -> labelService.find(id).orElseThrow(),
                            id -> assignmentService.find(id).orElseThrow()).apply(CreateEventsRequest.toCreateEventRequest(event)));
                }
            });

            assignmentService.updateModificationTIme(assignment.get());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

}
