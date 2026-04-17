import java.io.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.Mapper.*;

public class SSGrouper 
	extends WritableComparator {
	
	// We are grouping the items in the DateTimePair (Custom Composite Key)
	public SSGrouper() {
		super(DateTimePair.class, true);
	}

	@Override
	public int compare(WritableComparable wc1, WritableComparable wc2) {
		// We are grouping based on two DateTimePairs
		DateTimePair pair = (DateTimePair) wc1;
		DateTimePair pair2 = (DateTimePair) wc2;

		// We only want to group based on the date in the DateTimePair
		return pair.getDate().compareTo(pair2.getDate());
	}
}
