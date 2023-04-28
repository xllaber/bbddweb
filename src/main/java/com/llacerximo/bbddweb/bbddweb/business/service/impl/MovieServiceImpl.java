package com.llacerximo.bbddweb.bbddweb.business.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.llacerximo.bbddweb.bbddweb.business.entity.Movie;
import com.llacerximo.bbddweb.bbddweb.business.service.MovieService;
import com.llacerximo.bbddweb.bbddweb.exceptions.ResourceNotFoundException;
import com.llacerximo.bbddweb.bbddweb.persistence.ActorRepository;
// import com.llacerximo.bbddweb.bbddweb.persistence.DirectorRepository;
import com.llacerximo.bbddweb.bbddweb.persistence.MovieRepository;
import com.llacerximo.bbddweb.bbddweb.persistence.impl.JdbcActorRepositoryImpl;
// import com.llacerximo.bbddweb.bbddweb.persistence.impl.JdbcDirectorRepositoryImpl;
import com.llacerximo.bbddweb.bbddweb.persistence.impl.JdbcMovieRepositoryImpl;

public class MovieServiceImpl implements MovieService {

    private MovieRepository movieRepository = new JdbcMovieRepositoryImpl();
    // private DirectorRepository directorRepository = new JdbcDirectorRepositoryImpl();
    private ActorRepository actorRepository = new JdbcActorRepositoryImpl();

    @Override
    public List<Movie> getAll() {
        return movieRepository.getAll();
    }

    @Override
    public Movie getById(String id) throws SQLException, ResourceNotFoundException {
        Movie movie = movieRepository.getById(id);
        System.out.println(movie.getDirector());
        movie.setActors(actorRepository.getByMovieId(id));
        return movie;
    }

    @Override
    public void insert(Movie movie) throws SQLException {
        movieRepository.insert(movie);
    }
    
}
