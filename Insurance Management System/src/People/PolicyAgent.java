package People;

import java.util.Random;
import java.util.Scanner;

import Policy.*;

public class PolicyAgent {
	
	private static Random R = new Random();
	private static long Serial_number  = R.nextInt(1000);
	
	public static void givePolicyNumber(Customer C)
	{
		C.setPolicy_Number(Serial_number);
	}

	public static void GetThePolicyAndAddCustomer(String policy_Name, Customer customer) 
	{
		Scanner s = new Scanner(System.in);
		int i = 0;
		while(i < 2)
		{
			if(policy_Name.equalsIgnoreCase("Property"))
			{
				Property_Policy.PolicyCustomer(customer);
				customer.setRecentlyJoinedPolicy("Property");
				customer.addIntoProperty(new Property_Policy());
				IMS_Admin.addIntoCustomersAndPolicies(customer, new Property_Policy());
				i = 3;
			}
			else if(policy_Name.equalsIgnoreCase("Life"))
			{
				Life_Policy.PolicyCustomer(customer);
				customer.setRecentlyJoinedPolicy("Life");
				customer.addIntoLife(new Life_Policy());
				IMS_Admin.addIntoCustomersAndPolicies(customer, new Life_Policy());
				i = 3;
			}
			else if(policy_Name.equalsIgnoreCase("Motor"))
			{
				Motor_Policy.PolicyCustomer(customer);
				customer.setRecentlyJoinedPolicy("Motor");
				customer.addIntoMotor(new Motor_Policy());
				IMS_Admin.addIntoCustomersAndPolicies(customer, new Motor_Policy());
				i = 3;
			}
			else
			{
				System.out.println("Policy Name is Wrong, cutomer not added to policy, recently not joined, policy into customer");
				System.out.println("Try again, Enter the Name of the Policy: ");
				policy_Name = s.next();
				i++;
			}
		}
	}

	public static void TakeCustomerDetails(Customer customer) {
		
		Scanner s = new Scanner(System.in);
		System.out.println("To Join into the Policy, We need your Details, please answer appropriately:");
		System.out.print("Enter your Name: ");
		String CustomerDetails = s.next();
		customer.setName(CustomerDetails);
		Policy_Specific_Details(customer);
		System.out.print("Enter your Phone Number: ");
		CustomerDetails = s.next();
		customer.setContact_Details(CustomerDetails);
		System.out.println("Enter your Complete Address: ");
		CustomerDetails = s.next();	
		customer.setAddress(CustomerDetails);
		
	}
	
	private static void Policy_Specific_Details(Customer customer)
	{
		Scanner s = new Scanner(System.in);
		if(customer.getRecentlyJoinedPolicy().equalsIgnoreCase("Property"))
		{
			System.out.println("Send your Land_Registration Document: ");
			String Details = s.next();
			customer.addIntoProperty_Details(Details);
			System.out.println("If Policy is for Renovation(Yes-No)");
			Details = s.next();
			if( !(Details.equalsIgnoreCase("Yes") || Details.equalsIgnoreCase("No")))
			{
				Details = "NO";
			}
			customer.addIntoProperty_Details(Details);
		}
		else if(customer.getRecentlyJoinedPolicy().equalsIgnoreCase("Life"))
		{
			System.out.println("Do you have Major Health Issues(Yes-No): ");
			String Details = s.next();
			if(Details.equalsIgnoreCase("Yes"))
			{
				System.out.println("Enter your Major Health Issue: ");
				Details = s.next();
				customer.addIntoLife_Details("Yes");
				customer.addIntoLife_Details(Details);
			}
		}
		else
		{
			System.out.println("Enter your Vehicle Registration Number: ");
			String Details = s.next();
			customer.addIntoMotor_Details(Details);
		}
	}
	
