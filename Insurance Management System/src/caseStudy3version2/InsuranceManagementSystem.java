package caseStudy3version2;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;

import People.Customer;
import People.IMS_Admin;
import People.PolicyAgent;

public class InsuranceManagementSystem {

	public static void main(String[] args) 
	{
		Display();
		Scanner s = new Scanner(System.in);
		System.out.println("1.JoinPolicy\n2.ClaimPolicy\n3.Exit");
		String S = s.next();
		while(S.equalsIgnoreCase("ClaimPolicy") || S.equalsIgnoreCase("JoinPolicy"))
		{
			if(S.equalsIgnoreCase("JoinPolicy"))
			{
				Customer customer;
				customer = Display1();
				Policy_Description();
				if(GetToKnowTheInterestOfCustomer(customer))
				{
					PolicyAgent.TakeCustomerDetails(customer);
				}
				else
				{
					System.out.println("No, Problem.");
					System.out.println("We have give freedom to make changes in our Policies: ");
					System.out.println("If you are Interested to make Change(Yes-No)");
					String Details = s.next();
					if(Details.equalsIgnoreCase("Yes"))
					{
						if(PolicyAgent.NewCustomerPolicy(customer))
						{
							PolicyAgent.TakeCustomerDetails(customer);
							System.out.println("Successfully Created a New Policy and Added the Customer");
						}
						else
						{
							System.out.println("Thanks for Visting Our Insurance Management System");
							System.out.println("1.JoinPolicy\n2.Exit");
							S = s.next();
							continue;
						}
					}
					else
					{
						System.out.println("Thanks for Visting Our Insurance Management System");
						System.out.println("1.JoinPolicy\n2.Exit");
						S = s.next();
						continue;
					}
				}
				System.out.println("1.JoinPolicy\n2.ClaimPolicy\n3.Exit");
				S = s.next();
			}
			
			if(S.equalsIgnoreCase("ClaimPolicy"))
			{
				Customer customer = new Customer();
				PolicyAgent.getCustomerDetails(customer);
				System.out.println("Customer: "  + customer.getName());
				try{ 
					if(customer.getName() == null)
					{
						System.out.println("We have no records with given details");
						System.out.println("1.JoinPolicy\n2.ClaimPolicy\n3.Exit");
						S = s.next();
						continue;
					}
				}catch(Exception e)
				{
					System.out.println("We have no records with given details1");
					System.out.println("1.JoinPolicy\n2.ClaimPolicy\n3.Exit");
					S = s.next();
					continue;
				}
				customer.ClaimPolicy();
				System.out.println("1.JoinPolicy\n2.ClaimPolicy\n3.Exit");
				S = s.next();
			}
		}
		
		IMS_Admin.UpdateCustomersAndPolicies();
		Set<Customer> customer = IMS_Admin.getCustomers();
		for(Customer cus: customer)
		{
			cus.WriteCustomerDetails();
		}
		clearFile("DefaultPolicies.txt");
	}
	
	public static void clearFile(String filename)
	{ 
		try{
	    FileWriter fw = new FileWriter(filename, false);
	    PrintWriter pw = new PrintWriter(fw, false);
	    pw.flush();
	    pw.close();
	    fw.close();
	    }catch(Exception exception){
	        System.out.println("Exception have been caught");
	    }
	}
	
	private static boolean GetToKnowTheInterestOfCustomer(Customer customer) 
	{
		Scanner s = new Scanner(System.in);
		System.out.print("Are you Interested to join in any one of the Policy(Yes-No): ");
		String Interest = s.next();
		if(Interest.equalsIgnoreCase("Yes"))
		{
			System.out.print("Enter the Policy Name: ");
			String Policy_Name = s.next();
			HashSet<String> ActivePolicies = IMS_Admin.getActivePolicies();
			for(String e: ActivePolicies)
			{
				if(Policy_Name.equalsIgnoreCase(e))
				{
					Policy_Name = e;
				}
			}
			if(customer.getPolicy_Number() == 0)
			{
				PolicyAgent.givePolicyNumber(customer);
				PolicyAgent.GetThePolicyAndAddCustomer(Policy_Name, customer);
			}
			return true;
		}
		else
		{
			return false;
		}
	}
	
	private static void Display()
	{
		
		ReadCustomersListAndPolicyThereAreIn();
		IMS_Admin.UpdateActivePoilicies();
		System.out.println("Welcome to Insurance Management System");
	}
	
	private static void ReadCustomersListAndPolicyThereAreIn()
	{
		Customer.ReadCustomerDetails();
	}

	private static Customer Display1()
	{
		Customer customer = new Customer();
		Scanner s = new Scanner(System.in);
		System.out.println("DO YOU WANT TO KNOW ABOUT THE POLICIES(Yes-No)");
		String I = s.next();
		if(I.equalsIgnoreCase("YES"))
		{
			IMS_Admin.DisplayDefaultPolicies();
		}
		else
		{
			System.out.println("You can proceed with the IMS Other Options:");
		}
		return customer;
	}
	
	private static void Policy_Description()
	{
		String Property_Description = "AMOUNT ASSURED: 2\nPREMIUM AMOUNT: 1\nTIME PERIOD: 1 YEAR\nFREQUENCY: HALF_YEARLY";
		String Life_Description = "AMOUNT ASSURED: 3\nPREMIUM AMOUNT: 1\nTIME PERIOD: 1 YEAR\nFREQUENCY: QUATERLY";
		String Motor_Description = "AMOUNT ASSURED: 4\nPREMIUM AMOUNT: 1\nTIME PERIOD: 1 YEAR\nFREQUENCY: MONTHLY";
		
		if(IMS_Admin.isProperty_Status())
		{
			System.out.println();
			System.out.println("Property_Description");
			System.out.println(Property_Description);
		}
		if(IMS_Admin.isLife_Status())
		{
			System.out.println();
			System.out.println("Life_Description");
			System.out.println(Life_Description);
		}
		if(IMS_Admin.isMotor_Status())
		{
			System.out.println();
			System.out.println("Motor_Description");
			System.out.println(Motor_Description);
		}
	}

}
