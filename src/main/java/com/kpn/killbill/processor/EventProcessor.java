package com.kpn.killbill.processor;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.kpn.killbill.model.CustomerDetail;
import com.kpn.killbill.model.Event;

@Component
public class EventProcessor implements ItemProcessor<Event, Event> {

	private static final Map<String, String> DEPT_NAMES = new HashMap<>();

	public EventProcessor() {
		DEPT_NAMES.put("001", "Technology");
		DEPT_NAMES.put("002", "Operations");
		DEPT_NAMES.put("003", "Accounts");
	}

	@Override
	public Event process(Event event) throws Exception {
		event.setStatus("NOT_PROCESSED");
		CustomerDetail detail = getCustomerDetails(event.getCustomerId());
		event.setIban(detail.getIban());
		event.setEmail(detail.getEmail());
		return event;
	}

	public CustomerDetail getCustomerDetails(String customerId) {

		switch (customerId) {

		case "00000000000213833085":
			return new CustomerDetail("NL 96 INGB 0660 6664 56", "kapil.gidwani@gmail.com");

		case "00000000008010010858":
			return new CustomerDetail("NL 66 INGB 0660 6664 56", "wild.anupam@gmail.com");

		case "00000000008010010807":
			return new CustomerDetail("NL 76 INGB 0660 6664 56", "kapil.gidwani@gmail.com");

		}
		
		return new CustomerDetail("NL 76 INGB 0660 6664 56", "kapil.gidwani@gmail.com");

	}
}
