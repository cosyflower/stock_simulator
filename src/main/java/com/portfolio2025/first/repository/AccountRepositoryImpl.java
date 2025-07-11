package com.portfolio2025.first.repository;

import com.portfolio2025.first.domain.Account;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class AccountRepositoryImpl extends BaseRepositoryImpl<Account, Long> implements AccountRepository {

    public AccountRepositoryImpl(EntityManager em) {
        super(em, Account.class);
    }

    // 추가 조회 방식
    @Override
    public Optional<Account> findByAccountNumber(String accountNumber) {
        String ql = "SELECT a FROM Account a WHERE a.accountNumber = :accountNumber";
        List<Account> result = em.createQuery(ql, Account.class)
                .setParameter("accountNumber", accountNumber)
                .getResultList();
        return result.stream().findFirst();
    }

    // 동명이인이 생길 수 있다
    // 1. String username이 아니라 로그인 세션 정보를 활용하는 방안
    // 2. String username + String email 정보, 즉 복합키를 활용해서 userId를 조회하는 방식
    @Override
    public List<Account> findByUser(String username) {
        String ql = "SELECT a FROM Account a JOIN a.user u WHERE u.name = :username";
        return em.createQuery(ql, Account.class)
                .setParameter("username", username)
                .getResultList();
    }


}

