import java.io.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.Mapper.*;

public class SSPartitioner extends Partitioner<StudentTripple, Text> {

	@Override
	public int getPartition(
			StudentTripple student, 
			Text values, 
			int numberOfPartitions) {
	
		// We are grouping by the name, so we also partition by the name
		return Math.abs(student.getName().hashCode() % numberOfPartitions);
	}
}
