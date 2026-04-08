import org.apache.log4j.Logger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import java.io.BufferedReader;
import java.io.FileReader;

public class Main {
	private static Logger LOGGER = Logger.getLogger(AverageTemperatureDriver.class);

	// Finds the total number of sales for each day
	// Does not use Map/Reduce
	public static void main(String[] args) {

		// Make sure there is an input file given as an argument
		if (!validateArgs(args)) {
			LOGGER.error("One input argument is needed.");
			return;
		}

		// Open the file and process each of the lines
		processFile(args[0]);
	}

	private static bool validateArgs(String[] args) {
		return args.length == 1;
	}

	private static void processFile(String path) {
		// Open the file at the given directory args[0]
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			String line;

			// Process each line in the text file
			while ((line = br.readLine()) != null) {
				processLine(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static int processLine(String line) {
		throw UnsupportedOperationException;
		return 0;
	}
}
