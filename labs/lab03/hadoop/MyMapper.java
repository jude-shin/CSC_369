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
	
		// The key that is to be written (will be sorted on this)
		String key = date + " " + time;
		// The final output based on the question
		String value = time + " " + id;

		context.write(new Text(key), new Text(value));
	}
}
