package robersj;


import java.util.List;

public class Coordinate {
	/**
	 * The coordinate of the x position.
	 */
	public int x;
	
	/**
	 * The coordinate of the y position.
	 */
	public int y;
	
	/**
	 * Creates a coordinate and stores the parameters into the instance variables.
	 * @param x
	 * @param y
	 */
	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Creates a new coordinate with the default values of (0, 0).
	 */
	public Coordinate() {
		this.x = 0;
		this.y = 0;
	}
	
	/**
	 * Creates a new coordinate and stores the parameter into the instance variables.
	 * @param c
	 */
	public Coordinate(Coordinate c) {
		this.x = c.x;
		this.y = c.y;
	}
	
	/**
	 * Sets the coordinates to the specified parameter.
	 * @param x The x coordinate.
	 * @param y The y coordinate.
	 */
	public void setCoordinates(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Returns true if the coordinate is out of bounds.
	 * @param c The coordinate you're checking.
	 * @return Returns true if the coordinate is out of bounds.
	 */
	public boolean isOutOfBounds(List<Coordinate> c) {
		return(this.x < Constants.LOWERBOUND) || (this.x > Constants.UPPERBOUND) || (this.y < Constants.LOWERBOUND) || (this.y > Constants.UPPERBOUND || c.contains(this));
	}
	
	/**
	 * Gets the last coordinate in a List
	 * @param sbc An arbitrary List
	 * @return Gets the last coordinate in a List.
	 */
	public static Coordinate getLastCoordinate(List<Coordinate> sbc) {
		return sbc.get(sbc.size() - 1);
	}
	
	/**
	 * Checks for equality between two coordinates.
	 */
	public boolean equals(Object obj) {
		if(obj instanceof Coordinate) {
			Coordinate temp = (Coordinate) obj;
			if(this.x == temp.x && this.y == temp.y)
				return true;
		}
		return false;
	}
	
	/**
	 * Converts the Coordinate to a String.
	 */
	public String toString() {
		return this.x + " : " + this.y;
	}
}
