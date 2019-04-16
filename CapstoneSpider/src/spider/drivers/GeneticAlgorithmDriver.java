package spider.drivers;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import spider.genomic.SpiderChromosome;

public class GeneticAlgorithmDriver {

	private static final int NUM_GENS = 1000;

	public static void main(String[] args) {
		BotHandler bot = new BotHandler();
		GenerationHandler generator = new GenerationHandler(new Random());
		long startTime = System.nanoTime();
		List<SpiderChromosome> currentGeneration = generator.generateInitialGeneration();
		for (int i = 0; i < NUM_GENS; i++) {
			System.out.println("Starting generation " + (i+1));
			for (SpiderChromosome chrom : currentGeneration) {
				//System.out.println("    Running chromosome " + (j+1) + " of 64");
				if (chrom.getScore() == 0) {
					bot.reInitBot();
					bot.makeRun(chrom);
					chrom.setScore(bot.getCurrBotScore());
					chrom.setDistanceMoved(bot.getDistanceMoved());
				}
			}
			Collections.sort(currentGeneration);
			System.out.printf("The best spider moved: %6.3f   score: %8.1f\n", 
					currentGeneration.get(0).getDistanceMoved(),
					currentGeneration.get(0).getScore());
			//System.out.println("Manufacturing generation " + (i+2));
			generator.setParentGeneration(currentGeneration);
			List<SpiderChromosome> temp = generator.getTopN(16);
			currentGeneration = generator.generateGeneration(temp);
			//System.out.println("TOP score is currently: " + temp.get(15).getScore());
			//System.out.println("LOW score is currently: " + temp.get(0).getScore());
		}
		System.out.printf("Running time: %5.1fs\n", (System.nanoTime() - startTime) / 1.0e9);
	}

}
