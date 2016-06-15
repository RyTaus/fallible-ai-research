public class Move {

    public Unit troop;
    public Unit target;
    public double moveScore;
    public Coord destination;
    public ActionMenu.Option action;

    
    public Move(Unit person, Unit enemy, Coord des, ActionMenu.Option choice, double score) {
        troop = person;
        target = enemy;
        destination = des;
        action = choice;
        moveScore = score;
    }
    
    public String toString() {
    	return moveScore + "	" + troop.toString() + "\n" + destination.toString() + "\n" + action.name();
    }
}