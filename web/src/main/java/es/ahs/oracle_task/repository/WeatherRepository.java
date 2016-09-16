package es.ahs.oracle_task.repository;

import es.ahs.oracle_task.model.Weather;

import java.util.Date;
import java.util.List;

/**
 * Created by akuznetsov on 07.09.2016.
 */
public interface WeatherRepository {

    void save(Weather weather);

    Weather getById(int id);

    List<Weather> getAll();

    List<Weather> getAllForCity(String cityName);

    List<Weather> getForCityAfterTime(String cityName, Date lastSended);

    boolean delete(int id);

}
