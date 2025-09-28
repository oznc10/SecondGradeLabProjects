import java.util.*;
import java.util.Date;
import java.time.*;
interface PaymentMethod{
        public void pay(double amount);
    }
    class CreditCardPayment implements PaymentMethod{
        private String cardNumber;
        private String cardHolderName;
        public CreditCardPayment(String cardNumber,String cardHolderName){
            this.cardNumber = cardNumber;
            this.cardHolderName = cardHolderName;
        }
        @Override
        public void pay(double amount){
            if(verifySecurePayment(cardNumber, cardHolderName) && (confirmTransaction(amount))){
                int length = cardNumber.length();
                String last4 = cardNumber.substring(length - 4);
                String masked = "*".repeat(length - 4) + last4;
                System.out.println("Successfull payment with card number " + masked + " with amount " + amount);
            }
            else{
                System.out.println("ERROR.Payment  process failed.");
            }
        }
        boolean verifySecurePayment(String cardNumber1,String cardHolderName1){
            if(cardNumber1.equals(cardNumber) && cardHolderName1.equals(cardHolderName)){
                return true;
            }
            return false;
        }
        boolean confirmTransaction(double amount){
            if(amount > 0){
                return true;
            }
            return false;
        }
        public String getCardNumber(){
            return this.cardNumber;
        }
        public String getCardHolderName(){
            return this.cardHolderName;
        }
        public void setCardNumber(String cardNumber){
            this.cardNumber = cardNumber;
        }
        public void setCardHolderName(String cardHolderName){
            this.cardHolderName = cardHolderName;
        }
    }
    class PayPalPayment implements PaymentMethod{
        private String email;
        public PayPalPayment(String email){
            this.email = email;
        }
        public String getEmail(){
            return this.email;
        }
        public void setEmail(String email){
            this.email = email;
        }
        boolean connectServer(String email){
            if(this.email.equals(email)){
                return true;
            }
            return false;
        }
        String retrieveInfo(String email,double amount){
            return "User Email: " + email + ", payment amount :" + amount;
        }
        @Override
         public void pay(double amount){
            if(amount > 0 && connectServer(email)){
                System.out.println("Successfull payment! " + retrieveInfo(email,amount));
            }
            else{
                System.out.println("ERROR.Payment  process failed.");
            }
         }
    }
    class Transaction implements Comparable<Transaction>{
        private Date transactionDate;
        private String transactionType;
        private double amount;
        public Transaction(String transactionType,double amount){
            this.transactionType = transactionType;
            this.amount = amount;
            this.transactionDate = new Date();
        }
        @Override
        public String toString(){
            return transactionDate + "\n" + transactionType + "\n" + amount;
        }
        public String getTransactionType(){
            return this.transactionType;
        }
        public void setTransactionType(String transactionType){
            this.transactionType = transactionType;
        }
        public double getAmount(){
            return this.amount;
        }
        public void setAmount(double amount){
            this.amount = amount;
        }
         @Override
        public int compareTo(Transaction o) {
            if(this.transactionDate.compareTo(o.transactionDate) > 0){
                return 1;
            }
            else if(this.transactionDate.compareTo(o.transactionDate) < 0){
                return -1;
            }
            else{
                return 0;
            }
        }   
    }
    abstract class Account{
        private String accountNumber;
        private double balance;
        private TreeSet<Transaction> set;
        public Account(String accountNumber,double balance,TreeSet<Transaction> set){
            this.accountNumber = accountNumber;
            this.balance = balance;
            this.set = set;
        }
        void deposit(double amount){
            if(amount > 0){
                balance += amount;
                System.out.println(amount + " $ deposited to to the account.");
            }
        }
        abstract void withdraw(double amount);
        void printTransactionHistory(){
            for(Transaction set : set){
                System.out.println(set);
            }
        }
        public String getAccountNumber(){
            return this.accountNumber;
        }
        public void setAccountNumber(String accountNumber){
            this.accountNumber = accountNumber;
        }
        public double getBalance(){
            return this.balance;
        }
        public void setBalance(double balance){
            this.balance = balance;
        }
        public TreeSet<Transaction> getTransactions(){
            return this.set;
        }
        public void setTransactions(TreeSet<Transaction> set){
            this.set = set;
        }
    }
    class CheckingAccount extends Account{
        private double overdraftLimit;
        public CheckingAccount(String accountNumber,double balance,TreeSet<Transaction> set,double overdraftLimit){
            super(accountNumber, balance, set);
            this.overdraftLimit = overdraftLimit;
        }
        @Override
        void withdraw(double amount){
            if((getBalance() + overdraftLimit) >= amount){
                setBalance(getBalance() - amount);
            }
        }
        public double getOverDraftLimit(){
            return this.overdraftLimit;
        }
        public void setOverDraftLimit(double overdraftLimit){
            this.overdraftLimit = overdraftLimit;
        }
    }
    class SavingsAccount extends Account{
        private double interestRate;
        public SavingsAccount(String accountNumber,double balance,TreeSet<Transaction> set,double interestRate){
            super(accountNumber, balance, set);
            this.interestRate = interestRate;
        }
        public double getInterestRate(){
            return this.interestRate;
        }
        public void setInterestRate(double interestRate){
            this.interestRate = interestRate;
        }
          @Override
        void withdraw(double amount){
            if(getBalance() >= amount){
                setBalance(getBalance() - amount);
            }
            else{
                System.out.println("ERROR.Insufficient balance.");
            }
        }
        void applyInterest(){
            double interest = getBalance() * interestRate;
            setBalance(getBalance() + interest);
        }
    }
    abstract class Customer{
        private String customerId;
        private String name;
        private ArrayList<Account> accounts;
        private PaymentMethod paymentMethod;
        public Customer(String customerId,String name,ArrayList<Account> accounts,PaymentMethod paymentMethod){
            this.customerId = customerId;
            this.name = name;
            this.accounts = accounts;
            this.paymentMethod = paymentMethod;
        }
public String getCustomerId() {
    return customerId;
}
public void setCustomerId(String customerId) {
    this.customerId = customerId;
}
public String getName() {
    return name;
}
public void setName(String name) {
    this.name = name;
}
public ArrayList<Account> getAccounts() {
    return accounts;
}
public void setAccounts(ArrayList<Account> accounts) {
    this.accounts = accounts;
}
public PaymentMethod getPaymentMethod() {
    return paymentMethod;
}
public void setPaymentMethod(PaymentMethod paymentMethod) {
    this.paymentMethod = paymentMethod;
}
void addAccount(Account account){
    accounts.add(account);
}
void makePayment(double amount){ 
    paymentMethod.pay(amount);
} 
abstract double getTransactionFee();
}
class RegularCustomer extends Customer{
    public RegularCustomer(String customerId,String name,ArrayList<Account> accounts,PaymentMethod paymentMethod){
        super(customerId,name,accounts,paymentMethod);
    }
    @Override
    double getTransactionFee(){
        return 2.5;
    }
}
class PremiumCustomer extends Customer{
    public PremiumCustomer(String customerId,String name,ArrayList<Account> accounts,PaymentMethod paymentMethod){
        super(customerId, name, accounts, paymentMethod);
        }
        @Override
    double getTransactionFee(){
        return 1.0;
    }
}
class Bank{
    private ArrayList<Customer> customers;
    private HashMap<String,Account> accounts; 
    public Bank(ArrayList<Customer> customers,HashMap<String,Account> accounts){
        this.customers = customers;
        this.accounts = accounts;
    }
    public ArrayList<Customer> getCustomers(){
        return this.customers;
    }
    public void setCustomers(ArrayList<Customer> customers){
        this.customers = customers;
    }
    public HashMap<String,Account> getAccounts(){
        return this.accounts;
    }
    public void setAccounts(HashMap<String,Account> accounts){
        this.accounts = accounts;
    }
    void addCustomer(Customer customer){
        customers.add(customer);
    }
    void openAccountForCustomer(String customerId, Account account){
        accounts.put(customerId,account);
    }
    boolean findAccount(String accountNumber){
        if(accounts.containsKey(accountNumber)){
            System.out.println("Account with number " + accountNumber + " has found.");
            return true;
        }
        System.out.println("ERROR.Invalid account number.");
        return false;
    }
    boolean findCustomer(String customerId){
        for(Customer customer : customers){
            if(customer.getCustomerId().equals(customerId)){
                System.out.println("Customer with ID " + customerId + " has found.");
                return true;
            }
        }
        return false;
    }
}
public class Lab02 {
    public static void main(String[] args) {
        // Transaction test
        TreeSet<Transaction> transactions = new TreeSet<>();
        transactions.add(new Transaction("DEPOSIT", 100));
        transactions.add(new Transaction("WITHDRAWAL", 50));

        System.out.println("=== Transaction History ===");
        for (Transaction t : transactions) {
            System.out.println(t);
        }

        // Account test
        CheckingAccount acc = new CheckingAccount("ACC123", 200, new TreeSet<>(), 100);
        acc.deposit(50);
        acc.withdraw(300); // overdraft test
        System.out.println("Balance: " + acc.getBalance());

        // Customer + Payment test
        ArrayList<Account> accounts = new ArrayList<>();
        accounts.add(acc);

        PaymentMethod card = new CreditCardPayment("1234567890123456", "Omer");
        Customer customer = new RegularCustomer("C001", "Omer", accounts, card);

        customer.makePayment(75);
        System.out.println("Transaction Fee: " + customer.getTransactionFee());
    }
}



