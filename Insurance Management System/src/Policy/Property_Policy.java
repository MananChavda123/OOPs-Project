package Policy;

import java.util.ArrayList;
import java.util.HashMap;

import People.Customer;

public class Property_Policy extends Policy {
	
	public Property_Policy()
	{
		this.AmountAssured = "2";
		this.PremiumAmount = "1";
		this.TimePeriod = "1";
		this.Frequency = "Half_Yearly";
	}
	
	private static ArrayList<Customer> PolicyCustomers = new ArrayList<Customer>();
	public static HashMap<Customer, Property_Policy> NewPolicies = new HashMap<Customer, Property_Policy>();
	
	
	
	
	public static HashMap<Customer, Property_Policy> getNewPolicies() {
		return NewPolicies;
	}

	public static ArrayList<Customer> getPolicyCustomers() {
		return PolicyCustomers;
	}

	public String getAmountAssured() {
		return AmountAssured;
	}

	public void setAmountAssured(String amountAssured) {
		this.AmountAssured = amountAssured;
	}

	public String getPremiumAmount() {
		return PremiumAmount;
	}

	public void setPremiumAmount(String premiumAmount) {
		PremiumAmount = premiumAmount;
	}

	public String getTimePeriod() {
		return TimePeriod;
	}

	public void setTimePeriod(String timePeriod) {
		TimePeriod = timePeriod;
	}

	public String getFrequency() {
		return Frequency;
	}

	public void setFrequency(String frequency) {
		Frequency = frequency;
	}

	
	
	public static void PolicyCustomer(Customer C)
	{
		PolicyCustomers.add(C);
	}
}
