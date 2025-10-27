package br.com.alura.AluraFake.repository;

import br.com.alura.AluraFake.domain.Course;
import br.com.alura.AluraFake.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByCourseOrderByPositionAsc(Course course);

    boolean existsByCourseAndStatement(Course course, String statement);
}
