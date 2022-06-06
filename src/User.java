import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class User {
	
	private String firstName;
	
	private String lastName;
	
	private String uid; //ID for the user
	
	private byte pinHash[]; // MD5 hash of the user's PIN
	
	private ArrayList<Account> accounts;

	public User(String firstName, String lastName, String pin, Bank theBank) {
		this.firstName = firstName;
		this.lastName = lastName;
		
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			this.pinHash = md.digest(pin.getBytes());
		} catch (NoSuchAlgorithmException e) {
			System.out.println("error, caught NoSuchAlgorithmException");
			e.printStackTrace();
			System.exit(1);
		}
		
		this.uid = theBank.getNewUserUID();
		
		this.accounts = new ArrayList<Account>();
		
		System.out.printf("New user %s, %s with UID %s created.\n",lastName, firstName, this.uid);
		
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void addAccount(Account account) {
		this.accounts.add(account);
	}

	public String getUid() {
		return this.uid;
	}

	public boolean validatePin(String pin) {
		
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			return MessageDigest.isEqual(md.digest(pin.getBytes()), this.pinHash);
		} catch (NoSuchAlgorithmException e) {
			System.out.println("error, caught NoSuchAlgorithmException");
			e.printStackTrace();
			System.exit(1);
		}
		
		return false;
	}

	public void printAccountsSummary() {
		
		System.out.printf("\n\n%s's accounts summary\n", this.firstName);
		for(int a = 0; a < this.accounts.size(); a++) {
			System.out.printf("%d) %s\n",a+1, this.accounts.get(a).getSummaryLine());
		}
		
	}

	public int numAccounts() {
		return this.accounts.size();
	}

	public void printAccTransHistory(int acctIndex) {
		this.accounts.get(acctIndex).printTransanctionHistory();
	}

	public double getAcctBalance(int acctIndex) {
		return this.accounts.get(acctIndex).getBalance();
	}

	public String getAcctUid(int acctIndex) {
		return this.accounts.get(acctIndex).getUid();
	}

	public void addAcctTransanction(int acctIndex, double amount, String memo) {
		
		this.accounts.get(acctIndex).addTransanction(amount,memo);
		
	}

	public void changePin(String newPin) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			this.pinHash = md.digest(newPin.getBytes());
		} catch (NoSuchAlgorithmException e) {
			System.out.println("error, caught NoSuchAlgorithmException");
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	
	

}
