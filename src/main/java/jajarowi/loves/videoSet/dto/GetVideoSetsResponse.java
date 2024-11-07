package jajarowi.loves.videoSet.dto;

import jajarowi.loves.video.entity.Video;
import lombok.*;

import java.util.Collection;
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
public class GetVideoSetsResponse {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    @EqualsAndHashCode
    public static class VideoSet {
        private long id;
        private String name;
        private int maxVideoCount;
        private int numberOfVideos;
    }

    @Singular("assignment")
    private List<GetVideoSetsResponse.VideoSet> videoSetList;

    public static Function<Collection<jajarowi.loves.videoSet.entity.VideoSet>, GetVideoSetsResponse> entityToDtoMapper() {
        return videoSets -> {
            GetVideoSetsResponse.GetVideoSetsResponseBuilder getVideoSetsResponseBuilder = GetVideoSetsResponse.builder();
            videoSets.stream()
                    .map(videoSet -> VideoSet.builder()
                            .id(videoSet.getId())
                            .name(videoSet.getName())
                            .maxVideoCount(videoSet.getMaxVideoCount())
                            .numberOfVideos(videoSet.getVideoList().size())
                            .build())
                    .forEach(getVideoSetsResponseBuilder::assignment);
            return getVideoSetsResponseBuilder.build();
        };
    }

}
