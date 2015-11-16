import junit.framework.TestCase;

import org.bson.Document;
import com.mongodb.client.FindIterable;
import com.mongodb.*;

import static com.mongodb.client.model.Filters.eq;

/**
 * Created by Ramjee on 01/11/2015.
 */
public class CreateObjectTest extends TestCase {

    CreateObject createObject = new CreateObject();

    public void testAddUser() throws Exception {
        //createObject.addUser(1,"15055564G", "15055564G");

        FindIterable<Document> iterable = createObject.db.getCollection("Users").find(eq("_id",1));
        iterable.forEach(new Block<Document>() {
            @Override
            public void apply(final Document document) {
                assertEquals("15055564G", document.get("username"));
                assertEquals("15055564G", document.get("password"));
            }
        });
    }

    public void testAddRoute() throws Exception {
        //createObject.addRoute("r4","Route4");
        //createObject.addRoute("r5","Route5");

        FindIterable<Document> iterable = createObject.db.getCollection("Routes").find(eq("_id","r5"));
        iterable.forEach(new Block<Document>() {
            @Override
            public void apply(final Document document) {
                assertEquals("Route4", document.get("routeName"));
                assertEquals("Timetable_Route4",document.get("timetable"));
            }
        });
    }

    public void testAddBusStop() throws Exception {
        //createObject.addBusStop("b1","Route4","PolyU","9.00 AM","6.00 PM");
        //createObject.addBusStop("b2","Route4","Wanchai","9.30 AM","6.30 PM");
        //createObject.addBusStop("b3","Route4","Central","9.45 AM","6.45 PM");
        //createObject.addBusStop("b1","Route5","PolyU","10.00 AM","7.00 PM");
        //createObject.addBusStop("b2","Route5","Jordan","10.30 AM","10.30 PM");
        //createObject.addBusStop("b3","Route5","Mongkok","10.45 AM","10.45 PM");

        FindIterable<Document> iterable = createObject.db.getCollection("Timetable_Route5").find(eq("_id","b5"));
        iterable.forEach(new Block<Document>() {
            @Override
            public void apply(final Document document) {
                System.out.println(document);
                assertEquals("PolyU", document.get("stopName"));
                assertEquals("9.00 AM",document.get("dayTime"));
                assertEquals("8.00 PM",document.get("eveningTime"));
            }
        });
    }
}
