package com.budget.app.repository;

import com.budget.app.entity.Transaction;
import com.budget.app.entity.plaid.PlaidAccessTokens;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaidAccessTokensRepository extends JpaRepository<PlaidAccessTokens, Integer> {
    default PlaidAccessTokens updateOrInsert(PlaidAccessTokens plaidAccessToken){return save(plaidAccessToken);}

    PlaidAccessTokens getByUserId(int id);
}
