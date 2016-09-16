package es.ahs.oracle_task.utils;

import es.ahs.oracle_task.model.City;
import es.ahs.oracle_task.model.Weather;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by akuznetsov on 07.09.2016.
 */
public class SomeUtils {
    private static final Logger log = LoggerFactory.getLogger(SomeUtils.class);

    public static String date2string(Date date) {
        if (date == null) return "null";
        SimpleDateFormat ft =
                new SimpleDateFormat("E yyyy.MM.dd 'at' hh:mm:ss a zzz");
        return (ft.format(date));
    }

    public static String date2dateString(Date date) {
        if (date == null) return "null";
        SimpleDateFormat df =
                new SimpleDateFormat("yyyy.MM.dd");
        return df.format(date);
    }

    public static String date2timeString(Date date) {
        if (date == null) return "null";
        SimpleDateFormat tf =
                new SimpleDateFormat("HH:MM");
        return tf.format(date);
    }

    public static int convert2int(String stringValue) {
        int value = -1000;
        try {
            value = Integer.parseInt(stringValue);
        } catch (NumberFormatException e) {
            log.warn("Can't convert current stringValue to int, string contains: ^{}^", (stringValue.length() > 10 ? "<big string>" : stringValue));
        }
        return value;
    }

    public static String extractDataFromPage(String yandexPage, String beforeValueString, String afterValueString, int numberAdditionalSkippedSymbols) {
        String resultString = "<no data>";
        try {
            int addToBefore = numberAdditionalSkippedSymbols > 0 ? numberAdditionalSkippedSymbols : 0;
            int minusFromAfter = numberAdditionalSkippedSymbols < 0 ? numberAdditionalSkippedSymbols : 0;
            final int SIZE_OF_TAGS_AND_TEXT = beforeValueString.length() + addToBefore;
            int indexBeforeValue = yandexPage.indexOf(beforeValueString) + SIZE_OF_TAGS_AND_TEXT;
            int indexAfterValue = yandexPage.indexOf(afterValueString, indexBeforeValue);
            resultString = yandexPage.substring(indexBeforeValue, indexAfterValue + minusFromAfter);
        } catch (Exception e) {
            log.warn("Error when extracting data from yandex page. \nError: {}", e.getLocalizedMessage());
        }
        return resultString;
    }

    public static int getCurrentDayOfWeekNumber() {
        Calendar c = Calendar.getInstance();
        Date date = new Date();
        c.setTime(date);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        return dayOfWeek;
    }

    public static List<City> getUniqueCities(List<City> cityList) {
        Map<Integer, City> cityMap = new HashMap<>();
        List<City> resultList = new ArrayList<>();
        for (City city : cityList) {
            cityMap.put(city.getId(), city);
        }
        resultList.addAll(new ArrayList<City>(cityMap.values()));
        return resultList;
    }

    public static String createMailHeader(List<Weather> weatherList) {
        StringBuffer sb = new StringBuffer();
        sb.append(String.format("Weather for city: %s    from: %s to: %s",
                weatherList.get(0).getCityName(),
                date2dateString(weatherList.get(0).getRegistrationDate()),
                date2dateString(weatherList.get(weatherList.size() - 1).getRegistrationDate())));
        return sb.toString();
    }

    public static String createMailBodyTableWithWeather(List<Weather> weatherList) {
        final String weatherStringFormat = "%-13s%-7s%-7s%-7s%-30s%-30s%-7s%-16s%n";
        StringBuffer sb = new StringBuffer();
        sb.append(String.format("%s%n%n", createMailHeader(weatherList)));
        sb.append(String.format(weatherStringFormat, "Date", "Time", "t'air", "water", "Wind", "comment", "humid.", "pressure"));
        sb.append("-------------------------------------------------------------------------------------------------------------------\n");
        for (Weather w : weatherList) {
            String regDate = date2dateString(w.getRegistrationDate());
            String regTime = date2timeString(w.getRegistrationTime());
            String airTemp = (w.getTempCurrent() > -100) ? w.getTempCurrent() + "" : "";
            String waterTemp = (w.getTempWater() > -100) ? w.getTempWater() + "" : "";
            sb.append(String.format(weatherStringFormat,
                    regDate, regTime, airTemp, waterTemp, w.getWind(), w.getWeatherComment(), w.getHumidity(), w.getPressure()));
        }
        sb.append(". . .\n\n");
        return sb.toString();
    }

    public static Date getNewestWeatherTime(List<Weather> weatherList) {
        Date date = new Date(0);
        for (Weather w : weatherList) {
            if (w.getSavingTime().after(date)) date = w.getSavingTime();
        }
        return date;
    }
}
