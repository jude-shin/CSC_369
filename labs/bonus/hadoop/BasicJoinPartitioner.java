import java.io.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.Mapper.*;

public class BasicJoinPartitioner 
	extends Partitioner<PairOfStrings, PairOfStrings> {

	@Override
	public int getPartition(PairOfStrings keyPair, PairOfStrings valuePair, int numberOfPartitions) {

		// TODO: I think this might be wrong???
		// String totalString = keyPair.toString() + valuePair.toString();

		// Only compare the left element for partitioning
		String totalString = keyPair.getLeftElement().toString();
		
		return Math.abs(totalString.hashCode() % numberOfPartitions);
	}
}
