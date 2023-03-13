package com.bootcamp.account.services;

import com.bootcamp.account.entity.AccountEntity;
import org.bson.types.Decimal128;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountService {
    public Flux<AccountEntity> ListAll();
    public Mono<AccountEntity> getByAccount(String AccountNumber);
    public Mono<AccountEntity> filterByAccount(String AccountNumber);
    public Mono<AccountEntity> validateCredit(String AccountNumber);
    public Mono<AccountEntity> register(AccountEntity EntAccount);
    public Mono<AccountEntity> registerAccountSaving(AccountEntity EntAccount);
    public Mono<AccountEntity> registerAccountCurrent(AccountEntity EntAccount);
    public Mono<AccountEntity> registerAccountPermanentDeadlines(AccountEntity EntAccount);
    public Mono<AccountEntity> registerCreditPersonal(AccountEntity EntAccount);
    public Mono<AccountEntity> registerCreditCompany(AccountEntity EntAccount);
    public Mono<AccountEntity> registerCreditCard(AccountEntity EntAccount);
}
