import java.io.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.Mapper.*;

public class Record implements Comparable <Record> {
	private String storeName;
	private String storeCity;
	private String storeId;
	private double total;

	public Record(String storeId, String storeName, String storeCity, double total){
		this.storeId = storeId;
		this.storeName = storeName;
		this.storeCity = storeCity;
		this.total = total;
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
		if(this.total > other.total) return -1;
		if(this.total < other.total) return 1;
	
		// Breaks any ties between the records that have the same id
		if (this.storeId > other.storeId) return  1 ;
		if (this.storeId < other.storeId) return  -1 ;

		return 0;		// The records are the same
	}
}
