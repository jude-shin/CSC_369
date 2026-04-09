import java.io.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.Mapper.*;

public class Mapper 
	extends Mapper<LongWritable, Text, Text, NullWritable> {
	@Override
	public void map(LongWritable key, Text value, Context context) 
		throws IOException, InterruptedException {

		// The string in one line of the input file
		String fullLine = value.toString().trim();

		// Make sure there are only 5 elements in a line
		// ID, date, time, storeID, customerID
		String[] tokens = fullLine.split(" ");
		if (tokens.length != 5) {
			return;
		}

		context.write(
				new Text(tokens[1]),	// The date as yyyy/mm/dd
				new NullWritable());	// We don't really care...
	}
}
