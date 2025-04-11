package bearly_passing.project.api;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.util.ReflectionTestUtils;
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
import bearly_passing.project.dto.StudySetDTO;
import bearly_passing.project.services.StudySetService;

@WebMvcTest(controllers = StudySetController.class)
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
    private Game game;

    @BeforeEach
    public void init() {
        student = new Student();
        student.setId(11111111L);
        student.setUsername("Baylor");
        student.setRole(UserRole.STUDENT);

        studySet = new StudySet();
        ReflectionTestUtils.setField(studySet, "id", 1L);
        studySet.setCreator(student);
        studySet.setTitle("Bearly Passing");
        studySet.setDescription("How to pass every class.");

        studySetDTO = new StudySetDTO();
        studySetDTO.setId(studySet.getId());
        studySetDTO.setTitle(studySet.getTitle());
        studySetDTO.setDescription(studySet.getDescription());

        studySet2 = new StudySet();
        ReflectionTestUtils.setField(studySet2, "id", 2L);
        studySet2.setCreator(student);
        studySet2.setTitle("Bearly Passing 2");
        studySet2.setDescription("How to pass some classes.");

        studySets = new ArrayList<StudySet>();
        studySets.add(studySet);
        studySets.add(studySet2);

        question = new Question();
        question.setId(100L);
        question.setStudySet(studySet);
        studySet.addQuestion(question);

        games = new ArrayList<>();
        
        game = new Game();
        game.setId(10L);
        game.setStudySet(studySet);
        games.add(game);
    }

    @Test
    public void StudySetController_GetSetById_ReturnsStudySetDTO() throws Exception {
        when(studySetService.getSetById(studySet.getId())).thenReturn(studySet);

        mockMvc.perform(get("/api/set/" + studySet.getId())
            .contentType(MediaType.APPLICATION_JSON))
            
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", CoreMatchers.is(studySetDTO.getId().intValue())));
    }

    @Test
    public void StudySetController_CreateStudySet_ReturnCreated() throws Exception {
        given(studySetService.createNewStudySet(ArgumentMatchers.anyString(), ArgumentMatchers.anyLong(), ArgumentMatchers.anyString()))
            .willReturn(studySet);

        mockMvc.perform(post("/api/set/create")
            .param("name", studySet.getTitle())
            .param("userId", student.getId().toString())
            .param("description", studySet.getDescription()))

            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", CoreMatchers.is(studySetDTO.getId().intValue())));
    }

    @Test
    public void StudySetController_CreateStudySetFromDTO_ReturnCreated() throws Exception {
        given(studySetService.createStudySet(ArgumentMatchers.any()))
            .willReturn(studySet);

        mockMvc.perform(post("/api/set/createFromDTO")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(studySetDTO)))

            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", CoreMatchers.is(studySetDTO.getId().intValue())));
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
            .contentType(MediaType.APPLICATION_JSON))
            
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(games.size()));
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
}
