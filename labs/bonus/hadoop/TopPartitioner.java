import java.io.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.Mapper.*;

public class TopPartitioner 
	extends Partitioner<CompositeKey, CompositeKey> {

	@Override
	public int getPartition(CompositeKey keyPair, CompositeKey valuePair, int numberOfPartitions) {
		return Math.abs(month.hashCode() * 163 + year.hashCode() * 31 + total.hashCode() % numberOfPartitions);
	}
}
