package org.empre.service;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class ServiceJob implements Job {
    public ServiceJob() {
        super();
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        // TODO Implement this method
        System.out.println("16 ANTES DE MAIN");
    MainProcess mainProcess = new MainProcess();
    //mainProcess.init();
    }
}
