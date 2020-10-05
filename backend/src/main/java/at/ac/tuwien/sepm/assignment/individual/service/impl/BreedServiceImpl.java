package at.ac.tuwien.sepm.assignment.individual.service.impl;

import at.ac.tuwien.sepm.assignment.individual.entity.Breed;
import at.ac.tuwien.sepm.assignment.individual.persistence.BreedDao;
import at.ac.tuwien.sepm.assignment.individual.service.BreedService;
import at.ac.tuwien.sepm.assignment.individual.util.Validator;
import java.lang.invoke.MethodHandles;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BreedServiceImpl implements BreedService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final BreedDao dao;
    private final Validator validator;

    @Autowired
    public BreedServiceImpl(BreedDao breedDao, Validator validator) {
        this.dao = breedDao;
        this.validator = validator;
    }

    @Override
    public Breed getOneById(Long id) {
        LOGGER.trace("getOneById({})", id);
        return dao.getOneById(id);
    }

    @Override
    public List<Breed> getAll() {
        LOGGER.trace("getAll()");
        return dao.getAll();
    }
}
