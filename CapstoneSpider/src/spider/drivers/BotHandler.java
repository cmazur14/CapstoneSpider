package spider.drivers;

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
		return bot.getScore();
	}
	
	public void makeRun(SpiderChromosome chrom, SpiderRobot robot) {
		for (int i = 0; i < 1000; i++) {
			robot.makeMove(chrom.getGene(i));
		}
	}

	public double getDistanceMoved() {
		return bot.getCurrDist();
	}

}
