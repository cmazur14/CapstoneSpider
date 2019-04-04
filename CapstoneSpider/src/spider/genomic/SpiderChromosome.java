package spider.genomic;

import java.util.ArrayList;
import java.util.Random;

public class SpiderChromosome {

	private SpiderGene[] chrom;
	private double score;
	
	public SpiderChromosome() {
		chrom = new SpiderGene[1000];
		for (int i = 0; i < 1000; i++) {
			chrom[i] = new SpiderGene();
		}
		score = 0;
	}
	
	public void setScore(long in) {
		score = in;
	}
	
	public double getScore() {
		return score;
	}
		
	public SpiderChromosome(int val) {
		chrom = new SpiderGene[1000];
		for (int i = 0; i < 1000; i++) {
			chrom[i] = new SpiderGene(val);
		}
	}
	
	public SpiderChromosome(int val, int scale) {
		chrom = new SpiderGene[1000];
		for (int i = 0; i < 1000; i++) {
			chrom[i] = new SpiderGene(val, scale);
		}
	}
	
	public SpiderChromosome(SpiderChromosome src) {
		chrom = src.getChromosome();
	}
	
	public SpiderChromosome(ArrayList<SpiderChromosome> input, Random rng) {
		//TODO
		chrom = input.get(0).getChromosome();
	}

	public SpiderGene getGene(int index) {
		return chrom[index];
	}
	
	public SpiderGene[] getChromosome() {
		return chrom;
	}
	
}
