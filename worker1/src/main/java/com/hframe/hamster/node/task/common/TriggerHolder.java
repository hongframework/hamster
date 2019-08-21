package com.hframe.hamster.node.task.common;

import com.alibaba.otter.canal.common.utils.BooleanMutex;
import org.quartz.*;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.triggers.CronTriggerImpl;

import java.text.ParseException;

/**
 * Created by zhangquanhong on 2017/3/21.
 */
public class TriggerHolder{

    private static SchedulerFactory schedulerFactory = new StdSchedulerFactory();
    private Scheduler scheduler = null;

    private String cronExpression;

    private volatile BooleanMutex triggerMutex = new BooleanMutex(false);
    private volatile BooleanMutex statusMutex = new BooleanMutex(false);

    public TriggerHolder(String cronExpression) throws SchedulerException, ParseException {
        this.cronExpression = cronExpression;
        scheduler  =  schedulerFactory.getScheduler();
        CronTriggerImpl trigger  =   new CronTriggerImpl();
        trigger.setCronExpression(cronExpression);
        trigger.setName(cronExpression);


        JobDetailImpl jobDetail  =   new JobDetailImpl();
        jobDetail.setJobClass(JobImpl.class);
        jobDetail.setKey(new JobKey(cronExpression));
        jobDetail.getJobDataMap().put("triggerMutex", triggerMutex);
        jobDetail.getJobDataMap().put("statusMutex", statusMutex);
        scheduler.scheduleJob(jobDetail, trigger);
        scheduler.start();
    }

    public void rollback() {
        triggerMutex.set(true);
    }

    public static class JobImpl implements Job{

        public JobImpl() {
        }

        @Override
        public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
            BooleanMutex triggerMutex = (BooleanMutex) jobExecutionContext.getMergedJobDataMap().get("triggerMutex");
            BooleanMutex statusMutex = (BooleanMutex) jobExecutionContext.getMergedJobDataMap().get("statusMutex");
            if(statusMutex.state()) {
                triggerMutex.set(true);
            }

        }
    }


    public void start() throws SchedulerException {
        statusMutex.set(true);
        scheduler.start();
    }

    public boolean isStart() {
        return statusMutex.state();
    }

    public void stop() {
        if(isStart()) {
            synchronized (this) {
                if(isStart()) {
                    statusMutex.set(false);
                    triggerMutex.set(false);
                }
            }
        }
    }
    public void waitForTrigger() throws InterruptedException {
        triggerMutex.get();
        triggerMutex.set(false);
    }

    public static TriggerHolder hold(String cronExpression) throws SchedulerException, ParseException {
        return new TriggerHolder(cronExpression);
    }


    public static void main(String[] args) throws ParseException, SchedulerException, InterruptedException {
        TriggerHolder holder = TriggerHolder.hold("0/3 * * * * ?");
        holder.start();
        while (true) {
            holder.waitForTrigger();
            System.out.println("1");
        }
    }

}
