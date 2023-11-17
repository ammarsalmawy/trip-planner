import java.rmi.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        try {
            //Look up the Planning implementation from the registry
            Planning planning = (Planning) Naming.lookup(Planning.SERVICENAME);

            System.out.println("Welcome to trip planner");


            //Get input from the user
            Scanner scanner = new Scanner(System.in);
            System.out.println("Please choose an option:");
            System.out.println("1- Start Itinerary");
            System.out.println("2- Suggest destinations");
            String choose = scanner.nextLine();
            if(choose.equals("1")){
                System.out.println("available cities in Canada: ");
                List<String> availableCities = planning.getCities();
                for (String ci: availableCities) {
                    System.out.println(ci);
                }
            System.out.print("Enter starting city: ");
            String startCity = scanner.nextLine();
            System.out.print("Enter destination city: ");
            String destination = scanner.nextLine();

            List<String> itineraryString = planning.createItinerary(startCity, destination);

                System.out.println(itineraryString.get(1));
            System.out.println("Enter the number of activities?");
            String numberOfActivities = scanner.nextLine();
            int numberOfActivitiesDouble = Integer.parseInt(numberOfActivities);

            List<String> activities = new ArrayList<>() ;
            if(numberOfActivitiesDouble >0 || numberOfActivitiesDouble <= 5){
            for (int i = 1; i <= numberOfActivitiesDouble; i++) {
                 System.out.println("Enter activity"+ i+":");
                 String activity = scanner.nextLine();
                 activities.add(activity);
                }


            String addingActivities = planning.addActivity(activities,itineraryString.get(0), destination );
            

            // Call the manageBudget method on the server
            List<Double> budgetApproved = planning.manageBudget(itineraryString.get(0) );

                System.out.println("The Average budget for this itinerary is "+ budgetApproved.get(0)+ " $");
                System.out.println("The minimum budget you can travel with is over "+ budgetApproved.get(1) + " $");
                System.out.println("Do you want to share your itinerary?");
                  String shareItinerary = scanner.nextLine();
                if(shareItinerary.equals("yes"))
                {
                    System.out.println("Please enter the username");
                    String username = scanner.nextLine();
                    boolean itineraryShared = planning.shareItinerary(itineraryString.get(0), username);
                    if(itineraryShared)
                    {
                        System.out.println("Itinerary shared! thank you");
                    }
                }
            
        }
        else if(numberOfActivitiesDouble > 5)
            {
                System.out.println("Number of activities is so large, try again!");
            }
    }
            else{
                System.out.println("1- Based on preferance?");
                System.out.println("2- Based on budget?");
                String choosenSuggest = scanner.nextLine();
                if(choosenSuggest.equals("1")) {
                    System.out.println("Please enter your preferance");
                    String preferance = scanner.nextLine();
                    String suggestedDestination = planning.suggestDestination(preferance);
                    System.out.println(suggestedDestination);
                }
                else if (choosenSuggest.equals("2"))
                {System.out.println("Please enter your minimum budget");
                    String miniBud = scanner.nextLine();
                    String suggestedD = planning.suggestDestinationBasedOnBudget(miniBud);
                    System.out.println(suggestedD);

                }
            }
        
            
        
            
          
        





        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
