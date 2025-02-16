package bearly_passing.project.domain;

import jakarta.persistence.ManyToMany;

import java.util.List;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Student extends User {
    @ManyToMany(mappedBy = "students")
    private List<Teacher> teachers;
}
