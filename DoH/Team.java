import java.util.ArrayList;
import java.util.Arrays;


public class Team {
	
	public ArrayList<Unit> units;
	public TeamType teamType;
	
	public Team(Unit[] u, TeamType tt) {
		units = new ArrayList<Unit>(Arrays.asList(u));

	}
	
	public boolean isUnitOn(Coord c) {
		for (Unit u : units) {
			if (u.position.equals(c)) {
				return true;
			}
		}
		return false;
	}
	
	public Unit unitOn(Coord c) {
		for (Unit u : units) {
			if (u.position.equals(c)) {
				return u;
			}
		}
//		System.out.println("No unit on");
		return null;
	}
	

	public void endTurn() {
		for (Unit u : units) {
			u.isDone = true;
		}
	}
	
	public void refresh() {
		for (Unit u : units) {
			u.isDone = false;
		}
	}
	
	public boolean isDone() {
		for (Unit u : units) {
			if (!u.isDone) {
				return false;
			}
		}
		return true;
	}
	
	public boolean hasLost() {
		return units.isEmpty();
	}
	
	public Team clone() {
		Team t = new Team(new Unit[]{}, teamType);
		for (Unit unit : units) {
			t.units.add(unit.clone());
		}
		return t;
	}
	
	public boolean equals(Team t) {
		if (units.size() != t.units.size()) {
			return false;
		}
		for (int i = 0; i < units.size(); i ++) {
			if (!units.get(i).equals(t.units.get(i))) {
				return false;
			}
			
		}
		return true;
	}
	
	public String toString() {
		String s = "";
		for (Unit u : units) {
			s += u.toString();
		}
		return s;
	}
	
}
