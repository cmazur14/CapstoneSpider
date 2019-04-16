package spider.drivers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

import spider.genomic.SpiderChromosome;
import spider.genomic.SpiderProducer;

public class GenerationHandler {

	private ArrayList<SpiderChromosome> in;
	private Random rng;
	private SpiderProducer producer;
	
	public GenerationHandler(ArrayList<SpiderChromosome> input, Random rng) {
		in = input;
		this.rng = rng;
		producer = new SpiderProducer();
	}
	
	public GenerationHandler(Random rng) {
		producer = new SpiderProducer();
		this.rng = rng;
	}

	public void setParentGeneration(ArrayList<SpiderChromosome> input) {
		in = input;
	}
	
	public ArrayList<SpiderChromosome> getTopN(int n) {
		ArrayList<SpiderChromosome> output = new ArrayList<>();
		in.sort(new Comparator<SpiderChromosome>() {
			@Override
			public int compare(SpiderChromosome o1, SpiderChromosome o2) {
				return (int) (o2.getScore() - o1.getScore());
			}
		});
		for (int i = 0; i < n; i++) {
			output.add(in.get(output.size() - i));
		}
		return output;
	}
	
	public ArrayList<SpiderChromosome> generateGeneration(ArrayList<SpiderChromosome> input) {
		try {
			return producer.getNextGeneration(input, rng);
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public ArrayList<SpiderChromosome> generateInitialGeneration() {
		ArrayList<SpiderChromosome> output = new ArrayList<>();
		for (int i = 0; i < 64; i++) {
			output.add(new SpiderChromosome(1, 1, rng));
		}
		return output;
	}
	
}
