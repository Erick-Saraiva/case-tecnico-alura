package br.com.alura.AluraFake.repository;

import br.com.alura.AluraFake.domain.Course;
import br.com.alura.AluraFake.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long>{

    List<Course> findByInstructor(User user);
}
