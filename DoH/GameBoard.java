import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;


public class GameBoard {
	
	public enum Phase {
		PLAYER,
		ENEMY
	}
	
	public enum State {
		SELECT_UNIT,
		SELECT_DESTINATION,
		SELECT_ACTION,
		BATTLE
		
	}	
	
	public Map map;
	public Team player;
	public Team enemy;
	
	public ActionMenu actionMenu;
	
	public Phase phase = Phase.PLAYER;
	public State state = State.SELECT_UNIT;
	
	public GameBoard(Map m, Team p, Team e) {
		map = m;
		player = p;
		enemy = e;
	}
	
	private Team getTeam(TeamType team) {
		if (team == TeamType.PLAYER) {
			return player;
		} else {
			return enemy;
		}
	}
	
	private Team otherTeam(TeamType team) {
		if (team == TeamType.PLAYER) {
			return enemy;
		} else {
			return player;
		}
	}
	
	public void move(Direction dir) {
		if (state == State.SELECT_UNIT || state == State.SELECT_DESTINATION) {
			map.moveCursor(dir);
		}  else if (state == State.SELECT_ACTION) {
			actionMenu.move(dir);
		}
	}
	
	public Coord[] canAttackFrom(Unit u, Coord c) {
		Coord[] poss = canMoveMap(u);
		ArrayList<Coord> result = new ArrayList<Coord>();
		for (Coord p : poss) {
			if (p.distance(c) >= u.job.range[0] && p.distance(c) <= u.job.range[1]) {
				result.add(p);
			}
		}
		return result.toArray(new Coord[result.size()]);
	}

	public void press() {
		// TODO Auto-generated method stub
		if (state == State.SELECT_UNIT) {
			if (player.isUnitOn(map.cursor)) {
				map.marked.moveTo(map.cursor);
				state = State.SELECT_DESTINATION;
			}
		} else if (state == State.SELECT_DESTINATION) {
			Unit uoi = player.unitOn(map.marked);
			if (Arrays.asList(canMoveMap(uoi)).contains(map.cursor)) {
				uoi.move(map.cursor);
				actionMenu = new ActionMenu(canActOn(uoi, otherTeam(uoi.team)));
				state = State.SELECT_ACTION;
			} else {
				state = State.SELECT_UNIT;
			}
		} else if (state == State.SELECT_ACTION) {
			state = actionMenu.press();
			if (state == State.BATTLE) {
				battle(unitOn(map.cursor), actionMenu.targets.select());
				state = State.SELECT_UNIT;
			}
		}
	}

	private void battle(Unit att, Unit rec) {
		att.battle(rec);
		
	}

	public Unit[] canActOn(Unit u, Team t) {
		return canAttack(u, false);
	}

	public void back() {
		if (state == State.SELECT_UNIT) {

		} else if (state == State.SELECT_DESTINATION) {
			state = State.SELECT_UNIT;
		} else if (state == State.SELECT_ACTION) {
			state = actionMenu.back();
			if (state == State.SELECT_DESTINATION) {
				player.unitOn(map.cursor).move(map.marked);
			}
		}
		
	}
	
	public boolean isUnitOn(Coord c) {
		return player.isUnitOn(c) || enemy.isUnitOn(c);
	}
	
	public Unit unitOn(Coord c) {
		if (player.isUnitOn(c)) {
			return player.unitOn(c);
		} else {
			return enemy.unitOn(c);
		}
	}
	
	public Unit[] canAttack(Unit u, boolean afterMove) {
		ArrayList<Unit> targets = new ArrayList<Unit>();
		Coord[] starting = {u.position};
		if (afterMove) {
			starting = canMoveMap(u);
		} 
		ArrayList<Coord> attackable = new ArrayList<Coord>(Arrays.asList(webOut(starting, u.job.range)));
		for (Unit unit : otherTeam(u.team).units) {
			if (attackable.contains(unit.position)) {
				targets.add(unit);
			}
		}
		return targets.toArray(new Unit[targets.size()]);
	}
	
	public Coord[] canAttackMap(Unit u) {
		return webOut(canMoveMap(u), u.job.range);
	}
	
