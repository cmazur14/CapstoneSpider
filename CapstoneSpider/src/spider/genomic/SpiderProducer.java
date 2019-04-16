package spider.genomic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SpiderProducer {
	
	private static final int populationSize = 64;
	private static final double POINT_MUTATION_PROBABILITY = 0.1;
	private static final double SHIFT_MUTATION_PROBABILITY = 0.1;
	private static final double CROSSOVER_PROBABILITY = 0.25;
	

	public SpiderProducer() {
	}
	
	public ArrayList<SpiderChromosome> getNextGeneration(List<SpiderChromosome> sortedParents, Random rng) throws CloneNotSupportedException {
		ArrayList<SpiderChromosome> newGeneration = new ArrayList<>();
		int i, j;		
		
		//While loop chooses 2 nonidentical random parents, and produces an offspring from them until the 
		//newGeneration is full
		newGeneration.add((SpiderChromosome) sortedParents.get(0).clone());
		newGeneration.add((SpiderChromosome) sortedParents.get(1).clone());
		while (newGeneration.size() < populationSize) {
			i = rng.nextInt(sortedParents.size());
			j = rng.nextInt(sortedParents.size());
			while (i == j) {
				j = rng.nextInt(sortedParents.size());
			}
			newGeneration.add(getChild(sortedParents.get(i), sortedParents.get(j), rng));
		}
		
		return newGeneration;
	}
	
	private SpiderChromosome getChild(SpiderChromosome p1, SpiderChromosome p2, Random rng) throws CloneNotSupportedException {
		if (rng.nextDouble() <= POINT_MUTATION_PROBABILITY) {
			//System.out.println("point mutation occurred");
			return p1.pointMutateAt(rng.nextInt(1000));
		}
		if (rng.nextDouble() <= SHIFT_MUTATION_PROBABILITY) {
			int val = 1;
			if (rng.nextDouble() <= 0.50)
				val = -1;
			//System.out.println("shift mutation occurred");
			return p1.shiftNSpotsAtLocation(rng.nextInt(4)+1, 4 + rng.nextInt(992), val);
		}
		if (rng.nextDouble() <= CROSSOVER_PROBABILITY) {
			int r1 = rng.nextInt(1000);
			int r2 = r1 + rng.nextInt(1000 - r1);
			//System.out.println("Crossover occurred");
			return p1.crossedWith_Between_And_(p2, r1, r2);
		}
		
		return (SpiderChromosome) p1.clone();
	}
	
}
