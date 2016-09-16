package es.ahs.oracle_task.utils;

/**
 * Created by akuznetsov on 07.09.2016.
 */
public class Consts {
    public static final String URL_YANDEX_CITIES_XML = "https://pogoda.yandex.ru/static/cities.xml";
    public static final String URL_YANDEX_WEATHER_BASEPATH = "https://yandex.ru/pogoda/";
    public static final String URL_YANDEX_REGIONS_LIST = "https://yandex.ru/pogoda/region/";

    public static final String OWM_API_KEY = "6d2027dab11ca257dde06f8b714380ef";
    public static final String OWM_URL_BASEPATH = "http://api.openweathermap.org/data/2.5/weather";
    public static final String OWM_URL_BASEPATH_WITH_KEY = OWM_URL_BASEPATH + "?" + OWM_API_KEY;
    public static final String testUrl = OWM_URL_BASEPATH_WITH_KEY + "&lat=35&lon=139";
//            "http://api.openweathermap.org/data/2.5/weather?lat=35&lon=139&appid=6d2027dab11ca257dde06f8b714380ef";


}
