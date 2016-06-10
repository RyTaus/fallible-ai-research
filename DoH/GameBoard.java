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
		BATTLE, MOVE
		
	}	
	
	public Map map;
	public Team player;
	public Team enemy;
	
	public Player playerController = new HumanPlayer();
	public Player enemyController = new EnemyPlayer();
	
	public ActionMenu actionMenu;
	public ActionHandler actionHandler;
	public MovementHandler movementHandler;
	
	public Phase phase = Phase.PLAYER;
	public State state = State.SELECT_UNIT;
	
	public GameBoard(Map m, Team p, Team e) {
		map = m;
		player = p;
		enemy = e;
	}
	
	public Player getPlayer(TeamType t) {
		if (t == TeamType.PLAYER) {
			return playerController;
		} else {
			return enemyController;
		}
	}
	
	public Player getPlayer(Phase p) {
		if (p == Phase.PLAYER) {
			return playerController;
		} else {
			return enemyController;
		}
	}
	
	public Team getTeam(Phase p) {
		if (p == Phase.PLAYER) {
			return player;
		} else {
			return enemy;
		}
	}
	
	private TeamType getTeamType(Phase p) {
		if (p == Phase.PLAYER) {
			return TeamType.PLAYER;
		} else {
			return TeamType.ENEMY;
		}
	}
	
	public Team getTeam(TeamType team) {
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
	
	public ArrayList<Direction> getPathTo(Unit u, Coord d) {
		return new PathFinder(this, u).getPurePathTo(d);
		
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
			if (getTeam(phase).isUnitOn(map.cursor)) {
				map.marked.moveTo(map.cursor);
				state = State.SELECT_DESTINATION;
			}
		} else if (state == State.SELECT_DESTINATION) {
			Unit uoi = getTeam(phase).unitOn(map.marked);
			if (Arrays.asList(canMoveMap(uoi)).contains(map.cursor)) {
				actionMenu = new ActionMenu(this, uoi, canActOn(uoi, map.cursor, otherTeam(uoi.team)));
				movementHandler = new MovementHandler(uoi, getPathTo(uoi, map.cursor));
				state = State.MOVE;
			} else {
				state = State.SELECT_UNIT;
			}
		} else if (state == State.SELECT_ACTION) {
			state = actionMenu.press();
			if (state == State.SELECT_UNIT) {
				unitOn(map.cursor).isDone = true;
				endTurn();
			} else if (state == State.BATTLE) {
				actionHandler = new ActionHandler(unitOn(map.cursor), actionMenu.targets.select(), actionMenu.menu.select(), this);
			}
		} 
	}


	private void endTurn() {
		if (phase == Phase.PLAYER) {
			if (player.isDone()) {
				phase = Phase.ENEMY;
				System.out.println("Now " + phase + " Turn");
				player.refresh();
				state = State.SELECT_UNIT;
			} 
		} else {
			if (enemy.isDone()) {
				phase = Phase.PLAYER;
				System.out.println("Now " + phase + " Turn");
				enemy.refresh();
				state = State.SELECT_UNIT;
			} 
		}
		
	}

	public Unit[] canActOn(Unit u, Coord c, Team t) {
		return canAttack(u, c);
	}

	public void back() {
		if (state == State.SELECT_UNIT) {

		} else if (state == State.SELECT_DESTINATION) {
			state = State.SELECT_UNIT;
		} else if (state == State.SELECT_ACTION) {
			state = actionMenu.back();
			if (state == State.SELECT_DESTINATION) {
				getTeam(phase).unitOn(map.cursor).move(map.marked);
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
	
	public Unit[] canAttack(Unit u, Coord c) {
		ArrayList<Unit> targets = new ArrayList<Unit>();
		Coord[] starting = {c};
		if (c == null) {
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
		ArrayList<Coord> list = new ArrayList<Coord>(Arrays.asList(webOut(canMoveMap(u), u.job.range)));
		for (int i = 0; i < list.size(); i ++) {
			for (int j = 0; j < getTeam(u.team).units.size(); j ++) {
				if (list.get(i).equals(getTeam(u.team).units.get(j).position)) {
					list.remove(i);
					break;
				}
			}
		}
		return list.toArray(new Coord[list.size()]);
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
					if (i + map.getCell(n).moveAffect(u) < possible.length && checkToAdd(n, u)) {
						possible[i + map.getCell(n).moveAffect(u)].add(n);
					}
				}
				if (e.x < map.width) {
					if (i + map.getCell(e).moveAffect(u) < possible.length && checkToAdd(e, u)) {
						possible[i + map.getCell(e).moveAffect(u)].add(e);
					}
				}
				if (s.y < map.height) {
					if (i + map.getCell(s).moveAffect(u) < possible.length && checkToAdd(s, u)) {
						possible[i + map.getCell(s).moveAffect(u)].add(s);
					}
				}
				if (w.x >= 0) {
					if (i + map.getCell(w).moveAffect(u) < possible.length && checkToAdd(w, u)) {
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

	public Coord[] canMoveMapVisual(Unit u) {
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
					if (i + map.getCell(n).moveAffect(u) < possible.length && checkToAdd(n, u)) {
						possible[i + map.getCell(n).moveAffect(u)].add(n);
					}
				}
				if (e.x < map.width) {
					if (i + map.getCell(e).moveAffect(u) < possible.length && checkToAdd(e, u)) {
						possible[i + map.getCell(e).moveAffect(u)].add(e);
					}
				}
				if (s.y < map.height) {
					if (i + map.getCell(s).moveAffect(u) < possible.length && checkToAdd(s, u)) {
						possible[i + map.getCell(s).moveAffect(u)].add(s);
					}
				}
				if (w.x >= 0) {
					if (i + map.getCell(w).moveAffect(u) < possible.length && checkToAdd(w, u)) {
						possible[i + map.getCell(w).moveAffect(u)].add(w);
					}
				}
			}
		}
		for (int i = 0; i < possible.length - 1; i ++) {
			possible[moves].addAll(possible[i]);
		}
		
		
		Iterator<Coord> iter = possible[moves].iterator();

		possible[moves].add(start);
		Coord[] stockArr = new Coord[possible[moves].size()];
		stockArr = possible[moves].toArray(stockArr);
		return stockArr;
	}
	
	public boolean checkToAdd(Coord c, Unit u) {
		if (c.x >= 0 && c.x < map.width && c.y >= 0 && c.y < map.height) {
			if (map.getCell(c).type.movePower > u.job.movePower) {
				return false;
			}
			if (u.team == TeamType.PLAYER) {
				return !enemy.isUnitOn(c);
			} else {
				return !player.isUnitOn(c);
			}
		}
		return false;
	}
	
	public int expectedDamage(Unit att, Coord attC, Unit rec) {
		int dmg = att.str - (rec.def + map.getCell(rec.position).type.defense);
		if (dmg < 0) {
			dmg = 0;
		}
		if (att.canAttackTwice(rec)) {
			dmg = dmg * 2;
		}
		return dmg;	
	}
	
	public int expectedRecDamage(Unit att, Coord attC, Unit rec) {
		int dmg = rec.str - (att.def + map.getCell(attC).type.defense);
		if (dmg < 0) {
			dmg = 0;
		}
		if (rec.canAttackTwice(att)) {
			dmg = dmg * 2;
		}
		return dmg;	
	}
	
	public void battle(Unit att, Unit rec) {
		if (att.canAttack(rec.position)) {
			rec.inflictDamage(expectedDamage(att, att.position, rec));
		}
		if (rec.canAttack(att.position)) {
			att.inflictDamage(expectedDamage(rec, rec.position, att));
		}
	}
	
	public void attack(Unit att, Unit rec) {
		rec.inflictDamage(expectedDamage(att, att.position, rec));
	}

	public void selectUnit() {
		// TODO Auto-generated method stub
		state = State.SELECT_DESTINATION;
		
	}

	public void moveUnit(Unit unit, Coord destination) {
		movementHandler = new MovementHandler(unit, getPathTo(unit, destination));
		state = State.MOVE;
		
	}

	public void performAction(Unit unit, ActionMenu.Option action, Unit target) {
		if (action == ActionMenu.Option.Wait) {
			unit.isDone = true;
			state = State.SELECT_UNIT;
		} else if (action == ActionMenu.Option.Attack) {
			battle(unit, target);
			actionHandler = new ActionHandler(unit, target, action, this);
			state = State.BATTLE;
		}
		endTurn();
		
	}

	public void update() {
		switch (state) {
		case BATTLE:
			if (actionHandler.update()) {
				actionHandler.attacker.isDone = true;
				endTurn();
				state = State.SELECT_UNIT;
			}
			break;
		case MOVE:
			if (movementHandler.update()) {
				state = State.SELECT_ACTION;
			}
			break;
		}
		
	}
}
