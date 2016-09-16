package es.ahs.oracle_task.utils;

import es.ahs.oracle_task.model.City;
import es.ahs.oracle_task.model.yandex_cities.CitiesType;
import es.ahs.oracle_task.model.yandex_cities.YandexCity;
import es.ahs.oracle_task.model.yandex_cities.YandexCountry;
import es.ahs.oracle_task.model.yandex_cities.ObjectFactory;
import es.ahs.oracle_task.service.CityService;
import es.ahs.oracle_task.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by akuznetsov on 09.09.2016.
 */
@Component
public class YandexCitiesXmlParser {
    /**
     * @param args
     * @throws JAXBException
     */

    @Autowired
    private CountryService countryService;

    @Autowired
    private CityService cityService;

    @Transactional
    public void saveYandexCitiesList(List<YandexCity> yandexCities) {
        for (YandexCity city : yandexCities) {
            cityService.save(city);
        }
        cityService.commit();
    }

    //    @Transactional
    public void parse() throws JAXBException {
        //1. We need to create JAXContext instance
        JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);

        //2. Use JAXBContext instance to create the Unmarshaller.
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        //3. Use the Unmarshaller to unmarshal the XML document to get an instance of JAXBElement.
        JAXBElement<CitiesType> unmarshalledObject =
                (JAXBElement<CitiesType>) unmarshaller.unmarshal(
                        ClassLoader.getSystemResourceAsStream("cities.xml"));

        //4. Get the instance of the required JAXB Root Class from the JAXBElement.
        CitiesType yandexCities = unmarshalledObject.getValue();
        List<YandexCountry> countryList = yandexCities.getCountry();
        List<YandexCity> cityList = new ArrayList<>();

        for (YandexCountry country : countryList) {
            countryService.save(country.getName());
            cityList = country.getCity();
            for (YandexCity city : cityList) {
                cityService.save(city);
            }
        }

        City city = new City();
        city.setName("Торревьеха");
        city.setCountry(countryService.get("Испания"));
        city.setPartOfTerritory("Аликанте, Валенсия");
        cityService.save(city);
    }
}
