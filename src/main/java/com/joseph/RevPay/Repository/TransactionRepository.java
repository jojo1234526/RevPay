package com.joseph.RevPay.Repository;

import com.joseph.RevPay.Model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
