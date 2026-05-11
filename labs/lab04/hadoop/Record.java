import java.io.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.Mapper.*;

public class Record implements Comparable <Record> {
	// An element of the inital input dataset
	private int id;
	private String name;
	private double price;

	public Record(String id, String name, String city, String price){
		this.id = Integer.getInteger(id);
		this.name = name;
		this.price = price;
	}

	@Override
	public String toString(){
		return id + ", " + name + ", " + price;
	}
	
	// The price should be compared to in decending order
	@Override
	public int compareTo(Record other){
		if(this.price > other.price) {
			return -1;
		} 

		if(this.price < other.price){
			return 1;
		}
	
		// Breaks any ties between the records that have the same id
		if (this.id > other.id) return  1 ;
		if (this.id < other.id) return  -1 ;

		// I belive that the ids are unique, however if it is not, we could do one 
		// more comparison to distinguish the name as well
	
		return 0;		// The records are the same
	}
}
