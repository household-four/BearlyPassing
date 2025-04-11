package bearly_passing.project.api;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import bearly_passing.project.domain.Student;
import bearly_passing.project.domain.Teacher;
import bearly_passing.project.domain.User;
import bearly_passing.project.domain.UserRole;
import bearly_passing.project.services.AdminService;
import bearly_passing.project.services.UserService;

@WebMvcTest(controllers = AdminController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private AdminService adminService;
    
    private Student student;
    private Student student2;
    private Student student3;
    private Teacher teacher;

    private List<User> users;

    @BeforeEach
    public void init() {
        student = new Student();
            student.setId(11111111L);
            student.setUsername("Baylor");
            student.setRole(UserRole.STUDENT);

        student2 = new Student();
            student2.setId(22222222L);
            student2.setUsername("Bear");
            student2.setRole(UserRole.STUDENT);

        student3 = new Student();
            student3.setId(33333333L);
            student3.setUsername("Green");
            student3.setRole(UserRole.STUDENT);

        teacher = new Teacher();
            teacher.setId(44444444L);
            teacher.setName("Gold");

        users = new ArrayList<>();
        users.add(student);
        users.add(student2);
        users.add(student3);
    }

    @Test
    public void AdminController_GetAllUsers_ReturnsUsers() throws Exception {
        when(userService.getAllUsers()).thenReturn(users);
        
        mockMvc.perform(get("/api/admin/users"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(users.size()));
    }

    @Test
    public void AdminController_PopulateDummyData_ReturnsStringSuccess() throws Exception {
        mockMvc.perform(post("/api/admin/populate"))
                .andExpect(status().isOk())
                .andExpect(content().string("Dummy data populated successfully!"));

        verify(adminService).populateDummyData();
    }

    @Test
    public void AdminController_GetStudentGrade_ReturnsGrade() throws Exception {
        when(adminService.studentGrade(1L)).thenReturn("John: 95.0");

        mockMvc.perform(get("/api/admin/grades").param("studentID", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string("John: 95.0"));
    }

    @Test
    public void AdminController_GetClass_ReturnsClassInfo() throws Exception {
        when(adminService.teacherClass(1L)).thenReturn("Teacher: Smith\n\nStudents:\nA\nB");

        mockMvc.perform(get("/api/admin/class").param("teacherID", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Teacher: Smith\n\nStudents:\nA\nB"));
    }
}
