package jajarowi.loves.event.dto;

import jajarowi.loves.assignment.entity.Assignment;
import jajarowi.loves.event.entity.Event;
import jajarowi.loves.label.entity.Label;
import lombok.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class CreateEventRequest {
    private String start;
    private String end;
    private Long labelId;
    private Long assignmentId;

    public static Function<CreateEventRequest, Event> dtoToEntityMapper(
            Function<Long, Label> labelFunction,
            Function<Long, Assignment> assignmentFunction) {

        return request -> Event.builder()
                .start(request.getStart())
                .end(request.getEnd())
                .label(labelFunction.apply(request.getLabelId()))
                .assignment(assignmentFunction.apply(request.getAssignmentId()))
                .build();
    }
}
