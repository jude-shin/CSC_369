import java.io.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.Mapper.*;

public class LeftJoinLineItemMapper 
	extends Mapper<LongWritable, Text, PairOfStrings, PairOfStrings> {

	@Override
	public void map(LongWritable key, Text value, Context context)
		throws IOException, InterruptedException {
		
		// The full line from the input file
		String line = value.toString().trim();

		// Split based on a comma
		String[] tokens = line.split(",");

		// Parse out the lineItemId, salesId, productId, and quantity
		// int lineItemId = Integer.parseInt(tokens[0].trim());
		int salesId = Integer.parseInt(tokens[1].trim());
		int productId = Integer.parseInt(tokens[2].trim());
		int quantity = Integer.parseInt(tokens[3].trim());
	
		// The order doesn't matter... I believe it is a one to one relationship, 
		// where there is only one line item for one sales item, and vice versa
		// So we will just secondary group and sort this first, and the sale second
		// ((salesId, "1",) , ("productId, quantity", "lineItem"))

		// pair of string b/c I am lazy... It will still secondary stort correct
		PairOfStrings k = new PairOfStrings(salesId, "1");	

		String valueLeft = productId + ", " + quantity;
		PairOfStrings v = new PairOfStrings(valueLeft, "lineItem");

		context.write(k, v); 
	}
}
