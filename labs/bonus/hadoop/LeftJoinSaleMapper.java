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
		int saleId = Integer.parseInt(tokens[0].trim());
		String date = tokens[1].trim();
		// String time = tokens[2].trim();
		int storeId = Integer.parseInt(tokens[3].trim());
		//int customerId = Integer.parseInt(tokens[4].trim());
	
		// The order doesn't matter... I believe it is a one to one relationship, 
		// where there is only one line item for one sales item, and vice versa
		// So we will just secondary group and sort this second, and the other second 
		// ((salesId, "2",) , ("date, storeId", "sale"))

		// pair of string b/c I am lazy... It will still secondary stort correct
		PairOfStrings k = new PairOfStrings(saleId, "2");	

		String valueLeft = date + ", " + storeId;
		PairOfStrings v = new PairOfStrings(valueLeft, "sale");

		context.write(k, v); 
	}
}
