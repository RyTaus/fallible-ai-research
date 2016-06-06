import java.awt.Color;


public class Cell {
	public enum Type {
		PLAIN(0, 0, 0, 0),
		SHRUB(0, 1, 1, 10),
		WATER(2, 0, 0, 0),
		EMPTY(100, 0, 0, 0),
		WALL(10, 0, 0, 0);
		
		public final int cost;
		public final int movePower;
		
		public final int avoid;
		public final int defense;
		
		Type(int pow, int mov, int av, int def) {
			cost = mov;
			movePower = pow;
			avoid = av;
			defense = def;
		}
		
		public Color img() {
			switch(this) {
				case PLAIN: return Color.YELLOW;
				case SHRUB: return Color.GREEN;
				case WATER: return Color.BLUE;
				case WALL: return Color.BLACK;
			
			}
			System.out.println("type of cell unspecified");
			return Color.WHITE;
				
		}
	}
	
	public Type type;
	
	public Cell(char c) {
		type = charToType(c);
	}

	private Type charToType(char c) {
		if (c == "-".charAt(0)) {
	    	return Type.PLAIN;
		} else if( c == "#".charAt(0)) {
			return Type.SHRUB;
		} else if (c == "~".charAt(0)) {
			return Type.WATER;
		} else if (c == "M".charAt(0)) {
			return Type.WALL;
		}
		return Type.EMPTY;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
