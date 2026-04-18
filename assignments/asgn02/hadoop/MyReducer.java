import java.io.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.Reducer.*;


public class MyReducer 
	extends Reducer<StudentTripple, GradePair, Text, Text> {

	@Override
	public void reduce(StudentTripple student, Iterable<GradePair> grades, Context context) 
		throws IOException, InterruptedException {

		// The output key will be the student's name and id
		Text k = new Text(student.getName() + ", " + student.getId());

		// For every one of the grades, join them with a ", "
		String value = "";
		boolean isFirst = true;
		for (GradePair g : grades) {
			if (!isFirst) {
				value = value + ", ";
			}

			value = value + g.toString();
			isFirst = false;
		}
		Text v = new Text(value);
	
		context.write(k, v);
	}
}
