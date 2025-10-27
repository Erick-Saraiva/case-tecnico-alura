package br.com.alura.AluraFake.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "task_option")
public class Option {

    public Option() {
    }

    public Option(Long id, String option, boolean isCorrect, Task task) {
        this.id = id;
        this.option = option;
        this.isCorrect = isCorrect;
        this.task = task;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "option_text", nullable = false, length = 80)
    private String option;

    @Column(name = "is_correct", nullable = false)
    private boolean isCorrect;

    @ManyToOne(optional = false)
    @JoinColumn(name = "task_id")
    private Task task;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}

