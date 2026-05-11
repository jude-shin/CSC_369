import java.io.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.Mapper.*;

public class LeftJoinSecondMapper 
	extends Mapper<LongWritable, Text, PairOfStrings, PairOfStrings> {

	@Override
	public void map(LongWritable key, Text value, Context context)
		throws IOException, InterruptedException {
		
		// The full line from the input file
		String line = value.toString().trim();

		// Split based on a comma
		String[] tokens = line.split(",");

		// Parse out the price, date, storeId, quantity
		String price = tokens[0].trim();
		String date = tokens[1].trim();
		String storeId = tokens[2].trim();
		String quantity = tokens[3].trim();
	
		// So we will just secondary group and sort this second
		// ((storeId, "2",) , ("price, date, quantity", "second"))

		// pair of string b/c I am lazy... It will still secondary stort correct
		PairOfStrings k = new PairOfStrings(new Text(storeId), new Text("2"));

		String valueLeft = price + ", " + date + ", " + quantity;
		PairOfStrings v = new PairOfStrings(new Text(valueLeft), new Text("second"));

		context.write(k, v); 
	}
}
