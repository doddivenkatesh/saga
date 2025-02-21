package com.javat.saga.order.config;

import java.util.function.Supplier;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.javat.saga.common.event.OrderEvent;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Configuration
public class OrderPublisherConfig {

	@Bean
	public Sinks.Many<OrderEvent> orderSinks(){
		return Sinks.many().multicast().onBackpressureBuffer();
	}
	
	@Bean
	public Supplier<Flux<OrderEvent>> orderSupplier(Sinks.Many<OrderEvent> sinks){
		return sinks::asFlux;
		
	}
}
