package com.kpn.killbill.writer;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kpn.killbill.model.Event;
import com.kpn.killbill.repository.EventRepository;

@Component
public class EventWriter implements ItemWriter<Event> {

	@Autowired
	private EventRepository eventRepository;

	@Override
	public void write(List<? extends Event> event) throws Exception {

		System.out.println("Data Saved for Event: " + event);
		eventRepository.saveAll(event);
	}

}
