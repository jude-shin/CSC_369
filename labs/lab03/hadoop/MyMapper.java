import java.io.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.Mapper.*;

public class MyMapper 
	extends Mapper<LongWritable, Text, Text, Text> {

	@Override
	public void map(LongWritable key, Text value, Context context) 
		throws IOException, InterruptedException {

		// The string in one line of the input file
		String fullLine = value.toString().trim();

		// Split the values by the commas
		String[] tokens = fullLine.split(",");
	
		// Sales file: ID, date, time, storeID, customerID
		if (tokens.length != 5) {
			return;
		}
		
		// Extract the data from the line
		String id = tokens[0].trim();
		String date = tokens[1].trim();
		String time = tokens[2].trim();
	
		// The key will be our custom key which will be sorted by date, then time
		// Nothing special needs to happen with the value, it is just the date of 
		// the sale and the id of that sale.
		context.write(new DateTime(date, time), new Text(date + " " + id));
	}
}
