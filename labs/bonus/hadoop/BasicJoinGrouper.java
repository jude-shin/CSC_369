import java.io.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.Mapper.*;

public class BasicJoinGrouper 
	extends WritableComparator {
	
	// We are grouping the items in the DateTimePair (Custom Composite Key)
	public BasicJoinGrouper() {
		super(PairOfStrings.class, true);
	}

	@Override
	public int compare(WritableComparable wc1, WritableComparable wc2) {
		// We are grouping based on two PairOfStrings
		PairOfStrings pair = (PairOfStrings) wc1;
		PairOfStrings pair2 = (PairOfStrings) wc2;

		// We only want to group based on the left string 
		return pair.getLeftElement().compareTo(pair2.getLeftElement());
	}
}

