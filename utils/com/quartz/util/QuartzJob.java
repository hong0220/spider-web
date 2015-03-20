package com.quartz.util;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class QuartzJob implements Job {
	public QuartzJob() {

	}

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		System.out.println("执行我.......");
	}
}