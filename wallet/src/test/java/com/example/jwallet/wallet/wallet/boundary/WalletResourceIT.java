package com.example.jwallet.wallet.wallet.boundary;

import com.example.jwallet.wallet.AbstractWalletIT;
import com.example.jwallet.wallet.wallet.entity.*;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class WalletResourceIT extends AbstractWalletIT {

    @Test
    void createWallet() {
        CreateWalletRequest createWalletRequest = new CreateWalletRequest();
        createWalletRequest.setCurrency("USD");

        BalanceResponse balanceResponse = target.path("wallets").request()
                .post(Entity.json(createWalletRequest), BalanceResponse.class);

        assertEquals("0", balanceResponse.getResponse().getResponseCode());
        assertEquals("OK", balanceResponse.getResponse().getResponseMessage());

        assertEquals(createWalletRequest.getCurrency(), balanceResponse.getData().getCurrency());
        assertEquals(BigDecimal.ZERO, balanceResponse.getData().getBalance());

    }

    @Test
    void getBalance() {
        WalletTestData walletTestData = createWallet("USD");

        BalanceResponse response = target.path("wallets/{walletId}/balance")
                .resolveTemplate("walletId", walletTestData.walletId).request()
                .get(BalanceResponse.class);

        assertEquals("0", response.getResponse().getResponseCode());
        assertEquals("OK", response.getResponse().getResponseMessage());

        assertEquals(walletTestData.currency, response.getData().getCurrency());
        assertEquals(BigDecimal.ZERO, response.getData().getBalance());
        assertNotNull(response.getData().getWalletId());

    }

    @Test
    void getTransactions() {
        WalletTestData walletTestData = createWalletWithBalance("USD", 100);

        TransactionsResponse transactionsResponse = target.path("wallets/{walletId}/transactions")
                .resolveTemplate("walletId", walletTestData.walletId).request()
                .get(TransactionsResponse.class);

        assertEquals("0", transactionsResponse.getResponse().getResponseCode());
        assertEquals("OK", transactionsResponse.getResponse().getResponseMessage());
        assertEquals(1, transactionsResponse.getData().size());

    }

    @Test
    void debit() {
        WalletTestData walletTestData = createWalletWithBalance("USD", 100);

        TransactionRequest transactionsRequest = new TransactionRequest();
        transactionsRequest.setWalletId(walletTestData.walletId);
        transactionsRequest.setAmount(BigDecimal.TEN);
        transactionsRequest.setCurrency(walletTestData.currency);

        TransactionResponse transactionResponse = target.path("wallets/{walletId}/transactions/debit")
                .resolveTemplate("walletId", walletTestData.walletId).request()
                .post(Entity.json(transactionsRequest), TransactionResponse.class);

        assertEquals("0", transactionResponse.getResponse().getResponseCode());
        assertEquals("OK", transactionResponse.getResponse().getResponseMessage());
        assertEquals(new BigDecimal(90), transactionResponse.getData().getBalance());
        assertEquals(BigDecimal.TEN, transactionResponse.getData().getAmount());
        assertEquals(TransactionType.DEBIT, transactionResponse.getData().getType());
        assertEquals(transactionsRequest.getCurrency(), transactionResponse.getData().getCurrency());
        assertNotNull(transactionResponse.getData().getTransactionId());
    }

    @Test
    void debitOverdrawing() {
        WalletTestData walletTestData = createWalletWithBalance("USD", 9);

        TransactionRequest transactionsRequest = new TransactionRequest();
        transactionsRequest.setWalletId(walletTestData.walletId);
        transactionsRequest.setAmount(BigDecimal.TEN);
        transactionsRequest.setCurrency(walletTestData.currency);

        Response response = target.path("wallets/{walletId}/transactions/debit")
                .resolveTemplate("walletId", walletTestData.walletId).request()
                .post(Entity.json(transactionsRequest), Response.class);

        assertEquals(400, response.getStatus());

        TransactionResponse transactionResponse = response.readEntity(TransactionResponse.class);
        assertEquals("10400", transactionResponse.getResponse().getResponseCode());
        assertEquals("Wallet Overdrawn", transactionResponse.getResponse().getResponseMessage());
    }

    @Test
    void credit() {
        WalletTestData walletTestData = createWallet("USD");

        TransactionRequest transactionsRequest = new TransactionRequest();
        transactionsRequest.setWalletId(walletTestData.walletId);
        transactionsRequest.setAmount(BigDecimal.TEN);
        transactionsRequest.setCurrency("USD");

        TransactionResponse transactionResponse = target.path("wallets/{walletId}/transactions/credit")
                .resolveTemplate("walletId", walletTestData.walletId).request()
                .post(Entity.json(transactionsRequest), TransactionResponse.class);

        assertEquals("0", transactionResponse.getResponse().getResponseCode());
        assertEquals("OK", transactionResponse.getResponse().getResponseMessage());
        assertEquals(BigDecimal.TEN, transactionResponse.getData().getBalance());
        assertEquals(BigDecimal.TEN, transactionResponse.getData().getAmount());
        assertEquals(TransactionType.CREDIT, transactionResponse.getData().getType());
        assertEquals(transactionsRequest.getCurrency(), transactionResponse.getData().getCurrency());
        assertNotNull(transactionResponse.getData().getTransactionId());
    }

    public WalletTestData createWallet(String currency) {
        WalletTestData walletTestData = new WalletTestData();
        walletTestData.currency = currency;

        CreateWalletRequest createWalletRequest = new CreateWalletRequest();
        createWalletRequest.setCurrency(currency);

        walletTestData.walletId = target.path("wallets").request()
                .post(Entity.json(createWalletRequest), BalanceResponse.class)
                .getData().getWalletId();

        return walletTestData;
    }

    public WalletTestData createWalletWithBalance(String currency, float balance) {
        WalletTestData walletTestData = createWallet(currency);

        TransactionRequest transactionsRequest = new TransactionRequest();
        transactionsRequest.setWalletId(walletTestData.walletId);
        transactionsRequest.setAmount(new BigDecimal(balance));
        transactionsRequest.setCurrency(currency);

        walletTestData.currentBalance = target.path("wallets/{walletId}/transactions/credit")
                .resolveTemplate("walletId", walletTestData.walletId).request()
                .post(Entity.json(transactionsRequest), TransactionResponse.class).getData().getBalance();

        return walletTestData;
    }

    static class WalletTestData {
        public long walletId;
        public String currency;
        public BigDecimal currentBalance;
    }
}