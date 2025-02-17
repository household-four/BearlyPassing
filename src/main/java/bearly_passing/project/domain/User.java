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

    private String name;

    @OneToMany(mappedBy = "creator")
    @JsonBackReference
    private List<StudySet> studySets;

}
