import java.io.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.Reducer.*;

public class FirstReducer extends Reducer<PairOfStrings, PairOfStrings, NullWritable, Text> {
	@Override
	public void reduce(PairOfStrings key, Iterable<PairOfStrings> values, Context context) 
		throws IOException, InterruptedException {

		PairOfStrings firstSale = null;
		PairOfStrings secondLineItem = null;

		int i = 0;
		for (PairOfStrings value : values) {
			// // check for the first item
			// if (i == 0 && value.getRightElement().toString() == "sale") {
			// 	firstSale = value;
			// }
	
			// // check for the second item
			// if (i == 1 && value.getRightElement().toString() == "lineItem") {
			// 	secondLineItem = value;
			// 	break;
			// }
	
			// // increment the values
			// i++;
			
			Text out = new Text(firstSale.getLeftElement() + ", " + firstSale.getRightElement() + ", " + secondLineItem.getLeftElement() + ", " + secondLineItem.getRightElement());
			context.write(NullWritable.get(), out);
		}

		// // Check my sanity
		// if (i > 1 || firstSale == null || secondLineItem == null) {
		// 	throw new IOException("Something wrong with the FirstReducer (probably the input from the mappers)");
		// }

		// // Concatinate all of the data together!
		// Text out = new Text(firstSale.getLeftElement() + ", " + secondLineItem.getLeftElement());
		// context.write(NullWritable.get(), out);

	}
}
