package jajarowi.loves.event.entity;

import jajarowi.loves.assignment.entity.Assignment;
import jajarowi.loves.label.entity.Label;
import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue
    private long id;
    @Column(name = "end_time")
    private String end;
    @Column(name = "start_time")
    private String start;

    @ManyToOne
    @JoinColumn(name = "label_id")
    private Label label;

    @ManyToOne
    @JoinColumn(name = "assignment_id")
    private Assignment assignment;

}
