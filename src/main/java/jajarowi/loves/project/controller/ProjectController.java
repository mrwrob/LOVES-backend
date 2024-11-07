package jajarowi.loves.project.controller;

//import com.auth0.jwt.exceptions.JWTDecodeException;

import jajarowi.loves.project.dto.CreateProjectRequest;
import jajarowi.loves.project.dto.GetProjectResponse;
import jajarowi.loves.project.dto.GetProjectsResponse;
import jajarowi.loves.project.dto.UpdateProjectRequest;
import jajarowi.loves.project.entity.Project;
import jajarowi.loves.project.service.ProjectService;
import jajarowi.loves.scientist.service.ScientistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("projects")
public class ProjectController {

    private final ProjectService projectService;
    private final ScientistService scientistService;


    @Autowired
    public ProjectController(ProjectService projectService, ScientistService scientistService) {
        this.projectService = projectService;
        this.scientistService = scientistService;

    }

    @GetMapping
    public ResponseEntity<GetProjectsResponse> getProjects(Authentication authentication) {
        return ResponseEntity.ok(GetProjectsResponse.entityToDtoMapper().apply(projectService.findAllByScientistLogin(authentication.getName())));
    }


    @GetMapping("{id}")
    public ResponseEntity<GetProjectResponse> getProject(@PathVariable long id) {
        return projectService.find(id).map(value -> ResponseEntity.ok(GetProjectResponse.entityToDtoMapper().apply(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PostMapping()
    public ResponseEntity<Void> create(@RequestBody CreateProjectRequest request, Authentication authentication) {
        request.setScientistName(authentication.getName());
        Project project = projectService.create(CreateProjectRequest.dtoToEntityMapper(name -> scientistService.find(name).orElseThrow()).apply(request));
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(project.getId()).toUri()).build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> update(@RequestBody UpdateProjectRequest request, @PathVariable long id) {
        Optional<Project> project = projectService.find(id);
        if (project.isPresent()) {
            projectService.update(project.get(), UpdateProjectRequest.dtoToEntityMapper(id).apply(request));
            return ResponseEntity.ok().build();
        } else return ResponseEntity.notFound().build();

    }


    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        Optional<Project> project = projectService.find(id);
        if (project.isPresent()) {
            projectService.delete(project.get());
            return ResponseEntity.ok().build();
        } else return ResponseEntity.notFound().build();
    }
}
