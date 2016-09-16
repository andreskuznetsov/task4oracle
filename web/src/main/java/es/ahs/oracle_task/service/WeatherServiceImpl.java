package es.ahs.oracle_task.service;

import es.ahs.oracle_task.model.Weather;
import es.ahs.oracle_task.repository.WeatherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by akuznetsov on 07.09.2016.
 */
@Service
public class WeatherServiceImpl implements WeatherService {
        private static final Logger log = LoggerFactory.getLogger(WeatherServiceImpl.class);


    @Autowired
    WeatherRepository repository;

    @Override
    public void save(Weather weather) {
        try {
            repository.save(weather);
        } catch (Exception e) {
            log.error("Got error: {}", e.getLocalizedMessage());
//            e.printStackTrace();
        } finally {
        }
    }

    @Override
    public Weather getById(int id) {
        return repository.getById(id);
    }

    @Override
    public List<Weather> getAll() {
        return repository.getAll();
    }

    @Override
    public List<Weather> getAllForCity(String cityName) {
        return repository.getAllForCity(cityName);
    }

    @Override
    public List<Weather> getForCityAfterTime(String cityName, Date lastSended) {
        if (lastSended == null) lastSended = new Date(0);
        List<Weather> weatherList = new ArrayList<>();
        weatherList.addAll(repository.getForCityAfterTime(cityName, lastSended));
        return weatherList;
    }

    @Override
    public List<Weather> getAllForCityAndTimePeriod(String cityName, Date startDate, Date endDate) {
        return null;
    }

    @Override
    public boolean delete(int id) {
        return repository.delete(id);
    }
}
