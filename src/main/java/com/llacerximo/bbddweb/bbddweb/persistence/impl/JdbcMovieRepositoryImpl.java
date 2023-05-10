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
import com.llacerximo.bbddweb.bbddweb.persistence.MovieRepository;

public class JdbcMovieRepositoryImpl implements MovieRepository{

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
        try (Connection connection = DBUtil.getConnection();){
            String sql = """
                insert into movies(imdb_id, title, year, runtime, director_id) values (?, ?, ?, ?, ?)
                """;
            List<Object> params = List.of(
                    movie.getId(),
                    movie.getTitle(),
                    movie.getYear(),
                    movie.getRuntime(),
                    movie.getDirector().getId()
            );
            DBUtil.insert(connection, sql, params);
            for (Actor actor : movie.getActors()) {
                String sqlActors = """
                    insert into actors_movies(actor_id, movie_id) values (?, ?)
                """;
                DBUtil.insert(connection, sqlActors, List.of(actor.getId(), movie.getId()));
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(String id) throws ResourceNotFoundException {
        try (Connection connection = DBUtil.getConnection()){
            String sql = "delete from movies where imdb_id = ?";
            List<Object> params = List.of(id);
            if (DBUtil.delete(connection, sql, params) < 0){
                throw new ResourceNotFoundException("Pelicula no encontrada");
            };
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Movie movie) throws ResourceNotFoundException {
        try (Connection connection = DBUtil.getConnection();){

            String sql = """
                        UPDATE movies SET
                            title = ?,
                            year = ?,
                            runtime = ?,
                            director_id = ?
                        WHERE imdb_id = ?
                    """;
            List<Object> params = List.of(
                    movie.getTitle(),
                    movie.getYear(),
                    movie.getRuntime(),
                    movie.getDirector().getId(),
                    movie.getId()
            );
            DBUtil.update(connection, sql, params);

            /*Borrar actores*/
            String deleteActors = "Delete from actors_movies where movie_id = ?";
            DBUtil.delete(connection, deleteActors, List.of(movie.getId()));

            /*Reinsertar actores*/
            for (Actor actor : movie.getActors()) {
                String sqlActors = """
                    insert into actors_movies(actor_id, movie_id) values (?, ?)
                """;
                DBUtil.insert(connection, sqlActors, List.of(actor.getId(), movie.getId()));
            }
            DBUtil.closeConnection(connection);

        } catch (SQLException e){
            throw new RuntimeException(e);
        }

    }

    @Override
    public int count() {
        try(Connection connection = DBUtil.getConnection()){
            String sql = "Select count(*) from movies";
            ResultSet resultSet = DBUtil.select(connection, sql, null);
            int totalMovies = 0;
            if (resultSet.next()){
                totalMovies = resultSet.getInt(1);
            }
            return totalMovies;
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Movie> paginate(int index, int MOVIES_PER_PAGE) {
        try(Connection connection = DBUtil.getConnection()){
            List<Movie> movies = new ArrayList<>();
            String sql = """
                        select * from movies limit (?, ?)
                    """;
            ResultSet resultSet = DBUtil.select(connection, sql, List.of(index, MOVIES_PER_PAGE));
            while(resultSet.next()){
                Movie movie = new Movie(
                        resultSet.getString("imdb_id"),
                        resultSet.getString("title"),
                        resultSet.getInt("year"),
                        resultSet.getInt("runtime")
                );
                movies.add(movie);
            }
            return movies;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
