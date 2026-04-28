import java.io.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.Mapper.*;

public class MyMapper 
	extends Mapper<LongWritable, Text, NullWritable, Text> {
	public static final int DEFAULT_N = 10;		// If no value is given
	private int n = DEFAULT_N; 

	// Only stores the top N values in main memory
	private TreeSet<Record> top = new TreeSet<Record>();

	@Override
	public void map(LongWritable key, Text value, Context context)
		throws IOException, InterruptedException {
		
		// The full line from the input file
		String line = value.toString().trim();

		// Split based on a comma
		String[] tokens = line.split(",");

		double weight = Double.parseDouble(tokens[2]);
		top.add(new Record(Integer.parseInt(tokens[0]),tokens[1],
					weight));
		//keep only top n
		if (top.size() > n) {
			top.remove(top.last());
		}
	}
}
