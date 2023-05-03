package com.llacerximo.bbddweb.bbddweb.business.entity;

public class Actor {
    
    private String id;
    private String name;
    private int birthYear;
    private int deathYear;
    
    public Actor(String id, String name, int birthYear, int deathYear) {
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
        return "Actor [id=" + id + ", name=" + name + ", birthYear=" + birthYear + ", deathYear=" + deathYear + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Actor other = (Actor) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
