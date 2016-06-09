import java.util.ArrayList;


public class MovementHandler {
	public Unit unit;
	public ArrayList<Direction> path;
	public int currStep = 0;
	
	public MovementHandler(Unit u, ArrayList<Direction> dir) {
		unit = u;
		path = dir;
	}
	
	public boolean update() {
		if (unit.animateWalk(path.get(currStep))) {
			unit.move(unit.position.getPointTo(path.get(currStep)));
			currStep ++;
		}
		return currStep == path.size();
		
	}
	
	
	
	
}
