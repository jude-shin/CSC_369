import java.io.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.Reducer.*;

public class FirstReducer extends Reducer<PairOfStrings, PairOfStrings, NullWritable, Text> {
	@Override
	public void reduce(PairOfStrings key, Iterable<PairOfStrings> values, Context context) 
		throws IOException, InterruptedException {

		String saleInfo = null;
		String lineItemInfo = null;

		int i = 0;
		for (PairOfStrings value : values) {
			// check for the first item
			if (i == 0 && value.getRightElement().toString() == "sale") {
				saleInfo = value.getLeftElement().toString();
			}
	
			// check for the second item
			if (i == 1 && value.getRightElement().toString() == "lineItem") {
				lineItemInfo = value.getLeftElement().toString();
			}
	
			// increment the values
			i++;
			
			Text out = new Text(key.getLeftElement() + ", " + key.getRightElement() + ", " + value.getLeftElement() + ", " + value.getRightElement());
			context.write(NullWritable.get(), out);
		}

		// Concatinate all of the data together!
		if (saleInfo != null && lineItemInfo != null) {
			Text out = new Text(saleInfo + ", " + lineItemInfo);
			context.write(NullWritable.get(), out);
		}
	}
}
