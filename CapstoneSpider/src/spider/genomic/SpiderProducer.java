package spider.genomic;

import java.util.ArrayList;
import java.util.Random;

public class SpiderProducer {

	public SpiderProducer() {
	}
	
	public ArrayList<SpiderChromosome> getNextGeneration(ArrayList<SpiderChromosome> parents, Random rng) {
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
			newGeneration.add(getChild(parents.get(i), parents.get(j)));
		}
		
		return newGeneration;
	}
	
	public SpiderChromosome getChild(SpiderChromosome p1, SpiderChromosome p2) {
		//TODO
		return null;
	}
	
}
