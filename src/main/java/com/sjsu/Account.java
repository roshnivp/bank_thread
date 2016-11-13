package com.sjsu;

/**
 * Created by Roshni Velluva Puthanidam on 11/11/16.
 */
public class Account {
    private String accountNo;
    private double balance;

    public Account( double balance) {
       // this.accountNo = accountNo;
        this.balance = balance;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    // to withdraw funds from the account
    public boolean transfer(double amount) {
        double newBalance;

        if (amount > balance) {
            //there are not enough funds in the account
            return false;
        } else {
            newBalance = balance - amount;
            balance = newBalance;
            return true;
        }

    }

    public boolean deposit(double amount) {
        double newBalance;

        if (amount < 0.0) {
            return false; // can not deposit a negative amount
        } else {
            newBalance = balance + amount;
            balance = newBalance;
            return true;
        }

    }

    @Override
    public String toString() {
        return "Account{" +
                "accountNo=" + accountNo +
                ", balance=" + balance +
                '}';
    }
}
