package jajarowi.loves.project.dto;

import jajarowi.loves.project.entity.Project;
import jajarowi.loves.scientist.entity.Scientist;
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
public class CreateProjectRequest {
    private String name;
    private String description;
    private String scientistName;

    public static Function<CreateProjectRequest, Project> dtoToEntityMapper(
            Function<String, Scientist> scientistFunction) {
        return request -> Project.builder()
                .name(request.getName())
                .description(request.getDescription())
                .creationTime(LocalDateTime.now())
                .modificationTime(LocalDateTime.now())
                .scientist(scientistFunction.apply(request.getScientistName()))
                .build();

    }
}
