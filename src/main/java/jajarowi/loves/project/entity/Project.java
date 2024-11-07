package jajarowi.loves.project.entity;

import jajarowi.loves.assignment.entity.Assignment;
import jajarowi.loves.label.entity.Label;
import jajarowi.loves.scientist.entity.Scientist;
import jajarowi.loves.video.entity.Video;
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
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne()
    @JoinColumn(name = "scientist_id")
    private Scientist scientist;

    @Column(name = "project_name")
    private String name;
    private String description;
    private LocalDateTime creationTime;
    private LocalDateTime modificationTime;

    @OneToMany(mappedBy = "project",cascade=CascadeType.REMOVE)
    private List<VideoSet> videoSetList;

    @OneToMany(mappedBy = "project",cascade=CascadeType.REMOVE)
    private List<Label> labelList;

    @OneToMany(mappedBy = "project",cascade=CascadeType.REMOVE)
    private List<Assignment> assignmentList;
}
