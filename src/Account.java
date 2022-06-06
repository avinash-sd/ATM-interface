import java.util.ArrayList;

public class Account {
	
	private String name;
	
	private String uid;
	
	private User holder;
	
	private ArrayList<Transanction> transanctions;

	public Account(String name, User holder, Bank theBank) {
		this.name = name;
		this.holder = holder;
		
		this.uid = theBank.getNewAccountUID();
		
		this.transanctions = new ArrayList<Transanction>();
		
	}

	public String getUid() {
		return this.uid;
	}

	public String getSummaryLine() {
		
		double balance = this.getBalance();
		
		if(balance >= 0) {
			return String.format("%s : $%.02f : %s", this.uid, balance, this.name);
		} else {
			return String.format("%s : $(%.02f) : %s", this.uid, balance, this.name);
		}
		
	}

	public double getBalance() {
		
		double balance = 0;
		for(Transanction t : this.transanctions) {
			balance += t.getAmount();
		}
		return balance;
		
	}

	public void printTransanctionHistory() {
		
		System.out.printf("\nTransanction history for account %s\n", this.uid);
		for(int t = this.transanctions.size()-1; t >= 0; t--) {
			System.out.println(this.transanctions.get(t).getSummaryLine());
		}
		System.out.println();
		
	}

	public void addTransanction(double amount, String memo) {
		
		Transanction newTrans = new Transanction(amount,memo,this);
		this.transanctions.add(newTrans);
		
	}
	
	

}
