package DB;

import static com.mongodb.client.model.Filters.exists;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import Information.*;
import com.mongodb.BasicDBList;
import org.bson.Document;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;

public class Reporter {
    final private MongoClient mongoClient;
    final private MongoDatabase database;
    private MongoCollection<Document> Attendance;

    public Reporter() {
        mongoClient = new MongoClient("localhost", 27017);
        database = mongoClient.getDatabase("local");
        Attendance = database.getCollection("SPRING2016");
    }

    // Incomplete. Query by class is the most desired query Eric Schweitzer.
    public void printByCSCIClass(){
        for (Course course : Course.values()) {
            FindIterable<Document> iterable = Attendance.find( exists("CLASSES."+course.name()) );
            iterable.sort(new Document("CLASSES."+course.name(), 1));

            System.out.println("\nList of Students in " + course.name());
            System.out.println("------------------------------------------");

            iterable.forEach( (Block<Document>) document -> {
                String s = ": " + document.get("LASTNAME") + ", " + document.get("FIRSTNAME");

                s = document.get("CLASSES",Document.class).get(course.name()) + s;

                // Get the size of the log (size refers to the number of sign ins the student has made)
                s = s + " -- " + Integer.toString(document.get("LOG",Document.class).size()) + " visits";

                System.out.println(s);
            });
        }
    }

    public void printFileByCSCIClass(){
        PrintWriter out = null;
        String time = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date());

        try {
            out = new PrintWriter(time+".txt");
        }catch(Exception e) {
            System.out.println("Exception thrown: " + e);
            e.printStackTrace();
            return;
        }

        final PrintWriter temp = out;

        for (Course course : Course.values()) {
            FindIterable<Document> iterable = Attendance.find( exists("CLASSES."+course.name()) );
            iterable.sort(new Document("CLASSES."+course.name(), 1));

            System.out.println("\nList of Students in " + course.name());
            System.out.println("------------------------------------------");

            iterable.forEach( (Block<Document>) document -> {
                String s = ": " + document.get("LASTNAME") + ", " + document.get("FIRSTNAME");

                s = document.get("CLASSES",Document.class).get(course.name()) + s;

                // Get the size of the log (size refers to the number of sign ins the student has made)
                s = s + " -- " + Integer.toString(document.get("LOG",Document.class).size()) + " visits\n";

                temp.write(s);
            });
        }

        try{
            temp.close();
            out.close();
        }catch(Exception e){
            System.out.println("Exception thrown: " + e);
            e.printStackTrace();
        }
    }
}
