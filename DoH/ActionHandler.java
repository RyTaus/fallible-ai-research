import java.util.ArrayList;


public class ActionHandler {
	
	public enum State {
		BATTLE,
		EXP,
		LEVELUP
	}
	
	public Unit attacker;
	public Unit receiver;
	public ActionMenu.Option action;
	public GameBoard gb;
	
	public ArrayList<Unit> order;
	public int currRot = 0;
	
	public int currFrame;
	public State state = State.BATTLE;
	
	public ActionHandler (Unit a, Unit r, ActionMenu.Option ac, GameBoard g) {
		attacker = a;
		receiver = r;
		action = ac;
		gb = g;
		currFrame = 1;
		
		getOrder();
//		System.out.println(attacker);
//		System.out.println(receiver);
//		System.out.println("---------------");
		
	}
	
	private Unit other(Unit u) {
		if (u.equals(attacker)) {
			return receiver;
		} else {
			return attacker;
		}
	}
	
	private void getOrder() {
		ArrayList<Unit> al = new ArrayList<Unit>();
		switch (action) {
		case Attack:
			al.add(attacker);
			if (receiver.canAttack(attacker.position)) {
				al.add(receiver);
			}
			if (attacker.canAttackTwice(receiver)) {
				al.add(attacker);
			} else if (receiver.canAttackTwice(attacker)) {
				al.add(receiver);
			}
			break;
		}
		order = al;
		System.out.println(order);
	}
	
	public boolean update() {
//		System.out.println("	" + currFrame);
		if (attacker.isDead || receiver.isDead) {
			if (attacker.isDead) {
				gb.getTeam(attacker.team).units.remove(attacker);
			}
			if (receiver.isDead) {
				gb.getTeam(receiver.team).units.remove(receiver);
			}
			return true;
		}
		if (order.get(currRot).animate(currFrame, action.toAnimType(), other(order.get(currRot)))) {
			switch (action) {
			case Attack:
				gb.attack(order.get(currRot), other(order.get(currRot)));
				System.out.println(order.get(currRot));
				System.out.println(other(order.get(currRot)));
				System.out.println("---------------");
			}
			if (other(order.get(currRot)).isDead) {
				if (attacker.isDead) {
					gb.getTeam(attacker.team).units.remove(attacker);
				}
				if (receiver.isDead) {
					gb.getTeam(receiver.team).units.remove(receiver);
				}
				return true;
			}
			currRot +=1;
		}
		currFrame +=1;

		
		return currRot == order.size();
		
	}
	
	
	
	
	
	
	
}
