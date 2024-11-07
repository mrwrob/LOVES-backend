package jajarowi.loves.assignment.dto;

import jajarowi.loves.assignee.entity.Assignee;
import jajarowi.loves.assignment.entity.Assignment;
import jajarowi.loves.project.entity.Project;
import lombok.*;

import java.time.LocalDateTime;
import java.util.function.BiFunction;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class UpdateAssignmentRequest {

    private String name;
    private String description;

    public static Function<UpdateAssignmentRequest, Assignment> dtoToEntityMapper(long id) {
        return request -> Assignment.builder()
                .id(id)
                .name(request.getName())
                .description(request.getDescription())
                .modificationTime(LocalDateTime.now())
                .build();
    }
}
