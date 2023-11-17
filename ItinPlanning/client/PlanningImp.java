import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class PlanningImp extends UnicastRemoteObject implements Planning {
    private Trip trip;


    protected PlanningImp(Trip trip) throws RemoteException {
        super();
        this.trip = trip;
    }

    @Override
    public List<String> createItinerary(String startCity, String destination) throws RemoteException {
        return trip.createItinerary( startCity,  destination);
    }

     @Override
    public String addActivity(List<String> choosenActivities, String itineraryId, String cityName) throws RemoteException {
        return trip.addActivity( choosenActivities,  itineraryId, cityName);
    }

    @Override
    public boolean shareItinerary(String itineraryId, String user) throws RemoteException {

        return trip.shareItinerary( itineraryId,  user);
    }

    @Override
    public String suggestDestination(String preference) throws RemoteException {
        return trip.suggestDestination(preference);
    }

    @Override
    public String suggestDestinationBasedOnBudget(String budget) throws RemoteException {
        return trip.suggestDestinationBasedOnBudget(budget);
    }

    @Override
    public List<Double> manageBudget( String itineraryId) throws RemoteException {
        return trip.manageBudget(  itineraryId);
    }

    @Override
    public List<String> getCities() throws RemoteException {
        return trip.availableCities();
    }
}
