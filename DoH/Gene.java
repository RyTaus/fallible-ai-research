import java.util.Arrays;


public class Gene implements Comparable{
	double fitness;
	
	
	double dmgDealtMax = 20.1;
	double dmgDealtCurr;
	double getKill;
	
	double valOfTarget;
	
	double dmgRecBattleMax;
	double dmgRecBattleCurr;
	double getKilled;
	
	double dmgPot;
	double dmgRecCurr;
	
	double potDamageDiff;
	
	double stopKill;
	
	double numInCluster;
	double distToEnemy;
	
	double[] weights = { dmgDealtMax, dmgDealtCurr, getKill, valOfTarget, 
			dmgRecBattleMax, dmgRecBattleCurr, dmgPot, dmgRecCurr,  potDamageDiff, getKilled, stopKill, numInCluster, distToEnemy };
	
	public Gene() {
		randomize();
	}

	public void randomize() {
		for (int i = 0; i < weights.length; i ++) {
			weights[i]= Math.random() * 1000;
		}
		fitness = (double) 0;
		
	}
	
	public Gene mate(Gene g) {
		Gene ng = new Gene();
		for (int i = 0; i < weights.length; i ++) {
			ng.weights[i] = (randDouble(weights[i], g.weights[i]) + randDouble(weights[i], g.weights[i])) / 2;
		}
		return ng;
	}
	
	public void mutate() {
		weights[(int) Math.floor(Math.random() * weights.length)] = Math.random() * 1000;
	}
	
	public void update(double f) {
		fitness += f;
	}
	
	public void resetFitness() {
		fitness = 0;
	}
	
	
	private double randDouble(double in, double ax) {
		double min;
		double max;
		if (in < ax) {
			min = in;
			max = ax;
		} else {
			min = ax;
			max = in;
		}
		return (Math.random() * (max - min)) + min;
	}
	
	public String toString() {
		return Arrays.toString(weights);
	}
	

	@Override
	public int compareTo(Object o) {
		Gene g = (Gene) o;
		return (int) (fitness - g.fitness);
	}
}
