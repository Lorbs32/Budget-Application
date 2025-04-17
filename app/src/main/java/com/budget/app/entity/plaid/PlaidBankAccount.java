package com.budget.app.entity.plaid;

import com.budget.app.entity.User;
import jakarta.persistence.*;

@Entity
public class PlaidBankAccount
{
    // Database Setup
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bank_account_id")
    private String id;

    @Column(name = "balance_available")
    private Double balanceAvailable;

    @Column(name = "balance_current")
    private Double balanceCurrent;

    @Column(name = "name")
    private String accountName;

    @Column(name = "name_official")
    private String accountNameOfficial;

    @Column(name = "subtype")
    private String subtype;

    @Column(name = "institution")
    private String institution;

    // Connections
    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private User user;

    public PlaidBankAccount() {}
    public PlaidBankAccount(String id, Double balanceAvailable, Double balanceCurrent, String accountName, String accountNameOfficial, String subtype, String institution, User user) {
        this.id = id;
        this.balanceAvailable = balanceAvailable;
        this.balanceCurrent = balanceCurrent;
        this.accountName = accountName;
        this.accountNameOfficial = accountNameOfficial;
        this.subtype = subtype;
        this.institution = institution;
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public Double getBalanceAvailable() {
        return balanceAvailable;
    }

    public Double getBalanceCurrent() {
        return balanceCurrent;
    }

    public String getAccountName() {
        return accountName;
    }

    public String getAccountNameOfficial() {
        return accountNameOfficial;
    }

    public String getSubtype() {
        return subtype;
    }

    public String getInstitution() {
        return institution;
    }

    public User getUser() {
        return user;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setBalanceAvailable(Double balanceAvailable) {
        this.balanceAvailable = balanceAvailable;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public void setBalanceCurrent(Double balanceCurrent) {
        this.balanceCurrent = balanceCurrent;
    }

    public void setAccountNameOfficial(String accountNameOfficial) {
        this.accountNameOfficial = accountNameOfficial;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public void setUser(User user) {
        this.user = user;
    }

    //    SAMPLE DATA FROM https://sandbox.plaid.com/accounts/balance/get
//    {
//        "accounts": [
//        {
//            "account_id": "VBE1qonrgWCrq3L9PK6zCQWPzkpAB7UqrJdEA",
//                "balances": {
//            "available": 100,
//                    "current": 110,
//        },
//            "name": "Plaid Checking",
//                "official_name": "Plaid Gold Standard 0% Interest Checking",
//                "subtype": "checking",
//        }
//    ],
//        "item": {
//        "institution_name": "Chase",
//    }


}
