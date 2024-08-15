package People;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import Policy.Life_Policy;
import Policy.Motor_Policy;
import Policy.Property_Policy;

public class Customer {
	private String Name;
	private String Address;
	private String Contact_Details;
	private String RecentlyJoinedPolicy;
	private ArrayList<Property_Policy> Property = new ArrayList<Property_Policy>();
	private ArrayList<Life_Policy> Life = new ArrayList<Life_Policy>(); 
	private ArrayList<Motor_Policy> Motor = new ArrayList<Motor_Policy>();
	
	private ArrayList<String> Property_Details = new ArrayList<String>();
	private ArrayList<String> Life_Details = new ArrayList<String>();
	private ArrayList<String> Motor_Details = new ArrayList<String>();
	private long Policy_Number = 0;
	
	private String Claim_Status = "NoClaim";
	
	
	

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public String getContact_Details() {
		return Contact_Details;
	}

	public void setContact_Details(String contact_Details) {
		Contact_Details = contact_Details;
	}

	public long getPolicy_Number() {
		return Policy_Number;
	}

	public void setPolicy_Number(long policy_Number) {
		Policy_Number = policy_Number;
	}

	public String getRecentlyJoinedPolicy() {
		return RecentlyJoinedPolicy;
	}

	public void setRecentlyJoinedPolicy(String recentlyJoinedPolicy) {
		RecentlyJoinedPolicy = recentlyJoinedPolicy;
	}

	public ArrayList<String> getProperty_Details() {
		return Property_Details;
	}

	
	public void addIntoProperty_Details(String property_Details) {
		Property_Details.add(property_Details);
	}

	public ArrayList<String> getLife_Details() {
		return Life_Details;
	}

	public void addIntoLife_Details(String life_Details) {
		Life_Details.add(life_Details);
	}

	public ArrayList<String> getMotor_Details() {
		return Motor_Details;
	}

	public void addIntoMotor_Details(String motor_Details) {
		Motor_Details.add(motor_Details);
	}

	public ArrayList<Property_Policy> getProperty() {
		return Property;
	}

	public void addIntoProperty(Property_Policy property) {
		Property.add(property);
	}

	public ArrayList<Life_Policy> getLife() {
		return Life;
	}

	public void addIntoLife(Life_Policy life) {
		Life.add(life);
	}

	public ArrayList<Motor_Policy> getMotor() {
		return Motor;
	}

	public void addIntoMotor(Motor_Policy motor) {
		Motor.add(motor);
	}
	
	private void saveToFile(String filename, String text, boolean append)
	{
		File file = new File(filename);
		FileWriter fw;
		try {
			fw = new FileWriter(file, append);
			PrintWriter pw = new PrintWriter(fw);
			pw.println(text);
			pw.close();
		} catch (IOException e) {
			System.out.println("IO Exception has Occured");
		}
	}
	
	public void WriteCustomerDetails()
	{
		if(this.getLife().size() != 0)
		{
			for(Life_Policy L: this.getLife())
			{
				String S = this.Name + ":" + this.Policy_Number + ":" + "LifePolicy" + ":" + L.getAmountAssured() + ":" + L.getPremiumAmount() + ":" + this.Claim_Status;
				saveToFile("CustomerDetails.txt", S, true);
			}
		}
		else if(this.getMotor().size() != 0)
		{
			for(Motor_Policy L: this.getMotor())
			{
				String S = this.Name + ":" + this.Policy_Number + ":" + "MotorPolicy" + ":" + L.getAmountAssured() + ":" + L.getPremiumAmount() + ":" + this.Claim_Status;
				saveToFile("CustomerDetails.txt", S, true);
			}
		}
		else if(this.getProperty().size() != 0)
		{
			for(Property_Policy L: this.getProperty())
			{
				String S = this.Name + ":" + this.Policy_Number + ":" + "PropertyPolicy" + ":" + L.getAmountAssured() + ":" + L.getPremiumAmount() + ":" + this.Claim_Status;
				saveToFile("CustomerDetails.txt", S, true);
			}
		}
	}
	
