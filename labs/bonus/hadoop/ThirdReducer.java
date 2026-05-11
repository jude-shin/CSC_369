import java.io.*;
import org.apache.log4j.Logger;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.Reducer.*;

public class ThirdReducer extends Reducer<PairOfStrings, PairOfStrings, NullWritable, Text> {

	@Override
	public void reduce(PairOfStrings key, Iterable<PairOfStrings> values, Context context) 
		throws IOException, InterruptedException {

		String nameCity = "";

		int i = 0;
		for (PairOfStrings value : values) {
			// check for the first item to be a product
			if (i == 0) {
				if ("store".equals(value.getRightElement().toString())) {
					nameCity = value.getLeftElement().toString();
				}
				else {
					// Don't write anything if there is no product before it
					return;
				}
			}
	
			// check for the other items to be of a diff type
			if (i > 0 && "second".equals(value.getRightElement().toString())) {
				// (storeId, name, city, price, date, quantity)
				Text out = new Text(key.getLeftElement() + ", " + nameCity + ", " + value.getLeftElement());
				context.write(NullWritable.get(), out);
			}
	
			// increment the counter
			i++;
		}
	}
}
