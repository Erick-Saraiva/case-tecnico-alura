package br.com.alura.AluraFake.task;

import br.com.alura.AluraFake.course.Course;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;

import java.util.List;

public class MultipleChoiceTask extends Task{

    public MultipleChoiceTask() {
    }

    public MultipleChoiceTask(Long id, String statement, int order, Type type, Course course, List<Option> options) {
        super(id, statement, order, type, course);
        this.options = options;
    }

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    private List<Option> options;

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }
}
