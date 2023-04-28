package com.llacerximo.bbddweb.bbddweb.business.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.llacerximo.bbddweb.bbddweb.business.entity.Director;
import com.llacerximo.bbddweb.bbddweb.business.service.DirectorService;
import com.llacerximo.bbddweb.bbddweb.exceptions.ResourceNotFoundException;
import com.llacerximo.bbddweb.bbddweb.persistence.DirectorRepository;
import com.llacerximo.bbddweb.bbddweb.persistence.impl.JdbcDirectorRepositoryImpl;

public class DirectorServiceImpl implements DirectorService{

    private DirectorRepository directorRepository = new JdbcDirectorRepositoryImpl();

    @Override
    public List<Director> getAll() {
        return directorRepository.getAll();
    }

    @Override
    public Director getById(String id) throws ResourceNotFoundException, SQLException {
        return directorRepository.getById(id);
    }

    @Override
    public Director getByMovieId(String id) throws ResourceNotFoundException, SQLException {
        return directorRepository.getByMovieId(id);

    }

}
