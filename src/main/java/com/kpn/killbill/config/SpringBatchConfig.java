package com.kpn.killbill.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import com.kpn.killbill.model.CustomerAccount;
import com.kpn.killbill.model.Event;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {

	@Bean
	public Job mrrJob(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory,
			ItemReader<CustomerAccount> itemReader, ItemProcessor<CustomerAccount, CustomerAccount> itemProcessor,
			ItemWriter<CustomerAccount> itemWriter) {

		Step step = stepBuilderFactory.get("MRR-file-load").<CustomerAccount, CustomerAccount>chunk(100)
				.reader(itemReader).processor(itemProcessor).writer(itemWriter).build();

		return jobBuilderFactory.get("MRR-Load").incrementer(new RunIdIncrementer()).start(step).build();
	}

	@Bean
	public Job edrJob(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory,
			ItemReader<Event> edrItemReader, ItemProcessor<Event, Event> edrItemProcessor,
			ItemWriter<Event> itemWriter) {

		Step step = stepBuilderFactory.get("EDR-file-load").<Event, Event>chunk(100).reader(edrItemReader)
				.processor(edrItemProcessor).writer(itemWriter).build();

		return jobBuilderFactory.get("MRR-Load").incrementer(new RunIdIncrementer()).start(step).build();
	}

	@Bean
	public FlatFileItemReader<CustomerAccount> itemReader(@Value("${input.mrr}") Resource resource) {

		FlatFileItemReader<CustomerAccount> flatFileItemReader = new FlatFileItemReader<>();
		flatFileItemReader.setResource(resource);
		flatFileItemReader.setName("CSV-Reader");
		flatFileItemReader.setLinesToSkip(1);
		flatFileItemReader.setLineMapper(lineMapper());
		return flatFileItemReader;
	}

	@Bean
	public FlatFileItemReader<Event> edrItemReader(@Value("${input.edr}") Resource resource) {

		FlatFileItemReader<Event> flatFileItemReader = new FlatFileItemReader<>();
		flatFileItemReader.setResource(resource);
		flatFileItemReader.setName("CSV-Reader");
		flatFileItemReader.setLinesToSkip(1);
		flatFileItemReader.setLineMapper(eventLineMapper());
		return flatFileItemReader;
	}

	@Bean
	public LineMapper<CustomerAccount> lineMapper() {

		DefaultLineMapper<CustomerAccount> defaultLineMapper = new DefaultLineMapper<>();
		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();

		lineTokenizer.setDelimiter(";");
		lineTokenizer.setStrict(false);
		lineTokenizer.setNames(new String[] { "CustomerId", "TransType", "ServiceProv", });

		BeanWrapperFieldSetMapper<CustomerAccount> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
		fieldSetMapper.setTargetType(CustomerAccount.class);

		defaultLineMapper.setLineTokenizer(lineTokenizer);
		defaultLineMapper.setFieldSetMapper(fieldSetMapper);

		return defaultLineMapper;
	}

	@Bean
	public LineMapper<Event> eventLineMapper() {

		DefaultLineMapper<Event> defaultLineMapper = new DefaultLineMapper<>();
		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();

		lineTokenizer.setDelimiter(";");
		lineTokenizer.setStrict(false);
		lineTokenizer.setNames(new String[] { "CustomerId", "TransType", "ChargeAmountIncl", "ServiceProv", });

		BeanWrapperFieldSetMapper<Event> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
		fieldSetMapper.setTargetType(Event.class);

		defaultLineMapper.setLineTokenizer(lineTokenizer);
		defaultLineMapper.setFieldSetMapper(fieldSetMapper);

		return defaultLineMapper;
	}

}
