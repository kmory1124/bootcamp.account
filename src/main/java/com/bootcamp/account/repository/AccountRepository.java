package com.bootcamp.account.repository;

import com.bootcamp.account.entity.AccountEntity;
import org.bson.types.ObjectId;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

//reference: https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/reactive/ReactiveCrudRepository.html
//al extender a la interfaz ReactiveCrudRepository se obtiene los metodos necesarios para poder operar con mongodb, de esta forma se ahorra el codigo.
public interface AccountRepository extends ReactiveCrudRepository<AccountEntity, ObjectId> {
}
