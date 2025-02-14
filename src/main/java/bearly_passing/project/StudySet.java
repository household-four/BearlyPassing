package bearly_passing.project;

import java.util.List;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class StudySet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    private User creator;

    @OneToMany(mappedBy = "studySet")
    private List<Game> games;

    @OneToMany(mappedBy = "studySet")
    private List<StudyItem> studyItems;

}
