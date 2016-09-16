package es.ahs.oracle_task.service.mail;

import es.ahs.oracle_task.model.WeatherMail;

/**
 * Created by akuznetsov on 12.09.2016.
 */
public interface MailService {

    boolean sendMail(WeatherMail weatherMail) throws Exception;
}
