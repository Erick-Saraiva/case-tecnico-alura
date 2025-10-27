package br.com.alura.AluraFake.controller;

import br.com.alura.AluraFake.domain.Course;
import br.com.alura.AluraFake.repository.CourseRepository;
import br.com.alura.AluraFake.enums.Status;
import br.com.alura.AluraFake.repository.TaskRepository;
import br.com.alura.AluraFake.enums.Role;
import br.com.alura.AluraFake.domain.User;
import br.com.alura.AluraFake.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/instructor")
public class InstructorController {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final TaskRepository taskRepository;

    public InstructorController(UserRepository userRepository,
                                CourseRepository courseRepository,
                                TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.taskRepository = taskRepository;
    }

    @GetMapping("/{id}/courses")
    public ResponseEntity<?> getInstructorCourses(@PathVariable Long id) {

        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found.");
        }

        if (!user.getRole().equals(Role.INSTRUCTOR)) {
            return ResponseEntity.badRequest()
                    .body("User is not an instructor.");
        }

        List<Course> courses = courseRepository.findByInstructor(user);

        List<Map<String, Object>> responseCourses = new ArrayList<>();
        int publishedCount = 0;

        for (Course course : courses) {
            Map<String, Object> data = new HashMap<>();
            data.put("id", course.getId());
            data.put("title", course.getTitle());
            data.put("status", course.getStatus());
            data.put("publishedAt", course.getPublishedAt());

            int taskCount = taskRepository.findByCourseOrderByPositionAsc(course).size();
            data.put("taskCount", taskCount);

            if (course.getStatus() == Status.PUBLISHED) {
                publishedCount++;
            }

            responseCourses.add(data);
        }

        Map<String, Object> finalResponse = new HashMap<>();
        finalResponse.put("courses", responseCourses);
        finalResponse.put("totalPublished", publishedCount);

        return ResponseEntity.ok(finalResponse);
    }
}
