package spider.genomic;

import java.util.Random;

public class SpiderGene implements Cloneable {

	private int[] moves;
	
	public SpiderGene() {
		moves = new int[24];
		for (int i = 0; i < 24; i++) {
			moves[i] = 0;
		}
	}
	
	public SpiderGene(int val) {
		moves = new int[24];
		for (int i = 0; i < 24; i++) {
			moves[i] = val;
		}
	}
	
	public SpiderGene(SpiderGene src) {
		moves = src.getMoves();
	}
	
	public SpiderGene(int val, int scale) {
		Random rng = new Random();
		moves = new int[24];
		for (int i = 0; i < 24; i++) {
			moves[i] = rng.nextInt(2 * val) - val;
		}
	}

	public void setMove(int index, int value) {
		moves[index] = value;
	}
	
	public int getMove(int index) {
		return moves[index];
	}
	
	public int[] getMoves() {
		return moves;
	}

}
