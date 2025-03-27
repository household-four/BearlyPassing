package bearly_passing.project.dto;

import bearly_passing.project.domain.Question;

public class QuestionMapper {

    public static QuestionDTO toDTO(Question question) {
        return new QuestionDTO(
            question.getId(),
            question.getBody(),
            question.getAnswer(),
            question.getDifficulty()
        );
    }

    // Optional: Add toEntity later if needed
}
