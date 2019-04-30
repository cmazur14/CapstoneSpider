package spider.drivers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import spider.genomic.SpiderChromosome;
import spider.genomic.SpiderProducer;

public class GenerationHandler {

	private List<SpiderChromosome> in;
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

	public void setParentGeneration(List<SpiderChromosome> input) {
		in = input;
	}
	
	public List<SpiderChromosome> getTopN(int n) {
		return in.stream()
				.sorted((o1,o2) -> (o2.getScore() > o1.getScore()) ? 1 : (o2.getScore() < o1.getScore() ? -1 : 0))
				.limit(n)
				.collect(Collectors.toList());
	}
	
	public List<SpiderChromosome> generateGeneration(List<SpiderChromosome> input) {
		try {
			return producer.getNextGeneration(input, rng);
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<SpiderChromosome> generateInitialGeneration() {
		ArrayList<SpiderChromosome> output = new ArrayList<>();
		for (int i = 0; i < 64; i++) {
			output.add(new SpiderChromosome(1, 1, rng));
		}
		return output;
	}
	
}
