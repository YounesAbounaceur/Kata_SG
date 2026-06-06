package com.kata.bank;

import java.io.PrintStream;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Responsable de l'affichage du relevé de compte (US3).
 * On lui injecte le PrintStream pour pouvoir tester la sortie facilement.
 */
public class StatementPrinter {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final PrintStream out;

    public StatementPrinter(PrintStream out) {
        this.out = out;
    }

    /**
     * Affiche le relevé complet à partir de la liste des transactions du compte.
     *
     * @param transactions la liste des opérations à afficher
     */
    public void print(List<Transaction> transactions) {
        out.println("Date       | Operation  | Amount    | Balance");
        out.println("-----------|------------|-----------|--------");

        for (Transaction t : transactions) {
            String date = t.getDate().format(DATE_FORMAT);
            String type = t.getType() == TransactionType.DEPOSIT ? "DEPOSIT   " : "WITHDRAWAL";
            String amount = String.format("%.2f", t.getAmount());
            String balance = String.format("%.2f", t.getBalanceAfter());

            out.printf("%-10s | %s | %-9s | %s%n", date, type, amount, balance);
        }
    }
}
