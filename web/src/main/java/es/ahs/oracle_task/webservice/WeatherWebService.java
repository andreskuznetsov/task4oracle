package es.ahs.oracle_task.webservice;

import es.ahs.oracle_task.model.City;
import es.ahs.oracle_task.model.Weather;
import es.ahs.oracle_task.utils.Coord;

/**
 * Created by akuznetsov on 07.09.2016.
 */
public interface WeatherWebService {
    Weather getWeather(City city);

    Weather getWeather(String cityName);

    Weather getWeather(int cityId);

    Weather getWeather(Coord coord);
}
