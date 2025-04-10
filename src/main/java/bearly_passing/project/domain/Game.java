package bearly_passing.project.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private GameType gameType;

    @ManyToOne
    @JoinColumn(name = "studyset_id")
    @JsonIgnore
    private StudySet studySet;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    @JsonIgnore
    private User creator;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<GameSession> sessions;

    public Long getStudySetId() {
        return studySet != null ? studySet.getId() : null;
    }

    public Long getUserId() {
        return creator != null ? creator.getId() : null;
    }

    public void setStudySet(StudySet studySet) {
        this.studySet = studySet;
    }

    public User getCreator() {
        return creator;
    }
}
