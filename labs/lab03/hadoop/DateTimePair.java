import java.io.IOException;
import org.apache.hadoop.util.*;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.output.*;
import org.apache.hadoop.mapreduce.lib.input.*;

public class DateTimePair
	implements Writable, WritableComparable<DateTimePair> {

	// Will be sorted first by date, and then by time
	private final Text date = new Text(); 
	private final Text time = new Text();

	// Empty constructor to satisfy the Java gods
	public DateTimePair() { 
	}
	
	// Constructor that will be used to populate the data
	public DateTimePair(String date, String time) {
		this.date.set(date);
		this.time.set(time);
	}
	
	// How to write the data to disk (note the order is same as read)
	@Override
	public void write(DataOutput out) throws IOException{
		date.write(out);
		time.write(out);
	}
	
	// How to read the data from disk (note the order is same as write)
	@Override
	public void readFields(DataInput in) throws IOException {
		date.readFields(in);
		time.readFields(in);
	}

	// How to sort the key (first the date, and then the time)
	@Override
	public int compareTo(DateTimePair pair) {
		// If the dates are the same, then compare times
		if(date.compareTo(pair.getDate()) == 0){
			return time.compareTo(pair.getTime());
		}
		
		// Otherwise, just compare the difference in the dates
		return date.compareTo(pair.getDate());
	}
	
	// Getter for the date
	public Text getDate() {
		return this.date;
	}       
	
	// Getter for the time
	public Text getTime() {
		return this.time;
	}
}

