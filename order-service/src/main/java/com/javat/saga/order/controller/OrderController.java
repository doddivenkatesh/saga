package com.javat.saga.order.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javat.saga.common.dto.OrderRequestDto;
import com.javat.saga.order.entity.PurchaseOrder;
import com.javat.saga.order.service.OrderSevice;

@RestController
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	private OrderSevice orderSevice;
	
	@PostMapping("/create")
	public PurchaseOrder createOrder(@RequestBody OrderRequestDto orderRequestDto) {
		return orderSevice.createOrder(orderRequestDto);
	}
	
	@GetMapping
	public List<PurchaseOrder> getOrders(){
		return orderSevice.getAllOrder();
	}

}
