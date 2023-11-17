import org.junit.Test;

import java.rmi.Naming;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class Testing {



    @Test
    public void testAddActivity() {

        try{

            Planning planning = (Planning) Naming.lookup(Planning.SERVICENAME);

            List<String> activities = Arrays.asList("Shopping", "Hiking");
            String result = planning.addActivity(activities, "10", "Montreal");

            // Assert statements to check if the result is as expected
            assertTrue(result.contains("Shopping costs in this city over 600"));
            assertTrue(result.contains("Hiking costs in this city over 70"));



        }catch (Exception e) {
            e.printStackTrace();
            fail("Exception during test: " + e.getMessage());
        }



    }



}
