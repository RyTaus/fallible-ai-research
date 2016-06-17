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


        player.add(new Team (new Unit[]{
                new Unit(new Coord(1, 0), 3, 20, 8, 3, 5, Class.Swordsman, TeamType.PLAYER)}, TeamType.PLAYER));
        enemy.add(new Team(new Unit[]{
                new Unit(new Coord(2, 2), 3, 20, 8, 3, 5, Class.Swordsman, TeamType.ENEMY)}, TeamType.ENEMY));
        testBoards.add(new GameBoard(new Map("src/test1"), player.get(1), enemy.get(1), new HumanPlayer(), new EnemyPlayer()));


        player.add(new Team (new Unit[]{
                new Unit(new Coord(0, 0), 3, 20, 8, 3, 5, Class.Swordsman, TeamType.PLAYER)}, TeamType.PLAYER));
        enemy.add(new Team(new Unit[]{
                new Unit(new Coord(1, 2), 3, 20, 8, 3, 5, Class.Swordsman, TeamType.ENEMY),
                new Unit(new Coord(5, 3), 3, 20, 8, 3, 5, Class.Swordsman, TeamType.ENEMY)}, TeamType.ENEMY));
        testBoards.add(new GameBoard(new Map("src/test2"), player.get(2), enemy.get(2), new HumanPlayer(), new EnemyPlayer()));


        player.add(new Team (new Unit[]{
                new Unit(new Coord(1, 0), 3, 20, 12, 3, 5, Class.Gunner, TeamType.PLAYER),
                new Unit(new Coord(0, 1), 3, 20, 12, 3, 5, Class.Gunner, TeamType.PLAYER)}, TeamType.PLAYER));
        enemy.add(new Team(new Unit[]{
                new Unit(new Coord(3, 1), 3, 20, 8, 2, 5, Class.Swordsman, TeamType.ENEMY)}, TeamType.ENEMY));
        testBoards.add(new GameBoard(new Map("src/test1"), player.get(3), enemy.get(3), new HumanPlayer(), new EnemyPlayer()));


        player.add(new Team (new Unit[]{
                new Unit(new Coord(1, 0), 3, 20, 12, 3, 5, Class.Gunner, TeamType.PLAYER),
                new Unit(new Coord(0, 1), 3, 20, 12, 3, 5, Class.Swordsman, TeamType.PLAYER)}, TeamType.PLAYER));
        enemy.add(new Team(new Unit[]{
                new Unit(new Coord(3, 1), 3, 20, 8, 2, 5, Class.Swordsman, TeamType.ENEMY)}, TeamType.ENEMY));
        testBoards.add(new GameBoard(new Map("src/test1"), player.get(4), enemy.get(4), new HumanPlayer(), new EnemyPlayer()));


        player.add(new Team (new Unit[]{
                new Unit(new Coord(0, 0), 3, 20, 12, 3, 5, Class.Gunner, TeamType.PLAYER)}, TeamType.PLAYER));
        enemy.add(new Team(new Unit[]{
                new Unit(new Coord(3, 1), 3, 20, 8, 4, 5, Class.Swordsman, TeamType.ENEMY),
                new Unit(new Coord(2, 1), 3, 20, 8, 6, 5, Class.Swordsman, TeamType.ENEMY)}, TeamType.ENEMY));
        testBoards.add(new GameBoard(new Map("src/test3"), player.get(5), enemy.get(5), new HumanPlayer(), new EnemyPlayer()));
        enemy.get(5).units.get(1).currHP = 5;


        player.add(new Team (new Unit[]{
                new Unit(new Coord(4, 4), 3, 20, 12, 5, 5, Class.Swordsman, TeamType.PLAYER)}, TeamType.PLAYER));
        enemy.add(new Team(new Unit[]{
                new Unit(new Coord(0, 0), 3, 20, 12, 4, 5, Class.Swordsman, TeamType.ENEMY),
                new Unit(new Coord(3, 2), 3, 20, 8, 4, 5, Class.Swordsman, TeamType.ENEMY)}, TeamType.ENEMY));
        testBoards.add(new GameBoard(new Map("src/test5"), player.get(6), enemy.get(6), new HumanPlayer(), new EnemyPlayer()));


        player.add(new Team (new Unit[]{
                new Unit(new Coord(1, 1), 3, 20, 12, 5, 5, Class.Swordsman, TeamType.PLAYER)}, TeamType.PLAYER));
        enemy.add(new Team(new Unit[]{
                new Unit(new Coord(0, 1), 3, 20, 10, 4, 5, Class.Swordsman, TeamType.ENEMY)}, TeamType.ENEMY));
        testBoards.add(new GameBoard(new Map("src/test4"), player.get(7), enemy.get(7), new HumanPlayer(), new EnemyPlayer()));
        player.get(7).units.get(0).currHP = 7;
        enemy.get(7).units.get(0).currHP = 10;
    }
    
    public GameBoard getTestLevel(int i) {
        return testBoards.get(i);
    }
}
