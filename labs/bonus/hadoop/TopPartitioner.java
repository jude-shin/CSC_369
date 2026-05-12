import java.io.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.Mapper.*;

public class TopPartitioner 
	extends Partitioner<CompositeKey, CompositeKey> {

	@Override
	public int getPartition(CompositeKey keyPair, CompositeKey valuePair, int numberOfPartitions) {
		return Math.abs(
				(keyPair.getMonth().hashCode() * 163 + 
				 keyPair.getYear().hashCode() * 31 + 
				 keyPair.getTotal().hashCode()) 
				% numberOfPartitions);
	}
}
