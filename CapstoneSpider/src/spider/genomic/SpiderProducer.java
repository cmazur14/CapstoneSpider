package spider.genomic;

import java.util.ArrayList;
import java.util.Random;

public class SpiderProducer {
	
	private static final double POINT_MUTATION_PROBABILITY = 0.10;
	private static final double SHIFT_MUTATION_PROBABILITY = 0.10;
	private static final double CROSSOVER_PROBABILITY = 0.15;
	

	public SpiderProducer() {
	}
	
	public ArrayList<SpiderChromosome> getNextGeneration(ArrayList<SpiderChromosome> parents, Random rng) throws CloneNotSupportedException {
		ArrayList<SpiderChromosome> newGeneration = new ArrayList<>();
		int i, j;		
		
		//While loop chooses 2 nonidentical random parents, and produces an offspring from them until the 
		//newGeneration is full
		while (newGeneration.size() < 64) {
			i = rng.nextInt(parents.size());
			j = rng.nextInt(parents.size());
			while (i == j) {
				j = rng.nextInt(parents.size());
			}
			newGeneration.add(getChild(parents.get(i), parents.get(j), rng));
		}
		
		return newGeneration;
	}
	
	public SpiderChromosome getChild(SpiderChromosome p1, SpiderChromosome p2, Random rng) throws CloneNotSupportedException {
		SpiderChromosome child = (SpiderChromosome) p1.clone();
		if (rng.nextDouble() <= POINT_MUTATION_PROBABILITY) {
			child = child.pointMutateAt(rng.nextInt(1000));
		}
		if (rng.nextDouble() <= SHIFT_MUTATION_PROBABILITY) {
			int val = 1;
			if (rng.nextDouble() <= 0.50)
				val = -1;
			child = child.shiftNSpotsAtLocation(rng.nextInt(4), rng.nextInt(1000), val);
		}
		if (rng.nextDouble() <= CROSSOVER_PROBABILITY) {
			int r1, r2;
			r1 = rng.nextInt(1000);
			r2 = r1 + rng.nextInt(1000 - r1);
			child = child.crossedWith_Between_And_(p2, r1, r2);
		}
		
		return child;
	}
	
}
