package jajarowi.loves.video.entity;

import jajarowi.loves.project.entity.Project;
import jajarowi.loves.videoSet.entity.VideoSet;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Setter
@Getter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "videos")
public class Video {
    @Id
    @GeneratedValue
    private long id;

    @NotBlank
    @Column(name="video_name")
    private String name;

    @Lob
    @Column(name = "video_file")
    private byte[] data;

    @ManyToOne
    @JoinColumn(name = "videoset_id")
    private VideoSet videoSet;
}
