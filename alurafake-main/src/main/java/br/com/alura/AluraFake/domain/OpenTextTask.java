package br.com.alura.AluraFake.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "opentexttask")
public class OpenTextTask extends Task {
    public OpenTextTask() {
        super();
    }
}
