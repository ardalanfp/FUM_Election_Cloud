package main.ElectionManager.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name= "elections")
public class Election {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true)
    @NotNull
    private String name;

    @Column
    @NotNull
    private Date startTime;

    @Column
    @NotNull
    private Date endTime;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }



    public Election(String name, Date startTime, Date endTime) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Election() {
    }
}
