package jajarowi.loves.project.dto;


import jajarowi.loves.assignment.dto.GetAssignmentsResponse;
import jajarowi.loves.label.dto.GetLabelsResponse;
import jajarowi.loves.label.entity.Label;
import jajarowi.loves.project.entity.Project;
import jajarowi.loves.videoSet.dto.GetVideoSetsResponse;
import jajarowi.loves.videoSet.entity.VideoSet;
import lombok.*;

import java.time.LocalDateTime;
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
public class GetProjectResponse {

    private long id;
    private String name;
    private String description;
    private List<Label> labelList;
    private List<Assignment> assignmentList;
    private List<VideoSet> videoSetList;

    @Builder
    @Setter
    @Getter
    private static class Label{
        private long id;
        private String name;
        private String color;
        private String type;
        private String shortcut;
    }

    @Builder
    @Setter
    @Getter
    private static class Assignment{
        private long id;
        private String name;
        private String description;
        private LocalDateTime creationTime;
        private LocalDateTime modificationTime;
        private int numberOfEvents;
        private String assigneeName;
        private Boolean finished;
        private long videoSetId;
        private String videoSetName;
    }

    @Builder
    @Setter
    @Getter
    private static class VideoSet{
        private long id;
        private String name;
        private int videoCount;
        private int maxVideoCount;
    }


    public static Function<Project, GetProjectResponse> entityToDtoMapper() {
        return project -> GetProjectResponse.builder()
                .id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .labelList(project.getLabelList().stream().map(
                        v -> Label.builder()
                                .id(v.getId())
                                .name(v.getName())
                                .color(v.getColor())
                                .type(v.getType())
                                .shortcut(v.getShortcut())
                                .build()
                ).collect(Collectors.toList()))
                .assignmentList(project.getAssignmentList().stream().map(
                        v -> Assignment.builder()
                                .id(v.getId())
                                .name(v.getName())
                                .description(v.getDescription())
                                .creationTime(v.getCreationTime())
                                .modificationTime(v.getModificationTime())
                                .numberOfEvents(v.getEventList().size())
                                .assigneeName(v.getAssignee().getLogin())
                                .finished(v.isFinished())
                                .videoSetId(v.getVideoSet().getId())
                                .videoSetName(v.getVideoSet().getName())
                                .build()
                ).collect(Collectors.toList()))
                .videoSetList(project.getVideoSetList().stream().map(
                        v -> VideoSet.builder()
                                .id(v.getId())
                                .name(v.getName())
                                .videoCount(v.getVideoList().size())
                                .maxVideoCount(v.getMaxVideoCount())
                                .build()
                ).collect(Collectors.toList()))
                .build();
    }

}
