package com.budget.app.entity.plaid;

import com.budget.app.entity.User;
import jakarta.persistence.*;

@Entity
public class PlaidAccessTokens
{
    // Database Setup
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plaid_access_id")
    private int id;

    @Column(name = "access_token")
    private String accessToken;

    @Column(name = "item_id")
    private String itemId;

    @Column(name = "request_id")
    private String requestId;

    // Connections
    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private User user;

    public PlaidAccessTokens() {}
    public PlaidAccessTokens(String accessToken, String itemId, String requestId, User user) {
        this.accessToken = accessToken;
        this.itemId = itemId;
        this.requestId = requestId;
        this.user = user;
    }

    public int getId() {return id;}

    public String getAccessToken() {return accessToken;}

    public String getItemId() {return itemId;}

    public String getRequestId() {return requestId;}

    public User getUser() {return user;}

    public void setId(int id) { this.id = id; }

    public void setAccessToken(String accessToken) { this.accessToken = accessToken; }

    public void setItemId(String itemId) { this.itemId = itemId; }

    public void setRequestId(String requestId) { this.requestId = requestId; }

    public void setUser(User user) { this.user = user; }
}
