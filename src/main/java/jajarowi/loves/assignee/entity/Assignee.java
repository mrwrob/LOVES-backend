package jajarowi.loves.assignee.entity;

import jajarowi.loves.assignment.entity.Assignment;
import jajarowi.loves.user.entity.User;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@SuperBuilder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DiscriminatorValue(value = "assignee")
public class Assignee extends User {

    @OneToMany(mappedBy = "assignee")
    @ToString.Exclude
    private List<Assignment> assignmentList;

}
