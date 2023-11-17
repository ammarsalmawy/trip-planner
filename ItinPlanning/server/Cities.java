import java.util.ArrayList;
import java.util.List;

public class Cities {

    private String name;
    private double cost;
    private List<Activities> acitivitiesList = new ArrayList<>();

    public Cities(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
    public void addActivities(Activities a)
    {
        acitivitiesList.add(a);
    }
    public List<Activities> getActivities()
    {
        return acitivitiesList;
    }
}