	public static boolean NewCustomerPolicy(Customer customer)
	{
		Scanner s = new Scanner(System.in);
		System.out.print("Select a Active Policy to Make Changes in it: ");
		String Details = s.next();
		if(Details.equalsIgnoreCase("Property"))
		{
			customer.setRecentlyJoinedPolicy("Property");
			Property_Policy PP = new Property_Policy();
			System.out.println("ASSURED\nPREMIUM");
			String S = s.next();
			int Size_Assured = 0;
			int Size_Premium = 0;
			while(S.equalsIgnoreCase("Assured") || S.equalsIgnoreCase("Premium")|| true)
			{
				if(S.equalsIgnoreCase("Assured"))
				{
					System.out.println("What is the Amount excepting as an assurance: ");
					int Assurance = s.nextInt();
					int Size = 0;
					while( !(IMS_Admin.CheckAssurance(PP, Assurance)))
					{
						Size++;
						if(Size == 3)
						{
							System.out.println("Sorry Sir, We can't have a deal");
							return false;
						}
						System.out.println("The Assurance is too low, please increase: ");
						Assurance = s.nextInt();
					}
					PP.setAmountAssured(String.valueOf(Assurance));
					Size_Assured++;
					if(Size_Assured >= 1 && Size_Premium >= 1)
					{
						break;
					}
					System.out.println("ASSURED\nPREMIUM");
					S = s.next();
				}
				else if(S.equalsIgnoreCase("Premium"))
				{
					System.out.println("What is the Premium Amount to pay: ");
					int Premium = s.nextInt();
					int Size = 0;
					while( !(IMS_Admin.CheckPremium(PP, Premium)))
					{
						Size++;
						if(Size == 3)
						{
							System.out.println("Sorry Sir, We can't have a deal");
							return false;
						}
						System.out.println("The Premium is too low, please increase: ");
						Premium = s.nextInt();
					}
					PP.setPremiumAmount(String.valueOf(Premium));
					Size_Premium++;
					if(Size_Assured >= 1 && Size_Premium >= 1)
					{
						break;
					}
					System.out.println("ASSURED\nPREMIUM");
					S = s.next();
				}
				else
				{
					System.out.println("Wrong Input, Try again: ");
					S = s.next();
					continue;
				}
			}
			customer.addIntoProperty(PP);
			Property_Policy.NewPolicies.put(customer, PP);
			IMS_Admin.addIntoCustomersAndPolicies(customer, PP);
		}
		else if(Details.equalsIgnoreCase("Life"))
		{
			customer.setRecentlyJoinedPolicy("Life");
			Life_Policy PP = new Life_Policy();
			System.out.println("ASSURED\nPREMIUM");
			String S = s.next();
			int Size_Assured = 0;
			int Size_Premium = 0;
			while(S.equalsIgnoreCase("Assured") || S.equalsIgnoreCase("Premium")|| true)
			{
				if(S.equalsIgnoreCase("Assured"))
				{
					System.out.println("What is the Amount excepting as an assurance: ");
					int Assurance = s.nextInt();
					int Size = 0;
					while( !(IMS_Admin.CheckAssurance(PP, Assurance)))
					{
						Size++;
						if(Size == 3)
						{
							System.out.println("Sorry Sir, We can't have a deal");
							return false;
						}
						System.out.println("The Assurance is too low, please increase: ");
						Assurance = s.nextInt();
					}
					PP.setAmountAssured(String.valueOf(Assurance));
					Size_Assured++;
					if(Size_Assured >= 1 && Size_Premium >= 1)
					{
						break;
					}
					System.out.println("ASSURED\nPREMIUM");
					S = s.next();
				}
				else if(S.equalsIgnoreCase("Premium"))
				{
					System.out.println("What is the Premium Amount to pay: ");
					int Premium = s.nextInt();
					int Size = 0;
					while( !(IMS_Admin.CheckPremium(PP, Premium)))
					{
						Size++;
						if(Size == 3)
						{
							System.out.println("Sorry Sir, We can't have a deal");
							return false;
						}
						System.out.println("The Premium is too low, please increase: ");
						Premium = s.nextInt();
					}
					PP.setPremiumAmount(String.valueOf(Premium));
					Size_Premium++;
					if(Size_Assured >= 1 && Size_Premium >= 1)
					{
						break;
					}
					System.out.println("ASSURED\nPREMIUM");
					S = s.next();
				}
				else
				{
					System.out.println("Wrong Input, Try again: ");
					S = s.next();
					continue;
				}
			}
			customer.addIntoLife(PP);
			Life_Policy.NewPolicies.put(customer, PP);
			IMS_Admin.addIntoCustomersAndPolicies(customer, PP);
		}
		else if(Details.equalsIgnoreCase("Motor"))
		{
			customer.setRecentlyJoinedPolicy("Motor");
			Motor_Policy PP = new Motor_Policy();
			System.out.println("ASSURED\nPREMIUM");
			String S = s.next();
			int Size_Assured = 0;
			int Size_Premium = 0;
			while(S.equalsIgnoreCase("Assured") || S.equalsIgnoreCase("Premium") || true)
			{
				if(S.equalsIgnoreCase("Assured"))
				{
					System.out.println("What is the Amount excepting as an assurance: ");
					int Assurance = s.nextInt();
					int Size = 0;
					while( !(IMS_Admin.CheckAssurance(PP, Assurance)))
					{
						Size++;
						if(Size == 3)
						{
							System.out.println("Sorry Sir, We can't have a deal");
							return false;
						}
						System.out.println("The Assurance is too low, please increase: ");
						Assurance = s.nextInt();
					}
					PP.setAmountAssured(String.valueOf(Assurance));
					Size_Assured++;
					if(Size_Assured >= 1 && Size_Premium >= 1)
					{
						break;
					}
					System.out.println("ASSURED\nPREMIUM");
					S = s.next();
				}
				else if(S.equalsIgnoreCase("Premium"))
				{
					System.out.println("What is the Premium Amount to pay: ");
					int Premium = s.nextInt();
					int Size = 0;
					while( !(IMS_Admin.CheckPremium(PP, Premium)))
					{
						Size++;
						if(Size == 3)
						{
							System.out.println("Sorry Sir, We can't have a deal");
							return false;
						}
						System.out.println("The Premium is too low, please increase: ");
						Premium = s.nextInt();
					}
					PP.setPremiumAmount(String.valueOf(Premium));
					Size_Premium++;
					if(Size_Assured >= 1 && Size_Premium >= 1)
					{
						break;
					}
					System.out.println("ASSURED\nPREMIUM");
					S = s.next();
				}
				else
				{
					System.out.println("Wrong Input, Try again: ");
					S = s.next();
					continue;
				}
			}
			customer.addIntoMotor(PP);
			Motor_Policy.NewPolicies.put(customer, PP);
			IMS_Admin.addIntoCustomersAndPolicies(customer, PP);
		}
		else
		{
			System.out.println("Wrong Input, Try again to create New policy");
			NewCustomerPolicy(customer);
		}
		return true;
	}

	public static void getCustomerDetails(Customer customer) 
	{
		IMS_Admin.UpdateCustomersAndPolicies();
		Scanner s = new Scanner(System.in);
		System.out.print("Enter your Name: ");
		String Name = s.next();
		System.out.println("Enter your Policy Number: ");
		Long Policy_Number = Long.parseLong(s.next());
		System.out.println(IMS_Admin.getCustomers().size());
		for(Customer c: IMS_Admin.getCustomers())
		{
			
			if(((c.getName()).equalsIgnoreCase(Name)) && (c.getPolicy_Number() == Policy_Number))
			{
				customer.setName(c.getName());
				customer.setPolicy_Number(c.getPolicy_Number());
				customer.setRecentlyJoinedPolicy(c.getRecentlyJoinedPolicy());
				return;
			}
		}
	}
}
