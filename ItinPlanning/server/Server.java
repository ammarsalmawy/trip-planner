import java.rmi.Naming;

public class Server {
    public static void main(String[] args) throws Exception {

        Trip trip = new Trip();

        Cities Toronto = new Cities("Toronto", 2000.0);
        Cities Quebec = new Cities("Quebec", 1400.0);
        Cities Ottawa = new Cities("Ottawa", 1500.0);
        Cities Montereal = new Cities("Montreal", 950.0);
        Cities Vancouver = (new Cities("Vancouver", 1100.0));
        Cities Edmonton = new Cities("Edmonton", 1700.0);

        Activities ShoppingT = new Activities("Shopping", 1500, Toronto);
        Activities HoneyMoonT = new Activities("HoneyMoon", 2000, Toronto);
        Activities HikingT = new Activities("Hiking", 120, Toronto);
        Activities AdventureT = new Activities("Adventure", 400, Toronto);
        Activities SightSeeingT = new Activities("SightSeeing", 1000, Toronto);

        Activities ShoppingQ = new Activities("Shopping", 2000, Quebec);
        Activities HoneyMoonQ = new Activities("HoneyMoon", 260, Quebec);
        Activities HikingQ = new Activities("Hiking", 160, Quebec);
        Activities AdventureQ = new Activities("Adventure", 700, Quebec);
        Activities SightSeeingQ = new Activities("SightSeeing", 1200, Quebec);

        Activities ShoppingO = new Activities("Shopping", 1000, Ottawa);
        Activities HoneyMoonO = new Activities("HoneyMoon", 1250, Ottawa);
        Activities HikingO = new Activities("Hiking", 80, Ottawa);
        Activities AdventureO = new Activities("Adventure", 200, Ottawa);
        Activities SightSeeingO = new Activities("SightSeeing", 1200, Ottawa);

        Activities ShoppingM = new Activities("Shopping", 600, Montereal);
        Activities HoneyMoonM = new Activities("HoneyMoon", 1400, Montereal);
        Activities HikingM = new Activities("Hiking", 70, Montereal);
        Activities AdventureM = new Activities("Adventure", 100, Montereal);
        Activities SightSeeingM = new Activities("SightSeeing", 800, Montereal);

        Toronto.addActivities(ShoppingT);  Toronto.addActivities(HoneyMoonT); Toronto.addActivities(HikingT);
        Toronto.addActivities(AdventureT); Toronto.addActivities(SightSeeingT);

        Quebec.addActivities(ShoppingQ); Quebec.addActivities(HoneyMoonQ); Quebec.addActivities(HikingQ);
        Quebec.addActivities(AdventureQ); Quebec.addActivities(SightSeeingQ);

        Ottawa.addActivities(ShoppingO); Ottawa.addActivities(HoneyMoonO); Ottawa.addActivities(HikingO);
         Ottawa.addActivities(AdventureO); Ottawa.addActivities(SightSeeingO);

         Montereal.addActivities(ShoppingM); Montereal.addActivities(HoneyMoonM); Montereal.addActivities(HikingM);
          Montereal.addActivities(AdventureM); Montereal.addActivities(SightSeeingM);

          trip.setDistance("Toronto", "Quebec", 800);
        trip.setDistance("Toronto", "Ottawa", 405);
        trip.setDistance("Toronto", "Montreal", 542);
        trip.setDistance("Toronto", "Vancouver", 3359);
        trip.setDistance("Toronto", "Edmonton", 3460);
        
        trip.addCity(Toronto);
        trip.addCity(Quebec);
        trip.addCity(Ottawa);
        trip.addCity(Montereal);
        trip.addCity(Vancouver);
        trip.addCity(Edmonton);



        PlanningImp planningImp = new PlanningImp(trip);


        Naming.rebind(Planning.SERVICENAME, planningImp);

//        Registry registry = LocateRegistry.createRegistry(1099);
//        registry.rebind("PlanningService", planningImp);
        System.out.println("Server ready");
    }
}
