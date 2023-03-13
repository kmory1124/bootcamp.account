package com.bootcamp.account.services;

import com.bootcamp.account.entity.AccountEntity;
import com.bootcamp.account.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

@Service
public class AccountServiceImplementation implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    //export in controller
    public Flux<AccountEntity> ListAll() {
        return accountRepository.findAll();
    }

    public Mono<AccountEntity>getByAccount(String AccountNumber){
        return accountRepository.findAll().filter(x -> x.getNumberAccount()!=null && x.getNumberAccount().equals(AccountNumber)).next();
    }

    //export in controller
    @Override
    public Mono<AccountEntity> filterByAccount(String AccountNumber) {
        return accountRepository.findAll().filter(x -> x.getNumberAccount() != null && x.getNumberAccount().equals(AccountNumber)).next();
        //.switchIfEmpty(Mono.error(new CustomException("Client not found")));
    }
    @Override
    public Mono<AccountEntity> validateCredit(String AccountNumber) {
        return accountRepository.findAll().filter(x -> x.getNumberAccount() != null && x.getNumberAccount().equals(AccountNumber)).next();
        //.switchIfEmpty(Mono.error(new CustomException("Client not found")));
    }

    //Register account with no validate
    @Override
    public Mono<AccountEntity> register(AccountEntity EntAccount) {
        EntAccount.setDateCreate(new Date());
        return accountRepository.save(EntAccount);
    }

    //export in controller (1)
    //Register account with validate
    @Override
    public Mono<AccountEntity> registerAccountSaving(AccountEntity EntAccount) {
        EntAccount.setLimitMoviment(5);
        EntAccount.setComision(0.00);
        EntAccount.setDateCreate(new Date());

        return getByAccount(EntAccount.getDocumentNumber())
                .switchIfEmpty(accountRepository.save(EntAccount));
    }
    //export in controller (1)
    //Register account with validate
    @Override
    public Mono<AccountEntity> registerAccountCurrent(AccountEntity EntAccount) {
        EntAccount.setLimitMoviment(null);
        EntAccount.setComision(10.00);
        EntAccount.setDateCreate(new Date());
        return getByAccount(EntAccount.getDocumentNumber())
                .switchIfEmpty(accountRepository.save(EntAccount));
    }

    //pendiente modificar la fecha de pago (1)
    public Mono<AccountEntity> registerAccountPermanentDeadlines(AccountEntity EntAccount) {
        EntAccount.setLimitMoviment(1);
        EntAccount.setComision(0.00);
        EntAccount.setDateCreate(new Date());
        EntAccount.setDatePay(new Date());
        return getByAccount(EntAccount.getDocumentNumber())
                .switchIfEmpty(accountRepository.save(EntAccount));
    }
    //export in controller
    //Register credit person
    @Override
    public Mono<AccountEntity> registerCreditPersonal(AccountEntity EntAccount) {
        EntAccount.setLimitMoviment(5);
        EntAccount.setComision(0.00);
        EntAccount.setDateCreate(new Date());

        return getByAccount(EntAccount.getDocumentNumber())
                .switchIfEmpty(accountRepository.save(EntAccount));
    }

    //export in controller
    //Register credit person
    @Override
    public Mono<AccountEntity> registerCreditCompany(AccountEntity EntAccount) {
        EntAccount.setLimitMoviment(5);
        EntAccount.setComision(0.00);
        EntAccount.setDateCreate(new Date());

        return getByAccount(EntAccount.getDocumentNumber())
                .switchIfEmpty(accountRepository.save(EntAccount));
    }

    //export in controller
    //Register credit person
    @Override
    public Mono<AccountEntity> registerCreditCard(AccountEntity EntAccount) {
        EntAccount.setLimitMoviment(5);
        EntAccount.setComision(0.00);
        EntAccount.setDateCreate(new Date());
        EntAccount.setNumberAccount(EntAccount.getNumberCard());
        EntAccount.setDatePay(new Date());

        return getByAccount(EntAccount.getDocumentNumber())
                .switchIfEmpty(accountRepository.save(EntAccount));
    }

}
