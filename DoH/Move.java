public class Move {

    public Unit troop;
    public Unit target;
    public int moveScore;
    public Coord destination;
    public ActionMenu.Option action;

    
    public Move(Unit person, Unit enemy, Coord des, ActionMenu.Option choice, int score) {
        troop = person;
        target = enemy;
        destination = des;
        action = choice;
        moveScore = score;
    }

}
