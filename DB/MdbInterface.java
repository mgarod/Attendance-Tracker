package DB;

import static com.mongodb.client.model.Filters.exists;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.stream.Collectors;

import Information.*;
import com.mongodb.BasicDBList;
import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;

public class MdbInterface {
	/***  Data Members ***/
    final private MongoClient mongoClient;
    final private MongoDatabase database;
    private MongoCollection<Document> Attendance;
    private HashMap<Integer, String> ActiveStudents;

	final String semester = "FALL2016";

    public MdbInterface() {
        mongoClient = new MongoClient("localhost", 27017);
        database = mongoClient.getDatabase("local");
        Attendance = database.getCollection(semester);
        ActiveStudents = new HashMap<>();
    }

	/*** Public Methods ***/
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

	public boolean registerStudent(final Student S){
		if ( studentExists(S.getEmplId()) ){
            throw new IllegalArgumentException(S.getEmplId() + " has already been registered");
		}
		
		Document class_doc = makeDocFromClasses(S.getCurrentClasses());
		
		Document student = new Document("EMPLID", S.getEmplId())
				.append("FIRSTNAME", S.getFirstName())
				.append("LASTNAME", S.getLastName())
				.append("YEARINSCHOOL", S.getYear().name())
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

		Document time_doc = new Document()
				.append("TIMEOUT", time_out)
				.append("TUTOR", sod.getTutor().toString());

		// If not in 135, then Topics discussed will be be null. Do not insert empty list into DB.
		if (sod.getTopics() != null){
			BasicDBList topicList = new BasicDBList();
			for (Topics135 topic : sod.getTopics())
				topicList.add(topic.toString());
			time_doc.append("TOPICSDISCUSSED", topicList);
		}

		// If not in 135, then Level of Learning will be null. Do not insert -1 value in to DB.
		if (sod.getLevelOfLearning() != -1) {
			time_doc.append("LEVELOFLEARNING", sod.getLevelOfLearning());
		}

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

		String fn = S.get("FIRSTNAME", String.class);
		String ln = S.get("LASTNAME", String.class);
		ClassYear year = ClassYear.getClassYear(S.get("YEARINSCHOOL", String.class));

		return new Student(emplId, fn, ln, year);
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

