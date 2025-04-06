package bearly_passing.project.dto;

import java.util.List;

import bearly_passing.project.domain.User;

public class StudySetDTO {
    private Long id;
    private String title;
    private String description;
    private List<Long> questionIds;
    private List<QuestionDTO> questions;
    private User creator;

    public StudySetDTO() {
    }

    public StudySetDTO(Long id, String title, String description, List<Long> questionIds, List<QuestionDTO> questions,
            User creator) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.questionIds = questionIds;
        this.questions = questions;
        this.creator = creator;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Long> getQuestionIds() {
        return questionIds;
    }

    public void setQuestionIds(List<Long> questionIds) {
        this.questionIds = questionIds;
    }

    public List<QuestionDTO> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionDTO> questions) {
        this.questions = questions;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

}
