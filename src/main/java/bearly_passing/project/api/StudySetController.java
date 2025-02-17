package bearly_passing.project.api;

import org.springframework.web.bind.annotation.SessionAttributes;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import bearly_passing.project.data.StudySetRepository;
import bearly_passing.project.domain.Question;
import bearly_passing.project.domain.StudySet;
import bearly_passing.project.domain.Teacher;
import bearly_passing.project.services.StudySetService;

@RestController
@RequestMapping("/set")
// @SessionAttributes("studySet")
public class StudySetController {

    @Autowired
    private StudySetService studySetService;

    // create study set icon
    // upload existing || new set || canvas import

    @GetMapping("/current")
    public String getMethodName() {
        return "set";
    }

    @PostMapping("/save")
    public void saveStudySet(@ModelAttribute("studySet") StudySet studySet) {
        try {
            String home = System.getProperty("user.home");
            Path path = Paths.get(home, "Downloads", studySet.getName() + ".json");

            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(path.toFile(), studySet);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/export")
    public void exportStudySet(@RequestBody StudySet studySet) {
        try {
            String home = System.getProperty("user.home");
            Path path = Paths.get(home, "Downloads", studySet.getName() + ".json");

            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(path.toFile(), studySet);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // For saving new study set to database
    @PostMapping("/create")
    public StudySet createStudySet(@RequestParam String name, @RequestParam Long userId) {
        // for now, uses userId as a request param
        // since we dont know who the current user is
        return studySetService.createNewStudySet(name, userId);
    }

}
