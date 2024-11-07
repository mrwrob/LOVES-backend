package jajarowi.loves.videoSet.controller;

//import com.auth0.jwt.exceptions.JWTDecodeException;

import jajarowi.loves.project.entity.Project;
import jajarowi.loves.project.service.ProjectService;
import jajarowi.loves.video.entity.Video;
import jajarowi.loves.video.service.VideoService;
import jajarowi.loves.videoSet.dto.CreateVideoSetRequest;
import jajarowi.loves.videoSet.dto.GetVideoSetResponse;
import jajarowi.loves.videoSet.dto.GetVideoSetsResponse;
import jajarowi.loves.videoSet.dto.UpdateVideoSetRequest;
import jajarowi.loves.videoSet.entity.VideoSet;
import jajarowi.loves.videoSet.service.VideoSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Optional;

@RestController
@RequestMapping()
public class VideoSetController {

    private final VideoSetService videoSetService;
    private final ProjectService projectService;
    private final VideoService videoService;

    @Autowired
    public VideoSetController(VideoSetService videoSetService, ProjectService projectService, VideoService videoService) {

        this.videoSetService = videoSetService;
        this.projectService = projectService;
        this.videoService = videoService;
    }

    @GetMapping("/projects/{projectId}/videosets")
    public ResponseEntity<GetVideoSetsResponse> getVideoSets(@PathVariable long projectId) {
        return ResponseEntity.ok(GetVideoSetsResponse.entityToDtoMapper().apply(videoSetService.findAllByProjectId(projectId)));
    }


    @GetMapping("/projects/{projectId}/videosets/{id}")
    public ResponseEntity<GetVideoSetResponse> getVideoSet(@PathVariable long projectId, @PathVariable long id) {
        return videoSetService.findByProjectIdAndId(projectId, id).map(set -> ResponseEntity.ok(GetVideoSetResponse.entityToDtoMapper().apply(set)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/projects/{projectId}/videosets")
    public ResponseEntity<Void> create(@RequestBody CreateVideoSetRequest request, @PathVariable long projectId) {
        request.setProjectId(projectId);
        Optional<Project> project = projectService.find(request.getProjectId());
        if (project.isPresent()) {
            VideoSet videoSet = videoSetService.create(CreateVideoSetRequest.dtoToEntityMapper(
                    v -> projectService.find(v).orElseThrow()).apply(request));
            projectService.updateModificationTime(project.get());
            return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(videoSet.getId()).toUri()).build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/projects/{projectId}/videosets/{id}")
    public ResponseEntity<Void> update(@RequestBody UpdateVideoSetRequest request, @PathVariable long id, @PathVariable long projectId) {

        Optional<VideoSet> videoSet = videoSetService.find(id);
        Optional<Project> project = projectService.find(projectId);
        if (videoSet.isPresent() && project.isPresent()) {
            videoSetService.update(videoSet.get(),UpdateVideoSetRequest.dtoToEntityMapper().apply(request));
            projectService.updateModificationTime(project.get());
            return ResponseEntity.ok().build();
        } else return ResponseEntity.notFound().build();


    }

    @DeleteMapping("/projects/{projectId}/videosets/{id}")
    public ResponseEntity<Void> delete(@PathVariable long projectId, @PathVariable long id) {

        Optional<VideoSet> videoSet = videoSetService.findByProjectIdAndId(projectId, id);
        Optional<Project> project = projectService.find(projectId);
        if (videoSet.isPresent() && project.isPresent()) {
            videoSetService.delete(videoSet.get());
            projectService.updateModificationTime(project.get());
            return ResponseEntity.ok().build();
        } else return ResponseEntity.notFound().build();
    }

}
