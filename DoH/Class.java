
public enum Class {
	Gunner (0, 3, new int[] {1, 2}),
	Swordsman (0, 4, new int[] {1, 1});
	
	public int movePower;
	public int movement;
	public int[] range;
	
	Class(int p, int m, int[] r) {
		movePower = p;
		movement = m;
		range = r;
	}
	
}
