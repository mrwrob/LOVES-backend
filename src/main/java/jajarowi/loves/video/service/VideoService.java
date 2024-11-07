package jajarowi.loves.video.service;

import jajarowi.loves.project.entity.Project;
import jajarowi.loves.video.entity.Video;
import jajarowi.loves.video.repository.VideoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class VideoService {

    private final VideoRepository repository;
    private final Validator validator;

    @Autowired
    public VideoService(VideoRepository repository, Validator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    @Transactional
    public Optional<Video> find(long id) {
        return repository.findById(id);
    }

    @Transactional
    public List<Video> findAll() {
        return repository.findAll();
    }

    public void storeVideo(MultipartFile file, long id) {
        String uploadsDir = "uploads/";
        try {
            Files.copy(file.getInputStream(), Paths.get(uploadsDir).resolve(id + "_" + file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
        }
        catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

    @Transactional
    public List<Video> findAllByVideoSetIdAndVideoSetProjectId(long videoSetId, long projectId){
        return repository.findAllByVideoSetIdAndVideoSetProjectId(videoSetId, projectId);
    }

    @Transactional
    public Optional<Video> findByVideoSetIdAndVideoSetProjectIdAndId(long videoSetId, long projectId, long id){
        return repository.findByVideoSetIdAndVideoSetProjectIdAndId(videoSetId,projectId,id);
    }

    @Transactional
    public List<Video> findAllByVideoSetAssignmentListIdAndVideoSetId(long assignmentId, long videoSetId){
        return repository.findAllByVideoSetAssignmentListIdAndVideoSetId(assignmentId,videoSetId);
    }

    @Transactional
    public Optional<Video> findByVideoSetAssignmentListIdAndVideoSetIdAndId(long assignmentId, long videoSetId, long id){
        return repository.findByVideoSetAssignmentListIdAndVideoSetIdAndId(assignmentId, videoSetId, id);
    }

    @Transactional
    public Video create(Video video) {
        Set<ConstraintViolation<Video>> violationSet = validator.validate(video);
        violationSet.forEach(v -> log.info(v.getPropertyPath() +" "+ v.getMessage()));
        if(video.getVideoSet().getVideoList().size()<video.getVideoSet().getMaxVideoCount())
            return repository.save(video);
        else
            return null;
    }

    @Transactional
    public void update(Video video) {
        repository.save(video);
    }

}
