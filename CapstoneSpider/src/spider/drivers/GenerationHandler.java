package spider.drivers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

import spider.genomic.SpiderChromosome;
import spider.genomic.SpiderProducer;

public class GenerationHandler {

	private ArrayList<SpiderChromosome> in;
	private ArrayList<SpiderChromosome> out;
	private Random rng;
	private SpiderProducer producer;
	
	public GenerationHandler(ArrayList<SpiderChromosome> input, Random rng) {
		in = input;
		out = new ArrayList<>();
		this.rng = rng;
		producer = new SpiderProducer();
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
		try {
			return producer.getNextGeneration(input, rng);
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
}
