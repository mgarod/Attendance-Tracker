package mongodb01;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.BasicDBObject;
import com.mongodb.Block;

import org.bson.Document;

import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.text.SimpleDateFormat;

public class mdb {
	/***  Data Members ***/
	final public static MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
	final public static MongoDatabase database = mongoClient.getDatabase("local");
	public static MongoCollection<Document> Attendance = database.getCollection("SPRING2016");
	
	final public static String[] class_codes
		= {"CSCI127", "CSCI135", "CSCI136", "CSCI150", "CSCI160", "CSCI235"};
	
	private static HashMap<String,String> ActiveStudents = new HashMap<>();
	
	
	/*** Methods ***/
	public static void main(String[] args){
		System.out.println("Starting mdb.main()");
		
		RegisterStudent("12345678");
		SignIn("12345678");
		RegisterStudent("87654321");	
		SignIn("87654321");
		
		System.out.println("\nList of current students");
		System.out.println("------------------------------------------");
		
		for (HashMap.Entry<String,String> entry : ActiveStudents.entrySet()) {
			  String key = entry.getKey();
			  String value = entry.getValue();
			  System.out.println("\t " + key + " : " + value);
		}
		System.out.print("\n");
		//SignOut("12345678");
		//SignOut("87654321");
		
		System.out.println("Exiting mdb.main()");
		mongoClient.close();
		
		PrintByCSCIClass();
	}
	
	/* There is very likely a nicer way to do this than by counting.
	 * 	I could not find an Collection.exists(new document(key, value) ) method in the API 
	 */
	public static boolean StudentExists(String EMPLID){
		long n = Attendance.count( new Document("EMPLID", EMPLID) );
		
		if( n == 1){
			String a = "Student found with EMPLID: " + Long.toString(n);
			System.out.println(a);
			return true;
		}else if ( n > 1 ){
			String b = "SERIOUS ERROR. More than one document with EMPLID: " + Long.toString(n);
			System.out.println(b);
			return false;
		}else{
			String c = "No student found with EMPLID: " + Long.toString(n);
			System.out.println(c);
			return false;
		}
		
	}
	
	/* Six (6) Arguments needed:
	 * 	EMPLID, First Name, Last Name, Year in School,
	 * 	Each CSCI class he/she is registered, Instructor name for each class 
	 */
	public static boolean RegisterStudent( String EMPLID /* All Strings from GUI Fields */ ){
		if ( StudentExists(EMPLID) ){
			String message = "This student has already been registered";
			System.out.println(message);
			return false;
		}
		
		Document class_doc = new Document(class_codes[0], "Poulisasis")
				.append(class_codes[1], "Sakas")
				.append(class_codes[2], "Tojeira")
				.append(class_codes[3], "Nikolaev");
		
		final String FN = "Michael";
		final String LN = "Garod";
		final String YEARINSCHOOL = "Senior";
		
		Document student = new Document("EMPLID", EMPLID)
				.append("FIRSTNAME", FN)
				.append("LASTNAME", LN)
				.append("YEARINSCHOOL", YEARINSCHOOL)
				.append("CLASSES", class_doc)
				.append("LOG", new Document() );
		
		System.out.println("Inserting new document");
		Attendance.insertOne(student);
		return true;
	}
	
	/* One (1) Argument needed:
	 *	EMPLID
	 */
	public static void SignIn(String EMPLID){
		if ( !StudentExists(EMPLID) ){
			String message = "This EMPLID: [" + EMPLID + "] has not been registered.\n"
					+ "The student must first be registered before signing in.";
			System.out.println(message);
			return;
		}
		
		String time_in = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date());
		ActiveStudents.put(EMPLID, time_in);
				
		Document queryEMPLID = new Document("EMPLID", EMPLID);
		Document update = new Document("$set", new Document("LOG."+time_in , "n/a") );
		Attendance.updateOne(queryEMPLID, update);
	}
	
	/* Four (4) Argument needed:
	 *	EMPLID, Topics discussed (one line), Level of Learning, Tutor Name
	 */
	public static void SignOut(String EMPLID /* All Strings from GUI Fields */){
		if ( !ActiveStudents.containsKey(EMPLID) ){
			String message = "This student was never signed in. Cannot sign out.";
			System.out.println(message);
			return;
		}
		
		String time_in = ActiveStudents.get(EMPLID);
		ActiveStudents.remove(EMPLID);
		String time_out = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date());
		
		final String TOPICSDISCUSSED = "Recursion";
		final String LEVELOFLEARNING = "4";
		final String TUTOR = "Garod";
		
		Document time_doc = new Document()
							.append("TIMEOUT", time_out)
							.append("TOPICSDISCUSSED", TOPICSDISCUSSED)
							.append("LEVELOFLEARNING", LEVELOFLEARNING)
							.append("TUTOR", TUTOR);
		
		Document queryEMPLID = new Document("EMPLID", EMPLID);
		Document update = new Document("$set", new Document( "LOG."+time_in , time_doc) );
		Attendance.updateOne(queryEMPLID, update);
	}
	
	/* This method may be called if a student forgets to sign out. There is no
	 * 	need to incorrectly sign out with the wrong sign out time.
	 *  The Mongo document was already constructed in SignIn(EMPLID).
	 *  There is no need to update the document. 
	 */
	public static void VoidSignOut(String EMPLID){
		ActiveStudents.remove(EMPLID);
	}
	
	public static void PrintByCSCIClass(){
		for (String csci_class : class_codes){
			FindIterable<Document> iterable = Attendance.find(new Document("CLASSES", csci_class));
			System.out.println("\nList of Students in " + csci_class);
			System.out.println("------------------------------------------");
			
			iterable.forEach((Block<Document>) document -> {
			    System.out.println(document);
			});
//					new Block<Document>() {
//			    @Override
//			    public void apply(final Document document) {
//			        System.out.println(document);
//			    }
//			}
//			);
//			
		}
	}
}