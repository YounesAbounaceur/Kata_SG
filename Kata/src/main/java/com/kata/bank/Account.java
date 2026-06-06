package com.kata.bank;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Compte bancaire.
 * Permet de déposer, retirer, et consulter l'historique des opérations.
 */
public class Account {

    private double balance;
    private final List<Transaction> transactions;

    public Account() {
        this.balance = 0;
        this.transactions = new ArrayList<>();
    }

    /**
     * Dépose un montant sur le compte (US1).
     *
     * @param amount le montant à déposer, doit être positif
     */
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Le montant du dépôt doit être positif");
        }
        balance += amount;
        transactions.add(new Transaction(LocalDate.now(), TransactionType.DEPOSIT, amount, balance));
    }

    /**
     * Retire un montant du compte (US2).
     *
     * @param amount le montant à retirer, doit être positif et ne pas dépasser le solde
     */
    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Le montant du retrait doit être positif");
        }
        if (amount > balance) {
            throw new IllegalArgumentException("Solde insuffisant pour effectuer ce retrait");
        }
        balance -= amount;
        transactions.add(new Transaction(LocalDate.now(), TransactionType.WITHDRAWAL, amount, balance));
    }

    /**
     * Retourne le solde actuel du compte.
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Retourne l'historique de toutes les opérations (US3).
     * La liste retournée est non-modifiable.
     */
    public List<Transaction> getTransactions() {
        return Collections.unmodifiableList(transactions);
    }
}
