import java.io.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.Reducer.*;


public class MyReducer 
	extends Reducer<NullWritable, IntWritable, NullWritable, IntWritable> {

	@Override
	public void reduce(NullWritable nw, Iterable<IntWritable> counts, Context context) 
		throws IOException, InterruptedException {
	
		// Add together all of the counts
		int total = 0;
		for (IntWritable c : counts) {
			total+=c.get();
		}

		context.write(NullWritable.get(), new IntWritable(total));
	}
}
