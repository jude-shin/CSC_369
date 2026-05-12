import java.io.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.Mapper.*;

// TODO: use this for the top n solution

public class CompositeKey
	implements Writable, WritableComparable<CompositeKey> {
	private IntWritable month;
	private IntWritable year;
	private DoubleWritable  total;

	public CompositeKey() {
		this.month = new IntWritable();
		this.year = new IntWritable();
		this.total = new DoubleWritable();
	}

	public CompositeKey(IntWritable month, IntWritable year, DoubleWritable total) {
		this.month = month;
		this.year = year;
		this.total = total;
	}

	public void set(IntWritable month, IntWritable year, DoubleWritable total) {
		this.month = month;
		this.year = year;
		this.total = total;
	}

	public IntWritable getMonth() {
		return month;
	}

	public IntWritable getYear() {
		return year;
	}

	public DoubleWritable getTotal() {
		return total;
	}

	public String getYearMonth() {
		return this.year.toString() + "-" + this.month.toString();
	}

	@Override
	public void write(DataOutput out) throws IOException {
		month.write(out);
		year.write(out);
		total.write(out);
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		month.readFields(in);
		year.readFields(in);
		total.readFields(in);
	}

	@Override
	public int compareTo(CompositeKey pair) {

		// Compare the year ascending first
		int compareValue = this.year.compareTo(pair.year);
		if (compareValue == 0) {

			// Compare the month ascending second
			compareValue = month.compareTo(pair.month);
			if (compareValue == 0) {

				// Compare the total descending last
				compareValue = -total.compareTo(pair.total);
			}
		}

		return compareValue;
	}
}
