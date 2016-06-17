import java.util.ArrayList;
import java.util.Arrays;



public class Zion implements Player{
	
	Move move = new Move(null, null, null, null, -1000000000);
	Gene gene;
	
	public Zion(Gene g) {
		gene = g;
	}

	@Override
	public Move makeMove(GameBoard gb, Team whichTeam) {
		move = new Move(null, null, null, null, -1000000000);
		Move temp;
		for (Unit u : availableUnits(gb.getTeam(gb.phase))) {
			for (Coord c : gb.canMoveMap(u)) {
				temp = new Move(u, null, c, ActionMenu.Option.Wait, 0);
				temp.moveScore = rateMove(gb, temp);
//				System.out.println("	" + temp);
				if (temp.moveScore > move.moveScore || move.troop == null) {
					move = temp;
				}
				
				for (Unit target : gb.canAttack(u, c)) {
					temp = new Move(u, target, c, ActionMenu.Option.Attack, 0);
					temp.moveScore = rateMove(gb, temp);
//					System.out.println("	" + temp);
					if (temp.moveScore > move.moveScore) {
						move = temp;
					}
				}
			}
		}
//		System.out.println("----  " + move);
		return move;
	}

	@Override
	public Unit getUnit() {
		// TODO Auto-generated method stub
		return move.troop;
	}

	@Override
	public Unit getTarget() {
		// TODO Auto-generated method stub
		return move.target;
	}

	@Override
	public Coord getDestination() {
		// TODO Auto-generated method stub
		return move.destination;
	}

	@Override
	public ActionMenu.Option getAction() {
		// TODO Auto-generated method stub
		return ActionMenu.Option.Wait;
	}
	
    public ArrayList<Unit> availableUnits(Team troops) {
        ArrayList<Unit> availableTroops = new ArrayList<Unit>();
        for (int i = 0; i < troops.units.size(); i++) {
            if (!troops.units.get(i).isDone) {
                availableTroops.add(troops.units.get(i));
            }
        }
//        System.out.println("          " + availableTroops.toString());
        return availableTroops;
    }

	
	public double rateMove(GameBoard gb, Move m) {
		double score = 0;
		double[] vals = getValues(gb, m);
		for (int i = 0; i < gene.weights.length; i ++) {
			score += vals[i] * gene.weights[i];
		}
		return score;
	}
	
	public double[] getValues(GameBoard gb, Move m) {
//		double[] weights = { dmgDealtMax, dmgDealtCurr, getKill, valOfTarget, 
//				dmgRecBattleMax, dmgRecBattleCurr, dmgPotMax, dmgPotCurr,  potDamageDiff, getKilled, stopKill, numInCluster };
		double dMax = 0;
		double dCurr = 0;
		double gKill = 0;
		double valTarg = 0;
		double rMax = 0;
		double rCurr = 0;
		double pd = 0;
		double prCurr = 0;
		double potDDiff = 0; //
		double gKilled = 0;
		double sKill = 0; //
		double nClust = 0;
		double dist = 10000;
		
		if (m.target == null) {
			dMax = 0;
			dCurr = 0;
			gKill = 0;
			valTarg = 0;
			rMax = 0;
			rCurr = 0;
			gKilled = 0;
		} else {
			double dmg = gb.expectedDamage(m.troop, m.destination, m.target);
			double rec = gb.expectedRecDamage(m.troop, m.destination, m.target);
			boolean twice = m.troop.canAttackTwice(m.target);
			if (twice && dmg / 2 > m.target.currHP) {
				rec = 0;
			}
			
			dMax = dmg / (double)m.target.maxHP;
			dCurr = dmg / (double)m.target.currHP;
			valTarg = getTargetValue();
			rMax = -rec / (double)m.troop.maxHP;
			rCurr = -rec / (double)m.troop.maxHP;
			
			gKill = (dmg > m.target.currHP) ? 10 : 0;
			gKilled = (rec > m.troop.currHP) ? -10 : 0;
		}
		for (Unit u : gb.getOtherTeam(gb.phase).units) {
			ArrayList<Unit> canAttack = new ArrayList<Unit>(Arrays.asList(gb.canAttack(u, null)));
			for (Unit poss : canAttack) {
				if (poss.equals(m.troop)) {
					pd += gb.expectedRecDamage(poss, poss.position, m.troop);
					prCurr -= gb.expectedDamage(poss, poss.position, m.troop);
				}
				
			}
			if (u.position.distance(m.troop.position) < dist) {
				dist = u.position.distance(m.troop.position);
			}
		}
		for (Unit u : gb.getTeam(gb.phase).units) {
			if (u.position.distance(m.destination) < 5 && u.isDone) {
				nClust += 1;
			}
		}
		
		prCurr = prCurr / (double) m.troop.currHP;
		
		return new double[] { dMax, dCurr, gKill, valTarg, rMax, rCurr, pd, prCurr, potDDiff, gKilled, sKill, nClust, dist };
	}

	private double getTargetValue() {
		// TODO Auto-generated method stub
		return 0;
	}

}
