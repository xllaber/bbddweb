package com.llacerximo.bbddweb.bbddweb.business.service;

import java.sql.SQLException;
import java.util.List;

import com.llacerximo.bbddweb.bbddweb.business.entity.Movie;
import com.llacerximo.bbddweb.bbddweb.exceptions.ResourceNotFoundException;

public interface MovieService {
    
    List<Movie> getAll();
    Movie getById(String id) throws SQLException, ResourceNotFoundException;
    void insert(Movie movie) throws SQLException;
}
