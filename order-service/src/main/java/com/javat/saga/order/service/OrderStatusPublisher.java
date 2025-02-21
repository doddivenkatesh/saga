package com.javat.saga.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javat.saga.common.dto.OrderRequestDto;
import com.javat.saga.common.event.OrderEvent;
import com.javat.saga.common.event.OrderStatus;

import reactor.core.publisher.Sinks;

@Service
public class OrderStatusPublisher {

	@Autowired
	private Sinks.Many<OrderEvent> orderSinks;
	
	public void publishOrderEvent(OrderRequestDto orderRequestDto,OrderStatus orderStatus) {
		OrderEvent orderEvent = new OrderEvent(orderRequestDto,orderStatus);
		orderSinks.tryEmitNext(orderEvent);
	}
}
