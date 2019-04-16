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
	
	public SpiderChromosome(SpiderGene[] childChrom) {
		chrom = childChrom;
		score = 0;
	}
	
	public void setScore(double d) {
		score = d;
	}
	
	public double getScore() {
		return score;
	}
		
	public SpiderChromosome(int val) {
		chrom = new SpiderGene[1000];
		for (int i = 0; i < 1000; i++) {
			chrom[i] = new SpiderGene(val);
		}
		score = 0;
	}
	
	public SpiderChromosome(int val, int scale) {
		chrom = new SpiderGene[1000];
		for (int i = 0; i < 1000; i++) {
			chrom[i] = new SpiderGene(val, scale);
		}
		score = 0;
	}
	
	public SpiderChromosome(int val, int scale, Random rng) {
		chrom = new SpiderGene[1000];
		for (int i = 0; i < 1000; i++) {
			chrom[i] = new SpiderGene(val, scale, rng);
		}
		score = 0;
	}
	
	public SpiderChromosome(SpiderChromosome src) {
		chrom = src.getChromosome();
	}

	public SpiderGene getGene(int index) {
		return chrom[index];
	}
	
	public SpiderGene[] getChromosome() {
		return chrom;
	}
	
	private void setChrom(SpiderGene[] input) {
		chrom = input;
	}
	
	private SpiderGene[] getChrom() {
		return chrom;
	}
	
	public SpiderChromosome crossedWith_Between_And_(SpiderChromosome p2, int r1, int r2) {
		SpiderGene[] p2Chrom = p2.getChrom();
		int l = chrom.length;
		
		SpiderGene[] child = new SpiderGene[l];
		for (int i = 0; i < r1; i++) {
			child[i] = chrom[i];
		}
		for (int i = r1; i < r2; i++) {
			child[i] = p2Chrom[i];
		}
		for (int i = r2; i < l; i++) {
			child[i] = chrom[i];
		}
		
		return new SpiderChromosome(child);
	}
	
	public SpiderChromosome pointMutateAt(int n) {
		SpiderGene[] childChrom = chrom.clone();
		childChrom[n] = new SpiderGene();
		return new SpiderChromosome(childChrom);
	}
	
	public SpiderChromosome shiftNSpotsAtLocation(int n, int location, int direction) {
		SpiderGene[] childChrom = chrom.clone();
		SpiderGene temp = childChrom[location + (n * direction)];
		childChrom[location + (n * direction)] = childChrom[location];
		childChrom[location] = temp;
		return new SpiderChromosome(childChrom);
	}
	
}
