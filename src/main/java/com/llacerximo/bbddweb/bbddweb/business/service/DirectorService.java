package com.llacerximo.bbddweb.bbddweb.business.service;

import java.sql.SQLException;
import java.util.List;

import com.llacerximo.bbddweb.bbddweb.business.entity.Director;
import com.llacerximo.bbddweb.bbddweb.exceptions.ResourceNotFoundException;

public interface DirectorService {
    List<Director> getAll();
    Director getById(String id) throws ResourceNotFoundException, SQLException;
    Director getByMovieId(String id) throws ResourceNotFoundException, SQLException;
}
