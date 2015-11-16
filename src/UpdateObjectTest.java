import junit.framework.TestCase;

/**
 * Created by Ramjee on 01/11/2015.
 */
public class UpdateObjectTest extends TestCase {

    UpdateObject updateObject = new UpdateObject();

    public void testUpdateRoute()throws Exception{
        updateObject.updateRoute("r5", "Route5Rename");
    }

    public void testUpdateStop()throws Exception{
        updateObject.updateStop("b1", "Timetable_Route5", "PolyU", "10.00 AM", "7.30 PM");
    }
}
