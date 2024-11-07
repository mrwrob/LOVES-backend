package jajarowi.loves.assignment.service;

import jajarowi.loves.assignment.entity.Assignment;
import jajarowi.loves.assignment.repository.AssignmentRepository;
import jajarowi.loves.project.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AssignmentService {

    private final AssignmentRepository repository;
    private final ProjectService projectService;

    @Autowired
    public AssignmentService(AssignmentRepository repository, ProjectService projectService) {
        this.repository = repository;
        this.projectService = projectService;
    }

    public Optional<Assignment> find(long id) {
        return repository.findById(id);
    }

    public List<Assignment> findAll() {
        return repository.findAll();
    }

    public List<Assignment> findAllByProjectId(long id){
        return repository.findAllByProjectId(id);
    }

    public List<Assignment> findAllByAssigneeLogin(String login){
        return repository.findAllByAssigneeLogin(login);
    }

    public Optional<Assignment> findByProjectIdAndId(long projectId, long id){
        return repository.findByProjectIdAndId(projectId, id);
    }

    @Transactional
    public Assignment create(Assignment assignment) {
        assignment.setFinished(false);
        assignment.setCreationTime(LocalDateTime.now());
        assignment.setModificationTime(LocalDateTime.now());
        return repository.save(assignment);
    }

    @Transactional
    public void update(Assignment oldAssignment, Assignment newAssignment) {
        oldAssignment.setName(newAssignment.getName());
        oldAssignment.setDescription(newAssignment.getDescription());
        oldAssignment.setModificationTime(newAssignment.getModificationTime());
        repository.save(oldAssignment);
    }

    @Transactional
    public void updateModificationTIme(Assignment assignment) {
        assignment.setModificationTime(LocalDateTime.now());
        projectService.updateModificationTime(assignment.getProject());
        repository.save(assignment);
    }

    @Transactional
    public void delete(Assignment assignment) {
        repository.delete(assignment);
    }

    @Transactional
    public void delete(long id) {
        repository.deleteById(id);
    }

    public boolean hasAssignee(Authentication authentication, Long assignmentId){
        String login = authentication.getName();
        Optional<Assignment> assignment = repository.findById(assignmentId);
        return authentication.isAuthenticated() && assignment.isPresent() && assignment.get().getAssignee().getLogin().equals(login);
    }

    public boolean isAccessible(Assignment assignment){
        return !assignment.isFinished() && assignment.getVideoSet().getVideoList().size() == assignment.getVideoSet().getMaxVideoCount();
    }

    public void finish(Assignment assignment) {
        assignment.setFinished(true);
        repository.save(assignment);
    }
}
