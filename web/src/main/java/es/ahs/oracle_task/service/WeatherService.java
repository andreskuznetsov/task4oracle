package es.ahs.oracle_task.service;

import es.ahs.oracle_task.model.Weather;

import java.util.Date;
import java.util.List;

/**
 * Created by akuznetsov on 07.09.2016.
 */
public interface WeatherService {
    void save(Weather weather);

    Weather getById(int id);

    List<Weather> getAll();

    List<Weather> getAllForCity(String cityName);

    List<Weather> getAllForCityAndTimePeriod(String cityName, Date startDate, Date endDate);

    List<Weather> getForCityAfterTime(String cityName, Date lasytSended);

    boolean delete(int id);
}
