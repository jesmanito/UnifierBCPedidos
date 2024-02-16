package org.empre.service;

import java.io.InputStream;

import java.util.Properties;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class ServiceCron {
    public static Scheduler cron() throws Exception{
        System.out.println("13 DENTRO DE serviceCronTrigger");   
        Properties prop;
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream ("config.properties");
        prop    = new Properties();
        prop.load(inputStream);
        int interval = 0;
        String entorno = GlobalInfo.ENTORNO;
        interval = Integer.parseInt(prop.getProperty(entorno+"_job"));

        final JobKey jobKeyCreation = new JobKey("ServiceJobCreation", "group1");
        final JobDetail jobCreation = JobBuilder.newJob(ServiceJob.class).withIdentity(jobKeyCreation).build();
        final Trigger triggerCreation = TriggerBuilder.newTrigger().withIdentity("ServiceCronTriggerCreation", "group1").withSchedule(SimpleScheduleBuilder.simpleSchedule()
        .withIntervalInMinutes(interval)
        .repeatForever())    
        .build();



        final Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        scheduler.start();

        scheduler.scheduleJob(jobCreation, triggerCreation);

    
        return scheduler;
    
    }


}
