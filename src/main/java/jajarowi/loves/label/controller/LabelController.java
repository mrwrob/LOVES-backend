package jajarowi.loves.label.controller;

import jajarowi.loves.label.dto.CreateLabelRequest;
import jajarowi.loves.label.dto.GetLabelsResponse;
import jajarowi.loves.label.entity.Label;
import jajarowi.loves.label.service.LabelService;
import jajarowi.loves.project.entity.Project;
import jajarowi.loves.project.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Optional;

@RestController
@RequestMapping()
public class LabelController {

    private final LabelService labelService;
    private final ProjectService projectService;


    @Autowired
    public LabelController(LabelService labelService, ProjectService projectService) {
        this.labelService = labelService;
        this.projectService = projectService;
    }

    @GetMapping("/assignments/{assignmentId}/labels")   //endpoint for assignee
    public ResponseEntity<GetLabelsResponse> getLabelsByAssignment(@PathVariable long assignmentId){
        return ResponseEntity.ok(GetLabelsResponse.entityToDtoMapper().apply(
                labelService.findAllByAssignmentId(assignmentId))
        );
    }

    @GetMapping("projects/{projectId}/labels")
    public ResponseEntity<GetLabelsResponse> getLabelsByProject(@PathVariable long projectId) {
        return ResponseEntity.ok(GetLabelsResponse.entityToDtoMapper().apply(labelService.findAllByProjectId(projectId)));
    }


    @PostMapping("projects/{projectId}/labels")
    public ResponseEntity<Void> create(@RequestBody CreateLabelRequest request, @PathVariable long projectId) {
            request.setProjectId(projectId);
            Optional<Project> project = projectService.find(projectId);
            if (project.isPresent()) {
                Label label = labelService.create(CreateLabelRequest.dtoToEntityMapper(id -> projectService.find(id).orElseThrow()).apply(request));
                projectService.updateModificationTime(project.get());
                return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(label.getId()).toUri()).build();
            }else return ResponseEntity.badRequest().build();

    }

    @DeleteMapping("projects/{projectId}/labels/{id}")
    public ResponseEntity<Void> delete(@PathVariable long projectId, @PathVariable long id) {
        Optional<Label> label = labelService.findByProjectIdAndId(projectId, id);
        Optional<Project> project = projectService.find(projectId);
        if (label.isPresent() && project.isPresent()) {
            labelService.delete(label.get());
            projectService.updateModificationTime(project.get());
            return ResponseEntity.ok().build();
        } else return ResponseEntity.notFound().build();
    }
}
