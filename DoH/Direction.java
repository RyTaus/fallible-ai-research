
public enum Direction {
	UP,
	DOWN,
	LEFT,
	RIGHT;
	
	public Coord toCoord() {
		if (this == Direction.UP) {
			return new Coord(0, -1);
		}
		if (this == Direction.DOWN) {
			return new Coord(0, 1);
		}
		if (this == Direction.LEFT) {
			return new Coord(-1, 0);
		}
		if (this == Direction.RIGHT) {
			return new Coord(1, 0);
		}
		return new Coord(0, 0);
	}
}
