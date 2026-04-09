import org.apache.log4j.Logger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
import java.util.HashMap;
import java.util.Map;
import java.io.BufferedReader;
import java.io.FileReader;

import org.apache.log4j.*;
import org.apache.hadoop.util.*;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.output.*;
import org.apache.hadoop.mapreduce.lib.input.*;

public class Main extends Configured implements Tool {
	private static Logger LOGGER = Logger.getLogger(Main.class);

	// Finds the total number of sales for each day
	// Does not use Map/Reduce
	public static void main(String[] args) {

		// Make sure there is an input file given as an argument
		if (!validateArgs(args)) {
			LOGGER.error("One input argument is needed.");
			// System.out.println("One input argument is needed.");
			return;
		}

		// Open the file and process each of the lines
		processFile(args[0]);
	}

	private static boolean validateArgs(String[] args) {
		return args.length == 1;
	}

	private static void processFile(String path) {
		// Maps a date (string) to an int (a sum)
		HashMap<String, Integer> dayToCt = new HashMap<>();

		// Open the file at the given directory args[0]
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			String line;

			// Process each line in the text file
			while ((line = br.readLine()) != null) {
				processLine(line, dayToCt);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		// Show what we got (and some extra stats for me)
		LOGGER.info("Results: ");
		int totalSales = 0;
		for (Map.Entry<String, Integer> day : dayToCt.entrySet()) {
			LOGGER.info("\t" + day.getKey() + ": " + day.getValue());
			totalSales += day.getValue();
		}

		LOGGER.info("Total Days: " + dayToCt.size());
		LOGGER.info("Total Sales: " + totalSales);
	}

	private static void processLine(String line, HashMap<String, Integer> dayToCt) {
		// Parse out the day of the sale
		// This is the second comma delimited string in a line
		String day = line.split("[, ]")[1];
		
		if (dayToCt.containsKey(day)) {
			dayToCt.put(day, dayToCt.get(day)+1);
		}
		else {
			dayToCt.put(day, 0);
		}
	}
}
