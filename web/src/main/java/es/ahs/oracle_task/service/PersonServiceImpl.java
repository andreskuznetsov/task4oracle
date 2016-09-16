package es.ahs.oracle_task.service;

import es.ahs.oracle_task.model.City;
import es.ahs.oracle_task.repository.CityRepository;
import es.ahs.oracle_task.repository.PersonRepository;

import es.ahs.oracle_task.model.Person;
import es.ahs.oracle_task.utils.exception.ExceptionUtil;
import es.ahs.oracle_task.utils.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akuznetsov on 31.08.2016.
 */

@Component
public class PersonServiceImpl implements PersonService {
    private static final Logger log = LoggerFactory.getLogger(PersonServiceImpl.class);

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private CityRepository cityRepository;

    public Person save(Person person) {
        try {
            personRepository.save(person);
        } catch (DataIntegrityViolationException e) {
            log.warn(e.getLocalizedMessage());
        }
        return person;
    }

    public Person getById(int id) throws NotFoundException {
        return ExceptionUtil.check(personRepository.getById(id), id);
    }

    public void delete(int id) throws NotFoundException{
        ExceptionUtil.check(personRepository.delete(id), id);
    }

    public List<Person> getAll() {
        return personRepository.findAll();
    }

    @Override
    public City getPrefferedCityPath(int personId) {
        Person person = personRepository.getById(personId);
        City city = person.getPrefCity();
//        City city = cityRepository.get(cityId);
        return city;
    }

    @Override
    public List<City> getAllPrefferedCities() {
        List<City> list = new ArrayList<>();
        List<Person> persons = getAll();
        for (Person p : persons) {
            list.add(p.getPrefCity());
        }
        return list;
    }

    public List<Person> getWeatherConsumersForSpecifiedDay(int dayNumber) {
        List<Person> persons = new ArrayList<>();
        persons.addAll(personRepository.getByConsumeDay(dayNumber));
        persons.addAll(personRepository.getByConsumeDay(7)); // Add every day consumers
        return persons;
    }
}
