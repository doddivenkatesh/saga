package com.javat.saga.common.event;

import java.util.Date;
import java.util.UUID;

import com.javat.saga.common.dto.PaymentRequestDto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class PaymentEvent implements Event {

	private UUID eventId = UUID.randomUUID();
	private Date eventDate = new Date();
	private PaymentRequestDto paymentRequestDto;
	private PaymentStatus paymentStatus;

	@Override
	public UUID getEventId() {
		return eventId;
	}

	@Override
	public Date getDate() {
		return eventDate;
	}

	public PaymentEvent(PaymentRequestDto paymentRequestDto, PaymentStatus paymentStatus) {
		super();
		this.paymentRequestDto = paymentRequestDto;
		this.paymentStatus = paymentStatus;
	}

}
