package br.com.alura.AluraFake.domain;

import br.com.alura.AluraFake.enums.Type;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "singlechoicetask")
@DiscriminatorValue("SINGLE")
public class SingleChoiceTask extends Task {

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Option> options;

    public SingleChoiceTask() {
    }

    public SingleChoiceTask(Long id, String statement, int position, List<Option> options, Type type, Course course) {
        super(id, statement, position, options, type, course);
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
