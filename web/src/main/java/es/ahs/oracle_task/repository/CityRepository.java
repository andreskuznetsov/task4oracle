package es.ahs.oracle_task.repository;

import es.ahs.oracle_task.model.City;

import java.util.List;

/**
 * Created by akuznetsov on 07.09.2016.
 */
public interface CityRepository {

    void save(City city);

    City getByYandexCityId(int cityId);

    City get(int dbId);

    City get(String cityName);

    City getByPathName(String pathName);

    List<City> getAll();

    void delete(City city);

    void commit();
}
