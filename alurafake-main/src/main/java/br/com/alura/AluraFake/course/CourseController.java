package br.com.alura.AluraFake.course;

import br.com.alura.AluraFake.task.Task;
import br.com.alura.AluraFake.task.TaskRepository;
import br.com.alura.AluraFake.task.Type;
import br.com.alura.AluraFake.user.*;
import br.com.alura.AluraFake.util.ErrorItemDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
public class CourseController {

    private final CourseRepository courseRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Autowired
    public CourseController(CourseRepository courseRepository, UserRepository userRepository, TaskRepository taskRepository){
        this.courseRepository = courseRepository;
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    @PostMapping("/course/new")
    public ResponseEntity createCourse(@Valid @RequestBody NewCourseDTO newCourse) {

        Optional<User> possibleAuthor = userRepository
                .findByEmail(newCourse.getEmailInstructor())
                .filter(User::isInstructor);

        if(possibleAuthor.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorItemDTO("emailInstructor", "User is not a instructor"));
        }

        Course course = new Course(newCourse.getTitle(), newCourse.getDescription(), possibleAuthor.get());

        courseRepository.save(course);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Transactional
    @PostMapping("/course/{id}/publish")
    public ResponseEntity<String> publishCourse(@PathVariable Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Course not found."));

        if (!course.getStatus().equals(Status.BUILDING)) {
            return ResponseEntity.badRequest().body("Only courses with status BUILDING can be published.");
        }

        List<Task> tasks = taskRepository.findByCourseOrderByPositionAsc(course);
        if (tasks.isEmpty()) {
            return ResponseEntity.badRequest().body("Course must have activities before publishing.");
        }

        boolean hasOpenText = false;
        boolean hasSingleChoice = false;
        boolean hasMultipleChoice = false;

        for (Task task : tasks) {
            if (task.getType() == Type.OPEN_TEXT) hasOpenText = true;
            if (task.getType() == Type.SINGLE_CHOICE) hasSingleChoice = true;
            if (task.getType() == Type.MULTIPLE_CHOICE) hasMultipleChoice = true;
        }

        if (!hasOpenText || !hasSingleChoice || !hasMultipleChoice) {
            return ResponseEntity.badRequest().body("Course must have at least one task of each type (OpenText, SingleChoice, MultipleChoice).");
        }

        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getPosition() != i + 1) {
                return ResponseEntity.badRequest().body("Task positions must be continuous (1, 2, 3...).");
            }
        }

        course.setStatus(Status.PUBLISHED);
        course.setPublishedAt(LocalDateTime.now());
        courseRepository.save(course);

        return ResponseEntity.ok("Course published successfully!");
    }


    @GetMapping("/course/all")
    public ResponseEntity<List<CourseListItemDTO>> createCourse() {
        List<CourseListItemDTO> courses = courseRepository.findAll().stream()
                .map(CourseListItemDTO::new)
                .toList();
        return ResponseEntity.ok(courses);
    }
}
