import java.io.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.Mapper.*;

public class TopMapper 
	extends Mapper<LongWritable, Text, PairOfStrings, PairOfStrings> {

	@Override
	public void map(LongWritable key, Text value, Context context)
		throws IOException, InterruptedException {
		
		// The full line from the input file
		String line = value.toString().trim();

		// Split based on a comma
		String[] tokens = line.split(",");

		// Parse out the information from the third join job
		// storeId, name, city, price, date, quantity
		String id = tokens[0].trim();
		String name = tokens[1].trim();
		String city = tokens[2].trim();
		String price = tokens[3].trim();
		String date = tokens[4].trim();
		String quanity = tokens[5].trim();

		// ====================================
		// Multiply the quanity and price to make a total
		float total = Float.getFloat(price) * Integer.getInteger(quanity);

		// Parse out dates 
		String year = date.trim().split("/")[0].trim();
		String month = date.trim().split("/")[1].trim();
		String yearMonth = year + "-" + month;
	
		// Composite key (yearMonth, total)
		PairOfStrings k = new PairOfStrings(new Text(yearMonth), new Text(Float.toString(total)));
	
		// Value (id, name, city, total)
		PairOfStrings v = new Text(id + ", " + name + ", " + city + ", " + total);

		context.write(k, v); 
	}
}
