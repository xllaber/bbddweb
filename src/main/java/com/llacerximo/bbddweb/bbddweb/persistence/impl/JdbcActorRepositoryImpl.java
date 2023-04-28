package com.llacerximo.bbddweb.bbddweb.persistence.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.llacerximo.bbddweb.bbddweb.business.entity.Actor;
import com.llacerximo.bbddweb.bbddweb.database.DBUtil;
import com.llacerximo.bbddweb.bbddweb.exceptions.ResourceNotFoundException;
import com.llacerximo.bbddweb.bbddweb.persistence.ActorRepository;

public class JdbcActorRepositoryImpl implements ActorRepository{
    
    @Override
    public List<Actor> getAll() {
        try {
            List<Actor> actors = new ArrayList<>();
            Connection connection = DBUtil.getConnection();
            String sql = "select * from actors";
            ResultSet resultSet = DBUtil.select(connection, sql, null);
            while (resultSet.next()) {
                Actor actor = new Actor(
                    resultSet.getString("imdb_id"),
                    resultSet.getString("name"),
                    resultSet.getInt("birthYear"),
                    resultSet.getInt("deathYear")
                );
                actors.add(actor);
            }
            DBUtil.closeConnection(connection);
            return actors;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Actor getById(String id) throws SQLException, ResourceNotFoundException{
        try{
            Connection connection = DBUtil.getConnection();
            String sql = """
                    select * from actors where imdb_id = ?
                    """;
            List<Object> params = List.of(id);
            ResultSet resultSet = DBUtil.select(connection, sql, params);
            if (resultSet.next()) {
                    Actor actor = new Actor(
                    resultSet.getString("imdb_id"),
                    resultSet.getString("name"),
                    resultSet.getInt("birthYear"),
                    resultSet.getInt("deathYear")
                );
                DBUtil.closeConnection(connection);
                return actor;
            }else {
                throw new ResourceNotFoundException("Pelíclua no encontrada");
            }
        } catch (SQLException e) {
        throw new RuntimeException(e);
        }
    }

    @Override
    public List<Actor> getByMovieId(String id) throws ResourceNotFoundException, SQLException{
        try {
            List<Actor> actors = new ArrayList<>();
            Connection connection = DBUtil.getConnection();
            String sql = """
                    select a.* from actors a inner join actors_movies m on a.imdb_id = m.actor_id
                    where m.movie_id = ?
                    """;
            List<Object> params = List.of(id);
            ResultSet resultSet = DBUtil.select(connection, sql, params);
            while (resultSet.next()) {
                Actor actor = new Actor(
                    resultSet.getString("imdb_id"),
                    resultSet.getString("name"),
                    resultSet.getInt("birthYear"),
                    resultSet.getInt("deathYear")
                );
                actors.add(actor);
            }
            if (actors.isEmpty()) {
                throw new ResourceNotFoundException("No se han encontrado los actores");
            }
            DBUtil.closeConnection(connection);
            return actors;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
