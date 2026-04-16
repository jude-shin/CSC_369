import java.io.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.Mapper.*;

public class MyMapper 
	extends Mapper<LongWritable, Text, Text, IntWritable> {

	@Override
	public void map(LongWritable key, Text value, Context context) 
		throws IOException, InterruptedException {

		// The string in one line of the input file
		String fullLine = value.toString().trim();

		// Split the dates from the temperature
		String[] tokens = fullLine.split(" ");
	
		String date = tokens[0];									// The date
		int temp = Integer.parseInt(tokens[1]);		// The temperature for that day

		context.write(new Text(date), new IntWritable(temp));
	}
}
