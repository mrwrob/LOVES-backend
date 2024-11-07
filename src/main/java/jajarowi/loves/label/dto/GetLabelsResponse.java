package jajarowi.loves.label.dto;

import jajarowi.loves.event.entity.Event;
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
public class GetLabelsResponse {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    @EqualsAndHashCode
    public static class Label {
        private long id;
        private String name;
        private String color;
        private Long projectId;
        private String type;
        private String shortcut;
    }

    @Singular("label")
    private List<Label> labelList;

    public static Function<Collection<jajarowi.loves.label.entity.Label>, GetLabelsResponse> entityToDtoMapper() {
        return labels -> {
            GetLabelsResponseBuilder responseBuilder = GetLabelsResponse.builder();
            labels.stream()
                    .map(label -> Label.builder()
                            .id(label.getId())
                            .name(label.getName())
                            .color(label.getColor())
                            .projectId(label.getProject().getId())
                            .type(label.getType())
                            .shortcut(label.getShortcut())
                            .build()
                    ).forEach(responseBuilder::label);
            return responseBuilder.build();
        };
    }
}
