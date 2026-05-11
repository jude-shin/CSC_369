import java.io.*;
import org.apache.log4j.Logger;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.Reducer.*;

public class SecondReducer extends Reducer<PairOfStrings, PairOfStrings, NullWritable, Text> {

	@Override
	public void reduce(PairOfStrings key, Iterable<PairOfStrings> values, Context context) 
		throws IOException, InterruptedException {

		int i = 0;
		for (PairOfStrings value : values) {
			// // check for the first item
			// if (i == 0 && "sale".equals(value.getRightElement().toString())) {
			// 	saleInfo = value.getLeftElement().toString();
			// }
	
			// // check for the second item
			// if (i == 1 && "lineItem".equals(value.getRightElement().toString())) {
			// 	lineItemInfo = value.getLeftElement().toString();
			// 	break;
			// }
	
			// // increment the counter
			// i++;

			Text out = new Text(
					key.getLeftElement() + ", " +
					key.getRightElement() + ", " + 
					value.getLeftElement() + ", " +
					value.getRightElement()
					);
			context.write(NullWritable.get(), out);
		}

		// // Concatinate all of the data together!
		// if (saleInfo != null && lineItemInfo != null) {
		// 	// The output will be in the form (date, storeId, productId, quantity)
		// 	Text out = new Text(saleInfo + ", " + lineItemInfo);
		// 	context.write(NullWritable.get(), out);
		// }
	}
}
