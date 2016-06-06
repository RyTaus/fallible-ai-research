
public class Coord {
	
	public int x;
	public int y;
	
	public Coord(int ex, int why) {
		x = ex;
		y = why;
	}
	
	public void move(Coord c) {
		x += c.x;
		y += c.y;
	}
	
	public void moveTo(Coord c) {
		x = c.x;
		y = c.y;
	}
	
	public int distance(Coord c) {
		return Math.abs(c.x - x) + Math.abs(c.y - y);
	}
	
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
	
	public boolean equals(Coord c) {
		return x == c.x && y == c.y;
	}

}
