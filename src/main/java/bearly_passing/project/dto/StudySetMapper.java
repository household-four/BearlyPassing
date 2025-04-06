package bearly_passing.project.dto;

import bearly_passing.project.domain.StudySet;
import bearly_passing.project.domain.User;
import bearly_passing.project.domain.Question;

import java.util.List;
import java.util.stream.Collectors;

public class StudySetMapper {

    public static StudySetDTO toDTO(StudySet studySet) {
        List<QuestionDTO> questionDTOs = studySet.getQuestions()
                .stream()
                .map(q -> new QuestionDTO(q.getId(), q.getBody(), q.getCorrectAnswer(), q.getDifficulty()))
                .collect(Collectors.toList());

        User creator = studySet.getCreator();

        return new StudySetDTO(
                studySet.getId(),
                studySet.getTitle(),
                studySet.getDescription(),
                studySet.getQuestions()
                        .stream()
                        .map(Question::getId)
                        .collect(Collectors.toList()),
                questionDTOs,
                creator);
    }

    public static List<StudySetDTO> toDTOList(List<StudySet> sets) {
        return sets.stream()
                .map(StudySetMapper::toDTO)
                .collect(Collectors.toList());
    }

    public static StudySet toEntity(StudySetDTO dto) {
        StudySet studySet = new StudySet();
        studySet.setTitle(dto.getTitle());
        studySet.setDescription(dto.getDescription());

        return studySet;
    }
}
