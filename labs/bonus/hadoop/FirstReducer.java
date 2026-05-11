import java.io.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.Reducer.*;

public class FirstReducer extends Reducer<PairOfStrings, PairOfStrings, NullWritable, PairOfStrings> {
	@Override
	public void reduce(PairOfStrings key, Iterable<PairOfStrings> values, Context context) 
		throws IOException, InterruptedException {

		PairOfStrings firstSale = null;
		PairOfStrings secondLineItem = null;

		for (PairOfStrings value : values) {
			// check that it is a sale
			if (firstSale==null && secondLineItem==null) {
				if (value.getRightElement().toString() == "sale") {
					firstSale = value;
				}
				else {
					continue;
				}
			}
	
			// Will check the second value
			else if (secondLineItem==null) {
				// check that it is a lineItem
				if (value.getRightElement().toString() == "lineItem") {
					secondLineItem = value;
					
					// Concatinate all of the data together!
					context.write(NullWritable.get(), firstSale.getLeftElement() + ", " + secondLineItem.getLeftElement);

					return;
				}
				else {
					continue;
				}
			}
		}

	}
}
