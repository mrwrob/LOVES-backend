package jajarowi.loves.event.dto;

import jajarowi.loves.assignment.entity.Assignment;
import jajarowi.loves.event.entity.Event;
import jajarowi.loves.label.entity.Label;
import lombok.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
public class CreateEventsRequest {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    @EqualsAndHashCode
    public static class Event {
        private Long id;
        private String start;
        private String end;
        private Long labelId;
        private Long assignmentId;
    }

    private Long assignmentId;

    @Singular("event")
    private List<CreateEventsRequest.Event> eventList;

    public static CreateEventRequest toCreateEventRequest(CreateEventsRequest.Event event) {
        return CreateEventRequest.builder()
                .start(event.getStart())
                .end(event.getEnd())
                .labelId(event.getLabelId())
                .assignmentId(event.getAssignmentId())
                .build();
    }
}
