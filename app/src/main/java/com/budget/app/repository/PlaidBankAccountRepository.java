package com.budget.app.repository;

import com.budget.app.entity.plaid.PlaidBankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaidBankAccountRepository extends JpaRepository<PlaidBankAccount, Integer> {
    default PlaidBankAccount updateOrInsert(PlaidBankAccount plaidBankAccount){return save(plaidBankAccount);}

    List<PlaidBankAccount> getByUserId(int id);
}
