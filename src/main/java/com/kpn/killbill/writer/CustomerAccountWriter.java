package com.kpn.killbill.writer;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kpn.killbill.model.CustomerAccount;
import com.kpn.killbill.repository.CustomerAccountRepository;

@Component
public class CustomerAccountWriter implements ItemWriter<CustomerAccount> {

	@Autowired
	private CustomerAccountRepository customerAccountRepository;

	@Override
	public void write(List<? extends CustomerAccount> customerAccount) throws Exception {

		System.out.println("Data Saved for customerAccount: " + customerAccount);
		customerAccountRepository.saveAll(customerAccount);
	}

}
