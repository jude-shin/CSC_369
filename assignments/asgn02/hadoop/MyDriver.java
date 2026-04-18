import org.apache.log4j.Logger;
import org.apache.hadoop.util.*;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.output.*;
import org.apache.hadoop.mapreduce.lib.input.*;

/* 
	 Map/Reduce Steps:

	 (1) Map
	 (2) Local Sort
	 (3) Local Group
	 (4) Combiner 
	 (5) Partition
	 (6) Global Sort
	 (7) Global Group
	 (8) Reduce
*/

public class MyDriver extends Configured implements Tool {
	private static final Logger THE_LOGGER = 
		Logger.getLogger(MyDriver.class);

	@Override
	public int run(String[] args) throws Exception {
		// Generic Job setup
		Job job = Job.getInstance();
		job.setJarByClass(MyDriver.class);
		job.setJobName("Driver"); 

		// =========================================================================

		// (1) Map
		job.setMapOutputKeyClass(StudentTripple.class); 
		job.setMapOutputValueClass(GradePair.class); 
		job.setMapperClass(MyMapper.class);

		// (2/6) Local/Global Sort 
		// For this example, we have a custom composite key (not a primitive)
		// So we don't need another sorting function; it is done in StudentTripple

		// (3/7) Local/Global Group
		job.setGroupingComparatorClass(SSGrouper.class);

		// (4) Combiner
		// job.setCombinerClass(MyReducer.class);

		// (5) Partition
		job.setPartitionerClass(SSPartitioner.class);

		// (8) Reduce
		job.setOutputKeyClass(Text.class); 
		job.setOutputValueClass(Text.class); 
		job.setReducerClass(MyReducer.class);
	
		// =========================================================================

		// Get the inputs from the args passed to this main method
		// ex) hadoop jar lab02.jar MyDriver /user/jshin53/hadoop/inputs
		//			/user/jshin53/hadoop/outputs
		FileInputFormat.setInputPaths(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));

		boolean status = job.waitForCompletion(true); 
		THE_LOGGER.info("run(): status=" + status);
		return status ? 0 : 1;
	}

	public static void main(String[] args) throws Exception {                
		// Validate Inputs
		if (args.length != 2) {
			throw new IllegalArgumentException
				("usage: <input> <output>");
		}

		THE_LOGGER.info("inputDir = " + args[0]);
		THE_LOGGER.info("outputDir = " + args[1]);
	
		// Run the new Driver
		int returnStatus = ToolRunner.run(new MyDriver(), args);
		THE_LOGGER.info("returnStatus=" + returnStatus);
		System.exit(returnStatus);
	}
}

