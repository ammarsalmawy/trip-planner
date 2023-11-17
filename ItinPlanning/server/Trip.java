import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Trip {


    Map<String, Map<String, Integer>> distances;
    Map<String, Cities> cities;
    Map<String,List<Activities>> acitiviyMap;
    private List<UserPreferance> userPreferanceList = new ArrayList<>();
    private HashMap<String, String> sharedItineraries = new HashMap<>();

    public Trip() throws RemoteException {

        cities = new HashMap<>();
        distances = new HashMap<>();
        acitiviyMap = new HashMap<>();
    }

    public void setDistance(String cityA, String cityB, int distance) {
        distances.computeIfAbsent(cityA, k -> new HashMap<>()).put(cityB, distance);
        distances.computeIfAbsent(cityB, k -> new HashMap<>()).put(cityA, distance);
    }

    public int getDistance(String cityA, String cityB) {
        if (distances.containsKey(cityA) && distances.get(cityA).containsKey(cityB)) {
            return distances.get(cityA).get(cityB);
        }

        return -1;
    }
    public double DistanceCost(int distance) {
        double costPerKm = 0.68;
        return distance * costPerKm;
    }

    public void addCity(Cities city) {
        List<Activities> aa = new ArrayList<>();
        cities.put(city.getName(), city);
        for(Activities a : city.getActivities()){
            aa.add(a);
            acitiviyMap.put(a.getName(), aa);
        }

    }

    public double getCost(String cityName) {
        Cities city = cities.get(cityName);
        return (city != null) ? city.getCost() : 0.0;
    }
    public double getActivityCost(String ActivityName, String cityName){
        double costA = 0.0;
        Cities city = cities.get(cityName);
        List<Activities> activitiesOfCity = new ArrayList<>();
        if(city!= null){
            for(Activities acc: city.getActivities())
            {
                activitiesOfCity.add(acc);
            }
        }

        for(Activities t : activitiesOfCity)
        {
            if(t.getName().equals(ActivityName))
            {
                costA =  t.getCost();
            }
        }

        return costA;
    }


    ///////////////////**************************************
    private HashMap<String, String> itineraries = new HashMap<>();
    private HashMap<String, String> activites = new HashMap<>();

    int id = 10;

    public List<String> createItinerary(String startCity, String destination) throws RemoteException {


        List<String> res = new ArrayList<>();
        id++;
        String formattedDouble = String.format("%.2f", DistanceCost(getDistance(startCity,destination )));

        String itineraryId = String.valueOf(id);
        String itineraryDetails = "From " + startCity + " to " + destination + " distance "+ getDistance(startCity,destination ) ;
        itineraries.put(itineraryId, itineraryDetails);
        String msg ="From "+ startCity + " to " + destination ;
        msg += " \n Avarage cost of visiting this city for a week is "+ getCost(destination) + " $";
        msg += " \n The distance is "+ getDistance(startCity,destination )+" which will cost average of "+ formattedDouble + " $";
        // System.out.println("Avarage cost of visiting this city for a week is "+ getCost(destination));
        // System.out.println("The distance is "+ getDistance(startCity,destination )+" which will cost avarage of "+ DistanceCost(getDistance(startCity,destination )));

        msg += " \n The activities available in this city are: \n ";
        Cities cities1 = cities.get(destination);
        for (Activities a: cities1.getActivities()) {
            msg += a.getName() + " costs "  + a.getCost()+ " $ \n ";
        }
        res.add(itineraryId);
        res.add(msg);
        return res;

    }

    public  String addActivity(List<String> choosenActivities, String itineraryId, String cityname) throws RemoteException {

        boolean isexist= false;
        Cities ccCities = cities.get(cityname);
        List<Activities> activ = new ArrayList<>();
        for(String s: choosenActivities){
            for(Activities a : ccCities.getActivities())
            {
                if(s.equals(a.getName()))
                {
                    activ.add(a);
                    isexist = true;
                }
            }

        }

        String msg = "The activities : /n";
        for(Activities acs: activ)
        {
            // System.out.println(acs.getName()+ " costs in this city over "+ acs.getCost());
            msg += acs.getName()+ " costs in this city over "+ acs.getCost() +" /n";
        }

        acitiviyMap.put(itineraryId, activ);
        return msg;



    }

    public boolean shareItinerary(String itineraryId, String user) throws RemoteException {
        String choosenIteinerary = itineraries.get(itineraryId);
        List<Double> budgetOfSharedItin = manageBudget(itineraryId);
        List<Activities> activitiesShared = acitiviyMap.get(itineraryId);
        List<String> stringActivities = new ArrayList<>();
        for (Activities a : activitiesShared) {
            stringActivities.add(a.getName());
        }
        int startIndex = choosenIteinerary.indexOf("From ") + "From ".length();
        int toIndex = choosenIteinerary.indexOf(" to ");
        int distanceIndex = choosenIteinerary.indexOf("distance ") + "distance ".length();


        String startCity = choosenIteinerary.substring(startIndex, toIndex);
        String destination = choosenIteinerary.substring(toIndex + " to ".length(), choosenIteinerary.indexOf(" distance "));


        if (itineraries.containsKey(itineraryId)) {
            sharedItineraries.put(itineraryId, user);
            userPreferanceList.add(new UserPreferance(user, stringActivities,startCity,destination, destination, budgetOfSharedItin));
            return true;
        } else {
            return false;
        }
    }

    public String suggestDestination(String preference) throws RemoteException {

        String id;
        String msg ="";

        for (UserPreferance u: userPreferanceList) {
            for (String s: u.getPreference()) {
                if(preference.equals(s))
                { msg +=  " \n "+u.getUsername()+" has shared his itinerary from "+ u.getStartcity() + " to "+ u.getDestination()+" with activities: \n";
                    for (String ac: u.getPreference()) {
                        msg += ac+" \n";
                    }
                    msg += "Distance between cities: "+ u.getDistance()+" \n";
                    msg += "Total budget for this itinerary = "+ u.getBudget().get(0) + " $ \n";
                    msg += "Minimum budget for this itinerary = "+ u.getBudget().get(1) + " $ ";
                   

                }
            }

        }
        return msg;
    }

    public String suggestDestinationBasedOnBudget(String Budget) throws RemoteException {

        String id;
        String msg ="";
        double bud = Double.parseDouble(Budget);

        for (UserPreferance u: userPreferanceList) {

                if(bud >= u.getBudget().get(1))
                { msg +=  " \n "+u.getUsername()+" has shared his itinerary from "+ u.getStartcity() + " to "+ u.getDestination()+" with activities: \n";
                    for (String ac: u.getPreference()) {
                        msg += ac+" \n";
                    }
                    msg += "Distance between cities: "+ u.getDistance()+" \n";
                    msg += "Total budget for this itinerary = "+ u.getBudget().get(0) + " $ \n";
                    msg += "Minimum budget for this itinerary = "+ u.getBudget().get(1) + " $ ";


                }


        }
        return msg;
    }

    public List<Double> manageBudget(String itineraryId) throws RemoteException {
        double itineraryActualCost = 0.0;
        double minimumTotalCost = 0.0;
        double minimumCityCost = 0.0;
        double minimumDistanceCost = 0.0;
        double minimumActivitiesCost = 0.0;
        List<Double> results = new ArrayList<>();

        String choosenIteinerary = itineraries.get(itineraryId);

        int startIndex = choosenIteinerary.indexOf("From ") + "From ".length();
        int toIndex = choosenIteinerary.indexOf(" to ");
        int distanceIndex = choosenIteinerary.indexOf("distance ") + "distance ".length();


        String startCity = choosenIteinerary.substring(startIndex, toIndex);
        String destination = choosenIteinerary.substring(toIndex + " to ".length(), choosenIteinerary.indexOf(" distance "));
        int distance = Integer.parseInt(choosenIteinerary.substring(distanceIndex));
////////////////////////////////////////////////////////////////////////////////////////////////////
        double startCityCost = getCost(startCity);
        double destinationCityCost = getCost(destination);
        double distanceCost = DistanceCost(distance);

        minimumCityCost = destinationCityCost * 0.8;
        minimumDistanceCost = distanceCost * 0.8;

        List<Double> activitiesCost = new ArrayList<>();
        List<Activities> chActivity = acitiviyMap.get(itineraryId);
        for(Activities s : chActivity)
        {
            activitiesCost.add(getActivityCost(s.getName(),destination));


        }
        double totalActivitiesCost= 0.0;

        for(Double d: activitiesCost)
        {
            totalActivitiesCost += d;
            minimumActivitiesCost += (d * 0.8);
        }

        itineraryActualCost = destinationCityCost + distanceCost + totalActivitiesCost;
        minimumTotalCost = minimumActivitiesCost + minimumCityCost + minimumDistanceCost;
       // System.out.println("The avarage of this itinerary is "+ itineraryActualCost+".");

        results.add(itineraryActualCost);
        results.add(minimumTotalCost);
        return results;
    }

    List<String> availableCities(){
        return new ArrayList<>(cities.keySet());
    }


}


