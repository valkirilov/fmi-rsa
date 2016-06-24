
import java.util.Calendar;

public class TaskRunner {

	// Input arguments
	static int numberOfThreads;
	static long squareSideSize;
	static boolean quietMode = false;
	static boolean validInput= true;
	
	static long numberOfPoints;
	static long pointsForThread;
	static String threadName;
	static PiThread[] piThreads;
	static double pi;

	public static void main(String[] args) {
		
		// Read the input parameters
		TaskRunner.readInput(args);

		// Configure the calculation
		TaskRunner.configure(args.length);
		
		// Let's track the execution time
		long startTime = Calendar.getInstance().getTimeInMillis();
		
		// Execute the calculation
		TaskRunner.run();
		
		// Let's track the end of execution
		long endTime = Calendar.getInstance().getTimeInMillis();
		
		// Show the results of the execution of the program
		if (!quietMode) {
			System.out.println("Threads used in current run: " + numberOfThreads);
			System.out.println("Pi: " + pi);
		}
		
		System.out.println("Total execution time for current run (millis): " + (endTime - startTime));
	}
	
	/**
	 * Read the cli input for the program
	 * @param args
	 */
	private static void readInput(String[] args) {
		for (int i = 0; i < args.length; i++) {
			// Read the size of the square
			if (args[i].equals("-s")) {
				squareSideSize = new Long(args[i + 1]);
			}
			
			// Read the number of thread which we can use
			if (args[i].equals("-t")) {
				numberOfThreads = new Integer(args[i + 1]);
			}
			
			// Check is the user prefer a quiet mode
			if (args[i].equals("-q")) {
				quietMode = true;
			}
		}
	}
	
	/**
	 * Validate the input arguments and make some configuration before the start of the calculations
	 * @param argsCount
	 */
	private static void configure(Integer argsCount) {
		if (argsCount < 4 || argsCount > 5) {
			System.out.println("ERROR: The number of the provided arguments is incrorrect. Please use the following arguments:");
			System.out.println("-s integer - size of the square");
			System.out.println("-t integer - number of the used threads");
			System.out.println("-q - quiet mode");
			validInput = false;
		}
		else if (squareSideSize <= 0) {
			System.out.println("ERROR: Argument for the size of the square is incorrect. Please provide a positive integer.");
			validInput = false;
		} 
		else if (numberOfThreads <= 0) {
			System.out.println("ERROR: Argument for the number of the threads is incorrect. Please provide a positive integer.");
			validInput = false;
		}
		else {
			piThreads = new PiThread [numberOfThreads];
			numberOfPoints = 10000000;
			pointsForThread = numberOfPoints / numberOfThreads;
			
			for (int t = 0; t < numberOfThreads; t++) {
				if (t == numberOfThreads - 1) {
					pointsForThread = numberOfPoints - pointsForThread * (numberOfThreads - 1);
				}
				
				PiThread piThread = new PiThread("Thread-" + Integer.toString(t+1),
						squareSideSize, pointsForThread, quietMode);
				
				piThread.start();
				piThreads[t] = piThread;
			}
		}
	}
	
	private static void run() {
		if (validInput) {
			long pointsInCircle = 0;
			
			for (int i = 0; i < numberOfThreads; i++) {
				try {
					piThreads[i].join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				pointsInCircle += piThreads[i].numberOfPointsInCircle;
			}
			
			// Calculate PI
			pi = 4 * ((double) pointsInCircle / numberOfPoints);
		}
		else {
			System.out.println("\nPlease fix the provided errors an run the program again");
			System.out.println("Example: java TaskRunner -s 100 -t 2");
		}
	}
	
}
