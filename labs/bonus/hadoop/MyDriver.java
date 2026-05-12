import java.io.*;
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

	private static Path lineItemPath;
	private static Path salePath;
	private static Path productPath;
	private static Path storePath;

	private static Path firstJoin;
	private static Path secondJoin;
	private static Path thirdJoin;

	private static Path outputPath;

	@Override
	public int run(String[] args) throws Exception {
		// return (runJob1()&&runJob2()&&runJob3()&&runJob4())? 0 : 1;
		return (runJob4())? 0 : 1;
	}
	
	/* 
		 lineItem LEFTJOIN sale (join on saleID)
	*/
	public boolean runJob1() throws IOException, InterruptedException, ClassNotFoundException {
		Job job = Job.getInstance();
		job.setJarByClass(MyDriver.class); //VERY VERY IMPORTANT
		job.setJobName("lineItem LEFTJOIN sale (join on join on saleID)");

		// Mapper inputs and outputs
		MultipleInputs.addInputPath(job, lineItemPath, TextInputFormat.class, LeftJoinLineItemMapper.class);
		MultipleInputs.addInputPath(job, salePath, TextInputFormat.class, LeftJoinSaleMapper.class);
		job.setMapOutputKeyClass(PairOfStrings.class);
		job.setMapOutputValueClass(PairOfStrings.class);

		// Reducer inputs and outputs
		job.setReducerClass(FirstReducer.class);
		job.setOutputKeyClass(NullWritable.class);
		job.setOutputValueClass(Text.class);
		
		// Grouping and Partitioning the outputs from both Mappers to the Reducers
		job.setPartitionerClass(BasicJoinPartitioner.class);
		job.setGroupingComparatorClass(BasicJoinGrouper.class);

		// Output the results to the first intermediate join path
		// (will be referenced for later use)
		FileOutputFormat.setOutputPath(job, firstJoin);

		boolean status = job.waitForCompletion(true);
		THE_LOGGER.info("run(): status=" + status);
		return status;
	}

	/* 
		 job1 LEFTJOIN product (join on productID)
	*/
	public boolean runJob2() throws IOException, InterruptedException, ClassNotFoundException {
		Job job = Job.getInstance();
		job.setJarByClass(MyDriver.class); //VERY VERY IMPORTANT
		job.setJobName("job1 LEFTJOIN product (join on productID)");

		// Mapper inputs and outputs
		MultipleInputs.addInputPath(job, firstJoin, TextInputFormat.class, LeftJoinFirstMapper.class);
		MultipleInputs.addInputPath(job, productPath, TextInputFormat.class, LeftJoinProductMapper.class);
		job.setMapOutputKeyClass(PairOfStrings.class);
		job.setMapOutputValueClass(PairOfStrings.class);

		// Reducer inputs and outputs
		job.setReducerClass(SecondReducer.class);
		job.setOutputKeyClass(NullWritable.class);
		job.setOutputValueClass(Text.class);
		
		// Grouping and Partitioning the outputs from both Mappers to the Reducers
		job.setPartitionerClass(BasicJoinPartitioner.class);
		job.setGroupingComparatorClass(BasicJoinGrouper.class);

		// Output the results to the first intermediate join path
		// (will be referenced for later use)
		FileOutputFormat.setOutputPath(job, secondJoin);

		boolean status = job.waitForCompletion(true);
		THE_LOGGER.info("run(): status=" + status);
		return status;
	}

	/* 
		 job2 LEFTJOIN store (join on storeID)
	*/
	public boolean runJob3() throws IOException, InterruptedException, ClassNotFoundException {
		Job job = Job.getInstance();
		job.setJarByClass(MyDriver.class); //VERY VERY IMPORTANT
		job.setJobName("job2 LEFTJOIN store (join on join on storeID)");

		// Mapper inputs and outputs
		MultipleInputs.addInputPath(job, secondJoin, TextInputFormat.class, LeftJoinSecondMapper.class);
		MultipleInputs.addInputPath(job, storePath, TextInputFormat.class, LeftJoinStoreMapper.class);
		job.setMapOutputKeyClass(PairOfStrings.class);
		job.setMapOutputValueClass(PairOfStrings.class);

		// Reducer inputs and outputs
		job.setReducerClass(ThirdReducer.class);
		job.setOutputKeyClass(NullWritable.class);
		job.setOutputValueClass(Text.class);
		
		// Grouping and Partitioning the outputs from both Mappers to the Reducers
		job.setPartitionerClass(BasicJoinPartitioner.class);
		job.setGroupingComparatorClass(BasicJoinGrouper.class);

		// Output the results to the first intermediate join path
		// (will be referenced for later use)
		FileOutputFormat.setOutputPath(job, thirdJoin);

		boolean status = job.waitForCompletion(true);
		THE_LOGGER.info("run(): status=" + status);
		return status;
	}

	/* 
		 top n solution for the months gross income
	*/
	public boolean runJob4() throws IOException, InterruptedException, ClassNotFoundException {
		Job job = Job.getInstance();
		job.setJarByClass(MyDriver.class); //VERY VERY IMPORTANT
		job.setJobName("top n solution for monthly gross rankings");

		// Mapper inputs and outputs
		FileInputFormat.setInputPaths(job, thirdJoin);
		job.setMapperClass(TopMapper.class);
		job.setMapOutputKeyClass(CompositeKey.class);
		job.setMapOutputValueClass(Text.class);

		// Reducer inputs and outputs
		job.setReducerClass(TopReducer.class);
		job.setOutputKeyClass(NullWritable.class);
		job.setOutputValueClass(Text.class);
		
		// Grouping and Partitioning the outputs from both Mappers to the Reducers
		job.setPartitionerClass(BasicJoinPartitioner.class);
		job.setGroupingComparatorClass(BasicJoinGrouper.class);

		// Output the results to the first intermediate join path
		// (will be referenced for later use)
		FileOutputFormat.setOutputPath(job, outputPath);

		boolean status = job.waitForCompletion(true);
		THE_LOGGER.info("run(): status=" + status);
		return status;
	}


	public static void main(String[] args) throws Exception {                
		// Validate Inputs
		if (args.length != 8) {
			throw new IllegalArgumentException
				("usage: <lineItemPath> <salePath> <productPath> <storePath> <firstJoin> <secondJoin> <thirdJoin> <output>");
		}

		lineItemPath = new Path(args[0]);
		salePath = new Path(args[1]);
		productPath = new Path(args[2]);
		storePath = new Path(args[3]);

		firstJoin = new Path(args[4]);
		secondJoin = new Path(args[5]);
		thirdJoin = new Path(args[6]);

		outputPath = new Path(args[7]);

		// Run the new Driver
		int returnStatus = ToolRunner.run(new MyDriver(), args);
		THE_LOGGER.info("returnStatus=" + returnStatus);
		System.exit(returnStatus);
	}
}

