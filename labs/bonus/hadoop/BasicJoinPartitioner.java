import java.io.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.Mapper.*;

public class BasicJoinPartitioner 
	extends Partitioner<PairOfStrings, PairOfStrings> {

	@Override
	public int getPartition(PairOfStrings keyPair, PairOfStrings valuePair, int numberOfPartitions) {
		String totalString = keyPair.toString() + valuePair.toString();
		return Math.abs(totalString.hashCode() % numberOfPartitions);
	}
}
