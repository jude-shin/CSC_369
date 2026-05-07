import java.io.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.Mapper.*;

public class LineItemLeftJoinSalesMapper 
	extends Mapper<LongWritable, Text, NullWritable, Text> {

	private int n = MyDriver.DEFAULT_N; 

	// Only stores the top N values in main memory
	private TreeSet<Record> top = new TreeSet<Record>();

	@Override
	public void map(LongWritable key, Text value, Context context)
		throws IOException, InterruptedException {
		
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

	// Run before the mapping stage
	@Override
	protected void setup(Context context) throws IOException, InterruptedException {
		// Either sets up the global variable from the given context, or the default
		this.n = context.getConfiguration().getInt("N", MyDriver.DEFAULT_N); 

		// If for some reason the top is not initalized, initalize it to an empty set
		if (this.top == null) {
			this.top = new TreeSet<Record>();
		}
	}
	
	// Run after all of the file is parsed
	@Override
	protected void cleanup(Context context) throws IOException, InterruptedException {
		// Write N lines of the top n at this one mapp node
		for (Record r : top) {
			// Will be in the same form as the input ("id, name, price")
			context.write(NullWritable.get(), new Text(r.toString()));
		}
	}
}
