package es.ahs.oracle_task.service.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Created by akuznetsov on 12.09.2016.
 */
@Component("Scheduler")
public class Scheduler {
    private static final Logger log = LoggerFactory.getLogger(Scheduler.class);

    @Autowired
    private SchedulerService schedulerService;

    public void showAlive() {
        log.debug("I'm alive!");
    }

    public void getFreshWeather() {
        schedulerService.getWeather();
    }

    public void sendMails() {
        schedulerService.prepareAndSendMail();
    }
}
