package jajarowi.loves.label.dto;

import jajarowi.loves.label.entity.Label;
import jajarowi.loves.project.entity.Project;
import lombok.*;

import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class CreateLabelRequest {
    private String name;
    private String color;
    private Long projectId;
    private String type;
    private String shortcut;

    public static Function<CreateLabelRequest, Label> dtoToEntityMapper(
            Function<Long, Project> projectFunction) {
        return request -> Label.builder()
                .name(request.getName())
                .color(request.getColor())
                .project(projectFunction.apply(request.getProjectId()))
                .type(request.getType())
                .shortcut(request.getShortcut())
                .build();
    }

}
