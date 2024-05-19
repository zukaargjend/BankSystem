package com.example.banksystem.services;

import com.example.banksystem.entities.Account;
import com.example.banksystem.entities.Bank;
import com.example.banksystem.excpetions.BankSystemRequestException;
import com.example.banksystem.repositories.BankRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BankService {
    private final BankRepository bankRepository;

    public BankService(BankRepository bankRepository){
        this.bankRepository = bankRepository;
    }

    public void createBank(String bankName){
        Bank bank = Bank.builder()
                .bankName(bankName)
                .totalTransferAmount(0.0)
                .totalTransactionFeeAmount(0.0)
                .transactionFlatFeeAmount(12.5)
                .transactionPercentFeeValue(0.5)
                .build();

        bankRepository.save(bank);
    }

    public Bank getBankById(Long bankId) throws BankSystemRequestException {
        return bankRepository.findById(bankId)
                .orElseThrow(() -> new BankSystemRequestException(+bankId + "Not Found!"));
    }

    public List<Account> getAllAccounts(Long bankId) throws BankSystemRequestException {
        Optional<Bank> bankOptional = bankRepository.findById(bankId);
        if(bankOptional.isPresent()){
            Bank bank = bankOptional.get();
            return bank.getAccounts();
        }else {
            throw new BankSystemRequestException(+bankId +"Not Found!");
        }
    }

    public void addTransactionFeeAndTransferAmount(Bank bank, Double transactionFeeAmount, Double transferAmount){
        bank.setTotalTransactionFeeAmount(bank.getTotalTransactionFeeAmount() + transactionFeeAmount);
        bank.setTotalTransferAmount(bank.getTotalTransferAmount() + transferAmount);

        bankRepository.save(bank);
    }

    public String checkTotalTransactionFeeAmount(String bankName){
        Double amount = bankRepository.findTotalTransactionFeeAmount(bankName);
        return "Total transaction fee amount per bank " + bankName + " is: " + amount + "$";
    }

    public String checkTotalTransferAmount(String bankName){
        Double amount = bankRepository.findTotalTransferAmount(bankName);
        return "Total transaction fee amount per bank " + bankName + " is: " + amount + "$";
    }
}
