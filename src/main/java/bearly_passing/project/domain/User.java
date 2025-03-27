package bearly_passing.project.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;

import lombok.Data;

@Data
@Entity
@Table(name = "users") // required since 'user' is a reserved keyword
@Inheritance(strategy = InheritanceType.JOINED) // makes separate student/teacher tables
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @OneToMany(mappedBy = "creator")
    @JsonBackReference
    private List<StudySet> studySets;

    public String getName() {
        return username;
    }

    public void setName(String name) {
        this.username = name;
    }

    public String getRoleName() {
        return role != null ? role.name() : null;
    }
}
