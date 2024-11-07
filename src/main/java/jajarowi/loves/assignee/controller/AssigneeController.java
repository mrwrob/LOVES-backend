package jajarowi.loves.assignee.controller;

import jajarowi.loves.assignee.dto.GetAssigneesResponse;
import jajarowi.loves.assignee.service.AssigneeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/assignees")
public class AssigneeController {

    private final AssigneeService assigneeService;

    public AssigneeController(AssigneeService assigneeService) {
        this.assigneeService = assigneeService;
    }

    @GetMapping
    public ResponseEntity<GetAssigneesResponse> getAssignees() {
        return ResponseEntity.ok(GetAssigneesResponse.entityToDtoMapper().apply(assigneeService.findAll()));
    }

    @GetMapping("search/{pattern}")
    public ResponseEntity<GetAssigneesResponse> getAssigneesWithPattern(@PathVariable String pattern) {
        return ResponseEntity.ok(GetAssigneesResponse.entityToDtoMapper().apply(assigneeService.findAllWithPattern(pattern)));
    }

}
