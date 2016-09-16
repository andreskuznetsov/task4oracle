package es.ahs.oracle_task.service;

import es.ahs.oracle_task.model.City;
import es.ahs.oracle_task.model.yandex_cities.YandexCity;

import java.util.List;

/**
 * Created by akuznetsov on 09.09.2016.
 */
public interface CityService {

    void save(City city);

    void save(YandexCity yandexCity);

    void save(String cityName);

    List<City> getAll();

    void delete(int id);

    City get(int id);

    City get(String cityName);

    City getByPathName(String pathName);

    void commit();

}
