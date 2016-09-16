package es.ahs.oracle_task.service.scheduler;

import es.ahs.oracle_task.model.City;
import es.ahs.oracle_task.model.Person;
import es.ahs.oracle_task.model.Weather;
import es.ahs.oracle_task.model.WeatherMail;
import es.ahs.oracle_task.service.PersonService;
import es.ahs.oracle_task.service.WeatherService;
import es.ahs.oracle_task.service.mail.MailService;
import es.ahs.oracle_task.utils.SomeUtils;
import es.ahs.oracle_task.webservice.YandexWeatherDataExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static es.ahs.oracle_task.utils.SomeUtils.getCurrentDayOfWeekNumber;
import static es.ahs.oracle_task.utils.SomeUtils.getNewestWeatherTime;
import static es.ahs.oracle_task.utils.SomeUtils.getUniqueCities;

/**
 * Created by akuznetsov on 14.09.2016.
 */

@Component
public class SchedulerService {
    private static final Logger log = LoggerFactory.getLogger(SchedulerService.class);

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private PersonService personService;

    @Autowired
//    @Qualifier("MailLoopback")
    private MailService mailService;

    void getWeather() {
        log.info("get weather");
        List<City> cities = personService.getAllPrefferedCities();
        cities = getUniqueCities(cities);
        List<Weather> savedWeather = new ArrayList<>();
        for (City city : cities) {
            Weather weather = new YandexWeatherDataExtractor().getWeather(city);
            weatherService.save(weather);
            savedWeather.add(weather);
        }
    }

    @Transactional
    private void sendMail(Person person, WeatherMail weatherMail) throws Exception {
        mailService.sendMail(weatherMail);
        person.setLastSended(weatherMail.lastSaved);
        personService.save(person);
    }

    public void prepareAndSendMail() {
        List<Person> persons = personService.getWeatherConsumersForSpecifiedDay(getCurrentDayOfWeekNumber());
        Map<String, WeatherMail> mailList = new HashMap<>();
        for (Person person : persons) {
            List<Weather> weatherList = weatherService.getForCityAfterTime(person.getPrefCity().getPathName(),
                    person.getLastSended());
            if (weatherList.size() > 0) {
                WeatherMail mail = new WeatherMail();
                mail.mailTo = person.getMail();
                mail.subj = SomeUtils.createMailHeader(weatherList);
                mail.body = SomeUtils.createMailBodyTableWithWeather(weatherList);
                mail.lastSaved = getNewestWeatherTime(weatherList);
                mailList.put(mail.mailTo, mail);
                try {
                    sendMail(person, mail);
                } catch (Exception e) {
                    log.warn("Error while sending to {} mail {}.\nException message: {}", person, mail, e.getLocalizedMessage());
                }
            }
        }
    }
}
