package jpmc;

public class BankAccount {
    private int balance;

    public BankAccount(int initialBalance) {
        this.balance = initialBalance;
    }

    // Method to deposit money into the account
    public synchronized void deposit(int amount) {
        System.out.println("Depositing $" + amount);
        balance += amount;
        System.out.println("New balance after deposit: $" + balance);
    }

    // Method to withdraw money from the account
    public synchronized void withdraw(int amount) {
        if (balance >= amount) {
            System.out.println("Withdrawing $" + amount);
            balance -= amount;
            System.out.println("New balance after withdrawal: $" + balance);
        } else {
            System.out.println("Insufficient balance to withdraw $" + amount);
        }
    }

    // Getter method to get the current balance
    public synchronized int getBalance() {
        return balance;
    }

    public static void main(String[] args) {
        BankAccount account = new BankAccount(1000); // Starting balance $1000

        // Create multiple threads for deposit and withdrawal
        Thread depositThread = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                account.deposit(200); // Deposit $200, 5 times
                try {
                    Thread.sleep(100); // Simulate some delay
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread withdrawThread = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                account.withdraw(300); // Withdraw $300, 5 times
                try {
                    Thread.sleep(150); // Simulate some delay
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // Start the threads
        depositThread.start();
        withdrawThread.start();

        // Wait for threads to finish (optional)
        try {
            depositThread.join();
            withdrawThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Print final balance
        System.out.println("Final balance: $" + account.getBalance());
    }
}
