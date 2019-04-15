package spider.drivers;

import java.util.ArrayList;
import java.util.Random;

import spider.genomic.SpiderChromosome;

public class GeneticAlgorithmDriver {
	
	private static final int NUM_GENS = 100;
	
	public static void main(String[] args) {
		BotHandler bot = new BotHandler();
		ArrayList<SpiderChromosome> currentGeneration = new ArrayList<>();
		GenerationHandler generator = new GenerationHandler(new Random());
		currentGeneration = generator.generateInitialGeneration();
		ArrayList<SpiderChromosome> temp;
		int j;
		for (int i = 0; i < NUM_GENS; i++) {
			System.out.println("Starting generation " + (i+1));
			j = 0;
			for (SpiderChromosome chrom : currentGeneration) {
				System.out.println("    Running chromosome " + (j+1) + " of 64");
				bot.reInitBot();
				bot.makeRun(chrom);
				chrom.setScore(bot.getCurrBotScore());
				j++;
			}
			System.out.println("Manufacturing generation " + (i+2));
			generator.setParentGeneration(currentGeneration);
			temp = generator.getTopN(16);
			currentGeneration = generator.generateGeneration(temp);
			System.out.println("Best score is currently: " + temp.get(15).getScore());
			System.out.println("Worst score is currently: " + temp.get(0).getScore());
		}
	}

}
