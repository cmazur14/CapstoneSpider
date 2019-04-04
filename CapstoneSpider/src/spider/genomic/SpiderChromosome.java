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
		
		chrom = child;
		return this;
	}
	
}
