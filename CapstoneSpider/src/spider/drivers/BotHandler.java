package spider.drivers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import spider.genomic.SpiderChromosome;
import spider.physical.SpiderRobot;

public class BotHandler {
	
	private SpiderRobot bot;
	
	public BotHandler() {
		bot = new SpiderRobot();
	}
	
	public SpiderRobot getBot() {
		return bot;
	}
	
	public void reInitBot() {
		bot = new SpiderRobot();
	}
	
	public double makeRun(SpiderChromosome chrom) {
		for (int i = 0; i < 1000; i++) {
			bot.makeMove(chrom.getGene(i));
		}
		return bot.getScore();
	}
	
	public double getCurrBotScore() {
		return bot.getScore() - 6.3;
	}
	
	public void makeRun(SpiderChromosome chrom, SpiderRobot robot) {
		for (int i = 0; i < 1000; i++) {
			robot.makeMove(chrom.getGene(i));
		}
	}

	public double getDistanceMoved() {
		return bot.getCurrDist() - .063;
	}
	
	public void makeFileOutRun(SpiderChromosome chrom, String foldername, String filename) throws IOException {
		PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("C:\\Users\\CJ\\Google Drive\\School\\Capstone\\Python\\" + filename, true)));
		writer.println(bot.toString());			
		for (int i = 0; i < 1000; i++) {
			bot.makeMove(chrom.getGene(i));
			writer.println(bot.toString());
		}
		writer.close();
	}

}
