package com.llacerximo.bbddweb.bbddweb.persistence;

import java.sql.SQLException;
import java.util.List;

import com.llacerximo.bbddweb.bbddweb.business.entity.Actor;
import com.llacerximo.bbddweb.bbddweb.exceptions.ResourceNotFoundException;

public interface ActorRepository {
    
    List<Actor> getAll();
    Actor getById(String id) throws SQLException, ResourceNotFoundException;
    List<Actor> getByMovieId(String id) throws ResourceNotFoundException, SQLException;
}
