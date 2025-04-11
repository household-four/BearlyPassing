package bearly_passing.project.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import bearly_passing.project.domain.Game;
import bearly_passing.project.domain.Question;
import bearly_passing.project.domain.Student;
import bearly_passing.project.domain.StudySet;
import bearly_passing.project.domain.UserRole;
import bearly_passing.project.dto.GameDTO;
import bearly_passing.project.dto.GameMapper;
import bearly_passing.project.dto.StudySetDTO;
import bearly_passing.project.dto.StudySetMapper;
import bearly_passing.project.services.StudySetService;

@WebMvcTest(controllers = AdminController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class StudySetControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockitoBean
    private StudySetService studySetService;

    @Autowired
    private ObjectMapper objectMapper;

    private Student student;
    private StudySet studySet;
    private List<Game> games;
    private StudySetDTO studySetDTO;
    private StudySet studySet2;
    private List<StudySet> studySets;
    private Question question;

    @BeforeEach
    public void init() {
        student = new Student();
            student.setId(11111111L);
            student.setUsername("Baylor");
            student.setRole(UserRole.STUDENT);

        studySet = new StudySet();
            studySet.setCreator(student);
            studySet.setTitle("Bearly Passing");
            studySet.setDescription("How to pass every class.");

        studySetDTO = new StudySetDTO();
            studySet.setCreator(student);
            studySet.setTitle("Bearly Passing");
            studySet.setDescription("How to pass every class.");

        studySet2 = new StudySet();
            studySet2.setCreator(student);
            studySet2.setTitle("Bearly Passing 2");
            studySet2.setDescription("How to pass some classes.");

        studySets = new ArrayList<StudySet>();
            studySets.add(studySet);
            studySets.add(studySet2);

        question = new Question();
            question.setStudySet(studySet);

        studySet.addQuestion(question);
    }

    @Test
    public void StudySetController_GetSetById_ReturnsStudySetDTO() throws Exception {
        when(studySetService.getSetById(studySet.getId())).thenReturn(studySet);

        mockMvc.perform(get("/api/set/" + studySet.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(studySetDTO)))
            
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", CoreMatchers.is(studySetDTO.getId())));
    }

    /*
    don't know how to do this one...
    @PostMapping("/export")
    public void exportStudySet(@RequestParam Long studySetId) {
        log.info("Exporting study set {}", studySetId);
        studySetService.saveStudySet(studySetId);
    }
    */

    /*
    @PostMapping("/import")
    public StudySetDTO importStudySet(@RequestParam Long studySetId) throws IOException {
        log.info("Importing study set {}", studySetId);
        StudySet imported = studySetService.loadStudySet(studySetId);
        return StudySetMapper.toDTO(imported);
    }
    */

    /*
    @PostMapping("/canvas")
    public StudySetDTO importCanvasSet(@RequestParam String canvasFile) throws IOException {
        log.info("Importing canvas file");
        StudySet canvasSet = studySetService.loadCanvasSet(canvasFile);
        return StudySetMapper.toDTO(canvasSet);
    }
    */

    @Test
    public void StudySetController_CreateStudySet_ReturnCreated() throws Exception {
        given(studySetService.createNewStudySet(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any()))
            .willAnswer((invocation -> invocation.getArgument(0)));

        mockMvc.perform(post("/api/set/create")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(studySet)))

            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", CoreMatchers.is(studySetDTO.getId())));
    }

    @Test
    public void StudySetController_CreateStudySetFromDTO_ReturnCreated() throws Exception {
        given(studySetService.createStudySet(ArgumentMatchers.any()))
            .willAnswer((invocation -> invocation.getArgument(0)));

        mockMvc.perform(post("/api/set/createFromDTO")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(studySetDTO)))

            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", CoreMatchers.is(studySetDTO.getId())));
    }

    @Test
    public void StudySetController_GetAllStudySets_ReturnsStudySetDTOs() throws Exception {
        when(studySetService.getAllStudySets()).thenReturn(studySets);
        
        mockMvc.perform(get("/api/set/sets"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(studySets.size()));
    }

    @Test
    public void StudySetController_GetGamesByStudySetId_ReturnsGameDTOs() throws Exception {
        when(studySetService.getGamesByStudySetId(studySet.getId())).thenReturn(games);

        mockMvc.perform(get("/api/set/games/" + studySet.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(games)))
            
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(games.size()));
    }
}
