import org.apache.log4j.Logger;
import org.apache.hadoop.util.*;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.output.*;
import org.apache.hadoop.mapreduce.lib.input.*;

public class Driver extends Configured implements Tool {
	private static final Logger THE_LOGGER = 
		Logger.getLogger(Driver.class);

	@Override
	public int run(String[] args) throws Exception {
		// Generic Job setup
		Job job = Job.getInstance();
		job.setJarByClass(Driver.class);
		job.setJobName("Driver"); 

		// Reducing output values
		job.setOutputKeyClass(Text.class); 
		job.setOutputValueClass(IntWritable.class); 

		// Mapping output values
		job.setMapOutputKeyClass(Text.class); 
		job.setMapOutputValueClass(NullWritable.class); 
	
		// Custom Mapping and Reducing classes
		job.setMapperClass(TemperatureMapper.class);
		job.setReducerClass(TemperatureReducer.class);
	
		// Get the inputs from the args passed to this main method
		// ex) hadoop jar lab02.jar Driver /user/jshin53/hadoop/inputs
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
		int returnStatus = ToolRunner.run(new Driver(), args);
		THE_LOGGER.info("returnStatus=" + returnStatus);
		System.exit(returnStatus);
	}
}

