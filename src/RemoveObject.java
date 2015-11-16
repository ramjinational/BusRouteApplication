/**
 * Created by Ramjee on 01/11/2015.
 */
import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;

import static com.mongodb.client.model.Filters.*;

import org.bson.Document;

public class RemoveObject {

    MongoClient dbClient = new MongoClient();
    MongoDatabase db =  dbClient.getDatabase("BUS");

    Document deleteDocument = new Document();

    public void removeUser(String username){
        FindIterable<Document> userIterable = db.getCollection("Users").find(eq("username",username));
        userIterable.forEach(new Block<Document>() {
            @Override
            public void apply(final Document document) {
                deleteDocument = document;
                db.getCollection("Users").findOneAndDelete(deleteDocument);
            }
        });
    }

    public void removeTimetable(String routeName){
        FindIterable<Document> timetableIterable = db.getCollection("Timetable_"+routeName).find();
        timetableIterable.forEach(new Block<Document>() {
            @Override
            public void apply(final Document document) {
                deleteDocument = document;
                db.getCollection("Timetable_"+routeName).findOneAndDelete(deleteDocument);
            }
        });

        db.getCollection("Timetable_"+routeName).drop();
    }

    public void removeRoute(String routeName){
        removeTimetable(routeName);

        FindIterable<Document> routeIterable = db.getCollection("Routes").find(eq("routeName",routeName));
        routeIterable.forEach(new Block<Document>() {
            @Override
            public void apply(final Document document) {
                deleteDocument = document;
                db.getCollection("Routes").findOneAndDelete(deleteDocument);
            }
        });
    }

    public void removeStop(String routeName, String stopName){
        FindIterable<Document> stopIterable = db.getCollection("Timetable_"+routeName).find(eq("busStopName", stopName));
        stopIterable.forEach(new Block<Document>() {
            @Override
            public void apply(final Document document) {
                deleteDocument = document;
                db.getCollection("Timetable_"+routeName).findOneAndDelete(deleteDocument);
            }
        });
    }

}
