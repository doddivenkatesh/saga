package com.javt.saga.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.javt.saga.payment.entity.UserTransactions;
@Repository
public interface UserTransactionRepository extends JpaRepository<UserTransactions, Integer> {


}
