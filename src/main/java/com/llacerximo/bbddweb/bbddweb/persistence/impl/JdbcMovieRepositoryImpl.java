package com.llacerximo.bbddweb.bbddweb.persistence.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.llacerximo.bbddweb.bbddweb.business.entity.Actor;
import com.llacerximo.bbddweb.bbddweb.business.entity.Director;
import com.llacerximo.bbddweb.bbddweb.business.entity.Movie;
import com.llacerximo.bbddweb.bbddweb.database.DBUtil;
import com.llacerximo.bbddweb.bbddweb.exceptions.ResourceNotFoundException;
import com.llacerximo.bbddweb.bbddweb.persistence.ActorRepository;
import com.llacerximo.bbddweb.bbddweb.persistence.MovieRepository;

public class JdbcMovieRepositoryImpl implements MovieRepository{

    // private ActorRepository actorRepository = new JdbcActorRepositoryImpl();

    @Override
    public List<Movie> getAll() {
        try {
            List<Movie> movies = new ArrayList<>();
            Connection connection = DBUtil.getConnection();
            String sql = "select * from movies";
            ResultSet resultSet = DBUtil.select(connection, sql, null);
            while (resultSet.next()) {
                Movie movie = new Movie(
                    resultSet.getString("imdb_id"),
                    resultSet.getString("title"),
                    resultSet.getInt("year"),
                    resultSet.getInt("runtime")
                );
                movies.add(movie);
            }
            DBUtil.closeConnection(connection);
            return movies;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        
    }

    @Override
    public Movie getById(String id) throws SQLException, ResourceNotFoundException {
        try {
            Connection connection = DBUtil.getConnection();
            String slq = """
                    select m.*, d.imdb_id as director_id, d.name, d.birthYear, d.deathYear
                    from movies m inner join directors d on m.director_id = d.imdb_id
                    where m.imdb_id = ?
                    """;
            List<Object> params = List.of(id);
            ResultSet resultSet = DBUtil.select(connection, slq, params);
            if (resultSet.next()) {
                    Movie movie = new Movie(
                    resultSet.getString("imdb_id"),
                    resultSet.getString("title"),
                    resultSet.getInt("year"),
                    resultSet.getInt("runtime")
                );
                Director director = new Director(
                    resultSet.getString("director_id"),
                    resultSet.getString("name"),
                    resultSet.getInt("birthYear"),
                    resultSet.getInt("deathYear")
                );
                movie.setDirector(director);
                // movie.setActors(actorRepository.getByMovieId(id));
                DBUtil.closeConnection(connection);
                return movie;
            }else {
                throw new ResourceNotFoundException("Pelíclua no encontrada");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insert(Movie movie) throws SQLException {
        Connection connection = DBUtil.getConnection();
        String sql = """
                insert into movies(imdb_id, title, year, director_id) values (?, ?, ?, ?)
                """;
        List<Object> params = List.of(
            movie.getId(), 
            movie.getTitle(), 
            movie.getYear(), 
            movie.getDirector().getId()
        );
        DBUtil.insert(connection, sql, params);
        String sqlActors = """
                    insert into actors_movies(actor_id, movie_id, characters) values (?, ?, ?)
                    """;
        for (Actor actor : movie.getActors()) {
            List<Object> paramsActor = List.of(
                movie.getId(),
                actor.getId(),
                actor.getName()
            );
            DBUtil.insert(connection, sqlActors, paramsActor);
        }
        DBUtil.closeConnection(connection);
    }
    
}
