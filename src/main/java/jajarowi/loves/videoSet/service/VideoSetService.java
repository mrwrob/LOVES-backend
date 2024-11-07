package jajarowi.loves.videoSet.service;

import jajarowi.loves.project.entity.Project;
import jajarowi.loves.project.service.ProjectService;
import jajarowi.loves.video.entity.Video;
import jajarowi.loves.videoSet.entity.VideoSet;
import jajarowi.loves.videoSet.repository.VideoSetRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class VideoSetService {

    private final VideoSetRepository repository;
    private final ProjectService projectService;

    @Autowired
    public VideoSetService(VideoSetRepository repository, ProjectService projectService) {
        this.repository = repository;
        this.projectService = projectService;
    }

    public Optional<VideoSet> find(long id) {
        return repository.findById(id);
    }

    public List<VideoSet> findAll() {
        return repository.findAll();
    }

    public List<VideoSet> findAllByProject(Long id) {
        Optional<Project> project = projectService.find(id);
        if(project.isPresent()) {
            return repository.findAllByProject(project.get());
        }
        return Collections.emptyList();
    }

    public Optional<VideoSet> findByProjectIdAndId(long projectId, long id){
        return repository.findByProjectIdAndId(projectId,id);
    }

    public List<VideoSet> findAllByProjectId(long projectId){
        return repository.findAllByProjectId(projectId);
    }
    public Optional<VideoSet> findByAssignmentId(long assignmentId){ return repository.findByAssignmentListId(assignmentId);}

    @Transactional
    public VideoSet create(VideoSet videoSet) {
        return repository.save(videoSet);
    }

    @Transactional
    public void update(VideoSet oldVideoSet, VideoSet newVideoSet) {
        oldVideoSet.setName(newVideoSet.getName());
        oldVideoSet.setMaxVideoCount(newVideoSet.getMaxVideoCount());
        repository.save(oldVideoSet);
    }

    @Transactional
    public void delete(VideoSet videoSet) {

        videoSet.getVideoList().forEach(video -> {
            String uploadsDir = "uploads/";
            Path fileStorageLocation = Paths.get(uploadsDir).toAbsolutePath().normalize();
            Path filePath = fileStorageLocation.resolve(video.getName()).normalize();
            try {
                Files.delete(filePath);
            } catch (IOException ex) {
                log.error("Cannot delete file: " + filePath);
            }
        });

        repository.delete(videoSet);
    }

    @Transactional
    public void delete(long id) {
        repository.deleteById(id);
    }

}
