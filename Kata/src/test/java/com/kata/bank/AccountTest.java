package com.kata.bank;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    private Account account;

    @BeforeEach
    void setUp() {
        account = new Account();
    }

    // ---- US1 : Dépôt ----

    @Test
    void deposit_should_increase_balance() {
        account.deposit(500);
        assertEquals(500, account.getBalance());
    }

    @Test
    void two_deposits_should_accumulate_balance() {
        account.deposit(500);
        account.deposit(300);
        assertEquals(800, account.getBalance());
    }

    @Test
    void deposit_should_record_transaction() {
        account.deposit(1000);

        List<Transaction> transactions = account.getTransactions();
        assertEquals(1, transactions.size());
        assertEquals(TransactionType.DEPOSIT, transactions.get(0).getType());
        assertEquals(1000, transactions.get(0).getAmount());
        assertEquals(1000, transactions.get(0).getBalanceAfter());
    }

    @Test
    void deposit_with_negative_amount_should_throw() {
        assertThrows(IllegalArgumentException.class, () -> account.deposit(-100));
    }

    @Test
    void deposit_with_zero_should_throw() {
        assertThrows(IllegalArgumentException.class, () -> account.deposit(0));
    }

    // ---- US2 : Retrait ----

    @Test
    void withdrawal_should_decrease_balance() {
        account.deposit(1000);
        account.withdraw(400);
        assertEquals(600, account.getBalance());
    }

    @Test
    void withdrawal_should_record_transaction() {
        account.deposit(1000);
        account.withdraw(400);

        List<Transaction> transactions = account.getTransactions();
        // 2 transactions : dépôt + retrait
        assertEquals(2, transactions.size());

        Transaction withdrawal = transactions.get(1);
        assertEquals(TransactionType.WITHDRAWAL, withdrawal.getType());
        assertEquals(400, withdrawal.getAmount());
        assertEquals(600, withdrawal.getBalanceAfter());
    }

    @Test
    void withdrawal_exceeding_balance_should_throw() {
        account.deposit(200);
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(300));
    }

    @Test
    void withdrawal_with_negative_amount_should_throw() {
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(-50));
    }

    // ---- US3 : Historique ----

    @Test
    void transactions_list_should_be_in_chronological_order() {
        account.deposit(1000);
        account.withdraw(200);
        account.deposit(500);

        List<Transaction> transactions = account.getTransactions();
        assertEquals(3, transactions.size());
        assertEquals(TransactionType.DEPOSIT, transactions.get(0).getType());
        assertEquals(TransactionType.WITHDRAWAL, transactions.get(1).getType());
        assertEquals(TransactionType.DEPOSIT, transactions.get(2).getType());
    }

    @Test
    void balance_after_each_transaction_should_be_correct() {
        account.deposit(1000);
        account.withdraw(200);
        account.deposit(500);

        List<Transaction> transactions = account.getTransactions();
        assertEquals(1000, transactions.get(0).getBalanceAfter());
        assertEquals(800, transactions.get(1).getBalanceAfter());
        assertEquals(1300, transactions.get(2).getBalanceAfter());
    }

    @Test
    void new_account_should_have_zero_balance_and_no_transactions() {
        assertEquals(0, account.getBalance());
        assertTrue(account.getTransactions().isEmpty());
    }

    @Test
    void transactions_list_should_be_unmodifiable() {
        account.deposit(100);
        // On ne doit pas pouvoir modifier la liste retournée depuis l'extérieur
        assertThrows(UnsupportedOperationException.class,
                () -> account.getTransactions().add(null));
    }
}
