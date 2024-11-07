package jajarowi.loves.project.dto;

import jajarowi.loves.assignment.entity.Assignment;
import jajarowi.loves.label.entity.Label;
import jajarowi.loves.project.entity.Project;
import jajarowi.loves.scientist.entity.Scientist;
import jajarowi.loves.video.entity.Video;
import jajarowi.loves.videoSet.entity.VideoSet;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class UpdateProjectRequest {
    private String name;
    private String description;

    public static Function<UpdateProjectRequest, Project> dtoToEntityMapper(long id) {
        return request -> Project.builder()
                .id(id)
                .name(request.getName())
                .description(request.getDescription())
                .modificationTime(LocalDateTime.now())
                .build();
    }
}
