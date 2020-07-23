package by.puesosi.schedule.config;

import by.puesosi.schedule.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * The type Scheduled config.
 */
@Configuration
public class ScheduledConfig {

    private Service service;

    /**
     * Instantiates a new Scheduled config.
     *
     * @param service the service
     */
    @Autowired
    public ScheduledConfig(Service service){
        this.service = service;
    }

    /**
     * Check group schedule.
     */
    @Scheduled(cron = "0 0 0 * * *")
    public void checkGroupSchedule(){
        service.checkBSUIRSchedule();
    }
}
