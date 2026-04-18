import java.io.*;
import org.apache.hadoop.util.*;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.output.*;
import org.apache.hadoop.mapreduce.lib.input.*;

public class GradePair implements Writable {
	// Holds a grade and the course. No need for any comparisons. 
	private final Text grade = new Text(); 
	private final Text course = new Text();

	// Empty constructor to satisfy the Java gods
	public GradePair() { 
	}
	
	// Constructor that will be used to populate the data
	public GradePair(String grade, String course) {
		this.grade.set(grade);
		this.course.set(course);
	}
	
	// How to write the data to disk (note the order is same as read)
	@Override
	public void write(DataOutput out) throws IOException{
		grade.write(out);
		course.write(out);
	}
	
	// How to read the data from disk (note the order is same as write)
	@Override
	public void readFields(DataInput in) throws IOException {
		grade.readFields(in);
		course.readFields(in);
	}

	// Getter for the grade
	public Text getGrade() {
		return this.grade;
	}

	// Getter for the course
	public Text getCourse() {
		return this.course;
	}

	// Hopefully this makes things easier
	@Override
	public String toString() {
		return "(" + grade.toString() + course.toString() + ")";
	}
}
