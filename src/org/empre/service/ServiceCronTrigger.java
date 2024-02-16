package org.empre.service;

import org.quartz.Scheduler;
import org.quartz.impl.StdSchedulerFactory;

public class ServiceCronTrigger {
    public static Scheduler cron() throws Exception{
            System.out.println("12 DENTRO DE serviceCronTrigger");
            final Scheduler scheduler = new StdSchedulerFactory().getScheduler();
            scheduler.start();

          //  scheduler.scheduleJob(jobCreation, triggerCreation);

            
            return scheduler;
        }
    /*
    public static Scheduler cron() throws Exception{
        System.out.println("12 DENTRO DE serviceCronTrigger");   
        Properties prop;
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream ("config.properties");
        prop    = new Properties();
        prop.load(inputStream);
        int interval = 0;
        String entorno = GlobalInfo.ENTORNO;
        interval = Integer.parseInt(prop.getProperty(entorno+"_job"));

        final JobKey jobKeyCreation = new JobKey("ServiceJobCreation", "group1");
        final JobDetail jobCreation = JobBuilder.newJob(ServiceJobCreation.class).withIdentity(jobKeyCreation).build();
        final Trigger triggerCreation = TriggerBuilder.newTrigger().withIdentity("ServiceCronTriggerCreation", "group1").withSchedule(SimpleScheduleBuilder.simpleSchedule()
        .withIntervalInMinutes(interval)
        .repeatForever())    
        .build();



        final Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        scheduler.start();

        scheduler.scheduleJob(jobCreation, triggerCreation);

 
        return scheduler;
 
    }
    
    */
}
