import java.io.*;
import org.apache.hadoop.util.*;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.output.*;
import org.apache.hadoop.mapreduce.lib.input.*;

public class StudentTripple 
	implements Writable, WritableComparable<StudentTripple> {

	// Will be sorted first by name (as well as the id), and then by grade
	private final Text nameId = new Text(); 
	private final Text grade = new Text();

	// Empty constructor to satisfy the Java gods
	public StudentTripple() { 
	}
	
	// Constructor that will be used to populate the data
	public StudentTripple(String name, int id, String grade) {
		this.nameId.set(name + ", " + id);
		this.grade.set(grade);
	}
	
	// How to write the data to disk (note the order is same as read)
	@Override
	public void write(DataOutput out) throws IOException{
		nameId.write(out);
		grade.write(out);
	}
	
	// How to read the data from disk (note the order is same as write)
	@Override
	public void readFields(DataInput in) throws IOException {
		nameId.readFields(in);
		grade.readFields(in);
	}

	// How to sort the key (first the name, and then the grade)
	@Override
	public int compareTo(StudentTripple trip) {
		// If the names are the same, then compare grades
		if(nameId.compareTo(trip.getNameId()) == 0){
			return grade.compareTo(trip.getGrade());
		}
		
		// Otherwise, just compare the difference in the names
		return nameId.compareTo(trip.getNameId());
	}
	
	// Getter for the name and id combo
	public Text getNameId() {
		return this.nameId;
	}       

	// Getter for the grade
	public Text getGrade() {
		return this.grade;
	}

	// I only really care about the name and the id being printed
	// And the combo 
	@Override
	public String toString() {
		return this.nameId.toString();
	}
}

