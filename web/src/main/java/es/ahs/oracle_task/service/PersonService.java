package es.ahs.oracle_task.service;

import es.ahs.oracle_task.model.City;
import es.ahs.oracle_task.model.Person;

import java.util.List;

/**
 * Created by akuznetsov on 07.09.2016.
 */
public interface PersonService {
    Person getById(int id);

    List<Person> getAll();

    List<Person> getWeatherConsumersForSpecifiedDay(int dayNumber);

    Person save(Person person);

    void delete(int id);

    City getPrefferedCityPath(int personId);

    List<City> getAllPrefferedCities();
}
