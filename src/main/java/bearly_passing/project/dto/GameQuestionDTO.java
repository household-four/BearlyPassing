package bearly_passing.project.dto;

import java.util.List;
import lombok.Data;

@Data
public class GameQuestionDTO {
    private Long questionId;
    private String questionBody;
    private List<String> answerOptions;
}
