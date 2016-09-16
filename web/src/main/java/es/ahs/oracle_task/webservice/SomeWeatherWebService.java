package es.ahs.oracle_task.webservice;

import es.ahs.oracle_task.model.City;
import es.ahs.oracle_task.model.Weather;
import es.ahs.oracle_task.utils.Coord;
import net.aksingh.owmjapis.CurrentWeather;
import net.aksingh.owmjapis.OpenWeatherMap;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.GZIPInputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

import static es.ahs.oracle_task.utils.Consts.OWM_API_KEY;
import static es.ahs.oracle_task.utils.Consts.URL_YANDEX_REGIONS_LIST;
import static es.ahs.oracle_task.utils.Consts.URL_YANDEX_WEATHER_BASEPATH;

/**
 * Created by akuznetsov on 06.09.2016.
 */
@Component
public class SomeWeatherWebService implements WeatherWebService {
//    private static final Logger log = LoggerFactory.getLogger(SomeWeatherWebService.class);


    @Override
    public Weather getWeather(City city) {
        return null;
    }

    @Override
    public Weather getWeather(String cityName) {
        return null;
    }

    @Override
    public Weather getWeather(int cityId) {
        return null;
    }

    @Override
    public Weather getWeather(Coord coord) {
        return null;
    }

    public CurrentWeather getWeatherByCityName(String cityName) throws IOException {
        OpenWeatherMap weatherMap = new OpenWeatherMap(OpenWeatherMap.Units.METRIC, OpenWeatherMap.Language.ENGLISH, OWM_API_KEY);
        return weatherMap.currentWeatherByCityName(cityName);
    }

    public String testGetHttp() {
        return httpGetWeatherForCity("torrevieja");
    }

    public String getYandexCountryList() {
        return httpGet("https://yandex.ru/pogoda/region");
    }

    public String getYandexCityList(String citiesListName) {
//        log.debug("cityPath: *{}*", URL_YANDEX_REGIONS_LIST + citiesListName);
        return httpGet(URL_YANDEX_REGIONS_LIST + citiesListName);
    }

    private String httpGetWeatherForCity(String city){
        return httpGet(URL_YANDEX_WEATHER_BASEPATH + city);
    }

    private String httpGet(String requestPath) {

        URL request;
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        String s;
        String response = null;
//        log.debug("httpGt initialized");
        try {
            request = new URL(requestPath);
            connection = (HttpURLConnection) request.openConnection();
//            log.debug("Req + conn");
            connection.setRequestMethod("GET");
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(false);
            connection.setRequestProperty("Accept-Encoding", "gzip, deflate");
            connection.connect();
//            log.debug("after connect");
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
//                log.debug("before encoding");
                String encoding = connection.getContentEncoding();
//                log.debug("Encoding: {}", encoding);
                try {
                    if (encoding != null && encoding.equalsIgnoreCase("gzip")) {
                        reader = new BufferedReader(new InputStreamReader(new GZIPInputStream(connection.getInputStream())));
                    } else if (encoding != null && encoding.equalsIgnoreCase("deflate")) {
                        reader = new BufferedReader(new InputStreamReader(new InflaterInputStream(connection.getInputStream(), new Inflater(true))));
                    } else {
                        reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    }
                    while ((s = reader.readLine()) != null) {
                        response = s;
                    }
                } catch (IOException e) {
                    System.err.println("Error: " + e.getMessage());
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            System.err.println("Error: " + e.getMessage());
                        }
                    }
                }
            } else { // if HttpURLConnection is not okay
                try {
                    reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                    while ((s = reader.readLine()) != null) {
                        response = s;
                    }
                } catch (IOException e) {
                    System.err.println("Error: " + e.getMessage());
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            System.err.println("Error: " + e.getMessage());
                        }
                    }
                }

                // if response is bad
                System.err.println("Bad Response: " + response + "\n");
                return null;
            }
        } catch (IOException e) {
            e.getStackTrace();

            System.err.println("zError: " + e.getMessage());
            response = null;
        } finally {
            if (connection != null) {
//                log.debug("D I S C O N N E C T");
                connection.disconnect();
            }
        }
        return response;
    }
}
