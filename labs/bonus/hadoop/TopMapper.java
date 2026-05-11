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

		// Parse out the TODO
		String date = tokens[0].trim();

		// ====================================
		// TODO: multiply the quanity and price to make a total
		float total = 0.0;

		// TODO: extract the date
		String month = "";
	
		// Composite key (month, total)
		PairOfStrings k = new PairOfStrings(new Text(month), new Text(Float.toString(total)));
	
		// Value (name, city, total)
		PairOfStrings v = new Text(id + ", " + name + ", " + city + ", " + total);

		context.write(k, v); 
	}
}
