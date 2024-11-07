package jajarowi.loves.label.service;

import jajarowi.loves.assignment.entity.Assignment;
import jajarowi.loves.assignment.service.AssignmentService;
import jajarowi.loves.label.entity.Label;
import jajarowi.loves.label.repository.LabelRepository;
import jajarowi.loves.project.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class LabelService {

    private final LabelRepository repository;
    private final ProjectService projectService;
    private final AssignmentService assignmentService;

    @Autowired
    public LabelService(LabelRepository repository, ProjectService projectService, AssignmentService assignmentService) {
        this.repository = repository;
        this.projectService = projectService;
        this.assignmentService = assignmentService;
    }

    public Optional<Label> find(long id) {
        return repository.findById(id);
    }

    public Optional<Label> findByProjectIdAndId(long projectId, long id) {
        return repository.findByProjectIdAndId(projectId, id);
    }

    public List<Label> findAll() {
        return repository.findAll();
    }

    public List<Label> findAllByAssignmentId(Long assignmentId) {
        return repository.findAllByProjectAssignmentListId(assignmentId);
    }

    public List<Label> findAllByProjectId(long projectId){
        return repository.findAllByProjectId(projectId);
    }

    @Transactional
    public Label create(Label label) {
        if(repository.findAllByShortcutAndProjectId(label.getShortcut(), label.getProject().getId()).isEmpty()) {
            return repository.save(label);
        }
        else return null;
    }

    @Transactional
    public void update(Label label) {
        repository.save(label);
    }

    @Transactional
    public void delete(Label label) {
        repository.delete(label);
    }

    @Transactional
    public void delete(long id) {
        repository.deleteById(id);
    }
}
