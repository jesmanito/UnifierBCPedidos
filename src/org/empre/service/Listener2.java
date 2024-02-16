package org.empre.service;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

public class Listener2 implements ServletContextListener {
    public Listener2() {
        super();
    }

    private static final Logger logger = LogManager.getLogger(Listener2.class);
    Scheduler sch = null;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        // TODO Implement this method
        System.out.println("ARRANCA EN LISTENER empieza");   
        //logger.info("EN LISTENER");

        
        ServiceCronTrigger serviceCronTrigger = new ServiceCronTrigger();
        ServiceCron ser = new ServiceCron();
        System.out.println("ARRANCA EN LISTENER 1");   
        try {
            System.out.println("ARRANCA EN LISTENER 2");   
            sch = StdSchedulerFactory.getDefaultScheduler();
            System.out.println("ARRANCA EN LISTENER 31333");   
        } catch (SchedulerException e) {
            System.out.println("ARRANCA EN LISTENER 4");   
        }
        try {
               System.out.println("ANTES DE serviceCronTrigger 1");   
            sch = ser.cron();
            //sch = serviceCronTrigger.cron();
          // sch =  serviceCronTrigger.cron();
               
             
               
           } catch (Exception e) {
               System.out.println("Error service cron: "+e.getMessage());
               logger.error("Error service cron: "+e.getMessage());
           }



        
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        // TODO Implement this method
        try {
            sch.shutdown();
        } catch (SchedulerException e) {
            logger.error("Error service cron shutdown: "+e.getMessage());
        }
    }
}
