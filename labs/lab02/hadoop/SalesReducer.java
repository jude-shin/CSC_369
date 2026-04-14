import java.io.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.Reducer.*;


public class SalesReducer 
	extends Reducer<Text, IntWritable, Text, IntWritable> {

	@Override
	public void reduce(Text date, Iterable<IntWritable> freqs, Context context) 
		throws IOException, InterruptedException {
	
		// Add together all of the frequencies
		int count = 0;
		for (IntWritable f : freqs) {
			count+=f.get();
		}

		context.write(date, new IntWritable(count));
	}
}

