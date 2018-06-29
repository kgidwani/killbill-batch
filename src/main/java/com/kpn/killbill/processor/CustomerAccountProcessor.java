package com.kpn.killbill.processor;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.kpn.killbill.model.CustomerAccount;

@Component
public class CustomerAccountProcessor implements ItemProcessor<CustomerAccount, CustomerAccount> {

	private static final Map<String, String> DEPT_NAMES = new HashMap<>();

	public CustomerAccountProcessor() {
		DEPT_NAMES.put("001", "Technology");
		DEPT_NAMES.put("002", "Operations");
		DEPT_NAMES.put("003", "Accounts");
	}

	@Override
	public CustomerAccount process(CustomerAccount customerAccount) throws Exception {
		customerAccount.setStatus("Active");
		return customerAccount;
	}
}
