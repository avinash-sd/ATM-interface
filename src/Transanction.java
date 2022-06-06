import java.util.Calendar;

public class Transanction {
	
	private double amount;
	
	private Calendar timeStamp;
	
	private String memo;
	
	private Account inAccount;

	public Transanction(double amount, Account inAccount) {
		
		this.amount = amount;
		this.inAccount = inAccount;
		this.timeStamp = Calendar.getInstance();
		this.memo = "";
	}

	public Transanction(double amount, String memo, Account inAccount) {
		
		this.amount = amount;
		this.memo = memo;
		this.inAccount = inAccount;
		this.timeStamp = Calendar.getInstance();
		
	}

	public double getAmount() {
		return this.amount;
	}

	public String getSummaryLine() {
		
		if(this.amount >= 0) {
			return String.format("%s : $%.02f : %s", this.timeStamp.getTime().toString(),
					this.amount,this.memo);
		} else {
			return String.format("%s : $(%.02f) : %s", this.timeStamp.getTime().toString(),
					this.amount,this.memo);
		}
	}
	
	

}
