package com.llacerximo.bbddweb.bbddweb.persistence;

import java.sql.SQLException;
import java.util.List;

import com.llacerximo.bbddweb.bbddweb.business.entity.Movie;
import com.llacerximo.bbddweb.bbddweb.exceptions.ResourceNotFoundException;

public interface MovieRepository {
    
    List<Movie> getAll();
    Movie getById(String id) throws SQLException, ResourceNotFoundException;
    void insert(Movie movie) throws SQLException;
    void delete(String id) throws SQLException, ResourceNotFoundException;
    void update(Movie movie) throws SQLException, ResourceNotFoundException;
    int count();
    List<Movie> paginate(int index, int MOVIES_PER_PAGE);
}
