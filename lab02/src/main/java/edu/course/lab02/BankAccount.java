package edu.course.lab02;

public class BankAccount {

    private int balance;

    public BankAccount(int initialBalance) {
        if (initialBalance < 0) {
            throw new IllegalArgumentException(
                "Начальный баланс не может быть отрицательным: " + initialBalance
            );
        }
        this.balance = initialBalance;
    }

    public void deposit(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException(
                "Сумма внесения должна быть положительной: " + amount
            );
        }
        balance += amount;
    }

    public void withdraw(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException(
                "Сумма снятия должна быть положительной: " + amount
            );
        }
        if (amount > balance) {
            throw new IllegalArgumentException(
                "Сумма снятия превышает баланс: balance=" + balance
                    + ", amount=" + amount
            );
        }
        balance -= amount;
    }

    public int getBalance() {
        return balance;
    }
}