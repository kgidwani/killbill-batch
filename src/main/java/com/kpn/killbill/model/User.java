package com.kpn.killbill.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class User {

	private Integer accountTypeId;
	private String userName;
	private Integer mainRoleId;
	private Integer statusId;
	private Integer currencyId;
	private Integer languageId;

}
