import java.io.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.Mapper.*;

public class MyMapper 
	extends Mapper<LongWritable, Text, NullWritable, IntWritable> {

	@Override
	public void map(LongWritable key, Text value, Context context) 
		throws IOException, InterruptedException {

		// The string in one line of the input file
		String fullLine = value.toString().trim();

		// Split the numbers by spaces
		String[] tokens = fullLine.split(" ");

		int count = 0;	// The number of integers that we "accepted"
		for (String chunk : tokens) {
			int num = Integer.parseInt(chunk); // The integer version of the string
			if (num%3 == 0) { // Only include those that are divisible by 3
				count++;
			}

			context.write(
					NullWritable.get(),			// Nothing...
					new IntWritable(count));	// The number of valid integers in this line
		}
	}
}
