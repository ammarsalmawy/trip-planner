import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public interface Planning extends Remote{
        public final static String SERVICENAME = "ArithService";
        List<String> createItinerary(String startCity, String destination) throws RemoteException;
        String addActivity(List<String> choosenActivities, String itineraryId, String cityName) throws RemoteException;
        boolean shareItinerary(String itineraryId, String user) throws RemoteException;
        String suggestDestination(String preference) throws RemoteException;
        String suggestDestinationBasedOnBudget(String budget) throws RemoteException;

        List<Double> manageBudget( String itineraryId) throws RemoteException;
        List<String> getCities () throws RemoteException;
        }