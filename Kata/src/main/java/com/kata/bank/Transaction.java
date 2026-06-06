package com.kata.bank;

import java.time.LocalDate;

/**
 * Représente une opération bancaire (dépôt ou retrait).
 * Immuable : une fois créée, ses données ne changent pas.
 */
public class Transaction {

    private final LocalDate date;
    private final TransactionType type;
    private final double amount;
    // Solde du compte APRÈS cette opération
    private final double balanceAfter;

    public Transaction(LocalDate date, TransactionType type, double amount, double balanceAfter) {
        this.date = date;
        this.type = type;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
    }

    public LocalDate getDate() {
        return date;
    }

    public TransactionType getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public double getBalanceAfter() {
        return balanceAfter;
    }
}
