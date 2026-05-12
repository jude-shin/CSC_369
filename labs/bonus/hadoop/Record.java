import java.io.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.Mapper.*;

public class Record implements Comparable <Record> {
	private int id;
	private String name;
	private String city;
	private double total;

	public Record(String id, String name, String city, String total){
		this.id = Integer.parseInt(id);
		this.name = name;
		this.city = city;
		this.total = Double.parseDouble(total);
	}

	// Called when printint the final result
	@Override
	public String toString() {
		return "(" + name + ", " + city + ", " + total + ")";
	}
	
	// The price should be compared to in decending order
	@Override
	public int compareTo(Record other){
		// First, we care about the sorting order by price. 
		if(this.total > other.total) return -1;
		if(this.total < other.total) return 1;
	
		// Breaks any ties between the records that have the same id
		if (this.id > other.id) return  1 ;
		if (this.id < other.id) return  -1 ;

		return 0;		// The records are the same
	}
}
