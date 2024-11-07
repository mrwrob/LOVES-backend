package jajarowi.loves.videoSet.dto;

import jajarowi.loves.videoSet.entity.VideoSet;
import lombok.*;

import java.util.function.BiFunction;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class UpdateVideoSetRequest {
    private String name;
    private int maxVideoCount;

    public static Function<UpdateVideoSetRequest, VideoSet> dtoToEntityMapper() {
        return request -> VideoSet.builder()
                .name(request.getName())
                .maxVideoCount(request.getMaxVideoCount())
                .build();
    }
}
