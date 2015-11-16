/**
 * Created by Ramjee on 01/11/2015.
 */
import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;

import org.bson.Document;

public class CreateObject {

    MongoClient dbClient = new MongoClient();
    MongoDatabase db =  dbClient.getDatabase("BUS");

    public void addUser(String userId, String username, String password){
        Document user = new Document("_id",userId)
                .append("username",username)
                .append("password",password);

        db.getCollection("Users").insertOne(user);
    }

    public void addRoute(String routeId, String routeName){
        String routeTimetable = "Timetable_";
        Document route = new Document("_id",routeId)
                .append("routeName",routeName)
                .append("timetable",routeTimetable.concat(routeName));

        db.getCollection("Routes").insertOne(route);
    }

    public void addBusStop(String busStopId, String routeName, String busStopName, String dayTime, String eveningTime){
        Document busStopDetails = new Document("_id",busStopId)
                .append("busStopName",busStopName)
                .append("dayTime",dayTime)
                .append("eveningTime",eveningTime);

        FindIterable<Document> iterable = db.getCollection("Routes").find(eq("routeName",routeName));
        iterable.forEach(new Block<Document>() {
            @Override
            public void apply(final Document document) {
                db.getCollection(document.get("timetable").toString()).insertOne(busStopDetails);
            }
        });
    }
}
