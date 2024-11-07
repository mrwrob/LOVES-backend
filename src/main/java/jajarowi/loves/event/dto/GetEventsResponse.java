package jajarowi.loves.event.dto;

import lombok.*;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class GetEventsResponse {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    @EqualsAndHashCode
    public static class Event {
        private String start;
        private String end;
        private Long id;
        private Long labelId;
        private Long assignmentId;
    }

    @Singular("event")
    private List<Event> eventList;

    public static Function<Collection<jajarowi.loves.event.entity.Event>, GetEventsResponse> entityToDtoMapper() {

        return events -> {
            GetEventsResponseBuilder responseBuilder = GetEventsResponse.builder();
            events.stream()
                    .map(event -> Event.builder()
                            .id(event.getId())
                            .start(event.getStart())
                            .end(event.getEnd())
                            .labelId(event.getLabel().getId())
                            .assignmentId(event.getAssignment().getId())
                            .build()
                    ).forEach(responseBuilder::event);
            return responseBuilder.build();
        };
    }
}
