import java.io.*;
import org.apache.log4j.Logger;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.Reducer.*;

public class ThirdReducer extends Reducer<PairOfStrings, PairOfStrings, NullWritable, Text> {

	@Override
	public void reduce(PairOfStrings key, Iterable<PairOfStrings> values, Context context) 
		throws IOException, InterruptedException {

		int i = 0;
		for (PairOfStrings value : values) {
			//// check for the first item to be a product
			//if (i == 0) {
			//	if ("product".equals(value.getRightElement().toString())) {
			//		descriptionPrice = value.getLeftElement().toString();
			//	}
			//	else {
			//		// Don't write anything if there is no product before it
			//		return;
			//	}
			//}
	
			//// check for the other items to be of a diff
			//if (i > 0 && "first".equals(value.getRightElement().toString())) {
			//	//Text out = new Text(
			//	//		key.getLeftElement() + ", " +
			//	//		key.getRightElement() + ", " + 
			//	//		value.getLeftElement() + ", " +
			//	//		value.getRightElement()
			//	//		);

			//	// (description, price, date, storeId, quantity)
			//	Text out = new Text(descriptionPrice + ", " + value.getLeftElement());
			//	context.write(NullWritable.get(), out);
			//}
	
			// increment the counter
			i++;

			Text out = new Text(
					key.getLeftElement() + ", " +
					key.getRightElement() + ", " + 
					value.getLeftElement() + ", " +
					value.getRightElement()
					);
	
			// ((storeId, "1",) , ("storeName, city", "store"))
			// ((storeId, "2",) , ("price, date, quantity", "second"))
			context.write(NullWritable.get(), out);
		}
	}
}
