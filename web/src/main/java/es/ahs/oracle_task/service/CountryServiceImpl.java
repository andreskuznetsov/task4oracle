package es.ahs.oracle_task.service;

import es.ahs.oracle_task.model.Country;
import es.ahs.oracle_task.repository.CountryRepository;
import es.ahs.oracle_task.repository.CountryRepositoryImpl;
import es.ahs.oracle_task.utils.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by akuznetsov on 09.09.2016.
 */

@Service
public class CountryServiceImpl implements CountryService {
        private static final Logger log = LoggerFactory.getLogger(CountryServiceImpl.class);


    @Autowired
    private CountryRepository countryRepository;

    @Override
    public void save(String countryName) {
        try {
            Country country = new Country();
            country.setName(countryName);
            save(country);
        } catch (Exception e) {
            log.warn(e.getLocalizedMessage());
        }
    }

    @Override
    public void save(Country country) {
        try {
            countryRepository.save(country);
        } catch (DataIntegrityViolationException e) {
            log.warn(e.getLocalizedMessage());
        }
    }

    @Override
    public List<Country> getAll() {
        return countryRepository.getAll();
    }

    @Override
    public void delete(int id) {
        countryRepository.delete(get(id));
    }

    @Override
    public Country get(int id) {
        return countryRepository.get(id);
    }

    @Override
    public Country get(String countryName) {
        try {
            return countryRepository.get(countryName);
        } catch (NotFoundException e) {
            save(countryName);
            return get(countryName);
        }
    }
}
