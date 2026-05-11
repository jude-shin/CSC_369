import java.io.*;
import org.apache.log4j.Logger;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.Reducer.*;

public class FirstReducer extends Reducer<PairOfStrings, PairOfStrings, NullWritable, Text> {
	private static final Logger THE_LOGGER = Logger.getLogger(MyDriver.class);

	@Override
	public void reduce(PairOfStrings key, Iterable<PairOfStrings> values, Context context) 
		throws IOException, InterruptedException {

		String saleInfo = null;
		String lineItemInfo = null;

		int i = 0;
		for (PairOfStrings value : values) {

			THE_LOGGER.info("\t---> (" + key.getLeftElement() + "): iteration (" + i + ")\n");

			// check for the first item
			if (i == 0 && "sale".equals(value.getRightElement().toString())) {
				THE_LOGGER.info("\t---> fornd the <sale> element!\n");
				saleInfo = value.getLeftElement().toString();
			}
	
			// check for the second item
			if (i == 1 && "lineItem".equals(value.getRightElement().toString())) {
				THE_LOGGER.info("\t---> fornd the <lineItem> element!\n");
				lineItemInfo = value.getLeftElement().toString();
			}
	
			// increment the values
			i++;
			
			// Text out = new Text(key.getLeftElement() + ", " + key.getRightElement() + ", " + value.getLeftElement() + ", " + value.getRightElement());
			// context.write(NullWritable.get(), out);
		}

		// Concatinate all of the data together!
		if (saleInfo != null && lineItemInfo != null) {
			Text out = new Text(saleInfo + ", " + lineItemInfo);
			context.write(NullWritable.get(), out);
		}
	}
}
