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
		// The parentheticals are already added from the toString() function
		Text v = new Text(String.join(", ", grades));
	
		context.write(k, v);
	}
}
