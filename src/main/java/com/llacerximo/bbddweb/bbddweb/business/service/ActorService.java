package com.llacerximo.bbddweb.bbddweb.business.service;

import java.sql.SQLException;
import java.util.List;

import com.llacerximo.bbddweb.bbddweb.business.entity.Actor;
import com.llacerximo.bbddweb.bbddweb.exceptions.ResourceNotFoundException;

public interface ActorService {
    List<Actor> getAll();
    Actor getById(String id) throws ResourceNotFoundException, SQLException;
    List<Actor> getByMovieId(String id) throws ResourceNotFoundException, SQLException;
}
