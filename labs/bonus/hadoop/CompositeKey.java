import java.io.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.Mapper.*;

// TODO: use this for the top n solution

public class CompositeKey
	implements Writable, WritableComparable<PairOfStrings> {
	private IntWritable month;
	private IntWritable day;
	private DoubleWritable  total;

	public PairOfStrings() {
	}

	public void set(IntWritable month, IntWritable day, DoubleWritable total){
		this.month = month;
		this.day = day;
		this.total = total;
	}

	public IntWritable getMonth(){
		return month;
	}

	public IntWritable getDay(){
		return day;
	}

	public DoubleWritable getTotal(){
		return total;
	}

	public CompositeKey(IntWritable month, IntWritable day, DoubleWritable total){
		this.month = month;
		this.day = day;
		this.total = total;
	}

	@Override
	public void write(DataOutput out) throws IOException {
		month.write(out);
		day.write(out);
		total.write(out);
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		month.readFields(in);
		day.readFields(in);
		total.readFields(in);
	}

	@Override
	public int compareTo(PairOfStrings pair) {
		 // TODO
		int compareValue = this.left.compareTo(pair.left);
		if (compareValue == 0) {
			compareValue = right.compareTo(pair.right);
		}

		return compareValue;
		// to sort ascending

		//return -1*compareValue; // to sort descending

	}

	public String toString(){
		return left+", "+right;
	}
}
