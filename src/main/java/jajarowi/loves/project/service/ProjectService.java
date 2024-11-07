package jajarowi.loves.project.service;

import jajarowi.loves.project.entity.Project;
import jajarowi.loves.project.repository.ProjectRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProjectService {

    private final ProjectRepository repository;
    private final ModelMapper modelMapper;

    @Autowired
    public ProjectService(ProjectRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    public Optional<Project> find(long id) {
        return repository.findById(id);
    }

    public List<Project> findAll() {
        return repository.findAll();
    }

    public List<Project> findAllByScientistLogin(String login) {
        return repository.findAllByScientistLogin(login);
    }

    @Transactional
    public Project create(Project project) {
        return repository.save(project);
    }

    @Transactional
    public void update(Project oldProject, Project newProject) {
        modelMapper.map(newProject, oldProject);
        repository.save(oldProject);
    }

    @Transactional
    public void updateModificationTime(Project project) {
        project.setModificationTime(LocalDateTime.now());
        repository.save(project);
    }

    @Transactional
    public void delete(Project project) {
        repository.delete(project);
    }

    public boolean hasOwner(Authentication authentication, Long projectId){
        String login = authentication.getName();
        Optional<Project> project = repository.findById(projectId);
        return authentication.isAuthenticated() && project.isPresent() && Objects.equals(project.get().getScientist().getLogin(), login);
    }

}
