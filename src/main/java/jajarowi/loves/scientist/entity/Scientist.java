package jajarowi.loves.scientist.entity;

import jajarowi.loves.video.entity.Video;
import jajarowi.loves.project.entity.Project;
import jajarowi.loves.user.entity.User;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.List;

@Setter
@Getter
@SuperBuilder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Transactional
@DiscriminatorValue(value = "scientist")
public class Scientist extends User {

    @OneToMany(mappedBy = "scientist")
    private List<Project> projectList;

}
