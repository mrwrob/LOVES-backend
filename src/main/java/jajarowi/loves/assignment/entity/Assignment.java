package jajarowi.loves.assignment.entity;

import jajarowi.loves.assignee.entity.Assignee;
import jajarowi.loves.event.entity.Event;
import jajarowi.loves.project.entity.Project;
import jajarowi.loves.videoSet.entity.VideoSet;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "assignments")
public class Assignment {

    @Id
    @GeneratedValue
    private long id;
    @Column(name = "assignment_name")
    private String name;
    private String description;
    private LocalDateTime creationTime;
    private LocalDateTime modificationTime;
    private boolean isFinished;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "assignee_id")
    private Assignee assignee;

    @OneToMany(mappedBy = "assignment", cascade=CascadeType.REMOVE)
    private List<Event> eventList;

    @ManyToOne
    @JoinColumn(name = "videoset_id")
    private VideoSet videoSet;
}
