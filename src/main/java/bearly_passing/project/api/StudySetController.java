package bearly_passing.project.api;

import org.springframework.web.bind.annotation.SessionAttributes;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;

import bearly_passing.project.data.StudySetRepository;
import bearly_passing.project.domain.StudySet;

@Controller
@RequestMapping("/set")
@SessionAttributes("studySet")
public class StudySetController {

    private final StudySetRepository studySetRepository;

    public StudySetController(StudySetRepository studySetRepository) {
        this.studySetRepository = studySetRepository;
    }

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
    
}
