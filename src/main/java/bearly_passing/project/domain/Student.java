package bearly_passing.project.domain;

import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Student extends User {

    @ManyToMany(mappedBy = "students")
    @JsonBackReference
    private List<Teacher> teachers;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<GameSession> assignedGames;

    private float grade;

    public float getGrade() {
        return grade;
    }

    public void setGrade(float grade) {
        this.grade = grade;
    }

    public List<GameSession> getAssignedGames() {
        return assignedGames;
    }

    public String getName() {
        return getUsername();
    }

    public void setName(String name) {
        setUsername(name);
    }
}
