import java.io.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.Mapper.*;

public class LeftJoinSaleMapper 
	extends Mapper<LongWritable, Text, PairOfStrings, PairOfStrings> {

	@Override
	public void map(LongWritable key, Text value, Context context)
		throws IOException, InterruptedException {
		
		// The full line from the input file
		String line = value.toString().trim();

		// Split based on a comma
		String[] tokens = line.split(",");

		// Parse out the salesId, date, time, storeId, customerId 
		String saleId = tokens[0].trim();
		String date = tokens[1].trim();
		// String time = tokens[2].trim();
		String storeId = tokens[3].trim();
		//int customerId = Integer.parseInt(tokens[4].trim());
	
		// The order doesn't matter... I believe it is a one to one relationship, 
		// where there is only one line item for one sales item, and vice versa
		// So we will just secondary group and sort this first 
		// ((salesId, "1",) , ("date, storeId", "sale"))

		// pair of string b/c I am lazy... It will still secondary stort correct
		PairOfStrings k = new PairOfStrings(new Text(saleId), new Text("1"));

		String valueLeft = date + ", " + storeId;
		PairOfStrings v = new PairOfStrings(new Text(valueLeft), new Text("sale"));

		context.write(k, v); 
	}
}
