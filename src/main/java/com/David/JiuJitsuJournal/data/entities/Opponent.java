package com.David.JiuJitsuJournal.data.entities;

import javax.persistence.*;

@Entity
@Table(name="opponents")
public class Opponent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "beltRankId")
    private BeltRank beltRank;

    @Column(name = "heightInInches")
    private int heightInInches;

    @Column(name = "weightInLbs")
    private int weightInLbs;

    public Opponent(){

    }

    public Opponent(String name, BeltRank beltRank, int heightInInches, int weightInLbs){
        this.name = name;
        this.beltRank = beltRank;
        this.heightInInches = heightInInches;
        this.weightInLbs = weightInLbs;
    }

    public long getId(){
        return id;
    }

    public void setId(long id){
        this.id = id;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public BeltRank getBeltRank(){
        return this.beltRank;
    }

    public void setBeltRank(BeltRank beltRank){
        this.beltRank = beltRank;
    }

    public int getHeightInInches() {
        return heightInInches;
    }

    public void setHeightInInches(int heightInInches) {
        this.heightInInches = heightInInches;
    }

    public int getWeightInLbs() {
        return weightInLbs;
    }

    public void setWeightInLbs(int weightInLbs) {
        this.weightInLbs = weightInLbs;
    }
}
