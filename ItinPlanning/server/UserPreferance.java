import java.util.ArrayList;
import java.util.List;

public class UserPreferance {
    private String username;
    private List<String> preference = new ArrayList<>();;
    private String startcity;
    private String destination;
    String distance;
    List<Double> budget;



    public UserPreferance(String username, List<String> preference, String startcity, String destination, String distance, List<Double> budget) {
        this.username = username;
        this.preference = preference;
        this.startcity = startcity;
        this.destination = destination;
        this.distance = distance;
        this.budget = budget;
    }
    public List<Double> getBudget() {
        return budget;
    }
    public String getDistance() {
        return distance;
    }

    public String getStartcity() {
        return startcity;
    }

    public String getDestination() {
        return destination;
    }

    public String getUsername() {
        return username;
    }


    public List<String> getPreference() {
        return preference;
    }


}
