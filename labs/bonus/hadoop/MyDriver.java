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
	private static final Logger THE_LOGGER = Logger.getLogger(MyDriver.class);
	public static final int DEFAULT_N = 10;

	@Override
	public int run(String[] args) throws Exception {
		return (runJob1()&&runJob2()&&runJob3()&&runJob4())? 0 : 1;

		// // Generic Job setup
		// Job job = Job.getInstance();
		// job.setJarByClass(MyDriver.class);
		// job.setJobName("Driver"); 

		// // =========================================================================

		// // (1) Map
		// job.setMapOutputKeyClass(NullWritable.class); 
		// job.setMapOutputValueClass(Text.class); 
		// job.setMapperClass(MyMapper.class);

		// // (2/6) Local/Global Sort 
		// // For this example, we have a custom composite key (not a primitive)
		// // So we don't need another sorting function; it is done in DateTimePair

		// // (3/7) Local/Global Group
		// // job.setGroupingComparatorClass(SSGrouper.class);

		// // (4) Combiner
		// // job.setCombinerClass(MyReducer.class);

		// // (5) Partition
		// // job.setPartitionerClass(SSPartitioner.class);

		// // (8) Reduce
		// job.setOutputKeyClass(NullWritable.class); 
		// job.setOutputValueClass(Text.class); 
		// job.setReducerClass(MyReducer.class);

		// // =========================================================================

		// // Get the inputs from the args passed to this main method
		// // ex) hadoop jar lab02.jar MyDriver /user/jshin53/hadoop/inputs
		// //			/user/jshin53/hadoop/outputs N
		// FileInputFormat.setInputPaths(job, new Path(args[0]));
		// FileOutputFormat.setOutputPath(job, new Path(args[1]));


		// // NOTE: set the variable for the Mapper and Reducer to read
		// job.getConfiguration().setInt("N", Integer.parseInt(args[2]));

		// boolean status = job.waitForCompletion(true); 
		// THE_LOGGER.info("run(): status=" + status);
		// return status ? 0 : 1;
	}
	
	/* 
		 lineItem LEFTJOIN sale (join on saleID)
	*/
	public boolean runJob1() throws IOException, InterruptedException, ClassNotFoundException {

		Job job = Job.getInstance();
		job.setJarByClass(LeftJoinDriver.class); //VERY VERY IMPORTANT
		job.setJobName("lineItem LEFTJOIN sale (join on join on saleID)");
		job.setPartitionerClass(LeftJoinPartitioner.class);
		job.setGroupingComparatorClass(LeftJoinGroupComparator.class);
		job.setReducerClass(LeftJoinReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		MultipleInputs.addInputPath(job, transactions, 
				TextInputFormat.class, LeftJoinTransactionMapper.class);
		MultipleInputs.addInputPath(job, users, TextInputFormat.class, 
				LeftJoinUserMapper.class);
		job.setMapOutputKeyClass(PairOfStrings.class);
		job.setMapOutputValueClass(PairOfStrings.class);
		FileOutputFormat.setOutputPath(job, middle1);
		boolean status = job.waitForCompletion(true);
		THE_LOGGER.info("run(): status=" + status);
		return status;
	}

	/* 
		 job1 LEFTJOIN product (join on productID)
	*/
	public boolean runJob2() throws IOException, InterruptedException, ClassNotFoundException {

		Job job = Job.getInstance();
		job.setJarByClass(LeftJoinDriver.class); //VERY VERY IMPORTANT
		job.setJobName("job1 LEFTJOIN product (join on productID)");
		job.setPartitionerClass(LeftJoinPartitioner.class);
		job.setGroupingComparatorClass(LeftJoinGroupComparator.class);
		job.setReducerClass(LeftJoinReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		MultipleInputs.addInputPath(job, transactions, 
				TextInputFormat.class, LeftJoinTransactionMapper.class);
		MultipleInputs.addInputPath(job, users, TextInputFormat.class, 
				LeftJoinUserMapper.class);
		job.setMapOutputKeyClass(PairOfStrings.class);
		job.setMapOutputValueClass(PairOfStrings.class);
		FileOutputFormat.setOutputPath(job, middle1);
		boolean status = job.waitForCompletion(true);
		THE_LOGGER.info("run(): status=" + status);
		return status;
	}

	/* 
		 job3 LEFTJOIN store (join on storeID)
	*/
	public boolean runJob3() throws IOException, InterruptedException, ClassNotFoundException {

		Job job = Job.getInstance();
		job.setJarByClass(LeftJoinDriver.class); //VERY VERY IMPORTANT
		job.setJobName("job3 LEFTJOIN store (join on join on storeID)");
		job.setPartitionerClass(LeftJoinPartitioner.class);
		job.setGroupingComparatorClass(LeftJoinGroupComparator.class);
		job.setReducerClass(LeftJoinReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		MultipleInputs.addInputPath(job, transactions, 
				TextInputFormat.class, LeftJoinTransactionMapper.class);
		MultipleInputs.addInputPath(job, users, TextInputFormat.class, 
				LeftJoinUserMapper.class);
		job.setMapOutputKeyClass(PairOfStrings.class);
		job.setMapOutputValueClass(PairOfStrings.class);
		FileOutputFormat.setOutputPath(job, middle1);
		boolean status = job.waitForCompletion(true);
		THE_LOGGER.info("run(): status=" + status);
		return status;
	}

	/* 
		 job4 LEFTJOIN store (join on storeID)
	*/
	public boolean runJob4() throws IOException, InterruptedException, ClassNotFoundException {

		Job job = Job.getInstance();
		job.setJarByClass(LeftJoinDriver.class); //VERY VERY IMPORTANT
		job.setJobName("job4 sorted top N solution (grouping on month)");
		job.setPartitionerClass(LeftJoinPartitioner.class);
		job.setGroupingComparatorClass(LeftJoinGroupComparator.class);
		job.setReducerClass(LeftJoinReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		// TODO: single input I believe
		MultipleInputs.addInputPath(job, transactions, 
				TextInputFormat.class, LeftJoinTransactionMapper.class);
		job.setMapOutputKeyClass(PairOfStrings.class);
		job.setMapOutputValueClass(PairOfStrings.class);
		FileOutputFormat.setOutputPath(job, middle1);
		boolean status = job.waitForCompletion(true);
		THE_LOGGER.info("run(): status=" + status);
		return status;
	}


	public static void main(String[] args) throws Exception {                
		// Validate Inputs
		if (args.length != 3) {
			throw new IllegalArgumentException
				("usage: <input> <output> <top_n>");
		}

		THE_LOGGER.info("inputDir = " + args[0]);
		THE_LOGGER.info("outputDir = " + args[1]);
		THE_LOGGER.info("topN" + args[2]);

		// Run the new Driver
		int returnStatus = ToolRunner.run(new MyDriver(), args);
		THE_LOGGER.info("returnStatus=" + returnStatus);
		System.exit(returnStatus);
	}
}

