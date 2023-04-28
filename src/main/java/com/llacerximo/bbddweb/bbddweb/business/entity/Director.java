package com.llacerximo.bbddweb.bbddweb.business.entity;

public class Director {
    
    private String id;
    private String name;
    private int birthYear;
    private int deathYear;

    public Director(String id, String name, int birthYear, int deathYear) {
        this.id = id;
        this.name = name;
        this.birthYear = birthYear;
        this.deathYear = deathYear;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public int getDeathYear() {
        return deathYear;
    }

    @Override
    public String toString() {
        return "Director [id=" + id + ", name=" + name + ", birthYear=" + birthYear + ", deathYear=" + deathYear + "]";
    }

}
