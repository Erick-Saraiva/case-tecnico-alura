package br.com.alura.AluraFake.controller;

import br.com.alura.AluraFake.domain.Course;
import br.com.alura.AluraFake.domain.MultipleChoiceTask;
import br.com.alura.AluraFake.domain.OpenTextTask;
import br.com.alura.AluraFake.domain.SingleChoiceTask;
import br.com.alura.AluraFake.repository.CourseRepository;
import br.com.alura.AluraFake.service.TaskService;
import br.com.alura.AluraFake.enums.Type;
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
