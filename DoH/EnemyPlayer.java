import java.util.ArrayList;

public class EnemyPlayer implements Player {

    Move enemyMove;

    public ArrayList<Unit> availableUnits(Team troops) {
        ArrayList<Unit> availableTroops = new ArrayList<Unit>();
        for (int i = 0; i < troops.units.size(); i++) {
            if (!troops.units.get(i).isDone) {
                availableTroops.add(troops.units.get(i));
            }
        }
        return availableTroops;
    }

    public Unit getUnit() {
        return enemyMove.troop;
    }

    public Unit getTarget() {
        return enemyMove.target;
    }

    public Coord getDestination() {
        return enemyMove.destination;
    }

    public ActionMenu.Option getAction() {
        return enemyMove.action;
    }

    public Move makeMove(GameBoard gb, Team troops) {
        Unit bestUnit = null;
        Unit bestTarget = null;
        ActionMenu.Option bestAct = null;
        int bestScore = -2;
        Coord bestDest = null;
        ArrayList<Unit> availableTroops = availableUnits(troops);
        int currScore = -2;
        Coord currDest = null;

        for (Unit currUnit : availableTroops) {
            
            int expRecDamage = 100000;

            if (gb.canAttack(currUnit, false).length == 0) {
                currScore = -1;
                expRecDamage = 0;
            }

            for (Unit currTarget : gb.canAttack(currUnit, false)) {     //Doesn't worry about putting itself in a better destination
                currDest = gb.canAttackFrom(currUnit, currTarget.position)[0];
                currScore = (gb.expectedDamage(currUnit, currDest, currTarget)/ currTarget.currHP);
                if (currScore > bestScore) {
                    bestUnit = currUnit;
                    bestTarget = currTarget;
                    bestDest = currDest;
                    bestAct = ActionMenu.Option.Attack;
                    bestScore = currScore;
                }
            }

            if (currScore == -1 && currScore >= bestScore) {
                bestUnit = currUnit;
                bestTarget = null;
                bestDest = currUnit.position;
                bestAct = ActionMenu.Option.Wait;
                bestScore = currScore;
            }

        }

        enemyMove = new Move(bestUnit, bestTarget, bestDest, bestAct, bestScore);
        return enemyMove;
    }
}
