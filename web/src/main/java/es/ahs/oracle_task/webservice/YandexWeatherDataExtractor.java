package es.ahs.oracle_task.webservice;

import es.ahs.oracle_task.model.City;
import es.ahs.oracle_task.model.Weather;
import es.ahs.oracle_task.utils.Coord;
import es.ahs.oracle_task.utils.exception.WeatherLoadingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.StandardCharsets;
import java.sql.Time;
import java.util.Scanner;

import static es.ahs.oracle_task.utils.SomeUtils.*;

/**
 * Created by akuznetsov on 06.09.2016.
 */
public class YandexWeatherDataExtractor implements WeatherWebService {
    private static final Logger log = LoggerFactory.getLogger(YandexWeatherDataExtractor.class);

    @Override
    public Weather getWeather(City city) {
        Weather weather = getWeather(city.getPathName());
        weather.setCityRef(city);
        return weather;
    }

    @Override
    public Weather getWeather(String cityName) {
        Weather weather = new Weather();
        weather.setCityName(cityName);
        weather = getAndParseWeatherFromPage(weather);
        return weather;
    }

    @Override
    public Weather getWeather(int cityId) {
        return null;
    }

    @Override
    public Weather getWeather(Coord coord) {
        return null;
    }

    private Weather getAndParseWeatherFromPage(Weather weather) {
        Weather tmpWeather = new Weather();
        tmpWeather.setCityName(weather.getCityName());
        tmpWeather.setSavingTime(new java.util.Date());
        String yandexWeatherPage = loadYandexWeatherPageForCity(weather.getCityName());
        tmpWeather.setTempWater(getWaterTemperature(yandexWeatherPage));
        tmpWeather.setTempCurrent(getCurrentTemperature(yandexWeatherPage));
        tmpWeather.setWind(getWindData(yandexWeatherPage));
        tmpWeather.setWeatherComment(getWeatherComment(yandexWeatherPage));
        tmpWeather.setHumidity(getHumidity(yandexWeatherPage));
        tmpWeather.setPressure(getPressure(yandexWeatherPage));
        tmpWeather.setRegistrationTime(Time.valueOf(getGeneratedTime(yandexWeatherPage)));
        tmpWeather.setRegistrationDate(new java.sql.Date(new java.util.Date().getTime()));
        return tmpWeather;
    }

    private String loadYandexWeatherPageForCity(String cityName) throws WeatherLoadingException {
        String page = "";
        ReadableByteChannel rbc = null;
        Scanner scanner = null;
        System.setProperty("proxySet", "true");
        System.setProperty("http.proxyHost", "proxy.reksoft.ru");
        System.setProperty("http.proxyPort", "3128");
        try {
            URL website = new URL("https://yandex.ru/pogoda/" + cityName);
            rbc = Channels.newChannel(website.openStream());
            scanner = new Scanner(rbc, StandardCharsets.UTF_8.name());
            // TODO: realize normal conversion from input stream to one string
            page = scanner.nextLine();
        } catch (IOException e) {
            log.warn("Can't load page for city: {}, error message: {}", cityName, e.getLocalizedMessage());
            throw new WeatherLoadingException(e.getLocalizedMessage());
        } finally {
            try {
                scanner.close();
                rbc.close();
            } catch (Exception e) {
                log.warn("Error while closing resources, error message: {}", e.getLocalizedMessage());
                throw new WeatherLoadingException(e.getLocalizedMessage());
            }
        }
        return page;
    }

    private String getGeneratedTime(String yandexWeatherPage) {
        // <div class="current-weather__info-row current-weather__info-row_type_time">Данные на 08:00</div>
        String generatedTimeString = "no data";
        final String beforeTimeString = "<div class=\"current-weather__info-row current-weather__info-row_type_time\">Данные на ";
        final String afterTimeString = "</div>";
        generatedTimeString = extractDataFromPage(yandexWeatherPage, beforeTimeString, afterTimeString, 0) + ":00";
        return generatedTimeString;
    }

    private String getPressure(String yandexWeatherPage) {
        // <div class="current-weather__info-row"><span class="current-weather__info-label">Давление: </span>761 мм рт. ст.</div>
        String pressureDataString = "no data";
        final String beforePressureString = "<div class=\"current-weather__info-row\"><span class=\"current-weather__info-label\">Давление: </span>";
        final String afterPressureString = "</div>";
        pressureDataString = extractDataFromPage(yandexWeatherPage, beforePressureString, afterPressureString, 0);
        return pressureDataString;
    }

    private String getHumidity(String yandexWeatherPage) {
        // <div class="current-weather__info-row"><span class="current-weather__info-label">Влажность: </span>78%</div>
        String humidityDataString = "no data";
        final String beforeHumidityString = "<div class=\"current-weather__info-row\"><span class=\"current-weather__info-label\">Влажность: </span>";
        final String afterHumidityString = "</div>";
        humidityDataString = extractDataFromPage(yandexWeatherPage, beforeHumidityString, afterHumidityString, 0);
        return humidityDataString;
    }

    private String getWeatherComment(String yandexWeatherPage) {
        //  <span class="current-weather__comment">небольшой дождь</span>
        String weatherCommentString = "no data";
        final String beforeWeatherCommentString = "<span class=\"current-weather__comment\">";
        final String afterWeatherCommentString = "</span>";
        weatherCommentString = extractDataFromPage(yandexWeatherPage, beforeWeatherCommentString, afterWeatherCommentString, 0);
        return weatherCommentString;
    }

    private String getWindData(String yandexWeatherPage) {
        String windDataString = "no data";
        final String beforeWindSpeedString = "<span class=\"wind-speed\">";
        final String afterWindSpeedString = "</span>";
        windDataString = extractDataFromPage(yandexWeatherPage, beforeWindSpeedString, afterWindSpeedString, 0);
        final String beforeWindDirectionString = "class=\" icon-abbr\" title=\"Ветер:";
        final String afterWindDirectionString = "\">";
        windDataString += extractDataFromPage(yandexWeatherPage, beforeWindDirectionString, afterWindDirectionString, 0);
        return windDataString;
    }

    private int getWaterTemperature(String yandexWeatherPage) {
        String waterTempString = "-10000";
        final String beforeWaterTempString = "<div class=\"current-weather__water\">";
        final String afterWaterTempString = "<i class=\"icon";
        waterTempString = extractDataFromPage(yandexWeatherPage, beforeWaterTempString, afterWaterTempString, 18);
        return convert2int(waterTempString);
    }

    private int getCurrentTemperature(String yandexWeatherPage) {
        String currentTempString = "-10000";
        final String beforeCurrentTempString = "<div class=\"current-weather__thermometer current-weather__thermometer_type_now\">";
        final String afterCurrentTempString = "°C</div>";
        int temp = 0;
        currentTempString = extractDataFromPage(yandexWeatherPage, beforeCurrentTempString, afterCurrentTempString, -1);
        currentTempString = currentTempString.replace("+", "");
        temp =  convert2int(currentTempString);
        return temp;
    }
}
