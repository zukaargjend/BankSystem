package com.example.banksystem.services;

import com.example.banksystem.dto.TransactionDTO;
import com.example.banksystem.entities.Account;
import com.example.banksystem.entities.Bank;
import com.example.banksystem.excpetions.BankSystemExceptionHandler;
import com.example.banksystem.excpetions.BankSystemRequestException;
import com.example.banksystem.repositories.AccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final BankService bankService;
    private final TransactionService transactionService;

    @Autowired
    public AccountService(AccountRepository accountRepository, BankService bankService, TransactionService transactionService){
        this.accountRepository = accountRepository;
        this.bankService = bankService;
        this.transactionService = transactionService;
    }

    public void createAccount(String name, Long bankId) throws BankSystemRequestException {
        Bank bank = bankService.getBankById(bankId);
        Account account = Account.builder()
                .name(name)
                .balance(0)
                .bank(bank)
                .build();

        accountRepository.save(account);
    }

    public String getAccountById(Long accountId) throws BankSystemRequestException {
        Optional<Account> optionalAccount = accountRepository.findById(accountId);
        if(optionalAccount.isPresent()){
            return optionalAccount.get().toString();
        }else{
            throw new BankSystemRequestException(+accountId +"doesn't exist");
        }
    }

    @Transactional
    public void performTransaction(TransactionDTO transactionDTO) throws BankSystemRequestException{
        Optional<Account> originatingAccountOptional = accountRepository.findById(transactionDTO.getOriginatingAccountId());
        Optional<Account> resultingAccountOptional = accountRepository.findById(transactionDTO.getResultingAccountId());
        if(originatingAccountOptional.isPresent() && resultingAccountOptional.isPresent()){
            Account originatingAccount = originatingAccountOptional.get();
            Account resultingAccount = resultingAccountOptional.get();
            Bank bank = originatingAccount.getBank();
            Double fee = transactionService.performTransaction(transactionDTO, bank.getTransactionFlatFeeAmount(), bank.getTransactionPercentFeeValue());
            if(originatingAccount.getBalance() - (transactionDTO.getAmount() + fee) > 0){
                bankService.addTransactionFeeAndTransferAmount(bank, fee, transactionDTO.getAmount());
                originatingAccount.setBalance(originatingAccount.getBalance() - (transactionDTO.getAmount() + fee));
                resultingAccount.setBalance(originatingAccount.getBalance() + transactionDTO.getAmount());
                this.accountRepository.save(originatingAccount);
                this.accountRepository.save(resultingAccount);
            }else{
                throw new BankSystemRequestException("Not enough money to do this transaction");
            }
        }else {
            throw new BankSystemRequestException("Transaction can not be executed");
        }
    }

    @Transactional
    public void withdraw(TransactionDTO transactionDTO) throws BankSystemRequestException{
        Optional<Account> accountOptional = accountRepository.findById(transactionDTO.getOriginatingAccountId());
        if(accountOptional.isPresent()){
            Account account = accountOptional.get();
            Bank bank = account.getBank();
            transactionDTO.setOriginatingAccountId(null);
            Double fee = transactionService.performTransaction(transactionDTO, bank.getTransactionFlatFeeAmount(), bank.getTransactionPercentFeeValue());
            if(account.getBalance() - (transactionDTO.getAmount() + fee) > 0){
                bankService.addTransactionFeeAndTransferAmount(bank, fee, transactionDTO.getAmount());
                account.setBalance(account.getBalance() - (transactionDTO.getAmount() + fee));
                this.accountRepository.save(account);
            }else{
                throw new BankSystemRequestException("Not enough money to do this transaction!");
            }
        }else{
            throw new BankSystemRequestException("Withdraw can not be executed!");
        }
    }

    @Transactional
    public void deposit(TransactionDTO transactionDTO) throws BankSystemRequestException{
        Optional<Account> accountOptional = accountRepository.findById(transactionDTO.getOriginatingAccountId());
        if(accountOptional.isPresent()){
            Account account = accountOptional.get();
            Bank bank = account.getBank();
            transactionDTO.setResultingAccountId(null);
            Double fee = transactionService.performTransaction(transactionDTO, bank.getTransactionFlatFeeAmount(), bank.getTransactionPercentFeeValue());
            bankService.addTransactionFeeAndTransferAmount(bank, fee, transactionDTO.getAmount());
            if(account.getBalance() > fee){
                account.setBalance(account.getBalance() + transactionDTO.getAmount() - fee);
                this.accountRepository.save(account);
            }else {
                throw new BankSystemRequestException("No money deposited!");
            }
        }else{
            throw new BankSystemRequestException("Deposit can not executed!");
        }
    }


    public String accountsBalance(Long accountId) throws BankSystemRequestException {
        Optional<Account> account = accountRepository.findById(accountId);
        if(account.isPresent()){
            return account.get().toString();
        }else {
            throw new BankSystemRequestException("This account doesn't exist");
        }
    }
}
