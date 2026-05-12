import java.io.*;
import org.apache.log4j.Logger;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.Reducer.*;
import java.util.TreeSet;

public class TopReducer extends Reducer<CompositeKey, Text, NullWritable, Text> {
	private int n = MyDriver.DEFAULT_N;
	private TreeSet<Record> top = new TreeSet<>();

	@Override
	public void reduce(CompositeKey key, Iterable<Text> values, Context context) 
		throws IOException, InterruptedException {
		// Composite key (month, total)
		// Value (id, name, city, total)

		for (Text value : values) {

			// The full line from the input file
			String line = value.toString().trim();

			// Split based on a comma
			String[] tokens = line.split(",");

			// Parse out the id, name, and the price
			String id = tokens[0].trim();
			String name = tokens[1].trim();
			String city = tokens[2].trim();
			String total = tokens[3].trim();

			// Add the element to the sorted running set
			top.add(new Record(id, name, city, total));

			// Keep only top n
			if (top.size() > n) {
				top.remove(top.last());
			}
		}

		// // Write the yearMonth first
		String output = key.getYearMonth() + ", ";

		// Loop through all of the top 10 items
		int i = 1;
		int stop = top.size();
		for(Record r : top){
			// Will be in the same form as the input ("id, name, price")
			output += r.toString();

			if (i != stop) {
				output += ", ";
			}

			i++;
		}

		// Write it all out!
		context.write(NullWritable.get(), new Text(output));
	}

	// Run before the reduce stage
	@Override
	protected void setup(Context context) throws IOException, InterruptedException {
		// Either sets up the global variable from the given context, or the default
		this.n = context.getConfiguration().getInt("N", MyDriver.DEFAULT_N); 

		// If for some reason the top is not initalized, initalize it to an empty set
		if (this.top == null) {
			this.top = new TreeSet<Record>();
		}
	}
}
