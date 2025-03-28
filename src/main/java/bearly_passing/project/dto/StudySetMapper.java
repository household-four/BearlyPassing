package bearly_passing.project.dto;

import bearly_passing.project.domain.StudySet;
import bearly_passing.project.domain.Question;

import java.util.List;
import java.util.stream.Collectors;

public class StudySetMapper {

    public static StudySetDTO toDTO(StudySet studySet) {
        return new StudySetDTO(
            studySet.getId(),
            studySet.getTitle(),
            studySet.getDescription(),
            studySet.getQuestions()
                    .stream()
                    .map(Question::getId)
                    .collect(Collectors.toList())
        );
    }

    public static StudySet toEntity(StudySetDTO dto) {
        StudySet studySet = new StudySet();
        studySet.setTitle(dto.getTitle());
        studySet.setDescription(dto.getDescription());
        
        return studySet;
    }
}