	public Coord[] webOut(Coord[] origins, int[] distance) {
		if (distance == null) {
			return new Coord[]{};
		}
		int minRange = distance[0];
		int maxRange = distance[1];
		@SuppressWarnings("unchecked")
		HashSet<Coord>[] possible = new HashSet[maxRange + 1];
		
		for (int i = 0; i < possible.length; i ++) {
			possible[i] = new HashSet<Coord>();
		}
		possible[0] = new HashSet<Coord>(Arrays.asList(origins));
		
		
		Coord n, e, s, w;
		for (int i = 1; i < possible.length; i ++) {
			for (Coord j : possible[i - 1]) {
				n = j.getPointTo(Direction.UP);
				e = j.getPointTo(Direction.RIGHT);
				s = j.getPointTo(Direction.DOWN);
				w = j.getPointTo(Direction.LEFT);
				
				if (n.y >= 0) {
					possible[i].add(n);
				}
				if (e.x < map.width) {
					possible[i].add(e);
				}
				if (s.y < map.height) {
					possible[i].add(s);
				}
				if (w.x >= 0) {
					possible[i].add(w);
				}
			}
			
		}
		for (int i = 0; i < possible.length - 1; i ++) {
			if (i >= minRange && i <= maxRange) {
				possible[maxRange].addAll(possible[i]);
			}
		}
		possible[maxRange].removeAll(possible[0]);

		Coord[] stockArr = new Coord[possible[maxRange].size()];
		stockArr = possible[maxRange].toArray(stockArr);
		return stockArr;
	}
	
	public Coord[] canMoveMap(Unit u) {
		if (u == null) {
			//System.out.println("canmovemap for null");
			return new Coord[]{};
		}
		TeamType t = u.team;
		Coord start = u.position;
		int moves = u.job.movement;
		@SuppressWarnings("unchecked")
		HashSet<Coord>[] possible = new HashSet[moves + 1];
		Coord pa = start;
		Coord n, e, s, w;
		
		for (int i = 0; i < possible.length; i ++) {
			possible[i] = new HashSet<Coord>();
		}
		
		possible[0].add(pa);
		for (int i = 1; i < possible.length; i ++) {
			for (Coord j : possible[i-1]) {
				
				n = j.getPointTo(Direction.UP);
				e = j.getPointTo(Direction.RIGHT);
				s = j.getPointTo(Direction.DOWN);
				w = j.getPointTo(Direction.LEFT);
				
				if (n.y >= 0) {
					if (i + map.getCell(n).moveAffect(u) < possible.length && checkToAdd(n, t)) {
						possible[i + map.getCell(n).moveAffect(u)].add(n);
					}
				}
				if (e.x < map.width) {
					if (i + map.getCell(e).moveAffect(u) < possible.length && checkToAdd(e, t)) {
						possible[i + map.getCell(e).moveAffect(u)].add(e);
					}
				}
				if (s.y < map.height) {
					if (i + map.getCell(s).moveAffect(u) < possible.length && checkToAdd(s, t)) {
						possible[i + map.getCell(s).moveAffect(u)].add(s);
					}
				}
				if (w.x >= 0) {
					if (i + map.getCell(w).moveAffect(u) < possible.length && checkToAdd(w, t)) {
						possible[i + map.getCell(w).moveAffect(u)].add(w);
					}
				}
			}
		}
		for (int i = 0; i < possible.length - 1; i ++) {
			possible[moves].addAll(possible[i]);
		}
		
		
		Iterator<Coord> iter = possible[moves].iterator();
		while (iter.hasNext()) {
			Coord str = iter.next();

		    if (isUnitOn(str))
		        iter.remove();
		}

		possible[moves].add(start);
		Coord[] stockArr = new Coord[possible[moves].size()];
		stockArr = possible[moves].toArray(stockArr);
		return stockArr;
	}

	private boolean checkToAdd(Coord c, TeamType t) {
		if (t == TeamType.PLAYER) {
			return !enemy.isUnitOn(c);
		} else {
			return !player.isUnitOn(c);
		}
	}
}
