package bearly_passing.project.domain;

import jakarta.persistence.*;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
public class Student extends User {

    @ManyToMany(mappedBy = "students")
    @JsonIgnore
    private List<Teacher> teachers;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference(value = "student-games")
    private List<GameSession> assignedGames;

    private float grade;

    public List<GameSession> getAssignedGames() {
        return assignedGames;
    }

    @Override
    public String getName() {
        return getUsername();
    }

    @Override
    public void setName(String name) {
        setUsername(name);
    }
}
