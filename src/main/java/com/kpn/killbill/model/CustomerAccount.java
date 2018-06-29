package com.kpn.killbill.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class CustomerAccount {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String customerId;

	private String serviceProv;

	private String transType;

	private String status;

}
