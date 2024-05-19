package com.example.banksystem.dto;

import java.util.List;

public record BankTransfer(Long id, String name, List<AccountDTO> accounts,
                           Double totalTransactionFeeAmount, Double totalTransferAmount,
                           Double transactionFlatFeeAmount, Double transactionPercentFeeValue) {
}
