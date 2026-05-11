import java.io.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.Mapper.*;

public class LeftJoinStoreMapper 
	extends Mapper<LongWritable, Text, PairOfStrings, PairOfStrings> {

	@Override
	public void map(LongWritable key, Text value, Context context)
		throws IOException, InterruptedException {
		
		// The full line from the input file
		String line = value.toString().trim();

		// Split based on a comma
		String[] tokens = line.split(",");

		// Parse out the storeId, storeName, address, city, ZIP, state, phoneNumber
		String storeId = tokens[0].trim();
		String storeName = tokens[1].trim();
		// String address = tokens[2].trim();
		String city = tokens[3].trim();
		// String zip = tokens[4].trim();
		// String state = tokens[5].trim();
		// String phone = tokens[6].trim();

		// So we will just secondary group and sort this first 
		// ((storeId, "1",) , ("storeName, city", "store"))

		// pair of string b/c I am lazy... It will still secondary stort correct
		PairOfStrings k = new PairOfStrings(new Text(storeId), new Text("1"));	

		String valueLeft = storeName + ", " + city;
		PairOfStrings v = new PairOfStrings(new Text(valueLeft), new Text("store"));

		context.write(k, v); 
	}
}
