package com.example.banksystem.repositories;

import com.example.banksystem.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findTransactionByOriginatingAccountId(Long originatingAccountId);
    List<Transaction> findTransactionByResultingAccountId(Long resultingAccountId);
}
