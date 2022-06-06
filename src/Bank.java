import java.util.ArrayList;
import java.util.Random;

public class Bank {
	
	private String name;
	
	private ArrayList<User> users;
	
	private ArrayList<Account> accounts;

	public String getNewUserUID() {
		
		String uid;
		Random rng = new Random();
		int len = 6;
		boolean nonUnique = false;
		
		do {
			uid = "";
			for(int c = 0; c < len; c++) {
				uid += ((Integer)rng.nextInt(10)).toString();
			}
			
			nonUnique = false;
			for(User u : this.users) {
				if(uid.compareTo(u.getUid()) == 0) {
					nonUnique = true;
					break;
				}
			}
		} while(nonUnique);
		
		return uid; 
	}

	public String getName() {
		return this.name;
	}

	public String getNewAccountUID() {
		
		String uid;
		Random rng = new Random();
		int len = 10;
		boolean nonUnique = false;
		
		do {
			uid = "";
			for(int c = 0; c < len; c++) {
				uid += ((Integer)rng.nextInt(10)).toString();
			}
			
			nonUnique = false;
			for(Account u : this.accounts) {
				if(uid.compareTo(u.getUid()) == 0) {
					nonUnique = true;
					break;
				}
			}
		} while(nonUnique);
		
		return uid;
	}

	public void addAccount(Account account) {
		this.accounts.add(account);
	}
	
	public User addUser(String firstName, String lastName, String pin) {
		
		User newUser = new User(firstName, lastName, pin, this);
		this.users.add(newUser);
		
		Account newAccount = new Account("Savings", newUser, this);
		newUser.addAccount(newAccount);
		this.addAccount(newAccount);
		
		return newUser; 
	}
	
	public User userLogin(String userID, String pin) {
		
		for(User u : this.users) {
			
			if(u.getUid().compareTo(userID) == 0 && u.validatePin(pin))
				return u;
		}
		
		return null;
	}

	public Bank(String name) {
		this.name = name;
		this.users = new ArrayList<User>();
		this.accounts = new ArrayList<Account>();
	}

}
