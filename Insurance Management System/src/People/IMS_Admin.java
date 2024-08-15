package People;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;

import Policy.Life_Policy;
import Policy.Motor_Policy;
import Policy.Policy;
import Policy.Property_Policy;

public class IMS_Admin {
	private static String UserName = "IMS";
	private static String Password = "IMS";
	
	private static HashSet<String> TotalDefaultPolicies = new HashSet<String>();
	private static HashSet<String> ActivePolicies = new HashSet<String>();
	private static boolean Property_Status = false;
	private static boolean Life_Status = false;
	private static boolean Motor_Status = false;
	
	private static HashMap<Customer, Policy> CustomersAndPolicies = new HashMap<Customer, Policy>();
	private static Set<Customer> Customers = CustomersAndPolicies.keySet();
	
	
	public static void addIntoCustomers(Customer customers) {
		Customers.add(customers);
	}

	public static HashMap<Customer, Policy> getCustomersAndPolicies() {
		return CustomersAndPolicies;
	}

	public static void addIntoCustomersAndPolicies(Customer C, Policy P) {
		CustomersAndPolicies.put(C, P);
	}

	public static Set<Customer> getCustomers() {
		return Customers;
	}

	public static HashSet<String> getActivePolicies() {
		return ActivePolicies;
	}

	public static boolean isProperty_Status() {
		return Property_Status;
	}

	public static boolean isLife_Status() {
		return Life_Status;
	}

	public static boolean isMotor_Status() {
		return Motor_Status;
	}

	private static void saveToFile(String filename, String text, boolean append)
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
	
	private static void Login()
	{
		Scanner s = new Scanner(System.in);
		String Username;
		String PassWord;
		System.out.println("Need to Authenticate for IMS_Admin");
		System.out.println("Please Login");
		System.out.print("Username: ");
		Username = s.next();
		System.out.print("Password: ");
		PassWord = s.next();
		
		if((Username.equals(UserName) && (PassWord.equals(Password))))
		{
			System.out.println("Successfully Admin has Login in ");
			return;
		}
		System.out.println("Failed to Login in");
		System.out.println("Try Again");
		Login();
	}
	
	private static void WriteTotalDefaultPolicies()
	{
		Scanner s = new Scanner(System.in);
		IMS_Admin.Login();
		System.out.print("Enter the no.of (Default)Policy Names: ");
		int Size = s.nextInt();
		System.out.println("Enter Names: ");
		for(int i = 0; i < Size; i++)
		{
			String S = s.next();
			if( !(IsVaild(S, Size)))
			{
				continue;
			}
		}
	}
	
	private static boolean IsVaild(String S, int Size)
	{
		String[] A = {"Property", "Life", "Motor"};
		for(int i = 0; i < Size; i++)
		{
			if(A[i].equalsIgnoreCase(S))
			{
				S = A[i];
				saveToFile("DefaultPolicies.txt", S, true);
				return true;
			}
		}
		return false;
	}
	
	private static void ReadTotalDefaultPolicies()
	{
		File file = new File("DefaultPolicies.txt");
		try {
			Scanner s = new Scanner(file);
			while(s.hasNextLine())
			{
				String Line = s.nextLine();
				TotalDefaultPolicies.add(Line);
			}
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found");
		}
	}
	public static void UpdateActivePoilicies()
	{
		Scanner s = new Scanner(System.in);
		WriteTotalDefaultPolicies();
		ReadTotalDefaultPolicies();
		System.out.println("Active Policies(Should be a part of Default Policies)");
		System.out.println("Enter no.of Active Policies Names:");
		int Size = s.nextInt();
		while( !(Size <= TotalDefaultPolicies.size()))
		{
			System.out.println("You are trying to add more Active Policies than Default Policies");
			System.out.println("Try again with another number less than " + TotalDefaultPolicies.size());
			Size = s.nextInt();
		}
		System.out.println("Enter Names: ");
		for(int i = 0; i < Size; i++)
		{
			String Active = s.next();
			if(IsPresentInDefault(Active))
			{
				ActivePolicies.add(Active);
				if(Active.equalsIgnoreCase("Property"))
				{
					Property_Status = true;
				}
				else if(Active.equalsIgnoreCase("Life"))
				{
					Life_Status = true;
				}
				else
				{
					Motor_Status = true;
				}
			}
		}
	}
	
	private static boolean IsPresentInDefault(String S)
	{
		for(String a: TotalDefaultPolicies)
		{
			if(a.equalsIgnoreCase(S))
			{
				S = a;
				return true;
			}
		}
		return false;
	}
	public static void DisplayDefaultPolicies()
	{
		System.out.println("Let's Explore Policies:");
		for(String A: TotalDefaultPolicies)
		{
			System.out.println(A);
		}
		if(ActivePolicies.size() == TotalDefaultPolicies.size())
		{
			System.out.println("These All Policies are Active Now.");
			Property_Status = Life_Status = Motor_Status = true;
		}
		else
		{
			System.out.println("The Active Policies are:");
			for(String A: ActivePolicies)
			{
				System.out.println(A);
			}
		}
	}
	
	public static boolean CheckAssurance(Policy P, int A)
	{
		if(A > (Integer.parseInt(P.getAmountAssured()) - 3))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	
	public static boolean CheckPremium(Policy P, int A)
	{
		if(A > (Integer.parseInt(P.getPremiumAmount()) - 3))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public static void UpdateCustomersAndPolicies()
	{
		for(Customer C: Property_Policy.getPolicyCustomers())
		{
			CustomersAndPolicies.put(C, new Property_Policy());	
		}
		Set<Customer> d = Property_Policy.getNewPolicies().keySet();
		Map<Customer, Property_Policy> e = Property_Policy.getNewPolicies();
		for(Customer C : d)
		{
			CustomersAndPolicies.put(C, e.get(d));
		}
		
		for(Customer C: Life_Policy.getPolicyCustomers())
		{
			CustomersAndPolicies.put(C, new Property_Policy());	
		}
		Set<Customer> d1 = Life_Policy.getNewPolicies().keySet();
		Map<Customer, Life_Policy> e1 = Life_Policy.getNewPolicies();
		for(Customer C : d1)
		{
			CustomersAndPolicies.put(C, e1.get(d1));
		}
		
		
		for(Customer C: Motor_Policy.getPolicyCustomers())
		{
			CustomersAndPolicies.put(C, new Motor_Policy());	
		}
		Set<Customer> d11 = Motor_Policy.getNewPolicies().keySet();
		Map<Customer, Motor_Policy> e11 =	Motor_Policy.getNewPolicies();
		for(Customer C : d11)
		{
			CustomersAndPolicies.put(C, e11.get(d11));
		}
		
	}	
}
