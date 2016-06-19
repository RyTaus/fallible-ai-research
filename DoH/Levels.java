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


        player.add(new Team (new Unit[]{
                new Unit(new Coord(4, 4), 3, 20, 12, 5, 5, Class.Swordsman, TeamType.PLAYER)}, TeamType.PLAYER));
        enemy.add(new Team(new Unit[]{
                new Unit(new Coord(0, 0), 3, 20, 12, 4, 5, Class.Swordsman, TeamType.ENEMY),
                new Unit(new Coord(3, 2), 3, 20, 8, 4, 5, Class.Swordsman, TeamType.ENEMY)}, TeamType.ENEMY));
        testBoards.add(new GameBoard(new Map("src/test5"), player.get(6), enemy.get(6), new HumanPlayer(), new EnemyPlayer()));
    
        player.add(new Team (new Unit[]{
                new Unit(new Coord(1, 1), 4, 20, 12, 5, 5, Class.Swordsman, TeamType.PLAYER),
                new Unit(new Coord(2, 2), 4, 20, 0, 55, 5, Class.Swordsman, TeamType.PLAYER)}, TeamType.PLAYER));
        enemy.add(new Team(new Unit[]{
                new Unit(new Coord(0, 1), 3, 20, 10, 4, 5, Class.Swordsman, TeamType.ENEMY),
                new Unit(new Coord(0, 0), 3, 20, 0, 40, 5, Class.Swordsman, TeamType.ENEMY)}, TeamType.ENEMY));
        testBoards.add(new GameBoard(new Map("src/test4"), player.get(7), enemy.get(7), new HumanPlayer(), new EnemyPlayer()));
        player.get(7).units.get(0).currHP = 7;
        enemy.get(7).units.get(0).currHP = 10;
    }
    
    public GameBoard getLevel(int i) {
        switch (i) {
        case 1:
            Team player = new Team(new Unit[]{new Unit(new Coord(1, 2), 3, 20, 6, 3, 5, Class.Gunner, TeamType.PLAYER),
                    new Unit( new Coord(3, 1), 3, 20, 7, 3, 6, Class.Gunner, TeamType.PLAYER)}, TeamType.PLAYER);
            Team enemy = new Team(new Unit[]{new Unit(new Coord(1, 1), 3, 20, 6, 1, 5, Class.Swordsman, TeamType.ENEMY),
                    new Unit( new Coord(2, 2), 3, 4, 2, 6, 3, Class.Swordsman, TeamType.ENEMY),
                    new Unit( new Coord(4, 4), 3, 20, 7, 4, 5, Class.Swordsman, TeamType.ENEMY)}, TeamType.ENEMY);
            return new GameBoard(new Map("src/Level1"), player, enemy, new HumanPlayer(), new EnemyPlayer());
        case 2:
            player = new Team(new Unit[]{new Unit(new Coord(1, 2), 3, 20, 6, 3, 5, Class.Gunner, TeamType.PLAYER),
                    new Unit( new Coord(3, 1), 3, 20, 7, 3, 6, Class.Gunner, TeamType.PLAYER),
                    new Unit( new Coord(3, 3), 3, 20, 7, 3, 6, Class.Swordsman, TeamType.PLAYER)}, TeamType.PLAYER);
            enemy = new Team(new Unit[]{new Unit(new Coord(1, 1), 3, 20, 6, 1, 5, Class.Swordsman, TeamType.ENEMY),
                    new Unit( new Coord(2, 2), 3, 4, 2, 6, 3, Class.Swordsman, TeamType.ENEMY),
                    new Unit( new Coord(4, 4), 3, 20, 7, 4, 5, Class.Swordsman, TeamType.ENEMY),
                    new Unit( new Coord(7, 7), 3, 20, 7, 4, 5, Class.Swordsman, TeamType.ENEMY)}, TeamType.ENEMY);
            return new GameBoard(new Map("src/Level1"), player, enemy, new HumanPlayer(), new EnemyPlayer());
        case 0:
            player = new Team(new Unit[]{new Unit(new Coord(3, 2), 3, 20, 6, 3, 5, Class.Gunner, TeamType.PLAYER)}, TeamType.PLAYER);
            enemy = new Team(new Unit[]{new Unit(new Coord(8, 8), 3, 20, 6, 3, 5, Class.Gunner, TeamType.ENEMY)}, TeamType.ENEMY);
            return new GameBoard(new Map("src/test"), player, enemy, new HumanPlayer(), new EnemyPlayer());
        case 3:
            player = new Team(new Unit[]{new Unit(new Coord(1, 1), 3, 20, 8, 4, 5, Class.Swordsman, TeamType.PLAYER),
                    new Unit(new Coord(0, 0), 3, 17, 5, 2, 33, Class.Gunner, TeamType.PLAYER)}, TeamType.PLAYER);
            enemy = new Team(new Unit[]{new Unit(new Coord(3, 1), 6, 20, 6, 3, 5, Class.Swordsman, TeamType.ENEMY),
                    new Unit(new Coord(4, 5), 9, 20, 6, 3, 5, Class.Swordsman, TeamType.ENEMY),
                    new Unit(new Coord(6, 1), 12, 24, 7, 3, 5, Class.Swordsman, TeamType.ENEMY)}, TeamType.ENEMY);
            return new GameBoard(new Map("src/Level2"), player, enemy, new HumanPlayer(), new EnemyPlayer());
        case 4:
            player = new Team(new Unit[]{new Unit(new Coord(0, 1), 3, 20, 8, 4, 5, Class.Swordsman, TeamType.PLAYER),}, TeamType.PLAYER);
            enemy = new Team(new Unit[]{new Unit(new Coord(2, 1), 6, 30, 6, 9, 5, Class.Swordsman, TeamType.ENEMY),
                    new Unit(new Coord(2, 2), 12, 24, 7, 1, 5, Class.Swordsman, TeamType.ENEMY)}, TeamType.ENEMY);
            return new GameBoard(new Map("src/blocked"), player, enemy, new HumanPlayer(), new EnemyPlayer());
        case 5:
            player = new Team (new Unit[]{
                    new Unit(new Coord(4, 0), 4, 20, 10, 4, 5, Class.Gunner, TeamType.PLAYER),
                    new Unit(new Coord(5, 0), 4, 20, 12, 7, 5, Class.Swordsman, TeamType.PLAYER)}, TeamType.PLAYER);
            enemy = new Team(new Unit[]{
                    new Unit(new Coord(7, 3), 3, 20, 8, 4, 5, Class.Gunner, TeamType.ENEMY),
                    new Unit(new Coord(2, 0), 3, 20, 9, 4, 5, Class.Swordsman, TeamType.ENEMY),
                    new Unit(new Coord(0,2), 3, 20, 10, 5, 5, Class.Swordsman, TeamType.ENEMY),
                    new Unit(new Coord(0, 4), 3, 25, 12, 5, 5, Class.Swordsman, TeamType.ENEMY),
                    new Unit(new Coord(1, 4), 3, 20, 10, 4, 5, Class.Swordsman, TeamType.ENEMY)}, TeamType.ENEMY);
            return new GameBoard(new Map("src/test8"), player, enemy, new HumanPlayer(), new EnemyPlayer());
        case 6:
            player = new Team (new Unit[]{
                    new Unit(new Coord(1, 0), 4, 20, 10, 4, 5, Class.Gunner, TeamType.PLAYER),
                    new Unit(new Coord(3, 0), 4, 20, 10, 4, 5, Class.Gunner, TeamType.PLAYER),
                    new Unit(new Coord(2, 0), 4, 20, 10, 4, 5, Class.Gunner, TeamType.PLAYER),
                    new Unit(new Coord(0, 0), 4, 20, 12, 5, 5, Class.Swordsman, TeamType.PLAYER)}, TeamType.PLAYER);
            enemy = new Team(new Unit[]{
                    new Unit(new Coord(0, 3), 4, 20, 10, 4, 5, Class.Gunner, TeamType.ENEMY),
                    new Unit(new Coord(1, 4), 4, 20, 10, 4, 5, Class.Gunner, TeamType.ENEMY),
                    new Unit(new Coord(5, 7), 4, 20, 10, 5, 5, Class.Swordsman, TeamType.ENEMY),
                    new Unit(new Coord(1, 5), 4, 20, 10, 5, 5, Class.Swordsman, TeamType.ENEMY),
                    new Unit(new Coord(0, 7), 3, 25, 12, 5, 5, Class.Swordsman, TeamType.ENEMY)}, TeamType.ENEMY);
            return new GameBoard(new Map("src/test9"), player, enemy, new HumanPlayer(), new EnemyPlayer());
        case 7:
            player = new Team (new Unit[]{
                    new Unit(new Coord(4, 0), 4, 16, 10, 4, 5, Class.Gunner, TeamType.PLAYER),
                    new Unit(new Coord(1, 0), 4, 16, 10, 4, 5, Class.Gunner, TeamType.PLAYER),
                    new Unit(new Coord(5, 0), 4, 20, 12, 5, 5, Class.Swordsman, TeamType.PLAYER),
                    new Unit(new Coord(0, 0), 4, 20, 12, 5, 5, Class.Swordsman, TeamType.PLAYER)}, TeamType.PLAYER);
            enemy = new Team(new Unit[]{
                    new Unit(new Coord(0, 3), 4, 14, 10, 4, 5, Class.Gunner, TeamType.ENEMY),
                    new Unit(new Coord(5, 2), 4, 14, 10, 4, 5, Class.Gunner, TeamType.ENEMY),
                    new Unit(new Coord(5, 5), 4, 14, 10, 4, 5, Class.Gunner, TeamType.ENEMY),
                    new Unit(new Coord(5, 3), 4, 18, 10, 5, 5, Class.Swordsman, TeamType.ENEMY),
                    new Unit(new Coord(4, 5), 4, 18, 10, 5, 5, Class.Swordsman, TeamType.ENEMY),
                    new Unit(new Coord(6, 5), 3, 22, 12, 6, 5, Class.Swordsman, TeamType.ENEMY)}, TeamType.ENEMY);
            return new GameBoard(new Map("src/test10"), player, enemy, new HumanPlayer(), new EnemyPlayer());
        case 8: 
            player = new Team (new Unit[]{
                    new Unit(new Coord(0, 0), 4, 10, 18, 4, 5, Class.Gunner, TeamType.PLAYER),
                    new Unit(new Coord(1, 1), 4, 20, 12, 5, 5, Class.Swordsman, TeamType.PLAYER)}, TeamType.PLAYER);
            enemy = new Team(new Unit[]{
                    new Unit(new Coord(9, 3), 4, 14, 10, 4, 5, Class.Gunner, TeamType.ENEMY),
                    new Unit(new Coord(7, 3), 4, 14, 10, 4, 5, Class.Gunner, TeamType.ENEMY),
                    new Unit(new Coord(10, 2), 4, 18, 10, 5, 5, Class.Swordsman, TeamType.ENEMY),
                    new Unit(new Coord(3, 3), 4, 18, 10, 5, 5, Class.Swordsman, TeamType.ENEMY),
                    new Unit(new Coord(3, 0), 3, 20, 12, 3, 5, Class.Swordsman, TeamType.ENEMY)}, TeamType.ENEMY);
            return new GameBoard(new Map("src/test11"), player, enemy, new HumanPlayer(), new EnemyPlayer());
        }
        
        return null;
    }
    
    public GameBoard getTestLevel(int i) {
        Global.screenHeight = testBoards.get(i).map.height;
        Global.screenWidth = testBoards.get(i).map.width;
        return testBoards.get(i);
    }
}
