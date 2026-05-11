import java.io.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.Reducer.*;
import java.util.TreeSet;

public class FirstReducer extends Reducer<PairOfStrings, PairOfStrings, PairOfStrings, PairOfStrings> {
	@Override
	public void reduce(PairOfStrings key, Iterable<PairOfStrings> values, Context context) 
		throws IOException, InterruptedException {

		// For every line that is given to one reduce node
		for (PairOfStrings value : values) {
			String line = value.toString().trim();

			// Split based on a comma
			String[] tokens = line.split(",");

			// If there is a first item, explore
			if (values.hasNext()) {
				PairOfStrings firstSale = values.next();
				
				// Make sure it is of type "sale"
				// If there is a second associated item
				if (firstSale.getRightElement() == "sale" && values.hasNext()) {
					PairOfStrings secondLineItem = values.next();
						
					// Make sure it is of type "lineItem"
					if (firstSale.getRightElement() == "lineItem") {
						// Concatinate all of the data together!
						context.write(NullWritable, firstSale.getLeftElement() + ", " + secondLineItem.getLeftElement);
					}
				}
			}

		}
	}
}
