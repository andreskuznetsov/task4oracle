package es.ahs.oracle_task.repository;

import es.ahs.oracle_task.model.Country;
import es.ahs.oracle_task.model.Weather;
import es.ahs.oracle_task.utils.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by akuznetsov on 09.09.2016.
 */
@Repository
public class CountryRepositoryImpl implements CountryRepository {
    private static final Logger log = LoggerFactory.getLogger(CountryRepositoryImpl.class);


    @PersistenceContext
    private EntityManager em;

    @Transactional
    @Override
    public void save(Country country) {
        try {
            em.persist(country);
        } catch (Exception e) {
            log.error("errorr while saving country: {}", country);
            log.warn(e.getLocalizedMessage());
        }
    }

    @Override
    public List<Country> getAll() {
        return em.createNamedQuery(Country.GET_ALL, Country.class).getResultList();
    }

    @Override
    public Country get(int id) {
        return em.find(Country.class, id);
    }

    @Override
    public Country get(String name) throws NotFoundException {
        Country country = null;
        country = em.createNamedQuery(Country.GET_BY_NAME, Country.class).setParameter(1, name).getSingleResult();
        if (country != null) return country;
        else throw new NotFoundException("Country '" + name + "' no found");
    }

    @Transactional
    @Override
    public void delete(Country country) {
        em.remove(country);
    }
}
