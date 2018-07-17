package com.kpn.killbill.model;

import java.util.List;

import com.kpn.killbill.ws.OrderChangeWS;
import com.kpn.killbill.ws.OrderWS;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Order {

	private OrderWS arg0;
	private List<OrderChangeWS> arg1;

}
