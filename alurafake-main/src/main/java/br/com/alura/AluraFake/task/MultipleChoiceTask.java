package br.com.alura.AluraFake.task;

import br.com.alura.AluraFake.course.Course;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;

import java.util.List;

public class MultipleChoiceTask extends Task {

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Option> options;

    public MultipleChoiceTask() {
        super(id, title, description);
        setType(Type.MULTIPLE_CHOICE);
    }

    public MultipleChoiceTask(String statement, int position, Course course, List<Option> options) {
        super(null, statement, position, Type.MULTIPLE_CHOICE, course);
        this.options = options;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }
}