	public static void ReadCustomerDetails()
	{
		
		try {
			File file = new File("CustomerDetails.txt");
			Scanner s = new Scanner(file);
			while(s.hasNextLine())
			{
				String Line = s.nextLine();
				String[] items = Line.split(":");
				
				Customer C = new Customer();
				C.setName(items[0]);
				C.setPolicy_Number(Long.parseLong(items[1]));
				C.setRecentlyJoinedPolicy(items[2]);
				if(items[2].equalsIgnoreCase("LifePolicy"))
				{
					Life_Policy  L = new Life_Policy();
					L.setAmountAssured(items[3]);
					L.setPremiumAmount(items[4]);
					L.PolicyCustomer(C);
					C.addIntoLife(L);
					IMS_Admin.addIntoCustomersAndPolicies(C, L);
				}
				else if(items[2].equalsIgnoreCase("MotorPolicy"))
				{
					Motor_Policy  L = new Motor_Policy();
					L.setAmountAssured(items[3]);
					L.setPremiumAmount(items[4]);
					L.PolicyCustomer(C);
					C.addIntoMotor(L);
					IMS_Admin.addIntoCustomersAndPolicies(C, L);
				}
				else if(items[2].equalsIgnoreCase("PropertyPolicy"))
				{
					Property_Policy  L = new Property_Policy();
					L.setAmountAssured(items[3]);
					L.setPremiumAmount(items[4]);
					L.PolicyCustomer(C);
					C.addIntoProperty(L);
					IMS_Admin.addIntoCustomersAndPolicies(C, L);
				}
				C.Claim_Status = items[5];
			}
			s.close();
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found");
		}
	}

	public void ClaimPolicy() 
	{
		Scanner s = new Scanner(System.in);
		String Policy = this.RecentlyJoinedPolicy;
		System.out.println(this.getRecentlyJoinedPolicy());
		if(Policy.equalsIgnoreCase("PropertyPolicy"))
		{
			System.out.print("Enter the Reason for the Claim: ");
			String Claim = s.next();
			int count = 0;
			while(true)
			{
				if(Claim.equalsIgnoreCase("Renovation") || Claim.equalsIgnoreCase("Building"))
				{
					System.out.println("We agreed for your Claim");
					Claim_Status = "Approved";
					break;
				}
				else
				{
					Claim_Status = "Pending";
					count++;
					if(count >= 2)
					{
						Claim_Status = "Rejected";
						break;
					}
					System.out.println("We didn't Received Required Input, Try again");
					Claim = s.next();
				}
			}
		}
		else if(Policy.equalsIgnoreCase("LifePolicy"))
		{
			System.out.print("Enter the Reason for the Claim: ");
			String Claim = s.next();
			int count = 0;
			while(true)
			{
				if(Claim.equalsIgnoreCase("NaturalDeath") || Claim.equalsIgnoreCase("AccidentalDealth"))
				{
					System.out.println("We agreed for your Claim");
					Claim_Status = "Approved";
					File f = new File("ClaimStatements.txt");
					String S = this.Name + ":" + this.Policy_Number + ":" + this.RecentlyJoinedPolicy + ":" + this.Claim_Status;
					saveToFile("ClaimStatements.txt", S, true);
					break;
				}
				else
				{
					Claim_Status = "Pending";
					count++;
					if(count >= 2)
					{
						Claim_Status = "Rejected";
						break;
					}
					System.out.println("We didn't Received Required Input, Try again");
					Claim = s.next();
				}
			}
		}
		else if(Policy.equalsIgnoreCase("MotorPolicy"))
		{
			System.out.print("Enter the Reason for the Claim: ");
			String Claim = s.next();
			int count = 0;
			while(true)
			{
				if(Claim.equalsIgnoreCase("Accident") || Claim.equalsIgnoreCase("Maintanence"))
				{
					System.out.println("We agreed for your Claim");
					Claim_Status = "Approved";
					break;
				}
				else
				{
					Claim_Status = "Pending";
					count++;
					if(count >= 2)
					{
						Claim_Status = "Rejected";
						break;
					}
					System.out.println("We didn't Received Required Input, Try again");
					Claim = s.next();
				}
			}
		}
	}
	
	
	
}
