import java.io.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.Mapper.*;

public class SSPartitioner 
	extends Partitioner<DateTimePair, Text> {

	@Override
	public int getPartition(
			DateTimePair pair, 
			Text values, 
			int numberOfPartitions) {

		return Math.abs(pair.getDate().hashCode() % numberOfPartitions);
	}
}
