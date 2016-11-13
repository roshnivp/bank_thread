/**
 * Created by Roshni Velluva Puthanidam on 11/11/16.
 */
public class Transaction {
    private int fromAcctNo;
    private int toAcctNo;
    private double amount;

    public Transaction(int fromAcctNo, int toAcctNo, double amount) {
        this.fromAcctNo = fromAcctNo;
        this.toAcctNo = toAcctNo;
        this.amount = amount;
    }

    public int getFromAcctNo() {
        return fromAcctNo;
    }

    public int getToAcctNo() {
        return toAcctNo;
    }

    public double getAmount() {
        return amount;
    }
}
