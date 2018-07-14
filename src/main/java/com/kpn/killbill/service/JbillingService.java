package com.kpn.killbill.service;

import org.springframework.stereotype.Service;

import com.kpn.killbill.model.User;
import com.kpn.killbill.ws.MigrationService;
import com.kpn.killbill.ws.MigrationServiceService;
import com.kpn.killbill.ws.UserWS;

@Service
public class JbillingService {

	private MigrationServiceService migrationServiceService = new MigrationServiceService();

	public Integer createUserWithCompanyId(User user) {

		UserWS userWS = new UserWS();

		userWS.setAccountTypeId(user.getAccountTypeId());
		userWS.setUserName(user.getUserName());
		userWS.setMainRoleId(user.getMainRoleId());
		userWS.setStatusId(user.getStatusId());
		userWS.setCurrencyId(user.getCurrencyId());
		userWS.setLanguageId(user.getLanguageId());

		MigrationService port = migrationServiceService.getMigrationServicePort();
		return port.createUserWithCompanyId(userWS, 10);

	}

}
