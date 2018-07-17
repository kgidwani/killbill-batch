package com.kpn.killbill.controller;

import java.net.URISyntaxException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kpn.killbill.model.Order;
import com.kpn.killbill.model.User;
import com.kpn.killbill.service.JbillingService;

@RestController
@RequestMapping("/jbilling")
public class JbillingController {

	@Autowired
	private JbillingService jbillingService;

	@PostMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Integer> createUser(@RequestBody @Valid User user) throws URISyntaxException {

		System.out.println(user);
		return ResponseEntity.ok(jbillingService.createUserWithCompanyId(user));

	}

	@PostMapping(value = "/order", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Integer> createOrder(@RequestBody @Valid Order order) throws URISyntaxException {
		
		System.out.println(order);

		return ResponseEntity.ok(jbillingService.createOrder(order));

	}

}
