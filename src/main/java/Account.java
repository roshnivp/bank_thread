/**
 * Created by Roshni Velluva Puthanidam on 11/11/16.
 */
public class Account {
    private int accountId;
    private int txnCnt;
    private double balance;

    public Account(int accountId, double balance) {
        this.accountId = accountId;
        this.balance = balance;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getTxnCnt() {
        return txnCnt;
    }

    public void setTxnCnt(int txnCnt) {
        this.txnCnt = txnCnt;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

//    // to withdraw funds from the account
//    public boolean withdraw(double amount) {
//        double newBalance;
//
//        if (amount > balance) {
//            //there are not enough funds in the account
//            return false;
//        } else {
//            newBalance = balance - amount;
//            balance = newBalance;
//            this.txnCnt += 1;
//            return true;
//        }
//
//    }
//
//    public boolean deposit(double amount) {
//        double newBalance;
//
//        if (amount < 0.0) {
//            return false; // can not deposit a negative amount
//        } else {
//            newBalance = balance + amount;
//            balance = newBalance;
//            this.txnCnt += 1;
//            return true;
//        }
//
//    }


    public synchronized void makeTransaction(Transaction t){
        txnCnt++;
        balance += t.getAmount();
    }

    @Override
    public synchronized String toString() {
        return  "acct : " + accountId +
                ", balance : " + balance +
                ", trans : " + txnCnt ;
    }
}
