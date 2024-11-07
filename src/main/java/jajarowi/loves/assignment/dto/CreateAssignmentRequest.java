package jajarowi.loves.assignment.dto;

import jajarowi.loves.assignee.entity.Assignee;
import jajarowi.loves.assignment.entity.Assignment;
import jajarowi.loves.project.entity.Project;
import jajarowi.loves.videoSet.entity.VideoSet;
import lombok.*;

import java.time.LocalDateTime;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class CreateAssignmentRequest {

    private String name;
    private String description;
    private long projectId;
    private String assigneeName;
    private long videoSetId;

    public static Function<CreateAssignmentRequest, Assignment> dtoToEntityMapper(
            Function<Long, Project> projectFunction,
            Function<String, Assignee> assigneeFunction,
            Function<Long, VideoSet> videoSetFunction) {
        return assignment -> Assignment.builder()
                .name(assignment.getName())
                .description(assignment.getDescription())
                .creationTime(LocalDateTime.now())
                .modificationTime(LocalDateTime.now())
                .isFinished(false)
                .project(projectFunction.apply(assignment.getProjectId()))
                .assignee(assigneeFunction.apply(assignment.getAssigneeName()))
                .videoSet(videoSetFunction.apply(assignment.getVideoSetId()))
                .build();
    }
}
