package com.example.banksystem.services;

import com.example.banksystem.dto.TransactionDTO;
import com.example.banksystem.entities.Transaction;
import com.example.banksystem.excpetions.BankSystemRequestException;
import com.example.banksystem.repositories.TransactionRepository;
import com.example.banksystem.utils.TransactionPerformer;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.banksystem.utils.TransactionPercentFee;
import com.example.banksystem.utils.TransactionFlatFee;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository){
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public Double performTransaction(TransactionDTO transactionDTO, Double flatFeeAmount,
                                     Double percentFeeAmount) throws BankSystemRequestException {

        TransactionPerformer transactionPerformer;

        if(transactionDTO.getTransactionForm().equals("Flat fee")){
            transactionPerformer = new TransactionFlatFee(flatFeeAmount);
        } else if(transactionDTO.getTransactionForm().equals("Percent fee")){
            transactionPerformer = new TransactionPercentFee(percentFeeAmount);
        } else{
            throw new BankSystemRequestException("Please, choose the fee");
        }
        this.transactionRepository.save(
                Transaction.builder()
                        .amount(transactionDTO.getAmount())
                        .originatingAccountId(transactionDTO.getOriginatingAccountId())
                        .resultingAccountId(transactionDTO.getResultingAccountId())
                        .transactionReason(transactionDTO.getTransactionReason())
                        .build());

        return transactionPerformer.performTransaction(transactionDTO.getAmount());
    }

    @Transactional
    public Set<String> transactionOverviewsPerAccount(Long accountId){
        List<Transaction> originatingTransactionList = transactionRepository.findTransactionByOriginatingAccountId(accountId);
        List<Transaction> resultingTransactionList = transactionRepository.findTransactionByResultingAccountId(accountId);
        if(originatingTransactionList.size() == 0 && resultingTransactionList.size() == 0){
            throw new BankSystemRequestException(+ accountId+ " has made a transaction!");
        }

        Set<Transaction> transactions = new HashSet<>();
        transactions.addAll(originatingTransactionList);
        transactions.addAll(resultingTransactionList);
        return transactions.stream().map(transaction -> transaction.toString()).collect(Collectors.toSet());
    }
}
