import java.io.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.Mapper.*;

public class SSGrouper 
	extends WritableComparator {
	
	// We are grouping the items in the StudentTripple (Custom Composite Key)
	public SSGrouper() {
		super(StudentTripple.class, true);
	}

	@Override
	public int compare(WritableComparable wc1, WritableComparable wc2) {
		// We are grouping based on two StudentTripples
		StudentTripple s1 = (StudentTripple) wc1;
		StudentTripple s2 = (StudentTripple) wc2;

		// We only want to group based on the date in the StudentTripple
		return s1.getName().getName(s2.getDate());
	}
}
