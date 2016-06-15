import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;


public class Population {
	
	int size;
	double killThresh;
	double mateThresh;
	double muteThresh;
	double mateAmount;
	ArrayList<Gene> genes;
	
	public Population(int siz, double killThres, double mateThres, double muteThres, double mateAmoun) {
		size = siz;
		killThresh = killThres;
		mateThresh = mateThres;
		muteThresh = muteThres;
		mateAmount = mateAmoun;
		genes = new ArrayList<Gene>();
		for (int i = 0; i < genes.size(); i ++) {
			genes.add(new Gene());
		}
	}
	
	public void sort() {
		Collections.sort(genes);
	}
	
	public void kill() {
		int end = (int) (killThresh * (double)size);
		for (int i = 0; i < end; i ++) {
			genes.remove(i);
		}
	}
	
	public void mutate() {
		int end = (int) (muteThresh * (double)size);
		for (int i = 0; i < end; i ++) {
			genes.get(new Random().nextInt(genes.size()));
		}
	}
	
	public void mate() {
		ArrayList<Gene> temp = new ArrayList<Gene>();
		int start = (int) (genes.size() * mateThresh);
		
		for (int i = 0; i < (int) (mateAmount * genes.size()); i ++) {
			ArrayList<Gene> poss = new ArrayList<Gene>();
			for (int j = start; j < genes.size(); j ++) {
				poss.add(genes.get(i));
			}
			int index = new Random().nextInt(poss.size());
			Gene mum = poss.get(index);
			poss.remove(index);
			index = new Random().nextInt(poss.size());
			Gene dad = poss.get(index);
			temp.add(mum.mate(dad));
		}
		for (Gene g : temp) {
			genes.add(g);
		}
	}
	
	public void refreshPopulation() {
		while (genes.size() < size) {
			genes.add(new Gene());
		}
	}
	
	public void resetFitness() {
		for (Gene g : genes) {
			g.fitness = 0;
		}
	}
	
	private int randomInt(int min, int max) {
		return new Random().nextInt(max - min) + min;
	}
	
	
}




