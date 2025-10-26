package br.com.alura.AluraFake.task;

import br.com.alura.AluraFake.course.Course;
import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
@DiscriminatorValue("SINGLE")
public class SingleChoiceTask extends Task {

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Option> options;

    public SingleChoiceTask() {
    }

    public SingleChoiceTask(Long id, String statement, int position, Type type, Course course, List<Option> options) {
        super(id, statement, position, type, course);
        this.options = options;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    public boolean hasExactlyOneCorrectOption() {
        return options != null && options.stream().filter(Option::isCorrect).count() == 1;
    }
}
