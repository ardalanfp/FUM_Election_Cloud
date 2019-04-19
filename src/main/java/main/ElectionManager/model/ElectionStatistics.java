package main.ElectionManager.model;


import javax.persistence.*;

@Entity
@Table(name= "electionStatistics")
public class ElectionStatistics {


    static {
    }

    @Id
    private int id = 1;


    private static int staticCounter = 0;

    public static  void incrementVoteCounter(){
        staticCounter++;
    }

    public static  int getCounter() {
        return staticCounter;
    }

    public static void setCounter(int counter){
        staticCounter = counter;
    }

    @Column
    private int counterColoumn;

    public int getCounterColoumn() {
        return counterColoumn;
    }

    public void setCounterColoumn(int counterColoumn) {
        this.counterColoumn = counterColoumn;
    }


    public ElectionStatistics() {
    }

    public ElectionStatistics(int counterColoumn) {
        this.counterColoumn = counterColoumn;
    }


}
