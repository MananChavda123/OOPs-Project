import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

interface Payment {
    void makePayment(double amount);
}

abstract class Vehicle {
    int vehicleId;
    String vehicleType;
    String NumberPlate;

    public Vehicle(int vehicleId, String vehicleType, String NumberPlate) {
        this.vehicleId = vehicleId;
        this.vehicleType = vehicleType;
        this.NumberPlate = NumberPlate;
    }

    abstract void enter(Floor floor);
    abstract void exit(Floor floor);
}

class Bike extends Vehicle {
    public Bike(int vehicleId, String NumberPlate) {
        super(vehicleId, "bike", NumberPlate);
    }

    void enter(Floor floor) {
        floor.displayflo();
        floor.enter();
    }

    void exit(Floor floor) {
        floor.exit();
    }
}

class Car extends Vehicle {
    public Car(int vehicleId, String NumberPlate) {
        super(vehicleId, "car", NumberPlate);
    }

    void enter(Floor floor) {
        floor.displayflo();
        floor.enter();
    }

    void exit(Floor floor) {
        floor.exit();
    }
}

class ElectricCar extends Vehicle {
    public ElectricCar(int vehicleId, String NumberPlate) {
        super(vehicleId, "electric", NumberPlate);
    }

    void enter(Floor floor) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Do you want to charge: ");
        String a = sc.next();
        if (a.equals("yes")) {
            System.out.print("How much time do you want to charge :");
            int time = sc.nextInt();
            Electric e = new Electric(time);
            e.amount();
        } 
        else {
            System.out.println("Ok, It's fine.");
        }
    }

    void exit(Floor floor) {
        floor.exit();
    }
}

class Truck extends Vehicle {
    public Truck(int vehicleId, String NumberPlate) {
        super(vehicleId, "truck", NumberPlate);
    }

    void enter(Floor floor) {
        floor.displayflo();
        floor.enter();
    }

    void exit(Floor floor) {
        floor.exit();
    }
}

class Floor {
    int floorno;
    String vehicletype;
    int size;
    int availableSlots;

    public Floor(int floorno, int size) {
        this.floorno = floorno;
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

    int getslots(){
        return availableSlots;
    }
}

class Electric {
    int time;

    public Electric(int time) {
        this.time = time;
    }

    void amount() {
        // calculate the amount in this part.
        System.out.println("the amount to be paid for charging is : " + time * 20);
        System.out.print("select the type of payment for charging: ");
        Scanner svr = new Scanner(System.in);
        int o = svr.nextInt();
            Payment_Method o1 = new Payment_Method();
            switch (o) {
                case 1:
                    o1.cash();
                    break;
                case 2:
                    o1.creditcard();
                    break;
                case 3:
                    o1.onlinePortal();
                    break;
                default:
                    System.out.println("invalid option is chosen ");
                    break;
            }
    }
}

class Customer {
    private String id;
    private int vehicleId;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private String vehicleType;
    private String mobileNo;

    public Customer(String id, int vehicleId, LocalDateTime entryTime, LocalDateTime exitTime, String vehicleType, String mobileNo) {
        this.id = id;
        this.vehicleId = vehicleId;
        this.entryTime = entryTime;
        this.exitTime = exitTime;
        this.vehicleType = vehicleType;
        this.mobileNo = mobileNo;
    }

    public String getId() {
        return id;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public LocalDateTime getExitTime() {
        return exitTime;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void calculateAndDisplayAmount() {
        Duration time = Duration.between(entryTime, exitTime);
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
        System.out.println("The amount to be paid is: " + amt);
    }
}

class Payment_Method {
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
            System.err.println("Thread interrupted while sleeping.");
        }
    }
}

public class Kase {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("enter the number of customers: ");
        int t=sc.nextInt();//inputing the number of customers

        System.out.print("Enter the number of floors: ");
       int n=sc.nextInt();//inputing the number of floors from the user(* Should be a multiple of 3 *)
       sc.nextLine();
       Floor[] floors = new Floor[n];

       for(int i=0;i<n;i++){
        floors[i]=new Floor(i,30);
       }
        int k= 1;
        while (t-- > 0) {
            
            System.out.println("enter the details of the "+k+" th customers ");
            System.out.print("enter the customers id : ");
            String id = sc.nextLine();

            System.out.print("enter the vehicle id: " );

            int vehicleId = sc.nextInt();
            
            sc.nextLine(); //

            System.out.print("Enter entry time in the format yyyy-MM-dd HH:mm:ss: ");
            String entryTimeStr = sc.nextLine();
            LocalDateTime entryTime = LocalDateTime.parse(entryTimeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            System.out.print("Enter exit time in the format yyyy-MM-dd HH:mm:ss: ");
            String exitTimeStr = sc.nextLine();
            LocalDateTime exitTime = LocalDateTime.parse(exitTimeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            
            
            System.out.print("enter the type of the vehicle : ");
            String vehicleType = sc.nextLine();
            System.out.print("enter the mobile no of the Customer : ");
            String mobileNo = sc.nextLine();

            Customer c = new Customer(id, vehicleId, entryTime, exitTime, vehicleType, mobileNo);

            Vehicle vehicle;
        
            int p=n/3;
            int j=0;

            switch (c.getVehicleId()) {
                case 1:
                    vehicle = new Bike(c.getVehicleId(), c.getMobileNo());
                    for(int i=2*p;i<3*p;i++){
                       if(floors[i].availableSlots>0){
                            vehicle.enter(floors[i]);
                            j=i;
                            break;
                       }
                    }
                    break;
                case 2:
                    if (c.getVehicleType().equals("electric")) {
                        vehicle = new ElectricCar(c.getVehicleId(), c.getMobileNo());
                    } 
                    else {
                        vehicle = new Car(c.getVehicleId(), c.getMobileNo());
                    }
                    for(int i=p;i<2*p;i++){
                       if(floors[i].availableSlots>0){
                        vehicle.enter(floors[i]);
                        j=i;
                        break;
                       }
                    }
                    break;
                case 3:
                    vehicle = new Truck(c.getVehicleId(), c.getMobileNo());
                    for(int i=0;i<p;i++){
                        if(floors[i].availableSlots>0){
                            vehicle.enter(floors[i]);
                            j=i;
                            break;
                        }
                    }
                    break;
                default:
                    System.out.println("This is an invalid vehicleId.");
                    continue;
            }

           k++;
            if (LocalDateTime.now().isAfter(c.getExitTime())) {
                vehicle.exit(floors[j]);
            }
            c.calculateAndDisplayAmount(); 
            System.out.print("select the type of payment for parking: ");
            int r = sc.nextInt();
            Payment_Method v = new Payment_Method();
            switch (r) {
                case 1:
                    v.cash();
                    break;
                case 2:
                    v.creditcard();
                    break;
                case 3:
                    v.onlinePortal();
                    break;
                default:
                    System.out.println("invalid option is chosen ");
                    break;
            }
            sc.nextLine();
        }
    }
}