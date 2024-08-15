import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

class Customer {
    String id; // this is customer's id.
    int vehicleid;
    LocalDateTime entrytime;
    LocalDateTime exittime;
    String vehicleType; // it should be normal or electric
    String mobileno;

    public Customer(String id, int vehicleid, LocalDateTime entrytime, LocalDateTime exittime, String vehicleType, String mobileno) {
        this.id = id;
        this.vehicleid = vehicleid;
        this.entrytime = entrytime;
        this.exittime = exittime;
        this.vehicleType = vehicleType;
        this.mobileno = mobileno;
    }

    public int amount() {
        Duration time = Duration.between(entrytime, exittime);
        long hours = time.toHours();
        long minutes = time.toMinutesPart();
        int amt = 0;
        if (hours == 0) {
            amt = 20;
        } else if (hours < 3) {
            amt = 20 + (int) hours * 10;
        } else {
            amt = 40 + (int) (hours - 2) * 5;
        }
        if (minutes > 0) {
            amt += 10;
        }
        return amt;
    }
}

class Floor {
    int floorno;
    String vehicletype;
    int size;
    int availableSlots;

    public Floor(int floorno, String vehicletype, int size) {
        this.floorno = floorno;
        this.vehicletype = vehicletype;
        this.size = size;
        this.availableSlots = size;
    }

    void displayflo() {
        System.out.println("Floor no is " + floorno);
    }

    void enter() {
        if (availableSlots > 0) {
            availableSlots--;
            System.out.println("Vehicle entered. " + availableSlots + " slots are available.");
        } else {
            System.out.println("No slots are available, go to another compartment");
        }
    }

    void exit() {
        availableSlots++;
        System.out.println("Vehicle exited. " + availableSlots + " slots are available.");
    }
}

class Electric { // extra class (useless.)
    int time;

    public Electric(int time) {
        this.time = time;
    }
    int amt(){
        return time*20;
    }
}

class Payment {
    public void cash() {
        try {
            System.out.println("you have chosen cash ");
            TimeUnit.SECONDS.sleep(2);
            System.out.println("The amount is paid by cash");
        } 
        catch (InterruptedException e) {
            Thread.currentThread().interrupt(); 
            System.err.println("try once again");
        }
    }

    public void creditcard() {
        System.out.println("You have chosen the credit card, insert the credit card");
        try {
            TimeUnit.SECONDS.sleep(2); 
            System.out.println("amount is paid by creditcard");
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt(); 
            System.err.println("try once again");
        }
    }

