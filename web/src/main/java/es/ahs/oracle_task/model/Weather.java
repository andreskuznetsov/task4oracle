package es.ahs.oracle_task.model;

import es.ahs.oracle_task.utils.SomeUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

/**
 * Created by akuznetsov on 07.09.2016.
 */
@NamedQueries({
        @NamedQuery(name = Weather.DELETE, query = "DELETE FROM Weather w WHERE w.id=:id"),
        @NamedQuery(name = Weather.ALL_SORTED_BY_ID, query = "SELECT DISTINCT(w) FROM Weather w ORDER BY w.id"),
        @NamedQuery(name = Weather.ALL_SORTED_BY_NAME, query = "SELECT w FROM Weather w"),
        @NamedQuery(name = Weather.ALL_FOR_CITY, query = "SELECT DISTINCT (w) FROM Weather w WHERE w.cityName = :cityname ORDER BY w.savingTime"),
        @NamedQuery(name = Weather.FOR_CITY_AFTER_TIME, query = "SELECT w FROM Weather w WHERE w.cityName = ?1 AND w.savingTime > ?2 ORDER BY w.savingTime")

})
//        @NamedQuery(name = Weather.BY_NAME, query = "SELECT w FROM Weather w WHERE w.name=?1"),

//        @NamedQuery(name = Weather.ALL_SORTED_BY_NAME, query = "SELECT DISTINCT(w) FROM Weather w ORDER BY w.name")

@Entity
public class Weather extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String DELETE = "Weather.delete";
    public static final String ALL_SORTED_BY_ID = "Weather.getAllSortedById";
    public static final String ALL_SORTED_BY_NAME = "Weather.getAllSortedByName";
    public static final String ALL_FOR_CITY = "Weather.getAllForCity";
    public static final String FOR_CITY_AFTER_TIME = "Weather.getForCityAfterTime";
//    public static final String BY_NAME = "Weather.getByName";

    @Temporal(TemporalType.TIMESTAMP)
    private Date savingTime;

    private java.sql.Date registrationDate;

    private Time registrationTime;

    @OneToOne
    private City cityRef;
    private String cityName;
    private String wind;    // <div class="current-weather__info-row current-weather__info-row_type_wind">
                            // <span class="current-weather__info-label">Ветер: </span> <span class="wind-speed">0,5 м/с</span>
                            // <abbr class=" icon-abbr" title="Ветер: северный">С</abbr>
    private String humidity; // <div class="current-weather__info-row"><span class="current-weather__info-label">Влажность: </span>78%</div>
    private String pressure; // <div class="current-weather__info-row"><span class="current-weather__info-label">Давление: </span>761 мм рт. ст.</div>
    private int tempCurrent;
    private int tempWater;
    private String weatherComment;

//  <span class="current-weather__col current-weather__col_type_now t t_c_14">
//  <i class="icon icon_size_48 icon_thumb_ovc-m-ra" aria-hidden="true" data-width="48"></i>
//  <span class="current-weather__comment">небольшой дождь</span>
//  <div class="current-weather__thermometer current-weather__thermometer_type_now">+14 °C</div></span>

//    private String timeGeneratedWeatherData; //<div class="current-weather__info-row current-weather__info-row_type_time">Данные на 08:00</div>

    public Weather() {
    }

    public Weather(City city, String cityName, Date date, int tempCurrent, int tempWater, String currentWeatherComment) {
        this.cityRef = city;
        this.cityName = cityName;
        this.savingTime = date;
        this.tempCurrent = tempCurrent;
        this.tempWater = tempWater;
        this.weatherComment = currentWeatherComment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public City getCityRef() {
        return cityRef;
    }

    public void setCityRef(City city) {
        this.cityRef = city;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Date getSavingTime() {
        return savingTime;
    }

    public void setSavingTime(Date savingTime) {
        this.savingTime = savingTime;
    }

    public int getTempCurrent() {
        return tempCurrent;
    }

    public void setTempCurrent(int tempCurrent) {
        this.tempCurrent = tempCurrent;
    }

    public int getTempWater() {
        return tempWater;
    }

    public void setTempWater(int tempWater) {
        this.tempWater = tempWater;
    }

    public String getWeatherComment() {
        return weatherComment;
    }

    public void setWeatherComment(String weatherComment) {
        this.weatherComment = weatherComment;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(java.sql.Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Time getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(Time registrationTime) {
        this.registrationTime = registrationTime;
    }

    @Override
    public String toString() {
        return "\nWeather{" +
                "id=" + id +
                ", savingTime=" + SomeUtils.date2string(savingTime) +
                ", city=" + cityRef +
                ", cityName='" + cityName + '\'' +
                ", registration time=" + registrationTime +
                ", registration date=" + registrationDate +
                ", wind='" + wind + '\'' +
                ", humidity='" + humidity + '\'' +
                ", pressure='" + pressure + '\'' +
                ", tempCurrent=" + tempCurrent +
                ", tempWater=" + tempWater +
                ", weatherComment='" + weatherComment + '\'' +
                '}';
    }
}
