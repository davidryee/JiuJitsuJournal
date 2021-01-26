package com.David.JiuJitsuJournal.Data.Entities;

import javax.persistence.*;

@Entity
@Table(name="opponents")
public class Opponent {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "beltRankId")
    private int beltRankId;

    @Column(name = "heightInInches")
    private int heightInInches;

    @Column(name = "weightInLbs")
    private int weightInLbs;

    public Opponent(){

    }

    public Opponent(String name, int beltRankId){
        this.name = name;
        this.beltRankId = beltRankId;
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

    public int getBeltRankId(){
        return this.beltRankId;
    }

    public void setBeltRankId(int beltRankId){
        this.beltRankId = beltRankId;
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
