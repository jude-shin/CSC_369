import java.io.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.Mapper.*;

public class MyMapper 
	extends Mapper<LongWritable, Text, StudentTripple, Text> {

	@Override
	public void map(LongWritable key, Text value, Context context) 
		throws IOException, InterruptedException {

		// The string in one line of the input file
		String fullLine = value.toString().trim();

		// Split the values by the commas
		String[] tokens = fullLine.split(",");
	
		// Input File: name, id, grade, course
		if (tokens.length != 4) {
			return;
		}
		
		// Extract the data from the line
		String name = tokens[0].trim();
		String id = tokens[1].trim();
		String grade = tokens[2].trim();
		String course = tokens[3].trim();
	
		// The key holds the name (and the id), and the grade for sorting
		Writable k = new StudentTripple(name, Integer.parseInt(id), grade);
		// The value holds the grade (again) and the course name
		Writable v = new GradePair(grade, course);
	
		context.write(k, v);
	}
}
