import java.io.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.Mapper.*;

public class TopGrouper 
	extends WritableComparator {
	
	// We are grouping the items in the DateTimePair (Custom Composite Key)
	public TopGrouper() {
		super(CompositeKey.class, true);
	}

	@Override
	public int compare(WritableComparable wc1, WritableComparable wc2) {
		// We are grouping based on two CompositeKey
		CompositeKey pair = (CompositeKey) wc1;
		CompositeKey pair2 = (CompositeKey) wc2;

		// We only want to group based on the year and the date
		return pair.getYearMonth().compareTo(pair2.getYearMonth());
	}
}

