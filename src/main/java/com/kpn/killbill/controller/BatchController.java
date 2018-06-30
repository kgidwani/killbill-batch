package com.kpn.killbill.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kpn.killbill.model.Event;
import com.kpn.killbill.processor.MailUtils;
import com.kpn.killbill.repository.EventRepository;
import com.kpn.killbill.writer.PMDMWritter;

@RestController
@RequestMapping("/job")
public class BatchController {

	@Autowired
	JobLauncher jobLauncher;

	@Autowired
	@Qualifier("mrrJob")
	Job mrrJob;

	@Autowired
	@Qualifier("edrJob")
	Job edrJob;

	@Autowired
	EventRepository eventRepository;

	@GetMapping(value = "/start/MRR", produces = MediaType.APPLICATION_JSON_VALUE)
	public BatchStatus startMrrJob() throws JobParametersInvalidException, JobExecutionAlreadyRunningException,
			JobRestartException, JobInstanceAlreadyCompleteException {

		Map<String, JobParameter> maps = new HashMap<>();
		maps.put("time", new JobParameter(System.currentTimeMillis()));
		JobParameters parameters = new JobParameters(maps);
		JobExecution jobExecution = jobLauncher.run(mrrJob, parameters);

		System.out.println("JobExecution: " + jobExecution.getStatus());

		System.out.println("MRR Batch is Running...");
		while (jobExecution.isRunning()) {
			System.out.println("...");
		}

		return jobExecution.getStatus();
	}

	@GetMapping(value = "/start/EDR", produces = MediaType.APPLICATION_JSON_VALUE)
	public BatchStatus startEdrJob() throws JobParametersInvalidException, JobExecutionAlreadyRunningException,
			JobRestartException, JobInstanceAlreadyCompleteException {

		System.out.println("Starting EDR Job");

		Map<String, JobParameter> maps = new HashMap<>();
		maps.put("time", new JobParameter(System.currentTimeMillis()));
		JobParameters parameters = new JobParameters(maps);
		JobExecution jobExecution = jobLauncher.run(edrJob, parameters);

		System.out.println("JobExecution: " + jobExecution.getStatus());

		System.out.println("EDR Batch is Running...");
		while (jobExecution.isRunning()) {
			System.out.println("...");
		}

		return jobExecution.getStatus();
	}

	@GetMapping(value = "/start/MONTHLY-RUN")
	public void montlyBillRunJob() throws IOException {

		System.out.println("Starting MONTHLY-RUN Job");
		List<Event> events = eventRepository.findAll();
		Float netflix = 0f;
		Float O365 = 0f;
		for (Event event : events) {
			event.setStatus("PROCESSED");
			eventRepository.save(event);
			if (event.getServiceProv().equalsIgnoreCase("NETFLIX")) {

				netflix += event.getChargeAmountIncl();
			} else if (event.getServiceProv().equalsIgnoreCase("O365")) {
				O365 += event.getChargeAmountIncl();
			}
			MailUtils.sendMail(event);
		}
	

		PMDMWritter.writePMDMCSV(events);
		PMDMWritter.writeGL(netflix, O365);
		System.out.println("completed MONTHLY-RUN Job");
		

	}

}
