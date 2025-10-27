package br.com.alura.AluraFake.course;

import br.com.alura.AluraFake.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long>{

    List<Course> findByInstructor(User user);
}
