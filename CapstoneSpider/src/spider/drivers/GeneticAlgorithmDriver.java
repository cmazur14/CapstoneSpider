package spider.drivers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import spider.genomic.SpiderChromosome;

public class GeneticAlgorithmDriver {

	private static final int NUM_GENS = 200000;
	private static final String DATA_FILE_NAME = "Data200000_point1_point1_point25_WithNegativeScoreModifiers_withJointDrag.csv";
	private static final int RANDOM_SEED = 314159265;

	public static void main(String[] args) throws IOException {
		GeneticAlgorithmDriver self = new GeneticAlgorithmDriver();
		BufferedWriter Bwriter = new BufferedWriter(new FileWriter("C:\\Users\\CJ\\Google Drive\\School\\Capstone\\Data\\" + DATA_FILE_NAME, true));
		PrintWriter writer = new PrintWriter(Bwriter);
		writer.write("");
		self.runGens(NUM_GENS, writer);
		writer.close();
	}
	
	public void runGens(int n, PrintWriter fileWriter) {
		BotHandler bot = new BotHandler();
		GenerationHandler generator = new GenerationHandler(new Random(RANDOM_SEED));
		long startTime = System.nanoTime();
		List<SpiderChromosome> currentGeneration = generator.generateInitialGeneration();
		for (int i = 1; i <= n; i++) {
			//System.out.println("Starting generation " + (i+1));
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
			if (i % 100 == 0) {
				System.out.printf("Generation %d moved: %6.1f centimeters   score: %8.1f\n",
						i,
						currentGeneration.get(0).getDistanceMoved() * 100,
						currentGeneration.get(0).getScore());
				fileWriter.printf("%d,%5.3f,%5.3f%n", i, currentGeneration.get(0).getDistanceMoved(), currentGeneration.get(0).getScore());
			} else if (i == 1) {
				System.out.printf("Generation %d moved: %6.1f centimeters   score: %8.1f\n",
						i,
						currentGeneration.get(0).getDistanceMoved() * 100,
						currentGeneration.get(0).getScore());
				fileWriter.printf("%d,%5.3f,%5.3f%n", i, currentGeneration.get(0).getDistanceMoved(), currentGeneration.get(0).getScore());
			}
			//System.out.println("Manufacturing generation " + (i+2));
			generator.setParentGeneration(currentGeneration);
			List<SpiderChromosome> temp = generator.getTopN(16);
			currentGeneration = generator.generateGeneration(temp);
		}
		System.out.printf("Generation %d moved: %6.1f centimeters   score: %8.1f\n",
				NUM_GENS,
				currentGeneration.get(0).getDistanceMoved() * 100,
				currentGeneration.get(0).getScore());
		fileWriter.printf("%d,%5.2f%n", NUM_GENS, currentGeneration.get(0).getScore());
		System.out.print("Executed " + NUM_GENS + " generations in");
		System.out.printf(": %5.1fs\n", (System.nanoTime() - startTime) / 1.0e9);
		try {
			bot.makeFileOutRun(currentGeneration.get(0), "TestRuns", "ResultOf200000Gens_AddedJointDrag.csv");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
