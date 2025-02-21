package com.javat.saga.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javat.saga.order.entity.PurchaseOrder;

public interface OrderRepository extends JpaRepository<PurchaseOrder, Integer>  {

}
