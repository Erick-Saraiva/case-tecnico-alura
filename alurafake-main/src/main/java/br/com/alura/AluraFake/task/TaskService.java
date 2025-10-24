package br.com.alura.AluraFake.task;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final OptionRepository optionRepository;

    public TaskService(TaskRepository taskRepository, OptionRepository optionRepository) {
        this.taskRepository = taskRepository;
        this.optionRepository = optionRepository;
    }

    public MultipleChoiceTask saveMultipleChoiceTask(MultipleChoiceTask task) {
        List<Option> options = task.getOptions();

        if (options == null || options.size() < 3 || options.size() > 5) {
            throw new IllegalArgumentException("A multiple choice question must have between 3 and 5 options.");
        }

        String statement = task.getStatement();
        if (statement == null || statement.length() < 4 || statement.length() > 255) {
            throw new IllegalArgumentException("Statement must have between 4 and 255 characters.");
        }

        int correctCount = 0;
        for (Option option : options) {
            if (option.isCorrect()) {
                correctCount++;
            }
        }
        if (correctCount < 2 || correctCount == options.size()) {
            throw new IllegalArgumentException("There must be at least two correct options and at least one incorrect option.");
        }

        List<String> texts = new ArrayList<>();
        for (Option option : options) {
            String text = option.getOption();

            if (text.length() < 4 || text.length() > 80) {
                throw new IllegalArgumentException("Each option must have between 4 and 80 characters.");
            }
            if (text.equalsIgnoreCase(statement)) {
                throw new IllegalArgumentException("Option text cannot be the same as the statement.");
            }

            boolean duplicate = false;
            for (String t : texts) {
                if (t.equalsIgnoreCase(text)) {
                    duplicate = true;
                    break;
                }
            }
            if (duplicate) {
                throw new IllegalArgumentException("Options must be unique.");
            }

            texts.add(text);
        }

        // TODO: validate course status == BUILDING
        // TODO: handle task order reordering

        MultipleChoiceTask savedTask = taskRepository.save(task);
        for (Option option : options) {
            option.setTask(savedTask);
            optionRepository.save(option);
        }

        return savedTask;
    }
}