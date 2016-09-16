package es.ahs.oracle_task.repository;

import es.ahs.oracle_task.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by akuznetsov on 31.08.2016.
 */
@Component
public class PersonRepositoryImpl implements PersonRepository {
    private static final Logger log = LoggerFactory.getLogger(PersonRepositoryImpl.class);

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void save(Person person) {
        if (person.getId() > 0)
            em.merge(person);
        else
            em.persist(person);
    }

    public Person getById(int id) {
        return em.find(Person.class, id);
    }

    public List<Person> findAll() {
        return em.createNamedQuery(Person.ALL_SORTED_BY_ID, Person.class).getResultList();
    }

    @Override
    public List<Person> getByConsumeDay(int dayNumber) {
        return em.createNamedQuery(Person.BY_CONSUME_DAY_NUMBER, Person.class).setParameter(1, dayNumber).getResultList();
    }

    @Transactional
    public boolean delete(int id) {
        return em.createNamedQuery(Person.DELETE).setParameter("id", id).executeUpdate() != 0;
    }
}
