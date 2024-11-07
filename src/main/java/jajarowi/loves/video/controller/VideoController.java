package jajarowi.loves.video.controller;

import jajarowi.loves.video.entity.Video;
import jajarowi.loves.video.service.VideoService;
import jajarowi.loves.videoSet.entity.VideoSet;
import jajarowi.loves.videoSet.service.VideoSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.MalformedURLException;
import java.util.Optional;

@RestController
@RequestMapping()
public class VideoController {

    private final VideoService videoService;
    private final VideoSetService videoSetService;

    @Autowired
    public VideoController(VideoService videoService, VideoSetService videoSetService) {
        this.videoService = videoService;
        this.videoSetService = videoSetService;
    }


    @GetMapping("assignments/{assignmentId}/videosets/{videoSetId}/videos/{id}")
    public ResponseEntity<Resource> getVideoByVideoSetAssignmentListIdAndVideoSetIdAndId(@PathVariable long assignmentId, @PathVariable long videoSetId, @PathVariable long id) throws MalformedURLException {
        Optional<Video> video = videoService.findByVideoSetAssignmentListIdAndVideoSetIdAndId(assignmentId, videoSetId, id);
        return video.<ResponseEntity<Resource>>map(value -> ResponseEntity.ok().body(new ByteArrayResource(value.getData()))).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PostMapping("/projects/{projectId}/videosets/{videoSetId}/file")
    public ResponseEntity<Void> create(@RequestBody MultipartFile file, @PathVariable long projectId, @PathVariable long videoSetId){
        Video video = new Video();
        video.setName(file.getName());
        try {
            VideoSet videoSet = videoSetService.findByProjectIdAndId(projectId,videoSetId).orElseThrow();
            if(videoSet.getVideoList().size()<videoSet.getMaxVideoCount()) {
                video.setVideoSet(videoSet);
                video.setData(file.getBytes());
                videoService.create(video);
                return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(video.getId()).toUri()).build();
            }else
                return ResponseEntity.badRequest().build();
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

}
