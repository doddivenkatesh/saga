package com.javt.saga.payment.config;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.javat.saga.common.event.OrderEvent;
import com.javat.saga.common.event.OrderStatus;
import com.javat.saga.common.event.PaymentEvent;
import com.javt.saga.payment.service.PaymentService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Configuration
public class PaymentConsumerConfig {

	@Autowired
	private PaymentService paymentService;
	
	@Bean
	public Function<Flux<OrderEvent>,Flux<PaymentEvent>> paymentprocessor(){
		
		return OrdereventFlux -> OrdereventFlux.flatMap(this :: processPayment);
	}
	
	private Mono<PaymentEvent> processPayment(OrderEvent orderEvent){
		//get user id
		// check the balance availability
		// if balance sufficient -> payment completed and deduct amount from DB
		// if payment not sufficient -> cancel order event and update the amount in DB
		if(OrderStatus.ORDER_CREATED.equals(orderEvent.getOrderStatus())) {
              return Mono.fromSupplier( () -> this.paymentService.newOrderEvent(orderEvent));			
		}else {
			return Mono.fromRunnable( () -> this.paymentService.cancelOrderEvent(orderEvent));
		}
		
	}
	
	
}
