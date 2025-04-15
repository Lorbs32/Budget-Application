package com.budget.app.controller;

import com.budget.app.entity.User;
import com.budget.app.entity.plaid.PlaidAccessTokens;
import com.budget.app.entity.plaid.PlaidBankAccount;
import com.budget.app.security.model.CustomUserDetails;
import com.budget.app.service.BudgetService;
import com.plaid.client.ApiClient;
import com.plaid.client.model.*;
import com.plaid.client.request.PlaidApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import retrofit2.Response;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/plaid")
public class PlaidController
{
    @Value("${plaid.client.id}")
    private String plaidClientId;

    @Value("${plaid.secret}")
    private String plaidSecret;

    @Autowired
    private BudgetService budgetService;


    @GetMapping("/link-token")
    public ResponseEntity<?> getLinkToken() throws IOException
    {
//        System.out.println("Plaid Client Id: " + plaidClientId);
//        System.out.println("Plaid Secret: " + plaidSecret);

        // Set up Plaid client
        HashMap<String, String> apiKeys = new HashMap<>();
        apiKeys.put("clientId", plaidClientId);
        apiKeys.put("secret", plaidSecret);

        ApiClient apiClient = new ApiClient(apiKeys);
        apiClient.setPlaidAdapter(ApiClient.Sandbox);
        PlaidApi plaidClient = apiClient.createService(PlaidApi.class);

        // Get current authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User currentUser = userDetails.getUser();
        String clientUserId = String.valueOf(currentUser.getId());

        // Build request
        LinkTokenCreateRequest request = new LinkTokenCreateRequest()
                .user(new LinkTokenCreateRequestUser().clientUserId(clientUserId))
                .clientName("Budget App")
                .products(Arrays.asList(Products.AUTH, Products.TRANSACTIONS))
                .countryCodes(Arrays.asList(CountryCode.US))
                .language("en");

        // Execute request
        Response<LinkTokenCreateResponse> response = plaidClient.linkTokenCreate(request).execute();

        if (response.isSuccessful())
        {
            // EXCLUDES other unused response body fields 'expiration', 'requestId', 'hostedLinkUrl'
            Map<String, String> tokenMap = new HashMap<>();
            tokenMap.put("link_token", response.body().getLinkToken());
            return ResponseEntity.ok(tokenMap);
            // return ResponseEntity.ok(response.body());
        }
        else
        {
            return ResponseEntity.status(500).body("Failed to create link token: " + response.errorBody().string());
        }
    }

//    @CrossOrigin(origins = "http://localhost:8080", allowCredentials = "true")
    @PostMapping("/public-token-exchange")
    public ResponseEntity<?> publicTokenExchange(@RequestParam("public_token") String publicToken) throws IOException
    {
        // Set up Plaid client
        HashMap<String, String> apiKeys = new HashMap<>();
        apiKeys.put("clientId", plaidClientId);
        apiKeys.put("secret", plaidSecret);

        ApiClient apiClient = new ApiClient(apiKeys);
        apiClient.setPlaidAdapter(ApiClient.Sandbox);
        PlaidApi plaidClient = apiClient.createService(PlaidApi.class);

        // Get current authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User currentUser = userDetails.getUser();

        // Build request
        ItemPublicTokenExchangeRequest request = new ItemPublicTokenExchangeRequest()
                .publicToken(publicToken);

        // Execute request
        Response<ItemPublicTokenExchangeResponse> response = plaidClient.itemPublicTokenExchange(request).execute();

        budgetService.updateOrInsertPlaidAccess(response.body().getAccessToken(),
                                                response.body().getItemId(),
                                                response.body().getRequestId(),
                                                currentUser);

        if (response.isSuccessful())
        {
            System.out.println("Public to persistent access token successful: " + response.body().toString());
            return ResponseEntity.status(200).body("Public to persistent access token successful: " + response.body().toString());
        }
        else
        {
            System.out.println("Failed to exchange public for persistent token: " + response.errorBody().string());
            return ResponseEntity.status(500).body("Failed to exchange public for persistent oken: " + response.errorBody().string());
        }
    }

    @GetMapping("accounts-balance")
    public ResponseEntity<?> accountsBalance() throws IOException
    {
        // Set up Plaid client
        HashMap<String, String> apiKeys = new HashMap<>();
        apiKeys.put("clientId", plaidClientId);
        apiKeys.put("secret", plaidSecret);

        ApiClient apiClient = new ApiClient(apiKeys);
        apiClient.setPlaidAdapter(ApiClient.Sandbox);
        PlaidApi plaidClient = apiClient.createService(PlaidApi.class);

        // Get current authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User currentUser = userDetails.getUser();

        //PlaidAccessTokens plaidAccessTokens = budgetService.getPlaidAccessToken(currentUser);

        // Build request
        AccountsBalanceGetRequest request = new AccountsBalanceGetRequest()
                .accessToken(budgetService.getPlaidAccessToken(currentUser).getAccessToken());
                        //plaidAccessTokens.getAccessToken());

        // Execute request
        Response<AccountsGetResponse> response = plaidClient.accountsBalanceGet(request).execute();

        // PARSE THROUGH THE RESPONSE
        // STORE THE DATA IN THE Plaid Bank Account entity table.
        if (response.isSuccessful())
        {
            // This assumes there is an account retrieved, and that there is only one account and is has a subtype defined.
            // TO DO null checking
            // TO DO flexibility for more than 1 account found / connected.
            String accountId = response.body().getAccounts().get(0).getAccountId();
            Double balanceAvailable = response.body().getAccounts().get(0).getBalances().getAvailable();
            Double balanceCurrent = response.body().getAccounts().get(0).getBalances().getCurrent();
            String accountName = response.body().getAccounts().get(0).getName();
            String accountNameOfficial = response.body().getAccounts().get(0).getOfficialName();
            String subtype = response.body().getAccounts().get(0).getSubtype().getValue();
            String institution = response.body().getItem().getInstitutionName();

            PlaidBankAccount plaidBankAccount = new PlaidBankAccount(accountId, balanceAvailable, balanceCurrent,
                    accountName, accountNameOfficial, subtype,
                    institution, currentUser);

            budgetService.updateOrInsertPlaidBankAccount(plaidBankAccount);

            System.out.println("Accounts balance get request was successful: " + response.body().toString());
            return ResponseEntity.status(200).body("Accounts balance get request was successful: " + response.body().toString());
        }
        else
        {
            System.out.println("Failed to get account balances: " + response.errorBody().string());
            return ResponseEntity.status(500).body("Failed to get account balances: " + response.errorBody().string());
        }
    }
}
