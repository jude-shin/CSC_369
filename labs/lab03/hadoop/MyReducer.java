import java.io.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.Reducer.*;


public class MyReducer 
	extends Reducer<DateTimePair, Text, Text, Text> {

	@Override
	public void reduce(DateTimePair dateTime, Iterable<Text> timeIdPairs, Context context) 
		throws IOException, InterruptedException {
	
		// The comma separated values that hold space separated (date id) pairs.
		String value = ""; 

		// Concatinate all of our items (will be in sorted order) separating by ", "
		for (Text p : timeIdPairs) {
			value = value + timeIdPairs + ", ";
		}

		// Clean up the leftover ", " at the end of our value string (2 characters)
		value = value.substring(0, str.length()-2);

		// Write the only the date as the key, and our concatinated string
		// as the value.
		context.write(dateTime.getDate(), new Text(value));
	}
}
