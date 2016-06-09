import java.util.ArrayList;
import java.util.Random;


public class RandomPlayer implements Player{
	Move myMove;

	@Override
	public Move makeMove(GameBoard gb, Team team) {
		ArrayList<Unit> temp = new ArrayList<Unit>();
		for (Unit u : team.units) {
			if (!u.isDone) {
				temp.add(u);
			}
		}
		Unit u = getRandomUnit(temp);
		Coord d = getRandomDestination(gb.canMoveMap(u));
		myMove = new Move(u, null, d, ActionMenu.Option.Wait, 0);
		return myMove;
	}

	@Override
	public Unit getUnit() {
		return myMove.troop;
	}

	@Override
	public Unit getTarget() {
		// TODO Auto-generated method stub
		return myMove.target;
	}

	@Override
	public Coord getDestination() {
		return myMove.destination;
	}

	@Override
	public ActionMenu.Option getAction() {
		return myMove.action;
	}
	
	public Unit getRandomUnit(ArrayList<Unit> array) {
	    int rnd = new Random().nextInt(array.size());
	    return array.get(rnd);
	}
	
	public Coord getRandomDestination(Coord[] array) {
	    int rnd = new Random().nextInt(array.length);
	    return array[rnd];
	}

}
