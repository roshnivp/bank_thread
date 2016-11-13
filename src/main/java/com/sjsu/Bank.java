import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Roshni Velluva Puthanidam on 11/11/16.
 */
public class Bank {

    private static final double DEFAULT_BALANCE = 1000;
    private static final int NUMBER_OF_ACCOUNTS = 20;
    private static final String SMALL_FILE = "small.txt";
    private static final Transaction LAST_TRANSACTION = new Transaction(-1,0,0);

    private static BlockingQueue<Transaction> transactions = new LinkedBlockingQueue<Transaction>();
    private static ArrayList<Account> accounts = new ArrayList<Account>();
    private static ArrayList<Worker> workers = new ArrayList<Worker>();

    public Bank(int numWorkers) {

        for (int i = 0; i < NUMBER_OF_ACCOUNTS; i++) {
            Account account = new Account(i, DEFAULT_BALANCE);
            accounts.add(account);
        }

        for (int i= 0; i < numWorkers; i++) {
            workers.add(new Worker());
        }

        for (Worker worker : workers) {
            worker.start();
            System.out.println("Starting workers "+ String.valueOf(worker.getId()));
        }

    }

    private static void startBanking(String txnFileName, int numWorkers) {

        System.out.println("Bank is starting");

        Bank bank = new Bank(numWorkers);
        try {
            ClassLoader classLoader = bank.getClass().getClassLoader();
            File file = new File(classLoader.getResource(txnFileName).getFile());

            BufferedReader br = new BufferedReader(new FileReader(file));
            while(true){
                String line = br.readLine();
                if(line == null){
                    for(int i = 0; i < numWorkers; i++)
                        transactions.put(LAST_TRANSACTION);
                    break;
                }
                final List<Transaction> transaction = bank.parseTransaction(line);

                transactions.put(transaction.get(0));
                transactions.put(transaction.get(1));
            }

            for (Worker w : workers) {
                w.join();
                System.out.println("Ending worker " + String.valueOf(w.getId()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.print("Bank is closed");
    }

    private List<Transaction> parseTransaction(String line) {
        final List<Transaction> transactions = new ArrayList<Transaction>();

        String [] data = line.split(" ");
        int[] txn = new int[data.length];

        for(int i = 0; i < data.length; i++)
            txn[i] = Integer.parseInt(data[i]);

        final Transaction transactionFrom = new Transaction(txn[0], txn[1], -txn[2]);
        transactions.add(transactionFrom);
        final Transaction transactionTo = new Transaction(txn[1], txn[0], +txn[2]);
        transactions.add(transactionTo);

        return transactions;

    }

    public class Worker extends Thread {
        @Override
        public void run() {
            while (true) {
                try {

                    Transaction nextTransaction = transactions.take();

                    if(nextTransaction.equals(LAST_TRANSACTION))
                        break;

                    Account fromAccount = accounts.get(nextTransaction.getFromAcctNo());
                    fromAccount.makeTransaction(nextTransaction);
                    System.out.println(fromAccount);

                } catch (InterruptedException e) {
                    e.printStackTrace();

                }
            }
        }
    }

    public static void main(String[] args) {
        try {
            startBanking(args[0], Integer.parseInt(args[1]));
        } catch (Exception e){
            System.out.println("Incorrect args. Starting with default arguments");
            int numWorkers = 4;
            startBanking(SMALL_FILE, numWorkers);
        }
    }

}
