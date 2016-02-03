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
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MdbInterface {
	/***  Data Members ***/
    final private MongoClient mongoClient;
    final private MongoDatabase database;
    private MongoCollection<Document> Attendance;
    private HashMap<Integer, String> ActiveStudents;

    public MdbInterface() {
        mongoClient = new MongoClient("localhost", 27017);
        database = mongoClient.getDatabase("local");
        Attendance = database.getCollection("SPRING2016");
        ActiveStudents = new HashMap<>();
    }

	/*** Public Methods ***/
	// Can be improved with pop-up error windows
	public boolean studentExists(final int emplId){
		long n = Attendance.count( new Document("EMPLID", emplId) );
		
		if( n == 1){
			return true;
		}else if ( n > 1 ){
            throw new IllegalArgumentException("SERIOUS ERROR. More than one document with EMPLID " + Long.toString(n));
		}else{
			return false;
		}	
	}
	
	// Student needs Year In School member
	public boolean registerStudent(final Student S){
		if ( studentExists(S.getEmplId()) ){
            throw new IllegalArgumentException(S.getEmplId() + " has already been registered");
		}
		
		Document class_doc = makeDocFromClasses(S.getCurrentClasses());
		
		Document student = new Document("EMPLID", S.getEmplId())
				.append("FIRSTNAME", S.getFirstName())
				.append("LASTNAME", S.getLastName())
				.append("YEARINSCHOOL", "YEARINSCHOOL")
				.append("CLASSES", class_doc)
				.append("LOG", new Document() );
		
		Attendance.insertOne(student);
		return true;
	}
	
	public void signIn(final int emplId){
		if ( !studentExists(emplId) ){
            throw new IllegalArgumentException(emplId + " has never been registered. Cannot sign in.");
		}
		
		String time_in = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date());
		ActiveStudents.put(emplId, time_in);
				
		Document queryEMPLID = new Document("EMPLID", emplId);
		Document update = new Document("$set", new Document("LOG."+time_in , "n/a") );
		Attendance.updateOne(queryEMPLID, update);
	}

	public void signOut(SignOutData sod){
		if ( !ActiveStudents.containsKey(sod.getEmplId()) ){
            throw new IllegalArgumentException(sod.getEmplId() + " was never signed in. Cannot sign out.");
		}
		
		String time_in = ActiveStudents.get(sod.getEmplId());
		ActiveStudents.remove(sod.getEmplId());
		String time_out = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date());
		
		//final String TOPICSDISCUSSED = "Recursion";
		//final String LEVELOFLEARNING = "4";
		//final String TUTOR = "Garod";
		
		Document time_doc = new Document()
							.append("TIMEOUT", time_out)
							.append("TOPICSDISCUSSED", new BasicDBList().addAll(sod.getTopics()))
							.append("LEVELOFLEARNING", sod.getLevelOfLearning())
							.append("TUTOR", "tutor_goes_here"); //TODO
		
		Document queryEMPLID = new Document("EMPLID", sod.getEmplId());
		Document update = new Document("$set", new Document( "LOG."+time_in , time_doc) );
		Attendance.updateOne(queryEMPLID, update);
	}
	
	// If a tutor forgets to sign out a student, use this to erase from the active list
	public void signOutAndVoid(final int emplId){
		ActiveStudents.remove(emplId);
	}
	
	// After sign in, use this method to display student information
	public Student getStudentByEmplId(final int emplId){
		if ( !studentExists(emplId) ){
			throw new IllegalArgumentException(emplId + " does not exist in the database");
		}
		
		Document S = Attendance.find(new Document("EMPLID", emplId)).first();
		
		return new Student(emplId, S.get("FIRSTNAME", String.class), S.get("LASTNAME", String.class),
                S.get("YEARINSCHOOL", ClassYear.class));
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

	/*** Private Methods ***/
	private Document makeDocFromClasses(final ArrayList<CompSciClass> array){
		Document d = new Document();
		
		for(CompSciClass class_code : array){
			d.append(class_code.getCourse().name(), class_code.getProfessor().name());
		}
		
		return d;
	}
}

