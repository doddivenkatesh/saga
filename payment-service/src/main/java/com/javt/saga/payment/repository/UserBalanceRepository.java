package com.javt.saga.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javt.saga.payment.entity.UserBalance;

public interface UserBalanceRepository extends JpaRepository<UserBalance, Integer> {

}
