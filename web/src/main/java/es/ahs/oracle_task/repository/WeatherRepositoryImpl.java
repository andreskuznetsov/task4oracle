package es.ahs.oracle_task.repository;

import es.ahs.oracle_task.model.Person;
import es.ahs.oracle_task.model.Weather;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by akuznetsov on 07.09.2016.
 */
@Repository
public class WeatherRepositoryImpl implements WeatherRepository {

    @PersistenceContext
    EntityManager em;

    @Transactional
    @Override
    public void save(Weather weather) {
        em.persist(weather);
    }

    @Override
    public Weather getById(int id) {
        return em.find(Weather.class, id);
    }

    @Override
    public List<Weather> getAll() {
        return em.createNamedQuery(Weather.ALL_SORTED_BY_NAME, Weather.class).getResultList();
    }

    @Override
    public List<Weather> getAllForCity(String cityName) {
        return em.createNamedQuery(Weather.ALL_FOR_CITY, Weather.class).setParameter("cityname", cityName).getResultList();
    }

    @Override
    public List<Weather> getForCityAfterTime(String cityName, Date lastSended) {
        List<Weather> weathers = new ArrayList<>();
        weathers.addAll(em.createNamedQuery(Weather.FOR_CITY_AFTER_TIME, Weather.class).setParameter(1, cityName).setParameter(2, lastSended).getResultList());
        return weathers;
    }

    @Override
    public boolean delete(int id) {
        return em.createNamedQuery(Weather.DELETE).setParameter("id", id).executeUpdate() != 0;
    }

}
