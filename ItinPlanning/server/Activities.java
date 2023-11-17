import java.util.HashMap;
import java.util.Map;

public class Activities {

    private String name;
    private double cost;
    private Cities associatedCity;


    public Activities(String name, double cost, Cities associatedCity){
        this.name = name;
        this.cost = cost;
        this.associatedCity = associatedCity;
    }
    public String getName() {
        return name;
    }


    public double getCost() {
        return cost;
    }

    public Cities getAssociatedcity(){
        return associatedCity;
    }
    public double calculateActivityCost(String activity) {
        // Replace this with your actual logic for calculating activity cost
        // For example, you can have a map of activities and their associated costs
        Map<String, Double> activityCosts = new HashMap<>();
        activityCosts.put("Sightseeing", 20.0);
        activityCosts.put("Shopping", 30.0);

        return activityCosts.getOrDefault(activity, 0.0);
    }
}
