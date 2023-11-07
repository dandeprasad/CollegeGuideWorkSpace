package org.UploadWorkspace;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class JobTrigger {
	public  void myJobtrigger() throws Exception {
		
		// Quartz 1.6.3
		// JobDetail job = new JobDetail();
		// job.setName("dummyJobName");
		// job.setJobClass(HelloJob.class);

		JobDetail job = JobBuilder.newJob(CallingJob.class)
			.withIdentity("dummyJobName", "group1").build();
		JobDetail jobfesttoday = JobBuilder.newJob(FestsCallingJob.class)
				.withIdentity("festjobToday", "groupfesttoday").build();
		JobDetail job1 = JobBuilder.newJob(SvpcetCallingJob.class)
				.withIdentity("dummyJobName1", "group11").build();

                //Quartz 1.6.3
		// SimpleTrigger trigger = new SimpleTrigger();
		// trigger.setStartTime(new Date(System.currentTimeMillis() + 1000));
		// trigger.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
		// trigger.setRepeatInterval(30000);

		// Trigger the job to run on the next round minute
		Trigger trigger = TriggerBuilder
			.newTrigger()
			.withIdentity("dummyTriggerName", "group1")
			.withSchedule(
				SimpleScheduleBuilder.simpleSchedule()
					.withIntervalInSeconds(180).repeatForever())
			.build();
		Trigger trigger1 = TriggerBuilder
				.newTrigger()
				.withIdentity("dummyTriggerName", "group11")
				.withSchedule(
					SimpleScheduleBuilder.simpleSchedule()
						.withIntervalInSeconds(180).repeatForever())
				.build();
		Trigger trigger3 = TriggerBuilder
				.newTrigger()
				.withIdentity("festjobTodayTrigger", "groupfesttoday")
				.withSchedule(
						CronScheduleBuilder.cronSchedule("0 30 17 * * ? *"))
					.build();

		
		// schedule it
		Scheduler scheduler = new StdSchedulerFactory().getScheduler();
		scheduler.start();
		scheduler.scheduleJob(job, trigger);

		// schedule it
				Scheduler scheduler1 = new StdSchedulerFactory().getScheduler();
				scheduler1.start();
				scheduler1.scheduleJob(job1, trigger1);
				
				Scheduler scheduler2 = new StdSchedulerFactory().getScheduler();
				scheduler2.start();
				scheduler2.scheduleJob(jobfesttoday, trigger3);
				
				
	}
}