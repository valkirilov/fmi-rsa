import java.util.Calendar;


public class PiThread extends Thread {
	
	String name;
	long numberOfPointsInCircle = 0;
	
	long squareSideSize;
	long pointsForThread;
	boolean quietMode;

	public PiThread(String threadName, long squareSideSize, long pointsForThread, boolean quietMode) {
		this.name= threadName;
		this.squareSideSize = squareSideSize;

		this.pointsForThread = pointsForThread;
		this.quietMode = quietMode;
	}

	public void run() {
		if (!quietMode) {
			System.out.println(name + " started.");
		}
		
		// Let's track the execution time of the thread
		long startTimeOfThread = Calendar.getInstance().getTimeInMillis();
		
		for (long i = 0; i < pointsForThread; i++) {
			long x = PiThread.getRandomNumber(0, squareSideSize);
			long y = PiThread.getRandomNumber(0, squareSideSize);
			
			Point point = new Point(x, y);
			if (PiThread.isPointInCircle((squareSideSize / 2), point)) {
				numberOfPointsInCircle++;
			}
		}
		
		// Let's track the end of the execution of the thread
		long endTimeOfThread = Calendar.getInstance().getTimeInMillis();
		
		if (!quietMode) {
			System.out.println(name + " stopped.");
			System.out.println(name + " execution time was (millis): " + (endTimeOfThread - startTimeOfThread));
		}
	}

	private static long getRandomNumber(long min, long max) {
		return Math.round((Math.random() * (max - min)) + min);
	}
	
	/**
	 * Check is a specific point in a circle by a givven radius
	 * @param radius
	 * @param point
	 * @return
	 */
	private static boolean isPointInCircle(long radius, Point point) {
		long distance = (radius - point.getX()) * (radius - point.getX()) + (radius - point.getY()) * (radius - point.getY());
		
		if (distance > radius * radius) {
			return false;
		}
		else {
			return true;
		}
	}
}
