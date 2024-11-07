package jajarowi.loves.assignment.controller;

//

import jajarowi.loves.assignee.service.AssigneeService;
import jajarowi.loves.assignment.dto.CreateAssignmentRequest;
import jajarowi.loves.assignment.dto.GetAssignmentResponse;
import jajarowi.loves.assignment.dto.GetAssignmentsResponse;
import jajarowi.loves.assignment.dto.UpdateAssignmentRequest;
import jajarowi.loves.assignment.entity.Assignment;
import jajarowi.loves.assignment.service.AssignmentService;
import jajarowi.loves.project.entity.Project;
import jajarowi.loves.project.service.ProjectService;
import jajarowi.loves.videoSet.service.VideoSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping()
public class AssignmentController {

    private final AssignmentService assignmentService;
    private final AssigneeService assigneeService;
    private final ProjectService projectService;
    private final VideoSetService videoSetService;


    @Autowired
    public AssignmentController(AssignmentService assignmentService, AssigneeService assigneeService, ProjectService projectService, VideoSetService videoSetService) {
        this.assignmentService = assignmentService;
        this.assigneeService = assigneeService;
        this.projectService = projectService;
        this.videoSetService = videoSetService;

    }

    @GetMapping("/assignments")         //endpoint for assignee
    public ResponseEntity<GetAssignmentsResponse> getAssignments(Authentication authentication) {
        return ResponseEntity.ok(GetAssignmentsResponse.entityToDtoMapper().apply(
                assignmentService.findAllByAssigneeLogin(authentication.getName()).stream().filter(a -> !a.isFinished()).collect(Collectors.toList())
        ));
    }


    @GetMapping("/assignments/{id}")    //endpoint for assignee
    public ResponseEntity<GetAssignmentResponse> getAssignment(@PathVariable long id) {
        Optional<Assignment> assignment = assignmentService.find(id);
        if(assignment.isPresent()){
            return assignment.filter(assignmentService::isAccessible)
                    .map(value -> ResponseEntity.ok(GetAssignmentResponse.entityToDtoMapper().apply(value)))
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.FORBIDDEN).build());
        }
        return ResponseEntity.notFound().build();
    }


    @GetMapping("/projects/{projectId}/assignments")    //endpoint for scientist
    public ResponseEntity<GetAssignmentsResponse> getAssignmentsByProject(@PathVariable long projectId) {
        return ResponseEntity.ok(GetAssignmentsResponse.entityToDtoMapper().apply(assignmentService.findAllByProjectId(projectId)));
    }

    @GetMapping("/projects/{projectId}/assignments/{id}")   //endpoint for scientist
    public ResponseEntity<GetAssignmentResponse> getAssignmentByProjectAndId(@PathVariable long projectId, @PathVariable long id) {
        return assignmentService.findByProjectIdAndId(projectId, id)
                .map(value -> ResponseEntity.ok(GetAssignmentResponse.entityToDtoMapper().apply(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PostMapping("/projects/{projectId}/assignments")   //endpoint for scientist
    public ResponseEntity<Void> create(@RequestBody CreateAssignmentRequest request, @PathVariable long projectId) {

        request.setProjectId(projectId);

        Optional<Project> project = projectService.find(request.getProjectId());

        if (project.isPresent()) {
            assignmentService.create(CreateAssignmentRequest.dtoToEntityMapper(
                    value -> projectService.find(value).orElseThrow(),
                    value -> assigneeService.find(value).orElseThrow(),
                    value -> videoSetService.find(value).orElseThrow()
            ).apply(request));
            projectService.updateModificationTime(project.get());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }

    @PostMapping("/projects/{projectId}/assignments/many")
    public ResponseEntity<List<Long>> createMany(@RequestBody List<CreateAssignmentRequest> request, @PathVariable long projectId) {

        List<Assignment> assignments = new LinkedList<>();
        Optional<Project> project = projectService.find(projectId);

        if (project.isPresent()) {
            for (CreateAssignmentRequest r : request) {
                r.setProjectId(projectId);
                if (projectService.find(r.getProjectId()).isEmpty())
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
                assignments.add(CreateAssignmentRequest.dtoToEntityMapper(
                        value -> projectService.find(value).orElseThrow(),
                        value -> assigneeService.find(value).orElseThrow(),
                        value -> videoSetService.find(value).orElseThrow()
                ).apply(r));
            }

            assignments.forEach(assignmentService::create);
            projectService.updateModificationTime(project.get());
            return ResponseEntity.ok(assignments.stream().map(Assignment::getId).collect(Collectors.toList()));
        } else return ResponseEntity.notFound().build();
    }


    @PutMapping("/projects/{projectId}/assignments/{id}")   //endpoint for scientist
    public ResponseEntity<Void> update(@RequestBody UpdateAssignmentRequest request, @PathVariable long projectId, @PathVariable long id) {
        Optional<Assignment> assignment = assignmentService.findByProjectIdAndId(projectId, id);
        Optional<Project> project = projectService.find(projectId);
        if (assignment.isPresent() && project.isPresent()) {
            assignmentService.update(assignment.get(), UpdateAssignmentRequest.dtoToEntityMapper(id).apply(request));
            projectService.updateModificationTime(project.get());
            return ResponseEntity.accepted().build();
        } else return ResponseEntity.notFound().build();
    }

    @PutMapping("/assignments/{id}/finish")   //endpoint for scientist
    public ResponseEntity<Void> finishAssignment(@PathVariable long id) {
        Optional<Assignment> assignment = assignmentService.find(id);
        if (assignment.isPresent()) {
            Project project = assignment.get().getProject();
            assignmentService.finish(assignment.get());
            projectService.updateModificationTime(project);
            return ResponseEntity.accepted().build();
        } else return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/projects/{projectId}/assignments/{id}")    //endpoint for scientist
    public ResponseEntity<Void> delete(@PathVariable long id, @PathVariable long projectId) {
        Optional<Assignment> assignment = assignmentService.find(id);
        Optional<Project> project = projectService.find(projectId);

        if (assignment.isPresent() && project.isPresent()) {
            assignmentService.delete(assignment.get());
            projectService.updateModificationTime(project.get());
            return ResponseEntity.accepted().build();
        } else return ResponseEntity.notFound().build();
    }

}
