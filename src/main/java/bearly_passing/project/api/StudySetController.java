package bearly_passing.project.api;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import bearly_passing.project.domain.Game;
import bearly_passing.project.domain.StudySet;
import bearly_passing.project.dto.GameDTO;
import bearly_passing.project.dto.GameMapper;
import bearly_passing.project.dto.StudySetDTO;
import bearly_passing.project.dto.StudySetMapper;
import bearly_passing.project.dto.UserDTO;
import bearly_passing.project.dto.UserMapper;
import bearly_passing.project.services.StudySetService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("api/set")
public class StudySetController {

    @Autowired
    private StudySetService studySetService;

    @GetMapping("/{id}")
    public StudySetDTO getSetById(@PathVariable Long id) {
        log.info("Getting study set {}", id);
        return StudySetMapper.toDTO(studySetService.getSetById(id));
    }

    @PostMapping("/export")
    public void exportStudySet(@RequestParam Long studySetId) {
        log.info("Exporting study set {}", studySetId);
        studySetService.saveStudySet(studySetId);
    }

    @PostMapping("/import")
    public StudySetDTO importStudySet(@RequestParam Long studySetId) throws IOException {
        log.info("Importing study set {}", studySetId);
        StudySet imported = studySetService.loadStudySet(studySetId);
        return StudySetMapper.toDTO(imported);
    }

    @PostMapping("/canvas")
    public StudySetDTO importCanvasSet(@RequestParam String canvasFile) throws IOException {
        log.info("Importing canvas file");
        StudySet canvasSet = studySetService.loadCanvasSet(canvasFile);
        return StudySetMapper.toDTO(canvasSet);
    }

    @PostMapping("/create")
    public StudySetDTO createStudySet(@RequestParam String name, @RequestParam Long userId,
            @RequestParam String description) {
        log.info("Creating study set {}", name);
        StudySet studySet = studySetService.createNewStudySet(name, userId, description);
        return StudySetMapper.toDTO(studySet);
    }

    @PostMapping("/createFromDTO")
    public StudySetDTO createStudySetFromDTO(@RequestBody StudySetDTO dto) {
        log.info("Creating study set from DTO");
        StudySet saved = studySetService.createStudySet(dto);
        return StudySetMapper.toDTO(saved);
    }

    @GetMapping("/sets")
    public List<StudySetDTO> getAllStudySets() {
        log.info("Getting all study sets");
        return studySetService.getAllStudySets()
                .stream()
                .map(StudySetMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/games/{id}")
    public List<GameDTO> getGamesByStudySetId(@PathVariable Long id) {
        log.info("Getting games for set {}", id);
        return GameMapper.toDTOList(studySetService.getGamesByStudySetId(id));
    }
}
