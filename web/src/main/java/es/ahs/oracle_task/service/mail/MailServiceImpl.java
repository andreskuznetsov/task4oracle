package es.ahs.oracle_task.service.mail;

import es.ahs.oracle_task.model.WeatherMail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Created by akuznetsov on 12.09.2016.
 */
@Profile("server")
@Component
public class MailServiceImpl implements MailService {
    private static final Logger log = LoggerFactory.getLogger(MailServiceImpl.class);

    @Autowired
    @Qualifier("mailSession")
    Session mail_session;

    @Override
    public boolean sendMail(WeatherMail weatherMail) throws Exception {
        log.info("Send mail: {}", weatherMail);
        MimeMessage msg = new MimeMessage(mail_session);

        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(weatherMail.mailTo));

        msg.setFrom(new InternetAddress("weatherrobot@weathertestapp.com"));

        msg.setSubject(weatherMail.subj);

        msg.setText(weatherMail.body);

        Transport.send(msg);
        return true;
    }
}
