/**
 * Created by Ramjee on 01/11/2015.
 */
import junit.framework.TestCase;

public class RemoveObjectTest extends TestCase {

    RemoveObject removeObject = new RemoveObject();

    public void testRemoveUser() throws Exception {
        removeObject.removeUser("15055563G");
    }

    public void testRemoveStop() throws Exception {
        removeObject.removeStop("Route4","Central");
    }

    public void testRemoveRoute() throws Exception {
        removeObject.removeRoute("Route4");
    }
}
