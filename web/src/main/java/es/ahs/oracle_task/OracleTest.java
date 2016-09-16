package es.ahs.oracle_task;

import es.ahs.oracle_task.model.City;
import es.ahs.oracle_task.model.Person;
import es.ahs.oracle_task.model.Weather;
import es.ahs.oracle_task.service.*;
import es.ahs.oracle_task.utils.SomeUtils;
import es.ahs.oracle_task.utils.YandexCitiesXmlParser;
import es.ahs.oracle_task.webservice.YandexGrandCountryAndCitiesListParser;
import es.ahs.oracle_task.webservice.YandexWeatherDataExtractor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.xml.bind.JAXBException;
import java.util.*;

import static es.ahs.oracle_task.utils.SomeUtils.*;

/**
 * Created by akuznetsov on 02.09.2016.
 */
@Component
public class OracleTest implements ServletContextListener {
    private static final Logger log = LoggerFactory.getLogger(OracleTest.class);

    public static void main(String[] args) throws Exception {
        new OracleTest().go("local");
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        log.debug("MAIN STARTED");
        go("server");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

    public void go(String contextType) {
        GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
        ctx.getEnvironment().setActiveProfiles(contextType);
        ctx.load("classpath:/spring/spring-app.xml");
        ctx.refresh();

//        grandYandexListParse(ctx);  // парсер страниц яндекса со списками городов для которых есть данные
//        testPersonsCreator(ctx);    // создание тестовых юзеров в бд
//        yandexParserTest(ctx);      // парсер другого списка от яндекса (в нем меньше данных, но там простой xml)
    }



    public void grandYandexListParse(GenericXmlApplicationContext ctx) {
        YandexGrandCountryAndCitiesListParser countryAndCitiesListParser = ctx.getBean(YandexGrandCountryAndCitiesListParser.class);
        countryAndCitiesListParser.parseGrandYandexLst();
    }

    public void testPersonsCreator(GenericXmlApplicationContext ctx) {
        PersonService personService = (PersonServiceImpl) ctx.getBean(PersonService.class);
        CityService cityService = ctx.getBean(CityService.class);
        List<Person> persons = personService.getWeatherConsumersForSpecifiedDay(1);

        Person person_1 = new Person("Test Person from Torrevieja");
        person_1.setMail("torresplaya@gmail.com");
        person_1.setPrefCity(cityService.getByPathName("torrevieja")); // Torrevieja
        person_1.setDayOfWeekForSend(7);
        Person person_2 = new Person("Person Tester from Barcelona");
        person_2.setMail("barsa@gmail.com");
        person_2.setPrefCity(cityService.getByPathName("barcelona")); // Barcelona
        person_2.setDayOfWeekForSend(7);
        Person person_3 = new Person("Nuevo Tester from SPb");
        person_3.setMail("spbuser@yandex.ru");
        person_3.setPrefCity(cityService.getByPathName("saint-petersburg")); // SPb
        person_3.setDayOfWeekForSend(7);
        Person person_4 = new Person("Peersoona Teestera from Helsinki");
        person_4.setMail("helsinkiuser@finlandia.fi");
        person_4.setPrefCity(cityService.getByPathName("helsinki")); // Helsinki
        person_4.setDayOfWeekForSend(7);
        Person person_5 = new Person("Testero Persono from Alicante");
        person_5.setMail("testeroalicante@gmail.com");
        person_5.setPrefCity(cityService.getByPathName("alicante"));
        person_5.setDayOfWeekForSend(0);
        Person person_6 = new Person("Testero Personero from Alicante");
        person_6.setMail("personeroalicante@gmail.com");
        person_6.setPrefCity(cityService.getByPathName("alicante"));
        person_6.setDayOfWeekForSend(0);
        Person person_7 = new Person("Testero Persono from Madrid");
        person_7.setMail("edridmadrid@gmail.com");
        person_7.setPrefCity(cityService.getByPathName("madrid"));
        person_7.setDayOfWeekForSend(0);
        personService.save(person_1);
        personService.save(person_2);
        personService.save(person_3);
        personService.save(person_4);
        personService.save(person_5);
        personService.save(person_6);
        personService.save(person_7);
        log.debug("All test-persons saved");
    }



    public void yandexParserTest(GenericXmlApplicationContext ctx) {
        YandexCitiesXmlParser parser = ctx.getBean(YandexCitiesXmlParser.class);
        try {
            parser.parse();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
