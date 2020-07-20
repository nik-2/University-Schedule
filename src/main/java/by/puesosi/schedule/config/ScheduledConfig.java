package by.puesosi.schedule.config;

import by.puesosi.schedule.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
public class ScheduledConfig {

    private Service service;

    @Autowired
    public ScheduledConfig(Service service){
        this.service = service;
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void checkGroupSchedule(){
        service.checkBSUIRSchedule();
    }
}
