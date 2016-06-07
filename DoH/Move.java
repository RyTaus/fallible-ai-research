public class Move {

    public Unit troop;
    public Unit target;
    public Coord destination;
    public ActionMenu.Option action;
    
    public Move(Unit person, Unit enemy, Coord des, ActionMenu.Option choice) {
        troop = person;
        target = enemy;
        destination = des;
        action = choice;
    }

}
