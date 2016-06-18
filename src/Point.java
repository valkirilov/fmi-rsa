
/**
 * 
 * A simple class which represents a Point in the 2D plane
 * 
 * @author valentin
 *
 */
public class Point {
	
	private long x;
	private long y;
	
	Point(long x, long y) {
		this.x = x;
		this.y = y;
	}
	
	long getX() {
		return x;
	}
	
	void setX(long x) {
		this.x = x;
	}
	
	long getY() {
		return y;
	}
	
	void setY(long y) {
		this.y = y;
	}
}
