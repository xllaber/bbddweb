package com.llacerximo.bbddweb.bbddweb.business.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.llacerximo.bbddweb.bbddweb.business.entity.Actor;
import com.llacerximo.bbddweb.bbddweb.business.service.ActorService;
import com.llacerximo.bbddweb.bbddweb.exceptions.ResourceNotFoundException;
import com.llacerximo.bbddweb.bbddweb.persistence.ActorRepository;
import com.llacerximo.bbddweb.bbddweb.persistence.impl.JdbcActorRepositoryImpl;

public class ActorServiceImpl implements ActorService{

    private ActorRepository actorRepository = new JdbcActorRepositoryImpl();

    @Override
    public List<Actor> getAll() {
        return actorRepository.getAll();
    }

    @Override
    public Actor getById(String id) throws ResourceNotFoundException, SQLException {
        return actorRepository.getById(id);
    }

    @Override
    public List<Actor> getByMovieId(String id) throws ResourceNotFoundException, SQLException {
        return actorRepository.getByMovieId(id);
    }

}
