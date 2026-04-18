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

	// Will be sorted first by name, and then by grade (id needed for printing)
	private final Text name = new Text(); 
	private final IntWritable id = new IntWritable();
	private final Text grade = new Text();

	// Empty constructor to satisfy the Java gods
	public StudentTripple() { 
	}
	
	// Constructor that will be used to populate the data
	public StudentTripple(String name, int id, String grade) {
		this.name.set(name);
		this.id.set(id);
		this.grade.set(grade);
	}
	
	// How to write the data to disk (note the order is same as read)
	@Override
	public void write(DataOutput out) throws IOException{
		name.write(name);
		id.write(id);
		grade.write(grade);
	}
	
	// How to read the data from disk (note the order is same as write)
	@Override
	public void readFields(DataInput in) throws IOException {
		name.readFields(in);
		id.readFields(in);
		grade.readFields(in);
	}

	// How to sort the key (first the name, and then the grade)
	@Override
	public int compareTo(StudentTripple trip) {
		// If the names are the same, then compare grades
		if(name.compareTo(trip.getName()) == 0){
			return grade.compareTo(trip.getGrade());
		}
		
		// Otherwise, just compare the difference in the names
		return name.compareTo(trip.getName());
	}
	
	// Getter for the name
	public Text getName() {
		return this.name;
	}       

	// Getter for the grade
	public IntWritable getId() {
		return this.id;
	}
	
	// Getter for the id
	public Text getGrade() {
		return this.grade;
	}
}

