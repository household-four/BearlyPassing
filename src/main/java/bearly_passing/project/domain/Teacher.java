package bearly_passing.project.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Teacher extends User {

    public Teacher() {
        super();
        setRole(UserRole.TEACHER); // Automatically assign role
    }

    @ManyToMany
    @JsonIgnore
    private List<Student> students;

    @Override
    public String getName() {
        return getUsername();
    }

    @Override
    public void setName(String name) {
        setUsername(name);
    }
}
