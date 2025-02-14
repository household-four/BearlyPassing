package bearly_passing.project;

import java.util.List;

import jakarta.persistence.*;

import lombok.Data;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED) // makes separate student/teacher tables
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "creator")
    private List<StudySet> studySets;

}
