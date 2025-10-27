package br.com.alura.AluraFake.task;

import br.com.alura.AluraFake.course.Course;
import br.com.alura.AluraFake.course.CourseRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;
    private final CourseRepository courseRepository;

    public TaskController(TaskService taskService, CourseRepository courseRepository) {
        this.taskService = taskService;
        this.courseRepository = courseRepository;
    }

    @PostMapping("/new/singlechoice")
    public ResponseEntity<String> createSingleChoice(@RequestBody SingleChoiceTask task) {
        Course course = courseRepository.findById(task.getCourse().getId())
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        task.setCourse(course);
        task.setType(Type.SINGLE_CHOICE);
        taskService.saveSingleChoiceTask(task);

        return ResponseEntity.status(HttpStatus.CREATED).body("Single choice task created successfully!");
    }

    @PostMapping("/new/multiplechoice")
    public ResponseEntity<String> createMultipleChoice(@RequestBody MultipleChoiceTask task) {
        Course course = courseRepository.findById(task.getCourse().getId())
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        task.setCourse(course);
        task.setType(Type.MULTIPLE_CHOICE);
        taskService.saveMultipleChoiceTask(task);

        return ResponseEntity.status(HttpStatus.CREATED).body("Multiple choice task created successfully!");
    }

    @PostMapping("/new/opentext")
    public ResponseEntity<String> createOpenText(@RequestBody OpenTextTask task) {
        Course course = courseRepository.findById(task.getCourse().getId())
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        task.setCourse(course);
        task.setType(Type.OPEN_TEXT);
        taskService.saveOpenTextTask(task);

        return ResponseEntity.status(HttpStatus.CREATED).body("Open text task created successfully!");
    }
}
