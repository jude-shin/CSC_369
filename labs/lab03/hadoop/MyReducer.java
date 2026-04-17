import java.io.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.Reducer.*;


public class MyReducer 
	extends Reducer<DateTimePair, Text, Text, Text> {

	@Override
	public void reduce(Text key, Iterable<IntWritable> temps, Context context) 
		throws IOException, InterruptedException {

		// The first one must be the maximum
		int max = temps.iterator().next().get();
	
		// Get the maximum temperature over the entire iterable
		for (IntWritable t : temps) {
			max = Integer.max(max, t.get());
		}

		context.write(key, new IntWritable(max));
	}
}
