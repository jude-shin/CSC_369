import java.io.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.Reducer.*;


public class SalesReducer 
	extends Reducer<Text, NullWritable, Text, IntWritable> {

	@Override
	public void reduce(Text date, Iterable<NullWritable> nulls, Context context) 
		throws IOException, InterruptedException {
	
		// Since we are dealing with an iterable, we need to manually 
		// iterate over the entire list to find the length
		int count = 0;
		for (NullWritable n : nulls) {
			count++;
		}

		context.write(date, new IntWritable());
	}
}

