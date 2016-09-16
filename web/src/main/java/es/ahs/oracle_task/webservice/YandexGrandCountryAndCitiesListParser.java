package es.ahs.oracle_task.webservice;

import es.ahs.oracle_task.model.City;
import es.ahs.oracle_task.model.Country;
import es.ahs.oracle_task.service.CityService;
import es.ahs.oracle_task.service.CountryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by akuznetsov on 13.09.2016.
 */

@Component
public class YandexGrandCountryAndCitiesListParser {
    private static final Logger log = LoggerFactory.getLogger(YandexGrandCountryAndCitiesListParser.class);

    @Autowired
    private CountryService countryService;

    @Autowired
    private CityService cityService;

    @Autowired
    SomeWeatherWebService someWeatherWebService;

    private static final String LIST_ITEM = "place-list__item-name\" href=\"/pogoda/";
    private static final String REGION_ITEM = "region/";
    private static final String CLOSE_ITEM_TAG = "\">";
    private static final String LIST_AND_REGION_ITEM = LIST_ITEM + REGION_ITEM;

    static List<Country> getCountries(String pageWithCountries) {
        List<Country> countryList = new ArrayList<>();
        Map<String, String> countriesMap = getRequiredMap(pageWithCountries, LIST_AND_REGION_ITEM, CLOSE_ITEM_TAG);
        for (Map.Entry<String, String> entry : countriesMap.entrySet()) {
            countryList.add(new Country(entry.getValue(), entry.getKey()));
        }
        return countryList;
    }

    static List<City> getCities(String pageWithCities) {
        List<City> cityList = new ArrayList<>();
        Map<String, String> citiesMap = getRequiredMap(pageWithCities, LIST_ITEM, CLOSE_ITEM_TAG);
        for (Map.Entry<String, String> entry : citiesMap.entrySet()) {
            cityList.add(new City(entry.getValue(), entry.getKey()));
        }
        return cityList;
    }

    static Map<String, String> getRequiredMap(String pageWithData, String startItem, String endItem) {
        Map<String, String> reqMap = new HashMap<>();
        int indexStartItem = 0;
        int indexEndItem = 0;
        int startItemSize = startItem.length();
        int endItemSize = endItem.length();
        int pageLength = pageWithData.length();
        while (indexStartItem >= 0 && indexEndItem >= 0 && (indexStartItem < pageLength && indexEndItem < pageLength) && (indexEndItem >= indexStartItem)) {
            indexStartItem = pageWithData.indexOf(startItem, indexEndItem);
            indexEndItem = pageWithData.indexOf(endItem, indexStartItem);
            if (indexEndItem < 0 || indexStartItem < 0 || (indexEndItem < indexStartItem)) {
                return reqMap;
            }
            String path = pageWithData.substring(indexStartItem + startItemSize, indexEndItem);
            indexStartItem = indexEndItem + endItemSize;
            indexEndItem = pageWithData.indexOf("</a>", indexStartItem);
            if (indexEndItem < 0 || indexStartItem < 0 || (indexEndItem < indexStartItem)) return reqMap;
            String name = pageWithData.substring(indexStartItem, indexEndItem);
                reqMap.put(path, name);
        }
        return reqMap;
    }

    static List<City> getCitiesList(String pageWithCities) {
        List<City> cities = new ArrayList<>();

        return cities;
    }

    @Transactional
    public void parseGrandYandexLst() {
        System.setProperty("proxySet", "true");
        System.setProperty("http.proxyHost", "proxy.reksoft.ru");
        System.setProperty("http.proxyPort", "3128");
        String page = someWeatherWebService.getYandexCountryList();
        List<Country> countryList = getCountries(page);
        List<City> cities = new ArrayList<>();
        for (Country c : countryList) {
            countryService.save(c);
            page = someWeatherWebService.getYandexCityList(c.getPathId());
            List<City> tmpCityList = getCities(page);
            List<City> citiesFromSecondRegion = new ArrayList<>();
            for (City tmpCity : tmpCityList) {
                if (tmpCity.getPathName().contains("region/")) {
                    String s = tmpCity.getPathName().split("/")[1];
                    String sPage = someWeatherWebService.getYandexCityList(s);
                    List<City> lCity = getCities(sPage);
                    for (City lC : lCity) {
                        lC.setPartOfTerritory(tmpCity.getName());
                        lC.setCountry(c);
                        cityService.save(lC);
                    }
                    citiesFromSecondRegion.addAll(lCity);
                } else {
                    tmpCity.setCountry(c);
                    cityService.save(tmpCity);
                }
            }
            cities.addAll(tmpCityList);
            cities.addAll(citiesFromSecondRegion);
        }
    }

}
