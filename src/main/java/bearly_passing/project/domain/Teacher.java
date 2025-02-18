package bearly_passing.project.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Teacher extends User {
    @ManyToMany
    @JsonManagedReference
    private List<Student> students;

    public List<Student> getStudents() {
        return students;
    }
}
