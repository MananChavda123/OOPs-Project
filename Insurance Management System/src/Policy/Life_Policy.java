package Policy;

import java.util.ArrayList;
import java.util.HashMap;

import People.Customer;

public class Life_Policy extends Policy {
	
	public Life_Policy()
	{
		this.AmountAssured = "3";
		this.PremiumAmount = "1";
		this.TimePeriod = "1";
		this.Frequency = "Quaterly";
	}
	
	private static ArrayList<Customer> PolicyCustomers = new ArrayList<Customer>();
	public static HashMap<Customer, Life_Policy> NewPolicies = new HashMap<Customer, Life_Policy>();
	
	
	
	
	public static ArrayList<Customer> getPolicyCustomers() {
		return PolicyCustomers;
	}

	public static HashMap<Customer, Life_Policy> getNewPolicies() {
		return NewPolicies;
	}

	public String getAmountAssured() {
		return AmountAssured;
	}

	public void setAmountAssured(String amountAssured) {
		AmountAssured = amountAssured;
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
