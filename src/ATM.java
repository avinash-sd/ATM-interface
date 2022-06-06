import java.util.Scanner;

public class ATM {

	public static void main(String[] args)  {
		
		Scanner sc = new Scanner(System.in);
		
		Bank theBank =  new Bank("Bank of Euphoria");
		
		User aUser  = theBank.addUser("Avinash", "S", "1234");
		
		Account newAccount = new Account("Checking", aUser, theBank);
		aUser.addAccount(newAccount);
		theBank.addAccount(newAccount);
		
		User curUser;
		
		while(true) {
			
			curUser  = ATM.mainMenuPrompt(theBank,sc);
			
			ATM.printUserMenu(curUser,sc);
			
		}

	}

	private static void printUserMenu(User theUser, Scanner sc) {
		
		theUser.printAccountsSummary();
		
		int choice;
		
		do {
			
			System.out.printf("Welcome %s, what would you like to do?\n", theUser.getFirstName());
			
			System.out.println("  1) Show account transanction history");
			System.out.println("  2) Withdraw");
			System.out.println("  3) Desposit");
			System.out.println("  4) Transfer");
			System.out.println("  5) Pin Change");
			System.out.println("  6) Quit");
			System.out.println();
			System.out.print("Enter choice: ");
			choice = sc.nextInt();
			
			if(choice < 1 || choice > 6) {
				System.out.println("Invalid choice. Please choose 1-6");
			}
		} while(choice < 1 || choice > 5);
		
		switch(choice) {
		
		case 1:
			ATM.showTransanctionHistory(theUser,sc);
			break;
		case 2:
			ATM.withdrawFunds(theUser, sc);
			break;
		case 3:
			ATM.depositFunds(theUser,sc);
			break;
		case 4:
			ATM.transferFunds(theUser, sc);
			break;
		case 5:
			ATM.pinChange(theUser,sc);
			break;
		case 6:
			sc.nextLine();
			break;
		}
		
		if(choice != 5) {
			ATM.printUserMenu(theUser, sc);
		}
		
	}

	private static void pinChange(User theUser, Scanner sc) {
		System.out.println("Enter the current PIN: ");
		sc.nextLine();
		String pin = sc.nextLine();
		if(theUser.validatePin(pin)) {
			System.out.println("Enter the new PIN: ");
			String newPin = sc.nextLine();
			theUser.changePin(newPin);
			System.out.println("PIN changed successfully ");
		}
		else {
			System.out.println("Wrong PIN! Cannot change PIN now!");
		}
	}

	private static void depositFunds(User theUser, Scanner sc) {
		
		int toAcct;
		double amount;
		double acctBal;
		String memo;
		
		do {
			System.out.printf("Enter the number (1-%d) of the account\n" + 
		"to deposit in: ", theUser.numAccounts());
			toAcct = sc.nextInt()-1;
			if(toAcct < 0 || toAcct >= theUser.numAccounts()) {
				System.out.println("Invalid account. Please try again");
			}
		} while(toAcct < 0 || toAcct >= theUser.numAccounts());
		acctBal = theUser.getAcctBalance(toAcct);
		
		do {
			System.out.printf("Enter the amount to deposit: $");
			amount = sc.nextDouble();
			if(amount < 0) {
				System.out.println("Amount must be greater than zero.");
			} 
		} while(amount < 0);
		
		sc.nextLine();
		
		System.out.print("Enter a memo: ");
		memo = sc.nextLine();
		
		theUser.addAcctTransanction(toAcct, amount, memo);
		
	}

	private static void withdrawFunds(User theUser, Scanner sc) {
		
		int fromAcct;
		double amount;
		double acctBal;
		String memo;
		
		do {
			System.out.printf("Enter the number (1-%d) of the account\n" + 
		"to withdraw from: ",theUser.numAccounts());
			fromAcct = sc.nextInt()-1;
			if(fromAcct < 0 || fromAcct >= theUser.numAccounts()) {
				System.out.println("Invalid account. Please try again");
			}
		} while(fromAcct < 0 || fromAcct >= theUser.numAccounts());
		acctBal = theUser.getAcctBalance(fromAcct);
		
		do {
			System.out.printf("Enter the amount to transfer (max $%.02f):",
					acctBal);
			amount = sc.nextDouble();
			if(amount < 0) {
				System.out.println("Amount must be greater than zero.");
			} else if(amount > acctBal) {
				System.out.printf("Amount must not be greater than\n" +
			"balance of $%.02f.\n", acctBal);
			}
		} while(amount < 0 || amount > acctBal);
		
		sc.nextLine();
		
		System.out.print("Enter a memo: ");
		memo = sc.nextLine();
		
		theUser.addAcctTransanction(fromAcct, -1*amount, memo);
	}

	public static void transferFunds(User theUser, Scanner sc) {
		
		int fromAcct;
		int toAcct;
		double amount;
		double acctBal;
		
		do {
			System.out.printf("Enter the number (1-%d) of the account\n" + 
		"to transfer from: ", theUser.numAccounts());
			fromAcct = sc.nextInt()-1;
			if(fromAcct < 0 || fromAcct >= theUser.numAccounts()) {
				System.out.println("Invalid account. Please try again");
			}
		} while(fromAcct < 0 || fromAcct >= theUser.numAccounts());
		acctBal = theUser.getAcctBalance(fromAcct);
		
		do {
			System.out.printf("Enter the number (1-%d) of the account\n" + 
		"to which the amount to be transferred: ", theUser.numAccounts());
			toAcct = sc.nextInt()-1;
			if(toAcct < 0 || toAcct >= theUser.numAccounts()) {
				System.out.println("Invalid account. Please try again");
			}
		} while(toAcct < 0 || toAcct >= theUser.numAccounts());
		
		do {
			System.out.printf("Enter the amount to transfer (max $%.02f):",
					acctBal);
			amount = sc.nextDouble();
			if(amount < 0) {
				System.out.println("Amount must be greater than zero.");
			} else if(amount > acctBal) {
				System.out.printf("Amount must not be greater than\n" +
			"balance of $%.02f.\n", acctBal);
			}
		} while(amount < 0 || amount > acctBal);
		
		theUser.addAcctTransanction(fromAcct, -1*amount, String.format(
				"Transfer to account %s", theUser.getAcctUid(toAcct)));
		theUser.addAcctTransanction(toAcct, amount, String.format(
				"Transfer to account %s", theUser.getAcctUid(fromAcct)));
		
	}

	public static void showTransanctionHistory(User theUser, Scanner sc) {
		
		int theAcct;
		
		do {
			System.out.printf("Enter the number (1-%d) of the account whose transanctions you want to see:", theUser.numAccounts());
			theAcct = sc.nextInt()-1;
			if(theAcct < 0 || theAcct >= theUser.numAccounts()) {
				System.out.println("Invalid account. Please try again.");
			}
		} while(theAcct < 0 || theAcct >= theUser.numAccounts());
		
		theUser.printAccTransHistory(theAcct);
		
	}

	private static User mainMenuPrompt(Bank theBank, Scanner sc) {
		
		String userId;
		String pin;
		User authUser;
		
		do {
			System.out.printf("\n\nWelcome to %s\n\n", theBank.getName());
			System.out.print("Enter user ID: ");
			userId = sc.nextLine();
			System.out.print("Enter pin: ");
			pin = sc.nextLine();
			
			authUser = theBank.userLogin(userId, pin);
			if(authUser == null) {
				System.out.println("Incorrect userId/pin combination. Please try again");
			}
		} while(authUser == null);
		
		return authUser;
	}

}
