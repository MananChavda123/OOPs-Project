/******************************************************************************

Welcome to GDB Online.
GDB online is an online compiler and debugger tool for C, C++, Python, Java, PHP, Ruby, Perl,
C#, OCaml, VB, Swift, Pascal, Fortran, Haskell, Objective-C, Assembly, HTML, CSS, JS, SQLite, Prolog.
Code, Compile, Run and Debug online from anywhere in world.

*******************************************************************************/
import java.util.*;
abstract class ATM{
    protected int Cash = 50000;//Total cash in cashdispenser in atm.
    abstract void displayBalance();
    
}
class User extends ATM{
    protected int accountno;
    protected int userpin;
    protected int amount;
    protected int balance;
    public User(int accountno,int userpin,int balance)
    {
        this.accountno = accountno;
        this.amount = amount;
        this.balance = balance;
        this.userpin = userpin;
    }
    public int deposit(int amount)
    {
        balance += amount;
        return balance;
    }
    public int withdrawal(int amount)
    {
        balance -= amount;
        return balance;
    }
    public void displayBalance()
    {
        System.out.println("Your current balance is" + balance);
    }
    
}

public class atmsystem
{
	public static void main(String[] args) {
	    Scanner scan = new Scanner(System.in);
	    User[] user = new User[5];
	    user[1] = new User(12345,25252,50000);
	    user[2] = new User(24689,47193,20000);
	    user[3] = new User(34125,99976,100000);
	    user[4] = new User(91765,87544,500000);
	    user[0] = new User(85553,80761,400000);
	    
	
		int money ;
		int m=0,k=0;//variable k declared to limit wrong attempts to 3
		while(k!=3)
		{
		System.out.println("Welcome to city Bank!\nPlease enter your account no. :");
		int acNo = scan.nextInt();
		System.out.println("Please enter your pin:");
		int pin = scan.nextInt();
		for(int i=0;i<5;++i)
		{
		    
		    if(acNo == user[i].accountno && pin == user[i].userpin)
		    {
		        System.out.println("Please choose from the following options:");
		        System.out.println("1.View your balance\n2.Withdraw amount\n3. Deposit funds\n");
		        int n = scan.nextInt();
		        switch(n){
		            case 1:
		                user[i].displayBalance();
		                break;
		                
		            case 2:
		                System.out.println("Please enter the amount to withdraw: ");
		                money = scan.nextInt();
		                if(money < user[i].Cash)
		                 {
		                     user[i].withdrawal(money);
		                     System.out.println("Your transaction was successful!");
		                     user[i].displayBalance();
		                 }
		                else{
		                    System.out.println("No required cash in the machine");
		                    }
		                    break;
		                    
		          case 3:
		              System.out.println("Please enter the amount to deposit: ");
		              money = scan.nextInt();
		              user[i].deposit(money);
		              System.out.println("Your transaction was successful!");
		              user[i].displayBalance();
		              
		              break;
		              
		          default:
		                break;
		               
		        }
		        
		    }
		    else
		    {
		        m++;
		       
		    }
		}
		if(m>4)
		{
		    System.out.println("Please enter valid details");
		    k++;
		}
	
		}
		
		
	}
}
