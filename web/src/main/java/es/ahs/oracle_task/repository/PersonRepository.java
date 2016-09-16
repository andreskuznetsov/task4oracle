package es.ahs.oracle_task.repository;

import es.ahs.oracle_task.model.Person;

import java.util.List;

/**
 * Created by akuznetsov on 07.09.2016.
 */
public interface PersonRepository {
    void save(Person person);

    Person getById(int id);

    List<Person> findAll();

    List<Person> getByConsumeDay(int dayNumber);

    boolean delete(int id);
}
