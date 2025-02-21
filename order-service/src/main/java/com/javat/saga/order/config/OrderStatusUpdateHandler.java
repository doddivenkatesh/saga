package com.javat.saga.order.config;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.javat.saga.common.dto.OrderRequestDto;
import com.javat.saga.common.event.OrderStatus;
import com.javat.saga.common.event.PaymentStatus;
import com.javat.saga.order.entity.PurchaseOrder;
import com.javat.saga.order.repository.OrderRepository;
import com.javat.saga.order.service.OrderStatusPublisher;

import jakarta.transaction.Transactional;

@Configuration
public class OrderStatusUpdateHandler {

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderStatusPublisher publisher;
	
	@Transactional
	public void updateOrder(int id,Consumer<PurchaseOrder> consumer) {
		
		 orderRepository.findById(id).ifPresent(consumer.andThen(this :: updateOrder));
	}
	
	public void updateOrder(PurchaseOrder purchaseOrder) {
		boolean isPaymentComplete =PaymentStatus.PAYMENT_COMPLETED.equals(purchaseOrder.getPaymentStatus());
		OrderStatus orderStatus=isPaymentComplete? OrderStatus.ORDER_COMPLETED:OrderStatus.ORDER_CANCELLED;
		purchaseOrder.setOrderStatus(orderStatus);
		if(!isPaymentComplete) {
			publisher.publishOrderEvent(null, orderStatus);
		}
	}
	
	public OrderRequestDto convertEntityToDto(PurchaseOrder purchaseOrder) {
		OrderRequestDto orderRequestDto = new OrderRequestDto();
		orderRequestDto.setOrderId(purchaseOrder.getId());
		orderRequestDto.setUserId(purchaseOrder.getUserId());
		orderRequestDto.setAmount(purchaseOrder.getPrice());
		orderRequestDto.setProductId(purchaseOrder.getProductId());
		return orderRequestDto;
	}
	
}
