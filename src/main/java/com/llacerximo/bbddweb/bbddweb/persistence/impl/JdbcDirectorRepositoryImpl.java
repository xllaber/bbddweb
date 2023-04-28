package com.llacerximo.bbddweb.bbddweb.persistence.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.llacerximo.bbddweb.bbddweb.business.entity.Director;
import com.llacerximo.bbddweb.bbddweb.database.DBUtil;
import com.llacerximo.bbddweb.bbddweb.exceptions.ResourceNotFoundException;
import com.llacerximo.bbddweb.bbddweb.persistence.DirectorRepository;

public class JdbcDirectorRepositoryImpl implements DirectorRepository{

    @Override
    public List<Director> getAll() {
        try {
            List<Director> directors = new ArrayList<>();
            Connection connection = DBUtil.getConnection();
            String sql = "select * from directors";
            ResultSet resultSet = DBUtil.select(connection, sql, null);
            while (resultSet.next()) {
                Director director = new Director(
                    resultSet.getString("imdb_id"),
                    resultSet.getString("name"),
                    resultSet.getInt("birthYear"),
                    resultSet.getInt("deathYear")
                );
                directors.add(director);
            }
            DBUtil.closeConnection(connection);
            return directors;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        
    }

    @Override
    public Director getById(String id) throws ResourceNotFoundException, SQLException{
        try {
            Connection connection = DBUtil.getConnection();
            String sql = """
                    select * from directors where imdb_id = ?
                    """;
            List<Object> params = List.of(id);
            ResultSet resultSet = DBUtil.select(connection, sql, params);
            if (resultSet.next()) {
                    Director director = new Director(
                    resultSet.getString("imdb_id"),
                    resultSet.getString("name"),
                    resultSet.getInt("birthYear"),
                    resultSet.getInt("deathYear")
                );
                DBUtil.closeConnection(connection);
                return director;
            }else {
                throw new ResourceNotFoundException("Director no encontrado");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Director getByMovieId(String id) throws ResourceNotFoundException, SQLException{
        try {
            Connection connection = DBUtil.getConnection();
            String sql = """
                    select * from director d inner join movies m on d.imdb_id = m.director_id
                    where m.imdb_id = ?
                    """;
            List<Object> params = List.of(id);
            ResultSet resultSet = DBUtil.select(connection, sql, params);
            if (resultSet.next()) {
                Director director = new Director(
                resultSet.getString("imdb_id"),
                resultSet.getString("title"),
                resultSet.getInt("birthYear"),
                resultSet.getInt("deathYear")
            );
            DBUtil.closeConnection(connection);
            System.out.println(director);
            return director;
            }else {
                throw new ResourceNotFoundException("Director no encontrado");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
}
