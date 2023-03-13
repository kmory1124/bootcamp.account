package com.bootcamp.account.services;

import com.bootcamp.account.entity.AccountEntity;
import com.bootcamp.account.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

//Anotacion que indica que esta clase sera de tipo servicio,
@Service
public class AccountServiceImplementation implements AccountService {

    //anotacion para enlazar con la clase repositorio
    @Autowired
    private AccountRepository accountRepository;

    //implementacion para listar todas las cuentas
    public Flux<AccountEntity> ListAll() {
        return accountRepository.findAll();
    }

    //implementacion para listar cuentas por numero de cuenta (de uso interno) (no se expondrá en el controller)
    public Mono<AccountEntity>getByAccount(String AccountNumber){
        return accountRepository.findAll().filter(x -> x.getNumberAccount()!=null && x.getNumberAccount().equals(AccountNumber)).next();
    }

    //implementacion para listar todas las cuentas filtrado por numero de cuenta (se añadirá validacion si no existe, arrojar mensaje perosnalizado)
    //se expondrá en el controller
    @Override
    public Mono<AccountEntity> filterByAccount(String AccountNumber) {
        return accountRepository.findAll().filter(x -> x.getNumberAccount() != null && x.getNumberAccount().equals(AccountNumber)).next();

    }

    //implementacion para realizar validaciones de cuenta, se ingrasa numero de cuenta y debe arrojar el tipo de cuenta
    //se realizará posteriormente por falta de tiempo.
    @Override
    public Mono<AccountEntity> validateCredit(String AccountNumber) {
        return accountRepository.findAll().filter(x -> x.getNumberAccount() != null && x.getNumberAccount().equals(AccountNumber)).next();

    }

    //implementacion para registrar cuentas sin validar si existen.
    @Override
    public Mono<AccountEntity> register(AccountEntity EntAccount) {
        EntAccount.setDateCreate(new Date());
        return accountRepository.save(EntAccount);
    }

    //export in controller (1)
    //implementacion para registrar cuentas de ahorro
    //se añadira posteriormente la validacion de que las condiciones apliquen dentro de un mismo mes.
    @Override
    public Mono<AccountEntity> registerAccountSaving(AccountEntity EntAccount) {
        EntAccount.setLimitMoviment(5); // limite maximo de movimientos
        EntAccount.setComision(0.00); // 0 comision
        EntAccount.setDateCreate(new Date()); //fecha actual

        return getByAccount(EntAccount.getDocumentNumber())
                .switchIfEmpty(accountRepository.save(EntAccount));
    }
    //export in controller (1)
    //implementacion para registrar cuentas corrientes
    //se añadira posteriormente la validacion de que las condiciones apliquen dentro de un mismo mes.
    @Override
    public Mono<AccountEntity> registerAccountCurrent(AccountEntity EntAccount) {
        EntAccount.setLimitMoviment(null); //sin limite de movimientos
        EntAccount.setComision(10.00); // 10% comision
        EntAccount.setDateCreate(new Date()); //fecha actual
        return getByAccount(EntAccount.getDocumentNumber())
                .switchIfEmpty(accountRepository.save(EntAccount));
    }

    //implementacion para registrar cuentas plazo fijo
    //pendiente modificar la fecha de pago (1)
    //se añadira posteriormente la validacion de que las condiciones apliquen dentro de un mismo mes.
    public Mono<AccountEntity> registerAccountPermanentDeadlines(AccountEntity EntAccount) {
        EntAccount.setLimitMoviment(1); //limite de movimiento
        EntAccount.setComision(0.00); //0 comision
        EntAccount.setDateCreate(new Date()); //fecha actual
        EntAccount.setDatePay(new Date()); //fecha de pago , se implementará posteriormente el aumentarle 1 mes la proxima fecha de pago
        return getByAccount(EntAccount.getDocumentNumber())
                .switchIfEmpty(accountRepository.save(EntAccount));
    }

    //implementacion para registrar credito personal
    //se creo la implementación, por falta de tiempo, pendiente realizar el codigo (este pertenece a otra implementación anterior)
    //solo debe permitir 1 por persona
    @Override
    public Mono<AccountEntity> registerCreditPersonal(AccountEntity EntAccount) {
        EntAccount.setLimitMoviment(5);
        EntAccount.setComision(0.00);
        EntAccount.setDateCreate(new Date());

        return getByAccount(EntAccount.getDocumentNumber())
                .switchIfEmpty(accountRepository.save(EntAccount));
    }

    //implementacion para registrar credito empresa
    //se creo la implementación, por falta de tiempo, pendiente realizar el codigo (este pertenece a otra implementación anterior)
    //debe permitir mas de 1 credito por persona
    @Override
    public Mono<AccountEntity> registerCreditCompany(AccountEntity EntAccount) {
        EntAccount.setLimitMoviment(5);
        EntAccount.setComision(0.00);
        EntAccount.setDateCreate(new Date());

        return getByAccount(EntAccount.getDocumentNumber())
                .switchIfEmpty(accountRepository.save(EntAccount));
    }

    //implementacion para registrar tarjeta de credito
    //se creo la implementación, por falta de tiempo, pendiente realizar el codigo (este pertenece a otra implementación anterior)
    @Override
    public Mono<AccountEntity> registerCreditCard(AccountEntity EntAccount) {
        EntAccount.setLimitMoviment(5);
        EntAccount.setComision(0.00);
        EntAccount.setDateCreate(new Date());
        EntAccount.setNumberAccount(EntAccount.getNumberCard()); // se le asigna el mismo numero de tarjeta a la cuenta  para el filtrado interno.
        EntAccount.setDatePay(new Date());

        return getByAccount(EntAccount.getDocumentNumber())
                .switchIfEmpty(accountRepository.save(EntAccount));
    }

}
