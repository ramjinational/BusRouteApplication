/**
 * Created by Ramjee on 01/11/2015.
 */
import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;

import static com.mongodb.client.model.Filters.*;

import org.bson.Document;

public class UpdateObject {

    MongoClient dbClient = new MongoClient();
    MongoDatabase db =  dbClient.getDatabase("BUS");

    Document replaceDocument = new Document();
    String tableName;

    public void updateTimetable(String routeId, String routeName){
        FindIterable<Document> routeIterable = db.getCollection("Routes").find(eq("_id",routeId));
        routeIterable.forEach(new Block<Document>() {
            @Override
            public void apply(final Document document) {
                tableName = document.get("timetable").toString();
                System.out.println(tableName);
            }
        });

        MongoNamespace newCollectionName = new MongoNamespace("BUS","Timetable_"+routeName);
        db.getCollection(tableName).renameCollection(newCollectionName);
    }

    public void updateRoute(String routeId, String routeName){
        updateTimetable(routeId, routeName);

        FindIterable<Document> routeIterable = db.getCollection("Routes").find(eq("_id",routeId));
        routeIterable.forEach(new Block<Document>() {
            @Override
            public void apply(final Document document) {
                replaceDocument = new Document("_id",routeId)
                        .append("routeName",routeName)
                        .append("timetable","Timetable_"+routeName);

                db.getCollection("Routes").findOneAndReplace(document, replaceDocument);
            }
        });
    }

    public void updateStop(String busStopId, String timetable, String busStopName, String dayTime, String eveningTime){
        FindIterable<Document> stopIterable = db.getCollection(timetable).find(eq("_id", busStopId));
        stopIterable.forEach(new Block<Document>() {
            @Override
            public void apply(final Document document) {
                replaceDocument = new Document("_id",busStopId)
                        .append("busStopName",busStopName)
                        .append("dayTime",dayTime)
                        .append("eveningTime",eveningTime);

                db.getCollection(timetable).findOneAndReplace(document, replaceDocument);
            }
        });
    }

}
