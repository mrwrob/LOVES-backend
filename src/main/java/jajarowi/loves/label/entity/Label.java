package jajarowi.loves.label.entity;

import jajarowi.loves.project.entity.Project;
import jajarowi.loves.event.entity.Event;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "labels")
public class Label {

    @Id
    @GeneratedValue
    private long id;
    @Column(name = "label_name")
    private String name;
    private String color;
    @Column(name = "label_type")
    private String type;
    private String shortcut;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @OneToMany(mappedBy = "label", cascade=CascadeType.REMOVE)
    private List<Event> eventList;
}
