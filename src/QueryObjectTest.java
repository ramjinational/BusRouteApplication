/**
 * Created by Ramjee on 01/11/2015.
 */
import jdk.nashorn.internal.ir.annotations.Ignore;
import junit.framework.TestCase;

public class QueryObjectTest extends TestCase {

    QueryObject queryObject = new QueryObject();

    public void testQueryCollections() throws Exception{
        queryObject.queryCollections();
        System.out.println(queryObject.collectionNames);
    }

    public void testQueryUsers() throws Exception {
        queryObject.queryUsers();
        System.out.println(queryObject.users);
    }

    public void testQueryCredentials() throws Exception {
        queryObject.queryCredentials("15055564G","15055564G");
        System.out.println(queryObject.jsonCredentials);
    }

    public void testQueryRouteList(){
        queryObject.queryRouteList();
        System.out.println(queryObject.jsonRouteList);
    }

    public void testQueryTimetable() throws Exception {
        queryObject.queryTimetable("Route5Rename");
        System.out.println(queryObject.jsonTimetable);
    }

    public void testQueryRoute() throws Exception {
        queryObject.queryRoute("PolyU","Jordan");
        System.out.println(queryObject.resultRoute);
        System.out.println(queryObject.jsonSourceStop);
        System.out.println(queryObject.jsonDestinationStop);
        System.out.println(queryObject.jsonTimetable);
    }

}
