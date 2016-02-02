package DB;

import static com.mongodb.client.model.Filters.exists;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.bson.Document;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import Information.CompSciClass;
import Information.Course;
import Information.Student;

public class MdbInterface {
	/***  Data Members ***/
	final private MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
	final private MongoDatabase database = mongoClient.getDatabase("local");
	private MongoCollection<Document> Attendance = database.getCollection("SPRING2016");
	private HashMap<Integer,String> ActiveStudents = new HashMap<>();

	
	/*** Public Methods ***/
	// Can be improved with pop-up error windows
	public boolean StudentExists(final int emplId){
		long n = Attendance.count( new Document("EMPLID", emplId) );
		
		if( n == 1){
			String a = "Student found with EMPLID: " + Long.toString(n);
			System.out.println(a);
			return true;
		}else if ( n > 1 ){
			String b = "SERIOUS ERROR. More than one document with EMPLID: " + Long.toString(n);
			System.out.println(b); // Window?
			return false;
		}else{
			String c = "No student found with EMPLID: " + Long.toString(n);
			System.out.println(c); // Window?
			return false;
		}	
	}
	
	// Student needs Year In School member
	public boolean RegisterStudent(final Student S){
		if ( StudentExists(S.getEmplId()) ){
			String message = "This student has already been registered";
			System.out.println(message);
			return false;
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
	
	public void SignIn(final int emplId){
		if ( !StudentExists(emplId) ){
			String message = "This EMPLID: [" + emplId + "] has not been registered.\n"
					+ "The student must first be registered before signing in.";
			System.out.println(message);
			return;
		}
		
		String time_in = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date());
		ActiveStudents.put(emplId, time_in);
				
		Document queryEMPLID = new Document("EMPLID", emplId);
		Document update = new Document("$set", new Document("LOG."+time_in , "n/a") );
		Attendance.updateOne(queryEMPLID, update);
	}
	
	/* This method will need to be changed to receive a SignOut Object when it is made 
	 
	public static void SignOut(final int emplId){
		if ( !ActiveStudents.containsKey(emplId) ){
			String message = "This student was never signed in. Cannot sign out.";
			System.out.println(message);
			return;
		}
		
		String time_in = ActiveStudents.get(emplId);
		ActiveStudents.remove(emplId);
		String time_out = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date());
		
		final String TOPICSDISCUSSED = "Recursion";
		final String LEVELOFLEARNING = "4";
		final String TUTOR = "Garod";
		
		Document time_doc = new Document()
							.append("TIMEOUT", time_out)
							.append("TOPICSDISCUSSED", TOPICSDISCUSSED)
							.append("LEVELOFLEARNING", LEVELOFLEARNING)
							.append("TUTOR", TUTOR);
		
		Document queryEMPLID = new Document("EMPLID", emplId);
		Document update = new Document("$set", new Document( "LOG."+time_in , time_doc) );
		Attendance.updateOne(queryEMPLID, update);
	}
	*/
	
	// If a tutor forgets to sign out a student, use this to erase from the active list
	public void SignOutAndVoid(final int emplId){
		ActiveStudents.remove(emplId);
	}
	
	// After sign in, use this method to display student information
	public Student getStudentByEmplId(final int emplId){
		if ( !StudentExists(emplId) ){
			String message = "This EMPLID: ["+emplId+"] has not been registered.\n";
			System.out.println(message);
			
			throw new IllegalArgumentException(emplId + " does not exist in the database");
		}
		
		Document S = Attendance.find(new Document("EMPLID", emplId)).first();
		
		return new Student(emplId, S.get("FIRSTNAME", String.class), S.get("LASTNAME", String.class));
	}

	// Incomplete. Query by class is the most desired query Eric Schweitzer.
	public void PrintByCSCIClass(){
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
	
	/*** Private Methods ***/
	private Document makeDocFromClasses(final ArrayList<CompSciClass> array){
		Document d = new Document();
		
		for(CompSciClass class_code : array){
			// Later change get Course to getCourse().name()
			d.append(class_code.getCourse().name(), class_code.getProfessor());
		}
		
		return d;
	}
}

