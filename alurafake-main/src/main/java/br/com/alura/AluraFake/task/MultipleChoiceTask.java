package br.com.alura.AluraFake.task;

import br.com.alura.AluraFake.course.Course;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "multiplechoicetask")
public class MultipleChoiceTask extends Task {

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Option> options;

    public MultipleChoiceTask() {
        setType(Type.MULTIPLE_CHOICE);
    }

    public MultipleChoiceTask(Long id, String statement, int position, Course course, List<Option> options) {
        super(id, statement, position, options, Type.MULTIPLE_CHOICE, course);
        this.options = options;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }
}
