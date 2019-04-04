package spider.drivers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

import spider.genomic.SpiderChromosome;

public class GenerationHandler {

	private ArrayList<SpiderChromosome> in;
	private ArrayList<SpiderChromosome> out;
	private Random rng;
	
	public GenerationHandler(ArrayList<SpiderChromosome> input, Random rng) {
		in = input;
		out = new ArrayList<>();
		this.rng = rng;
	}
	
	public ArrayList<SpiderChromosome> getTopN(int n) {
		ArrayList<SpiderChromosome> output = new ArrayList<>();
		out.sort(new Comparator<SpiderChromosome>() {
			@Override
			public int compare(SpiderChromosome o1, SpiderChromosome o2) {
				return (int) (o1.getScore() - o2.getScore());
			}
		});
		for (int i = 0; i < n; i++) {
			output.add(out.get(output.size() - i));
		}
		return output;
	}
	
	public ArrayList<SpiderChromosome> generateGeneration(ArrayList<SpiderChromosome> input) {
		ArrayList<SpiderChromosome> output = new ArrayList<>();
		while (output.size() < 64) {
			output.add(new SpiderChromosome(input, rng));
		}
		
		return output;
	}
	
}
