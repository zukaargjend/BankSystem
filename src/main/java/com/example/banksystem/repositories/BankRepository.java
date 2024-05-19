package com.example.banksystem.repositories;

import com.example.banksystem.entities.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BankRepository extends JpaRepository<Bank, Long> {

    @Query(value = "select total_transaction_fee_amount from bank b where b.bank_name = ?", nativeQuery = true)
    Double findTotalTransactionFeeAmount(String bankName);

    @Query(value = "select total_transfer_amount from bank b where b.bank_name = ?", nativeQuery = true)
    Double findTotalTransferAmount(String bankName);
}
