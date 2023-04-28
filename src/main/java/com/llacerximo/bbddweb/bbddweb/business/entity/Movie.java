package com.llacerximo.bbddweb.bbddweb.business.entity;

import java.util.ArrayList;
import java.util.List;

public class Movie {
    
    private String id;
    private String title;
    private int runtime;
    private int year;

    private String img; 
    private Director director;
    private List<Actor> actors;

    
    public Movie(String id, String title, int year, int runtime) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.runtime = runtime;
    }

    public Movie(String id, String title, int runtime, String img, Director director, List<Actor> actors) {
        this.id = id;
        this.title = title;
        this.runtime = runtime;
        this.img = img;
        this.director = director;
        this.actors = actors;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public Director getDirector() {
        return director;
    }

    public void setDirector(Director director) {
        this.director = director;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

    public void setActor(Actor actor) {
        if (actors == null) {
            actors = new ArrayList<>();
        }
        actors.add(actor);
    }

    @Override
    public String toString() {
        return "Movie [id=" + id + ", title=" + title + ", runtime=" + runtime + ", year=" + year + ", img=" + img
                + ", director=" + director + ", actors=" + actors + "]";
    }
    
}
