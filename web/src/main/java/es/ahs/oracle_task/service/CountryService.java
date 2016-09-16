package es.ahs.oracle_task.service;

import es.ahs.oracle_task.model.Country;

import java.util.List;

/**
 * Created by akuznetsov on 09.09.2016.
 */
public interface CountryService {

    void save(Country country);

    void save(String countryName);

    List<Country> getAll();

    void delete(int id);

    Country get(int id);

    Country get(String countryName);
}
