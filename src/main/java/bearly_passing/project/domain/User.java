package bearly_passing.project.domain;

import java.util.List;

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
    
    private List<StudySet> studySets;

}
