import java.io.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.Mapper.*;

public class Record implements Comparable <Record> {
	private String month;
	private String storeName;
	private int storeId;
	private String storeCity;
	private double totalSales;

	public Record(String month, String storeName, int storeId, String storeCity, double totalSales){
		this.month = month;
		this.storeName = storeName;
		this.storeId = storeId;
		this.storeCity = storeCity;
		this.totalSales = totalSales;
	}

	// Called when printint the final result
	@Override
	public String toString(){
		return "(" + storeName + ", " + storeCity + ", " + totalSales + ")";
	}
	
	// The price should be compared to in decending order
	@Override
	public int compareTo(Record other){
		// First, we care about the sorting order by price. 
		if(this.price > other.price) return -1;
		if(this.price < other.price) return 1;
	
		// Breaks any ties between the records that have the same id
		if (this.id > other.id) return  1 ;
		if (this.id < other.id) return  -1 ;

		// I belive that the ids are unique, however if it is not, we could do one 
		// more comparison to distinguish the names or the cities as well
	
		return 0;		// The records are the same
	}
}
