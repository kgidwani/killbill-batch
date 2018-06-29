package com.kpn.killbill.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerDetail {

	private String iban;

	private String email;

}
