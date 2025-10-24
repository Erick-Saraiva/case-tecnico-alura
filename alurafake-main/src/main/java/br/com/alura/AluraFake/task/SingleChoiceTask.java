package br.com.alura.AluraFake.task;

import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;

import javax.swing.text.html.Option;
import java.util.List;

public class SingleChoiceTask extends Task{

    public SingleChoiceTask() {
    }

    public SingleChoiceTask(Long id, String statement, int order, Type type, Course course, List<Option> options) {
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
