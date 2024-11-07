package jajarowi.loves.videoSet.entity;

import jajarowi.loves.assignment.entity.Assignment;
import jajarowi.loves.project.entity.Project;
import jajarowi.loves.video.entity.Video;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Setter
@Getter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "videosets")
public class VideoSet {
    @Id
    @GeneratedValue
    private long id;

    @Column(name = "videoset_name")
    private String name;

    private int maxVideoCount = 2;

    @OneToMany(mappedBy = "videoSet", cascade=CascadeType.REMOVE)
    private List<Assignment> assignmentList;

    @OneToMany(mappedBy = "videoSet", cascade=CascadeType.REMOVE)
    @Size(max = 2)
    private List<Video> videoList;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

}
