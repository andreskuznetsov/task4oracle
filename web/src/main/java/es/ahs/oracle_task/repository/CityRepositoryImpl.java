package es.ahs.oracle_task.repository;

import es.ahs.oracle_task.model.City;
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
public class CityRepositoryImpl implements CityRepository {
        private static final Logger log = LoggerFactory.getLogger(CityRepositoryImpl.class);


    @PersistenceContext
    private EntityManager em;

    @Transactional
    @Override
    public void save(City city) {
        try {
            if (city.getId() == 0)
                em.persist(city);
            else em.merge(city);
        } catch (DataIntegrityViolationException e) {
            log.error("errorr while saving city: {}", city);
            log.warn(e.getLocalizedMessage());
        }
    }

    @Override
    public City getByYandexCityId(int cityId) throws NotFoundException {
        try {
            City city = em.find(City.class, cityId);
            return city;
        } catch (Exception e) {
            throw new NotFoundException(e.getLocalizedMessage());
        }
    }

    @Override
    public City getByPathName(String pathName) {
        try {
            City city = em.createNamedQuery(City.GET_BY_PATHNAME, City.class).setParameter(1, pathName).getSingleResult();
            return city;
        } catch (Exception e) {
            throw new NotFoundException(e.getLocalizedMessage());
        }
    }

    public void save(List<City> cities) {
    }

    @Override
    public City get(int dbId) {
        try {
            City city = em.find(City.class, dbId);
            return city;
        } catch (Exception e) {
            throw new NotFoundException(e.getLocalizedMessage());
        }
    }

    @Override
    public City get(String cityName) {
        try {
            City city = em.createNamedQuery(City.GET_BY_NAME, City.class).setParameter(1, cityName).getSingleResult();
            return city;
        } catch (Exception e) {
            throw new NotFoundException(e.getLocalizedMessage());
        }
    }

    @Override
    public List<City> getAll() {
        return em.createNamedQuery(City.GET_ALL, City.class).getResultList();
    }

    @Transactional
    @Override
    public void delete(City city) throws NotFoundException {
        try {
            em.remove(city);
        } catch (Exception e) {
            throw new NotFoundException(e.getLocalizedMessage());
        }
    }

    @Transactional
    @Override
    public void commit() {
        em.flush();
    }
}
