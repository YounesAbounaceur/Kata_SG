package com.kata.bank;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StatementPrinterTest {

    // Utilitaire : capture la sortie console dans une String
    private String captureOutput(List<Transaction> transactions) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(buffer);

        StatementPrinter printer = new StatementPrinter(ps);
        printer.print(transactions);

        return buffer.toString();
    }

    @Test
    void print_should_display_header() {
        String output = captureOutput(List.of());
        assertTrue(output.contains("Date"));
        assertTrue(output.contains("Operation"));
        assertTrue(output.contains("Amount"));
        assertTrue(output.contains("Balance"));
    }

    @Test
    void print_should_display_deposit_line() {
        Transaction deposit = new Transaction(
                LocalDate.of(2026, 6, 1),
                TransactionType.DEPOSIT,
                500.0,
                500.0
        );

        String output = captureOutput(List.of(deposit));

        assertTrue(output.contains("01/06/2026"));
        assertTrue(output.contains("DEPOSIT"));
        assertTrue(output.contains("500.00"));
    }

    @Test
    void print_should_display_withdrawal_line() {
        Transaction withdrawal = new Transaction(
                LocalDate.of(2026, 6, 2),
                TransactionType.WITHDRAWAL,
                200.0,
                300.0
        );

        String output = captureOutput(List.of(withdrawal));

        assertTrue(output.contains("02/06/2026"));
        assertTrue(output.contains("WITHDRAWAL"));
        assertTrue(output.contains("200.00"));
        assertTrue(output.contains("300.00"));
    }

    @Test
    void print_should_display_all_transactions() {
        List<Transaction> transactions = List.of(
                new Transaction(LocalDate.of(2026, 6, 1), TransactionType.DEPOSIT, 1000.0, 1000.0),
                new Transaction(LocalDate.of(2026, 6, 2), TransactionType.WITHDRAWAL, 400.0, 600.0),
                new Transaction(LocalDate.of(2026, 6, 3), TransactionType.DEPOSIT, 250.0, 850.0)
        );

        String output = captureOutput(transactions);

        // Les 3 dates doivent apparaître dans la sortie
        assertTrue(output.contains("01/06/2026"));
        assertTrue(output.contains("02/06/2026"));
        assertTrue(output.contains("03/06/2026"));
    }

    @Test
    void print_with_empty_list_should_only_show_header() {
        String output = captureOutput(List.of());
        // Juste l'en-tête + le séparateur, pas d'autres lignes de données
        long lineCount = output.lines().count();
        assertEquals(2, lineCount);
    }
}
