
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

	public Coord getPointTo(Direction dir) {
		Coord c = new Coord(x, y);
		c.move(dir.toCoord());
		return c;
	}
	
	public Coord getDirection(Coord c) {
		return new Coord(x - c.x, y - c.y);
	}
	
	public void add(Coord c) {
		x += c.x;
		y += c.y;
	}
	
	public void sub(Coord c) {
		x -= c.x;
		y -= c.y;
	}
	
	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
	
	@Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Coord other = (Coord) obj;
        return (x == other.x && y == other.y);
    }
	
	@Override
    public int hashCode() {
        int hash = Integer.parseInt(String.valueOf(x) + String.valueOf(y));
        return hash;
	}

}
