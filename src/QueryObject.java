/**
 * Created by Ramjee on 01/11/2015.
 */
import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

import com.mongodb.client.MongoIterable;
import org.bson.Document;

import java.util.ArrayList;

public class QueryObject {

    MongoClient dbClient = new MongoClient();
    MongoDatabase db =  dbClient.getDatabase("BUS");

    String jsonCredentials = null;
    String jsonSourceStop = null;
    String jsonDestinationStop = null;
    String resultRoute = null;
    ArrayList<String> jsonTimetable = new ArrayList<>();
    ArrayList<String> jsonRouteList = new ArrayList<>();
    ArrayList<String> collectionNames = new ArrayList<>();
    ArrayList<String> users = new ArrayList<>();

    public void queryCollections(){
        MongoIterable collectionNameIterable = db.listCollectionNames();
        collectionNameIterable.forEach(new Block() {
            @Override
            public void apply(Object o) {
                collectionNames.add(o.toString());
            }
        });
    }

    public void queryUsers(){
        FindIterable<Document> userIterable = db.getCollection("Users").find();
        userIterable.forEach(new Block<Document>() {
            @Override
            public void apply(final Document document) {
                users.add(document.toJson());
            }
        });
    }

    public void queryCredentials(String username, String password){
        FindIterable<Document> userIterable = db.getCollection("Users").find(and(eq("username",username),eq("password",password)));
        userIterable.forEach(new Block<Document>() {
            @Override
            public void apply(final Document document) {
                jsonCredentials = document.toJson();
            }
        });
    }

    public void queryRouteList(){
        FindIterable<Document> routeIterable = db.getCollection("Routes").find();
        routeIterable.forEach(new Block<Document>() {
            @Override
            public void apply(final Document document) {
                jsonRouteList.add(document.toJson());
            }
        });
    }

    public void queryTimetable(String routeName){
        String timetable = "Timetable_";
        timetable = timetable.concat(routeName);

        FindIterable<Document> timetableIterable = db.getCollection(timetable).find();
        timetableIterable.forEach(new Block<Document>() {
            @Override
            public void apply(final Document document) {
                jsonTimetable.add(document.toJson());
            }
        });
    }

    public void queryRoute(String sourceStopName, String destinationStopName){
        FindIterable<Document> routeIterable = db.getCollection("Routes").find();
        routeIterable.forEach(new Block<Document>() {
            @Override
            public void apply(final Document document) {
                if (resultRoute == null) {
                    queryStop(String.valueOf(document.get("routeName")), String.valueOf(document.get("timetable")), sourceStopName, destinationStopName);
                }
            }
        });
    }

    public void queryStop(String routeName, String timetable, String sourceStopName, String destinationStopName){
        FindIterable<Document> sourceStopIterable = db.getCollection(timetable).find(eq("busStopName", sourceStopName));
        sourceStopIterable.forEach(new Block<Document>() {
            @Override
            public void apply(final Document document) {
                jsonSourceStop = document.toJson();
            }
        });

        FindIterable<Document> destinationStopIterable = db.getCollection(timetable).find(eq("busStopName", destinationStopName));
        destinationStopIterable.forEach(new Block<Document>() {
            @Override
            public void apply(final Document document) {
                jsonDestinationStop = document.toJson();
            }
        });

        if ((jsonSourceStop != null) && (jsonDestinationStop != null)){
            resultRoute = routeName;
            queryTimetable(routeName);
        }
    }

}
