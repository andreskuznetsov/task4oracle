package es.ahs.oracle_task.service;

import es.ahs.oracle_task.model.City;
import es.ahs.oracle_task.model.Country;
import es.ahs.oracle_task.model.yandex_cities.YandexCity;
import es.ahs.oracle_task.repository.CityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by akuznetsov on 09.09.2016.
 */
@Service
public class CityServiceImpl implements CityService {
        private static final Logger log = LoggerFactory.getLogger(CityServiceImpl.class);


    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CountryService countryService;

    @Override
    public void save(City city) {
        try {
            cityRepository.save(city);
        } catch (Exception e) {
            log.warn("Can't save city: {}", city);
            log.warn(e.getLocalizedMessage());
        }
    }

    @Override
    public void save(YandexCity yandexCity) {
        City city = new City();
        city.setName(yandexCity.getValue());
        city.setPartOfTerritory(yandexCity.getPart());
        Country country = countryService.get(yandexCity.getCountry());
        city.setCountry(country);
        save(city);
    }

    @Override
    public void save(String cityName) {
        City city = new City();
        city.setName(cityName);
        cityRepository.save(city);
    }

    @Override
    public List<City> getAll() {
        return cityRepository.getAll();
    }

    @Override
    public City getByPathName(String pathName) {
        return cityRepository.getByPathName(pathName);
    }

    @Override
    public void delete(int id) {
        cityRepository.delete(get(id));
    }

    @Override
    public City get(int id) {
        return cityRepository.get(id);
    }

    @Override
    public City get(String cityName) {
        return cityRepository.get(cityName);
    }

    @Override
    public void commit() {
        cityRepository.commit();
    }
}
