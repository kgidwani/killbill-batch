package com.kpn.killbill.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String customerId;

	private String transType;

	private String serviceProv;

	private String status;

	private float chargeAmountIncl;

	private String iban;

	private String email;

}
