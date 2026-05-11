import java.io.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.Mapper.*;

public class LeftJoinProductMapper 
	extends Mapper<LongWritable, Text, PairOfStrings, PairOfStrings> {

	@Override
	public void map(LongWritable key, Text value, Context context)
		throws IOException, InterruptedException {
		
		// The full line from the input file
		String line = value.toString().trim();

		// Split based on a comma
		String[] tokens = line.split(",");

		// Parse out the productId, description, price
		String productId = tokens[0].trim();
		String description = tokens[1].trim();
		String price = tokens[2].trim();
	
		// So we will just secondary group and sort this first (only one product"
		// ((productId, "1",) , ("description, price", "product"))

		// pair of string b/c I am lazy... It will still secondary stort correct
		PairOfStrings k = new PairOfStrings(new Text(productId), new Text("1"));	

		String valueLeft = description + ", " + price;
		PairOfStrings v = new PairOfStrings(new Text(valueLeft), new Text("product"));

		context.write(k, v); 
	}
}
