package jajarowi.loves.assignee.dto;

import jajarowi.loves.assignment.entity.Assignment;
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
public class GetAssigneesResponse {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    @EqualsAndHashCode
    public static class Assignee {
        private String login;
        private List<Long> assignmentIdList;
    }

    @Singular("assignee")
    private List<Assignee> assigneeList;

    public static Function<Collection<jajarowi.loves.assignee.entity.Assignee>, GetAssigneesResponse> entityToDtoMapper() {
        return assignees -> {
            GetAssigneesResponseBuilder responseBuilder = GetAssigneesResponse.builder();
            assignees.stream()
                    .map(assignee -> Assignee.builder()
                            .login(assignee.getLogin())
                            .assignmentIdList(assignee.getAssignmentList().stream().map(Assignment::getId).collect(Collectors.toList()))
                            .build()
                    ).forEach(responseBuilder::assignee);
            return responseBuilder.build();
        };
    }
}
