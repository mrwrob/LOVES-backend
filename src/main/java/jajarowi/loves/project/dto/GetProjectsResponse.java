package jajarowi.loves.project.dto;

import jajarowi.loves.assignment.entity.Assignment;
import jajarowi.loves.label.entity.Label;
import jajarowi.loves.videoSet.entity.VideoSet;
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
public class GetProjectsResponse {

    @Getter
    @Setter
    @Builder
    private static class Project {
        private long id;
        private String name;
        private String description;
        private LocalDateTime creationTime;
        private LocalDateTime modificationTime;
        private int numberOfLabels;
        private int numberOfAssignments;
        private int numberOfVideoSets;
    }

    @Singular("project")
    private List<Project> projectList;

    public static Function<Collection<jajarowi.loves.project.entity.Project>, GetProjectsResponse> entityToDtoMapper() {
        return projects -> {
            GetProjectsResponseBuilder responseBuilder = GetProjectsResponse.builder();
            projects.stream()
                    .map(project -> Project.builder()
                            .id(project.getId())
                            .name(project.getName())
                            .description(project.getDescription())
                            .creationTime(project.getCreationTime())
                            .modificationTime(project.getModificationTime())
                            .numberOfLabels(project.getLabelList().size())
                            .numberOfAssignments(project.getAssignmentList().size())
                            .numberOfVideoSets(project.getVideoSetList().size())
                            .build()
                    ).forEach(responseBuilder::project);
            return responseBuilder.build();
        };
    }
}
