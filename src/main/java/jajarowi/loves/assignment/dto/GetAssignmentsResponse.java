package jajarowi.loves.assignment.dto;

import jajarowi.loves.event.entity.Event;
import lombok.*;

import java.time.LocalDateTime;
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
public class GetAssignmentsResponse {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    @EqualsAndHashCode
    public static class Assignment {
        private long id;
        private String name;
        private String description;
        private String assigneeName;
        private boolean isFinished;
        private LocalDateTime creationTime;
        private LocalDateTime modificationTime;
        private int numberOfEvents;
        private String projectName;
        private String videoSetName;
        private boolean isAccessible;
    }

    @Singular("assignment")
    private List<Assignment> assignmentList;

    public static Function<Collection<jajarowi.loves.assignment.entity.Assignment>, GetAssignmentsResponse> entityToDtoMapper() {
        return assignments -> {
            GetAssignmentsResponseBuilder responseBuilder = GetAssignmentsResponse.builder();
            assignments.stream()
                    .map(assignment -> Assignment.builder()
                            .id(assignment.getId())
                            .name(assignment.getName())
                            .description(assignment.getDescription())
                            .assigneeName(assignment.getAssignee().getLogin())
                            .isFinished(assignment.isFinished())
                            .creationTime(assignment.getCreationTime())
                            .modificationTime(assignment.getModificationTime())
                            .numberOfEvents(assignment.getEventList().size())
                            .projectName(assignment.getProject().getName())
                            .videoSetName(assignment.getVideoSet().getName())
                            .isAccessible(
                                    assignment.getVideoSet().getMaxVideoCount()==
                                            (assignment.getVideoSet().getVideoList()==null ? 0 : assignment.getVideoSet().getVideoList().size())
                            )
                            .build())
                    .forEach(responseBuilder::assignment);
            return responseBuilder.build();
        };
    }
}
