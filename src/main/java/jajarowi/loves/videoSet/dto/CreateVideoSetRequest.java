package jajarowi.loves.videoSet.dto;

import jajarowi.loves.project.entity.Project;
import jajarowi.loves.videoSet.entity.VideoSet;
import lombok.*;

import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class CreateVideoSetRequest {

    private String name;
    private int maxVideoCount;
    private Long projectId;

    public static Function<CreateVideoSetRequest, VideoSet> dtoToEntityMapper(
            Function<Long, Project> projectFunction) {
        return videoSet -> VideoSet.builder()
                .name(videoSet.getName())
                .maxVideoCount(videoSet.getMaxVideoCount())
                .project(projectFunction.apply(videoSet.getProjectId()))
                .build();
    }

}
