import java.io.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.Mapper.*;

public class LeftJoinFirstMapper 
	extends Mapper<LongWritable, Text, PairOfStrings, PairOfStrings> {

	@Override
	public void map(LongWritable key, Text value, Context context)
		throws IOException, InterruptedException {
		
		// The full line from the input file
		String line = value.toString().trim();

		// Split based on a comma
		String[] tokens = line.split(",");

		// Parse out the date, storeId, productId, quantity
		String date = tokens[0].trim();
		String storeId = tokens[1].trim();
		String productId = tokens[2].trim();
		String quantity = tokens[3].trim();
	
		// So we will just secondary group and sort this first 
		// ((productId, "2",) , ("date, storeId, quantity", "first"))

		// pair of string b/c I am lazy... It will still secondary stort correct
		PairOfStrings k = new PairOfStrings(new Text(productId), new Text("1"));	

		String valueLeft = date + ", " + storeId + ", " + productId;
		PairOfStrings v = new PairOfStrings(new Text(valueLeft), new Text("first"));

		context.write(k, v); 
	}
}
