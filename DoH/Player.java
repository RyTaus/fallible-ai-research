public interface Player {

    public Move makeMove(GameBoard gb, Team whichTeam);
    public Unit getUnit();
    public Unit getTarget();
    public Coord getDestination();
    public ActionMenu.Option getAction();

}
