package es.ahs.oracle_task.service.mail;

import es.ahs.oracle_task.model.WeatherMail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * Created by akuznetsov on 12.09.2016.
 */
@Profile("local")
@Component("MailLoopback")
public class MailServiceLoopback implements MailService {
    private static final Logger log = LoggerFactory.getLogger(MailServiceLoopback.class);

    @Override
    public boolean sendMail(WeatherMail weatherMail) throws Exception {
        log.info("Send mail: {}", weatherMail);
        return true;
    }
}
