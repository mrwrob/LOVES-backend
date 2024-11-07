package jajarowi.loves.assignment.dto;

import jajarowi.loves.assignment.entity.Assignment;
import jajarowi.loves.event.entity.Event;
import jajarowi.loves.video.entity.Video;
import jajarowi.loves.videoSet.dto.GetVideoSetResponse;
import lombok.*;

import java.util.Date;
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
public class GetAssignmentResponse {

    private long id;
    private String name;
    private String description;
    private String assigneeName;
    private VideoSet videoSet;
    private List<Event> eventList;
    private List<Label> labelList;

    @Builder
    @Setter
    @Getter
    private static class VideoSet{
        private long id;
        private List<Long> videoIdList;
        private String name;
    }

    @Builder
    @Setter
    @Getter
    private static class Event{
        private long id;
        private long labelId;
        private String start;
        private String end;
    }


    @Builder
    @Setter
    @Getter
    private static class Label{
        private String name;
        private String color;
        private String type;
        private String shortcut;
    }


    public static Function<Assignment, GetAssignmentResponse> entityToDtoMapper() {
        return assignment -> GetAssignmentResponse.builder()
                .id(assignment.getId())
                .name(assignment.getName())
                .description(assignment.getDescription())
                .assigneeName(assignment.getAssignee().getLogin())
                .videoSet(VideoSet.builder()
                        .id(assignment.getVideoSet().getId())
                        .videoIdList(assignment.getVideoSet().getVideoList().stream().map(Video::getId).collect(Collectors.toList()))
                        .name(assignment.getVideoSet().getName())
                        .build()
                )
                .eventList(assignment.getEventList().stream().map(
                        v -> Event.builder()
                                .id(v.getId())
                                .labelId(v.getLabel().getId())
                                .start(v.getStart())
                                .end(v.getEnd())
                                .build()
                        ).collect(Collectors.toList()))
                .labelList(assignment.getProject().getLabelList().stream().map(
                        v -> Label.builder()
                                .name(v.getName())
                                .color(v.getColor())
                                .type(v.getType())
                                .shortcut(v.getShortcut())
                                .build()
                        ).collect(Collectors.toList()))
                .build();
    }

}
