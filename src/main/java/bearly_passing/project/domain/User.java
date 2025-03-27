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

    // Updated from user to username to match the DTO field name
    private String username;

    // for role-based access like "Student", "Teacher", "Admin", etc.
    private String role;

    @OneToMany(mappedBy = "creator")
    @JsonBackReference
    private List<StudySet> studySets;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

