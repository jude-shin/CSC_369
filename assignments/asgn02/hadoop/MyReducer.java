import java.io.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.Reducer.*;


public class MyReducer 
	extends Reducer<StudentTripple, GradePair, NullWritable, Text> {

	@Override
	public void reduce(StudentTripple student, Iterable<GradePair> grades, Context context) 
		throws IOException, InterruptedException {
		String value = "";

		// For every one of the grades, join them with a ", "
		boolean isFirst = true;
		for (GradePair g : grades) {
			if (!isFirst) {
				value = value + ", ";
			}

			value = value + g.toString();
			isFirst = false;
		}
	
		context.write(student, new Text(value));
	}
}
