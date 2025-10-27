package br.com.alura.AluraFake.user;

import br.com.alura.AluraFake.task.*;
import br.com.alura.AluraFake.course.Course;
import br.com.alura.AluraFake.course.CourseRepository;
import br.com.alura.AluraFake.course.Status;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TaskServiceTest {

    @Test
    void shouldNotAllowTaskIfCourseIsNotBuilding() {
        TaskRepository taskRepository = mock(TaskRepository.class);
        OptionRepository optionRepository = mock(OptionRepository.class);
        CourseRepository courseRepository = mock(CourseRepository.class);

        TaskService service = new TaskService(taskRepository, courseRepository);

        Course course = new Course();
        course.setId(1L);
        course.setStatus(Status.PUBLISHED);

        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));

        MultipleChoiceTask task = new MultipleChoiceTask();
        task.setCourse(course);
        task.setStatement("Example Question");
        task.setPosition(1);

        Option o1 = new Option(null, "Java", true, task);
        Option o2 = new Option(null, "Python", false, task);
        Option o3 = new Option(null, "Kotlin", true, task);
        task.setOptions(List.of(o1, o2, o3));

        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            service.saveMultipleChoiceTask(task);
        });

        assertEquals("Only courses with status BUILDING can receive tasks.", ex.getMessage());
    }

    @Test
    void shouldThrowErrorWhenLessThanThreeOptions() {
        TaskRepository taskRepository = mock(TaskRepository.class);
        OptionRepository optionRepository = mock(OptionRepository.class);
        CourseRepository courseRepository = mock(CourseRepository.class);

        TaskService service = new TaskService(taskRepository, courseRepository);

        Course course = new Course();
        course.setId(1L);
        course.setStatus(Status.BUILDING);
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));

        MultipleChoiceTask task = new MultipleChoiceTask();
        task.setCourse(course);
        task.setStatement("Example Question");
        task.setPosition(1);

        Option o1 = new Option(null, "Java", true, task);
        Option o2 = new Option(null, "Python", false, task);
        task.setOptions(List.of(o1, o2));

        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            service.saveMultipleChoiceTask(task);
        });

        assertEquals("A multiple choice question must have between 3 and 5 options.", ex.getMessage());
    }

}

