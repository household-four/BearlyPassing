package bearly_passing.project.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import bearly_passing.project.domain.StudySet;
import bearly_passing.project.domain.User;
import bearly_passing.project.services.StudySetService;

@RestController
@RequestMapping("/set")
public class StudySetController<Question> {

    @Autowired 
    private StudySetService studySetService;

    @PostMapping("/export")
    public void exportStudySet(@RequestParam Long studySetId) {
        studySetService.saveStudySet(studySetId);
    }

    @PostMapping("/import")
    public StudySet importStudySet(@RequestParam Long studySetId) throws IOException {
        return studySetService.loadStudySet(studySetId);
    }

    @PostMapping("/canvas")
    public StudySet importCanvasSet(@RequestParam String canvasFile) throws IOException {
        return studySetService.loadCanvasSet(canvasFile);
    }

    // For saving new study set to database
    @PostMapping("/create")
    public StudySet createStudySet(@RequestParam String name, @RequestParam Long userId) {
        // for now, uses userId as a request param
        // since we dont know who the current user is
        return studySetService.createNewStudySet(name, userId);
    }

    @GetMapping("/sets")
    public List<StudySet> getAllStudySets() {
        return studySetService.getAllStudySets();
    }

}
