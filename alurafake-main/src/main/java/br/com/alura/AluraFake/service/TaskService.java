package br.com.alura.AluraFake.service;

import br.com.alura.AluraFake.domain.*;
import br.com.alura.AluraFake.repository.CourseRepository;
import br.com.alura.AluraFake.enums.Status;
import br.com.alura.AluraFake.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final CourseRepository courseRepository;

    public TaskService(TaskRepository taskRepository, CourseRepository courseRepository) {
        this.taskRepository = taskRepository;
        this.courseRepository = courseRepository;
    }

    public MultipleChoiceTask saveMultipleChoiceTask(MultipleChoiceTask task) {
        List<Option> options = task.getOptions();

        if (options == null || options.size() < 3 || options.size() > 5) {
            throw new IllegalArgumentException("A multiple choice question must have between 3 and 5 options.");
        }

        if (task.getStatement() == null || task.getStatement().length() < 4 || task.getStatement().length() > 255) {
            throw new IllegalArgumentException("Statement must have between 4 and 255 characters.");
        }

        int correctCount = 0;
        for (Option option : options) {
            if (option.isCorrect()) {
                correctCount++;
            }
        }
        if (correctCount < 2 || correctCount == options.size()) {
            throw new IllegalArgumentException("There must be at least two correct options and at least one incorrect option.");
        }

        List<String> texts = new ArrayList<>();
        for (Option option : options) {
            String text = option.getOption();

            if (text.length() < 4 || text.length() > 80) {
                throw new IllegalArgumentException("Each option must have between 4 and 80 characters.");
            }
            if (text.equalsIgnoreCase(task.getStatement())) {
                throw new IllegalArgumentException("Option text cannot be the same as the statement.");
            }

            boolean duplicate = false;
            for (String t : texts) {
                if (t.equalsIgnoreCase(text)) {
                    duplicate = true;
                    break;
                }
            }
            if (duplicate) {
                throw new IllegalArgumentException("Options must be unique.");
            }

            texts.add(text);
        }

        Course course = courseRepository.findById(task.getCourse().getId())
                .orElseThrow(() -> new IllegalArgumentException("Course not found."));

        if (!course.getStatus().equals(Status.BUILDING)) {
            throw new IllegalArgumentException("Only courses with status BUILDING can receive tasks.");
        }

        List<Task> existingTasks = taskRepository.findByCourseOrderByPositionAsc(course);
        int requestedOrder = task.getPosition();

        if (existingTasks.size() + 1 < requestedOrder) {
            throw new IllegalArgumentException("Invalid order sequence. There are missing previous orders.");
        }

        for (Task existing : existingTasks) {
            if (existing.getPosition() >= requestedOrder) {
                existing.setPosition(existing.getPosition() + 1);
                taskRepository.save(existing);
            }
        }

        boolean exists = taskRepository.existsByCourseAndStatement(course, task.getStatement());
        if (exists) {
            throw new IllegalArgumentException("A task with this statement already exists in the course.");
        }

        MultipleChoiceTask savedTask = taskRepository.save(task);
        for (Option option : options) {
            option.setTask(savedTask);
        }

        return savedTask;
    }

    public SingleChoiceTask saveSingleChoiceTask(SingleChoiceTask task) {
        List<Option> options = task.getOptions();

        if (options == null || options.size() < 2 || options.size() > 5) {
            throw new IllegalArgumentException("A single choice question must have between 2 and 5 options.");
        }

        if (task.getStatement() == null || task.getStatement().length() < 4 || task.getStatement().length() > 255) {
            throw new IllegalArgumentException("Statement must have between 4 and 255 characters.");
        }

        int correctCount = 0;
        for (Option option : options) {
            if (option.isCorrect()) {
                correctCount++;
            }
        }
        if (correctCount > 1 || correctCount == options.size()) {
            throw new IllegalArgumentException("There must be only one correct option.");
        }

        List<String> texts = new ArrayList<>();
        for (Option option : options) {
            String text = option.getOption();

            if (text.length() < 4 || text.length() > 80) {
                throw new IllegalArgumentException("Each option must have between 4 and 80 characters.");
            }
            if (text.equalsIgnoreCase(task.getStatement())) {
                throw new IllegalArgumentException("Option text cannot be the same as the statement.");
            }

            boolean duplicate = false;
            for (String t : texts) {
                if (t.equalsIgnoreCase(text)) {
                    duplicate = true;
                    break;
                }
            }
            if (duplicate) {
                throw new IllegalArgumentException("Options must be unique.");
            }

            texts.add(text);
        }

        Course course = courseRepository.findById(task.getCourse().getId())
                .orElseThrow(() -> new IllegalArgumentException("Course not found."));

        if (!course.getStatus().equals(Status.BUILDING)) {
            throw new IllegalArgumentException("Only courses with status BUILDING can receive tasks.");
        }

        List<Task> existingTasks = taskRepository.findByCourseOrderByPositionAsc(course);
        int requestedOrder = task.getPosition();

        if (existingTasks.size() + 1 < requestedOrder) {
            throw new IllegalArgumentException("Invalid order sequence. There are missing previous orders.");
        }

        for (Task existing : existingTasks) {
            if (existing.getPosition() >= requestedOrder) {
                existing.setPosition(existing.getPosition() + 1);
                taskRepository.save(existing);
            }
        }

        boolean exists = taskRepository.existsByCourseAndStatement(course, task.getStatement());
        if (exists) {
            throw new IllegalArgumentException("A task with this statement already exists in the course.");
        }

        SingleChoiceTask savedTask = taskRepository.save(task);
        for (Option option : options) {
            option.setTask(savedTask);
        }

        return savedTask;
    }

    public OpenTextTask saveOpenTextTask(OpenTextTask  task) {

        if (task.getStatement() == null || task.getStatement().length() < 4 || task.getStatement().length() > 255) {
            throw new IllegalArgumentException("Statement must have between 4 and 255 characters.");
        }

        Course course = courseRepository.findById(task.getCourse().getId())
                .orElseThrow(() -> new IllegalArgumentException("Course not found."));

        if (!course.getStatus().equals(Status.BUILDING)) {
            throw new IllegalArgumentException("Only courses with status BUILDING can receive tasks.");
        }

        List<Task> existingTasks = taskRepository.findByCourseOrderByPositionAsc(course);
        int requestedOrder = task.getPosition();

        if (existingTasks.size() + 1 < requestedOrder) {
            throw new IllegalArgumentException("Invalid task order. The sequence must be continuous.");
        }

        for (Task existing : existingTasks) {
            if (existing.getPosition() >= requestedOrder) {
                existing.setPosition(existing.getPosition() + 1);
                taskRepository.save(existing);
            }
        }

        boolean exists = taskRepository.existsByCourseAndStatement(course, task.getStatement());
        if (exists) {
            throw new IllegalArgumentException("A task with this statement already exists in the course.");
        }

        return taskRepository.save(task);
    }
}