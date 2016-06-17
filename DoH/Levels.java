import java.util.ArrayList;


public class Levels {
	 
	public int current;
	public int testCurrent;
	public ArrayList<GameBoard> testBoards = new ArrayList<GameBoard>();
	
	public Levels() {
		current = 0;
		testCurrent = 0;
		
		ArrayList<Team> player = new ArrayList<Team>();
		ArrayList<Team> enemy = new ArrayList<Team>();
		
		
		player.add(new Team(new Unit[]{
				new Unit(new Coord(2, 1), 3, 20, 7, 3, 5, Class.Gunner, TeamType.PLAYER),
				new Unit(new Coord(2, 2), 5, 22, 8, 5, 5, Class.Swordsman, TeamType.PLAYER)}, TeamType.PLAYER));
		enemy.add(new Team(new Unit[]{
				new Unit(new Coord(6, 2), 2, 16, 8, 4, 2, Class.Swordsman, TeamType.ENEMY),
				new Unit(new Coord(5, 2), 2, 19, 6, 5, 6, Class.Gunner, TeamType.ENEMY)}, TeamType.ENEMY));
		testBoards.add(new GameBoard(new Map("src/test0"), player.get(0), enemy.get(0), new HumanPlayer(), new EnemyPlayer()));
	}
	
	public GameBoard getTestLevel(int i) {
		return testBoards.get(i);
	}
}
