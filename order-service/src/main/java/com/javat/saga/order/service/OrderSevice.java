package com.javat.saga.order.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.javat.saga.common.dto.OrderRequestDto;
import com.javat.saga.common.event.OrderStatus;
import com.javat.saga.order.entity.PurchaseOrder;
import com.javat.saga.order.repository.OrderRepository;

import jakarta.transaction.Transactional;

public class OrderSevice {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderStatusPublisher orderStatusPublisher;

	@Transactional
	public PurchaseOrder createOrder(OrderRequestDto orderRequestDto) {
		PurchaseOrder order = orderRepository.save(convertDtoToEntity(orderRequestDto));
		orderRequestDto.setOrderId(order.getId());
		// produce kafka event with status ORDER_CREATED
		orderStatusPublisher.publishOrderEvent(orderRequestDto, OrderStatus.ORDER_CREATED);
		return order;
	}
	
	public List<PurchaseOrder> getAllOrder(){
		return orderRepository.findAll();
	}

	private PurchaseOrder convertDtoToEntity(OrderRequestDto dto) {

		PurchaseOrder purchaseOrder = new PurchaseOrder();
		purchaseOrder.setProductId(dto.getProductId());
		purchaseOrder.setUserId(dto.getUserId());
		purchaseOrder.setOrderStatus(OrderStatus.ORDER_CREATED);
		purchaseOrder.setPrice(dto.getAmount());
		return purchaseOrder;
	}
}
