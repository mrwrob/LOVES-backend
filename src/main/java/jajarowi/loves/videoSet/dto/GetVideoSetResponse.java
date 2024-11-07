package jajarowi.loves.videoSet.dto;

import jajarowi.loves.video.entity.Video;
import jajarowi.loves.videoSet.entity.VideoSet;
import lombok.*;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class GetVideoSetResponse {

    private Long id;
    private String name;
    private int maxVideoCount;
    private List<Long> videoIdList;
    private long projectId;

    public static Function<VideoSet, GetVideoSetResponse> entityToDtoMapper() {
        return videoSet -> GetVideoSetResponse.builder()
                .id(videoSet.getId())
                .name(videoSet.getName())
                .maxVideoCount(videoSet.getMaxVideoCount())
                .videoIdList(videoSet.getVideoList().stream().map(Video::getId).collect(Collectors.toList()))
                .projectId(videoSet.getProject().getId())
                .build();
    }
}
