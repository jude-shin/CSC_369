import java.io.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.Reducer.*;


public class MyReducer 
	extends Reducer<Text, IntWritable, Text, IntWritable> {

	@Override
	public void reduce(Text date, Iterable<IntWritable> temps, Context context) 
		throws IOException, InterruptedException {

		int max = temps.next();
	
		// Get the maximum temperature over the entire iterable
		for (IntWritable t : temps) {
			max = Integer.max(max, t.get());
		}

		context.write(date, new IntWritable(max));
	}
}
