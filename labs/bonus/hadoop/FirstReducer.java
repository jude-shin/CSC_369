import java.io.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.Reducer.*;
import java.util.TreeSet;

public class FirstReducer extends Reducer<PairOfStrings, PairOfStrings, NullWritable, PairOfStrings> {
	@Override
	public void reduce(PairOfStrings key, Iterable<PairOfStrings> values, Context context) 
		throws IOException, InterruptedException {

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
