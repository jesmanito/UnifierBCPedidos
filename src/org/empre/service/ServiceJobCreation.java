package org.empre.service;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class ServiceJobCreation implements Job {
    public ServiceJobCreation() {
        super();
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        // TODO Implement this method
System.out.println("11 ANTES DE MAIN");
        //Main main = new Main();
        //main.init();
    }
}
