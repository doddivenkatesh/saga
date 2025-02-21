package com.javt.saga.payment.service;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javat.saga.common.dto.OrderRequestDto;
import com.javat.saga.common.dto.PaymentRequestDto;
import com.javat.saga.common.event.OrderEvent;
import com.javat.saga.common.event.PaymentEvent;
import com.javat.saga.common.event.PaymentStatus;
import com.javt.saga.payment.entity.UserBalance;
import com.javt.saga.payment.entity.UserTransactions;
import com.javt.saga.payment.repository.UserBalanceRepository;
import com.javt.saga.payment.repository.UserTransactionRepository;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;

@Service
public class PaymentService {

	@Autowired
	private UserBalanceRepository userBalanceRepository;

	@Autowired
	private UserTransactionRepository userTransactionRepository;

	@PostConstruct
	public void initUserBalanceInDB() {
		userBalanceRepository
				.saveAll(Stream.of(new UserBalance(101, 5000), new UserBalance(102, 3000), new UserBalance(103, 4200),
						new UserBalance(104, 20000), new UserBalance(105, 999)).collect(Collectors.toList()));

	}

	/*
	 * //get user id // check the balance availability // if balance sufficient ->
	 * payment completed and deduct amount from DB // if payment not sufficient ->
	 * cancel order event and update the amount in DB
	 */
	@Transactional
	public PaymentEvent newOrderEvent(OrderEvent orderEvent) {
		OrderRequestDto orderRequestDto = orderEvent.getOrderRequestDto();
		PaymentRequestDto paymentRequestDto = new PaymentRequestDto(orderRequestDto.getOrderId(),
				orderRequestDto.getUserId(), orderRequestDto.getAmount());

		return userBalanceRepository.findById(orderRequestDto.getUserId())
				.filter(ub -> ub.getPrice() > orderRequestDto.getAmount()).map(ub -> {
					ub.setPrice(ub.getPrice() - orderRequestDto.getAmount());

					userTransactionRepository.save(new UserTransactions(orderRequestDto.getOrderId(),
							orderRequestDto.getUserId(), orderRequestDto.getAmount()));
					return new PaymentEvent(paymentRequestDto, PaymentStatus.PAYMENT_COMPLETED);
				}).orElse(new PaymentEvent(paymentRequestDto, PaymentStatus.PAYMENT_FAILED));
	}

	@Transactional
	public void cancelOrderEvent(OrderEvent orderEvent) {
		userTransactionRepository.findById(orderEvent.getOrderRequestDto().getOrderId()).ifPresent(ut -> {
			userTransactionRepository.delete(ut);
			userTransactionRepository.findById(ut.getUserId())
					.ifPresent(ub -> ub.setAmount(ub.getAmount() + ut.getAmount()));
		});
	}

}
