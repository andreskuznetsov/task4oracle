package es.ahs.oracle_task.model;

import java.util.Date;

/**
 * Created by akuznetsov on 15.09.2016.
 */
public class WeatherMail {
    public String mailTo;
    public String subj;
    public String body;
    public Date lastSaved;

    @Override
    public String toString() {
        return "WeatherMail{" +
                "mailTo='" + mailTo + '\'' +
                ", subj='" + subj + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
