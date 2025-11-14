package com.nttdata.ms_account.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    private String id;                 // ID interno
    private String accountNumber;      // Número de cuenta
    private String customerId;         // Cliente dueño principal
    private AccountType accountType;   // Tipo de cuenta

    // Solo para cuentas a plazo fijo (FIXED_TERM)
    private Integer fixedDay;

    // Saldo actual
    private BigDecimal balance;

    // Conteo de movimientos del mes (solo ahorro)
    private Integer monthlyMovements;

    // Solo para cuentas empresariales (business)
    private List<String> holders;      // Titulares
    private List<String> signatories;  // Firmantes autorizados

    private Boolean active;

    // =============================================================
    //                       MÉTODOS DE DOMINIO
    // =============================================================

    public void deposit(BigDecimal amount, LocalDate date) {
        requireActive();
        requirePositive(amount);

        if (isFixedTerm() && !isFixedDay(date)) {
            throw new RuntimeException("Deposits only allowed on fixed day for FIXED_TERM");
        }

        balance = safeBalance().add(amount);
        incrementMovementIfApplies();
    }

    public void withdraw(BigDecimal amount, LocalDate date) {
        requireActive();
        requirePositive(amount);

        if (safeBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient funds");
        }

        if (isFixedTerm() && !isFixedDay(date)) {
            throw new RuntimeException("Withdrawals only allowed on fixed day for FIXED_TERM");
        }

        if (isSavings() && monthlyMovementsExceeded()) {
            throw new RuntimeException("Monthly movement limit exceeded");
        }

        balance = safeBalance().subtract(amount);
        incrementMovementIfApplies();
    }

    // Añadir titular
    public void addHolder(String holderId) {
        if (holders == null) holders = new ArrayList<>();
        if (!holders.contains(holderId)) holders.add(holderId);
    }

    // Añadir firmante autorizado
    public void addSignatory(String signatoryId) {
        if (signatories == null) signatories = new ArrayList<>();
        if (!signatories.contains(signatoryId)) signatories.add(signatoryId);
    }

    // =============================================================
    //                       HELPERS PRIVADOS
    // =============================================================

    private void requirePositive(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Amount must be greater than zero");
        }
    }

    private void requireActive() {
        if (active != null && !active) {
            throw new RuntimeException("Account is inactive");
        }
    }

    private boolean isFixedTerm() {
        return accountType == AccountType.FIXED_TERM;
    }

    private boolean isSavings() {
        return accountType == AccountType.SAVINGS;
    }

    private boolean isChecking() {
        return accountType == AccountType.CHECKING;
    }

    private boolean isFixedDay(LocalDate date) {
        return fixedDay != null && date.getDayOfMonth() == fixedDay;
    }

    private BigDecimal safeBalance() {
        return balance == null ? BigDecimal.ZERO : balance;
    }

    private void incrementMovementIfApplies() {
        if (!isSavings()) return;

        if (monthlyMovements == null) monthlyMovements = 0;
        monthlyMovements++;
    }

    private boolean monthlyMovementsExceeded() {
        if (!isSavings()) return false;
        if (monthlyMovements == null) return false;

        return monthlyMovements >= SAVINGS_MOVEMENT_LIMIT;
    }

    private static final int SAVINGS_MOVEMENT_LIMIT = 5;
}
