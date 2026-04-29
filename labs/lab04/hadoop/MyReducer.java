import java.io.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.Reducer.*;
import java.util.TreeSet;

public class MyReducer extends Reducer<NullWritable, Text, NullWritable, Text> {
	private int n = MyDriver.DEFAULT_N;
	private SortedSet<Record> top = new TreeSet<>();

	@Override
	public void reduce(NullWritable key, Iterable<Text> values, Context context) 
		throws IOException, InterruptedException {
		// For every line that is given to one reduce node
		for (Text value : values) {
			// The full line from the input file
			String line = value.toString().trim();

			// Split based on a comma
			String[] tokens = line.split(",");

			// Parse out the id, name, and the price
			int id = Integer.parseInt(tokens[0].trim());
			String name = tokens[1].trim();
			double price = Double.parseDouble(tokens[2].trim());

			// Add the element to the sorted running set
			top.add(new Record(id, name, price));

			// Keep only top n
			if (top.size() > n) {
				top.remove(top.last());
			}
		}
	
		// Finally, write the global top n records as the output
		for(Record r : top){
			// Will be in the same form as the input ("id, name, price")
			context.write(NullWritable.get(), new Text(r.toString()));
		}
	}

	// Run before the reduce stage
	@Override
	protected void setup(Context context) throws IOException, InterruptedException {
		// Either sets up the global variable from the given context, or the default
		this.n = context.getConfiguration().getInt("N", DEFAULT_N); 

		// If for some reason the top is not initalized, initalize it to an empty set
		if (this.top == null) {
			this.top = new TreeSet<Record>();
		}
	}
}