    public void onlinePortal() {
        try {
            TimeUnit.SECONDS.sleep(2); 
            System.out.println("The amount will be generated at the time of exit.");
        } 
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("try once again.");
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the size of the ground floor: ");
        int z = sc.nextInt(); // This is for the size of the truck Floor

        System.out.print("Enter the size of the first floor: ");
        int x = sc.nextInt(); // This is for the size of the bike Floor

        System.out.print("Enter the size of the second floor: ");
        int y = sc.nextInt(); // This is for the size of the car Floor

        System.out.print("Enter the number of customers: ");
        int t = sc.nextInt();
        sc.nextLine(); // Consume the newline character after reading t

        Floor groundFloor = new Floor(0, "truck", z);
        Floor bikeFloor = new Floor(2, "", x);
        Floor carFloor = new Floor(1, "", y);

        Customer[] customers = new Customer[t]; // Create an array to store customers

        // Initialize variables to track the latest exit times for different vehicle types
        LocalDateTime latestCarExitTime = LocalDateTime.MIN;
        LocalDateTime latestBikeExitTime = LocalDateTime.MIN;
        LocalDateTime latestTruckExitTime = LocalDateTime.MIN;

        for (int i = 0; i < t; i++) {
            if(i==0){
                System.out.println("enter the details of the "+(i+1)+"st Customer");
            }
            else{
            System.out.println("enter the details of the "+(i+1)+"th Customer");
            }
            System.out.print("Enter the customer id: ");
            String id = sc.nextLine();
            System.out.print("Enter the vehicle's id: ");
            int vehicleid = sc.nextInt();
            sc.nextLine(); // Consume the newline character

            System.out.print("Enter entry time in the format yyyy-MM-dd HH:mm:ss: ");
            String entryTimeStr = sc.nextLine();
            LocalDateTime entryTime = LocalDateTime.parse(entryTimeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            System.out.print("Enter exit time in the format yyyy-MM-dd HH:mm:ss: ");
            String exitTimeStr = sc.nextLine();
            LocalDateTime exitTime = LocalDateTime.parse(exitTimeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            sc.nextLine();

            System.out.print("Enter the type of the vehicle: ");
            String vehicleType = sc.nextLine();
            System.out.print("Enter the mobile no. of the customer: ");
            String mobileno = sc.nextLine();

            customers[i] = new Customer(id, vehicleid, entryTime, exitTime, vehicleType, mobileno);

            // Update the latest exit time based on vehicle type
            if (vehicleid == 2 && exitTime.isAfter(latestBikeExitTime)) {
                latestBikeExitTime = exitTime;
            } 
            else if (vehicleid == 1 && exitTime.isAfter(latestCarExitTime)) {
                latestCarExitTime = exitTime;
            }
             else if (vehicleid == 3 && exitTime.isAfter(latestTruckExitTime)) {
                latestTruckExitTime = exitTime;
            }
        }

        int k=1;
        for (Customer c : customers) {
            switch (c.vehicleid) {
                case 1:
                System.out.println("enter the external details of "+k+" th Customer");
                carFloor.displayflo();
                if (c.vehicleType.equals("electric")) {
                    carFloor.enter();
                    System.out.print("Do you want to charge: ");
                    String a = sc.next();
                    if (a.equals("yes")) {
                        System.out.print("How much time do you want to charge :");
                        int time = sc.nextInt(); // update this line according to the code.
                        Electric e = new Electric(time);
                        System.out.println("the total amount is to be paid = "+(c.amount()+e.amt()));
                        Payment p2 = new Payment();
                System.out.println("enter the number for choosing the payment method ");
                System.out.println("1 - cash");
                System.out.println("2-creditcard");
                System.out.println("3 - onlinePortal");
                int d = sc.nextInt();
                switch(d){
                    case 1 : p2.cash();
                    break;
                    case 2 : p2.creditcard();
                    break;
                    case 3 : p2.onlinePortal();
                    break;
                    default : System.out.println("invalid option is chosen");
                    break;
                }
                    } 
                    else {
                System.out.println("Ok, It's fine.");
                System.out.println("the amount is to be paid ="+c.amount());
                Payment p2 = new Payment();
                System.out.println("enter the number for choosing the payment method ");
                System.out.println("1 - cash");
                System.out.println("2-creditcard");
                System.out.println("3 - onlinePortal");
                int d = sc.nextInt();
                switch(d){
                    case 1 : p2.cash();
                    break;
                    case 2 : p2.creditcard();
                    break;
                    case 3 : p2.onlinePortal();
                    break;
                    default : System.out.println("invalid option is chosen");
                    break;
                }
                    }
                } 
                else {
                    carFloor.enter();
                    System.out.println("the amount is to be paid = "+c.amount());
                     Payment p2 = new Payment();
                System.out.println("enter the number for choosing the payment method ");
                System.out.println("1 - cash");
                System.out.println("2-creditcard");
                System.out.println("3 - onlinePortal");
                int b = sc.nextInt();
                switch(b){
                    case 1 : p2.cash();
                    break;
                    case 2 : p2.creditcard();
                    break;
                    case 3 : p2.onlinePortal();
                    break;
                    default : System.out.println("invalid option is chosen");
                    break;
                }
                }
                break;
                case 2:
                    bikeFloor.displayflo();
                    System.out.println("enter the external details of "+k+" th Customer");
                    // Check if the next bike entry time is before the exit time of the previous bike
                    if (c.entrytime.isBefore(latestBikeExitTime)) {
                        System.out.println("No slots are available, go to another compartment");
                    } else {
                        bikeFloor.enter();
                        System.out.println("Tthe amount is to be paid = " + c.amount());
                        Payment p2 = new Payment();
                        System.out.println("Enter the number for choosing the payment method ");
                        System.out.println("1 - cash");
                        System.out.println("2 - creditcard");
                        System.out.println("3 - onlinePortal");
                        int d = sc.nextInt();
                        switch (d) {
                            case 1:
                                p2.cash();
                                break;
                            case 2:
                                p2.creditcard();
                                break;
                            case 3:
                                p2.onlinePortal();
                                break;
                            default:
                                System.out.println("Invalid option is chosen");
                                break;
                        }
                    }
                    break;
                case 3:
                    groundFloor.displayflo();
                    System.out.println("enter the external details of "+k+" th Customer");
                    // Check if the next truck entry time is before the exit time of the previous truck
                    if (c.entrytime.isBefore(latestTruckExitTime)) {
                        System.out.println("No slots are available, go to another compartment");
                    } else {
                        groundFloor.enter();
                        System.out.println("the amount is to be paid = " + c.amount());
                        Payment p3 = new Payment();
                        System.out.println("Enter the number for choosing the payment method ");
                        System.out.println("1 - cash");
                        System.out.println("2 - creditcard");
                        System.out.println("3 - onlinePortal");
                        int a = sc.nextInt();
                        switch (a) {
                            case 1:
                                p3.cash();
                                break;
                            case 2:
                                p3.creditcard();
                                break;
                            case 3:
                                p3.onlinePortal();
                                break;
                            default:
                                System.out.println("Invalid option is chosen");
                                break;
                        }
                    }
                    break;
                default:
                    System.out.println("This is an invalid vehicleid.");
                    break;
            }
            k++;
        }
    }
}
