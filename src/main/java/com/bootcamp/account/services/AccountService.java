package com.bootcamp.account.services;

import com.bootcamp.account.entity.AccountEntity;
import org.bson.types.Decimal128;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountService {

    //se crea interfaz para listar todos los usuarios. (se expone en el controller)
    public Flux<AccountEntity> ListAll();

    //se crea interfaz para buscar una cuenta por numero de cuenta, para uso interno. (no se expone en el controller)
    public Mono<AccountEntity> getByAccount(String AccountNumber);

    //se crea interfaz para buscar una cuenta por numero de cuenta (se tenia la intencion para que cuando no encuentre un
    // registro mostrar un mensaje personalizado,
    // se implementará posteriormente por el momento se deja igual que la interfaz anterior.) (se expone en el controller)
    public Mono<AccountEntity> filterByAccount(String AccountNumber);

    //se crea interfaz para realizar validaciones de cuenta, se ingresa la cuenta y debe retornar que tipo de cuenta es.
    // (se terminara posteriormente por falta de tiempo)
    public Mono<AccountEntity> validateCredit(String AccountNumber);

    //se crea interfaz para registar una nueva cuenta sin validar que esta exista (de uso interno) (no se expondrá en el controller)
    public Mono<AccountEntity> register(AccountEntity EntAccount);

    //se crea interfaz para registrar una nueva cuenta tipo ahorros validando que esta no exista (se expondrá en el controller)
    public Mono<AccountEntity> registerAccountSaving(AccountEntity EntAccount);

    //se crea interfaz para registrar una nueva cuenta tipo corriente validando que esta no exista (se expondrá en el controller)
    public Mono<AccountEntity> registerAccountCurrent(AccountEntity EntAccount);

    //se crea interfaz para registrar una nueva cuenta tipo plazo fijos validando que esta no exista (se expondrá en el controller)
    public Mono<AccountEntity> registerAccountPermanentDeadlines(AccountEntity EntAccount);

    //se crea interfaz para registrar un nuevo credito personal (se expondrá en el controller) (se terminara posteriormente por falta de tiempo)
    public Mono<AccountEntity> registerCreditPersonal(AccountEntity EntAccount);

    //se crea interfaz para registrar un nuevo credito empresa (se expondrá en el controller) (se terminara posteriormente por falta de tiempo)
    public Mono<AccountEntity> registerCreditCompany(AccountEntity EntAccount);

    //se crea interfaz para registrar una nueva tarjeta credito (se expondrá en el controller) (se terminara posteriormente por falta de tiempo)
    public Mono<AccountEntity> registerCreditCard(AccountEntity EntAccount);
}
