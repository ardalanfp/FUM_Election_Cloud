package main.ElectionManager.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name= "ElectionChoices")
public class ElectionChoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int id;

    @ManyToOne
    @JoinColumn
    @NotNull
    private Election election;

    @Column
    @NotNull
    private String choice;

    public ElectionChoice() {
    }

    public ElectionChoice(Election election, String choice) {
        this.election = election;
        this.choice = choice;
    }

    public Election getElection() {
        return election;
    }

    public void setElection(Election election) {
        this.election = election;
    }

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    public int getId() {
        return id;
    }

}
