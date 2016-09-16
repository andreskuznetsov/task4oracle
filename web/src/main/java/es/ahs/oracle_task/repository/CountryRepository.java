package es.ahs.oracle_task.repository;

import es.ahs.oracle_task.model.Country;
import es.ahs.oracle_task.model.yandex_cities.YandexCountry;

import java.util.List;

/**
 * Created by akuznetsov on 09.09.2016.
 */
public interface CountryRepository {

    void save(Country country);

    List<Country> getAll();

    Country get(int id);

    Country get(String name);

    void delete(Country country);
}
