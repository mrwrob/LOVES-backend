package jajarowi.loves.video.dto;

import jajarowi.loves.video.entity.Video;
import lombok.*;

import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class GetVideoResponse {
    private long id;
    private String name;

    public static Function<Video, GetVideoResponse> entityToDtoMapper() {
        return video -> GetVideoResponse.builder()
                .id(video.getId())
                .name(video.getName())
                .build();
    }
}
