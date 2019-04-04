package spider.physical;

import java.util.ArrayList;
import java.util.Comparator;

import javax.vecmath.Vector3d;

import spider.algorithm.LegMovement;
import spider.genomic.SpiderGene;

public class SpiderRobot {

	private static final double RADIUS_OF_BODY = 0.07;
	private static final Vector3d xAxis = new Vector3d(1, 0, 0);
	private static final Vector3d yAxis = new Vector3d(0, 1, 0);
	private static final Vector3d zAxis = new Vector3d(0, 0, 1);
	
	private static final double dTheta = 0.0349066;
	private static final double coxaLength = 0.025;
	private static final double femurLength = 0.03;
	private static final double tibiaLength = 0.045;
	private static final double defaultHipAngle = 0.0;
	private static final double defaultKneeAngle = 0.95;
	private static final double defaultAnkleAngle = 4.0;
	private static final double hipMax = defaultHipAngle + Math.PI / 2;
	private static final double hipMin = defaultHipAngle - Math.PI / 2;
	private static final double kneeMax = defaultKneeAngle + Math.PI / 2;
	private static final double kneeMin = defaultKneeAngle - Math.PI / 2;
	private static final double ankleMax = defaultAnkleAngle + Math.PI / 2;
	private static final double ankleMin = defaultAnkleAngle - Math.PI / 2;
	
	private int legAngleScoreModifier;
	private int balanceError;
	
	private Vector3d oHat;
	private Vector3d fHat;
	private Vector3d lHat;
	private Vector3d lMotion;
	private ArrayList<SpiderLeg> legs;
	
	public SpiderRobot() {
		legs = new ArrayList<SpiderLeg>();
		oHat = (Vector3d) zAxis.clone();
		fHat = (Vector3d) xAxis.clone();
		lHat = (Vector3d) yAxis.clone();
		lMotion= new Vector3d(0, 0, 0);
		legAngleScoreModifier = 0;
		balanceError = 0;
		initLegs();
	}
	
	private void initLegs() {
		double theta = 22.5; 
		while (theta < 360) {
			legs.add(new SpiderLeg(RADIUS_OF_BODY, 
									coxaLength, 
									femurLength, 
									tibiaLength, 
									Math.toRadians(theta), 
									defaultHipAngle, 
									defaultKneeAngle, 
									defaultAnkleAngle, 
									fHat, 
									lHat, 
									oHat));
			theta += 45;
		}
	}
	
	public void makeMove(SpiderGene move) {
		int i = 0;
		for (SpiderLeg leg : legs) {
			double newHipAngle = leg.getHipAngle() + dTheta * move.getMove(i*3);
			double newKneeAngle = leg.getKneeAngle() + dTheta * move.getMove(i * 3 + 1);
			double newAnkleAngle = leg.getAnkleAngle() + dTheta * move.getMove(i * 3 + 2);
			if (!(newHipAngle <= hipMax && newHipAngle >= hipMin)) {
				legAngleScoreModifier++;
				newHipAngle = leg.getHipAngle();
			}
			if (!(newKneeAngle <= kneeMax && newKneeAngle >= kneeMin)) {
				legAngleScoreModifier++;
				newKneeAngle = leg.getKneeAngle();
			}
			if (!(newAnkleAngle <= ankleMax && newAnkleAngle >= ankleMin)) {
				legAngleScoreModifier++;
				newAnkleAngle = leg.getAnkleAngle();
			}
			i++;
			leg.move(newHipAngle, newKneeAngle, newAnkleAngle);
		}
		determineStageMovement();
	}
	
	private void determineStageMovement() {
		ArrayList<LegMovement> movements = new ArrayList<>();
		for (SpiderLeg leg : legs) {
			movements.add(leg.getLowestMovement());
		}
		movements.sort(new Comparator<LegMovement>() {
			public int compare(LegMovement o1, LegMovement o2) {
				return (int) (o1.getNewPosition().getZ() - o2.getNewPosition().getZ() * 10000);
			}
		});
		int indexA = -1;
		int indexB = -1;
		int indexC = -1;
		for (int i = 0; i < 6; i++) {
			for (int j = 1; j < 7; j++) {
				for (int k = 2; k < 8; k++) {
					if (surroundCenterOfMass(movements.get(i), 
											 movements.get(j),
											 movements.get(k))) {
						indexA = i;
						indexB = j;
						indexC = k;
						break;
					}
				}
			}
		}
		if (indexA == -1 || indexB == -1 || indexC == -1) {
			balanceError++;
		} else {
		Vector3d tempVec = new Vector3d();
		tempVec.add(new Vector3d(movements.get(indexA).getMove().getX(), movements.get(indexA).getMove().getY(), 0));
		tempVec.add(new Vector3d(movements.get(indexB).getMove().getX(), movements.get(indexB).getMove().getY(), 0));
		tempVec.add(new Vector3d(movements.get(indexC).getMove().getX(), movements.get(indexC).getMove().getY(), 0));
		tempVec.scale(0.333333333333333);
		lMotion.add(tempVec);
		}
	}
	
	private boolean surroundCenterOfMass(LegMovement legMovement, LegMovement legMovement2, LegMovement legMovement3) {
		//barycentric coordinate system method
		
		double x1 = legMovement.getNewPosition().getX();
		double y1 = legMovement.getNewPosition().getY();
		double x2 = legMovement2.getNewPosition().getX();
		double y2 = legMovement2.getNewPosition().getY();
		double x3 = legMovement3.getNewPosition().getX();
		double y3 = legMovement3.getNewPosition().getY();
		double x = 0.0;
		double y = 0.0;
		
		double denominator = ((y2 - y3) * (x1-x3)) + ((x3 - x2) * (y1 - y3));
		double a = ((y2 - y3) * (x - x3) + (x3 - x2) * (y - y3)) / denominator;
		double b = ((y3 - y1) * (x - x3) + (x1 - x3) * (y - y3)) / denominator;
		double c = 1 - a - b;
		
		return 0 <= a && a <= 1 && 
			   0 <= b && b <= 1 && 
			   0 <= c && c <= 1;
	}

	@Override
	public String toString() {
		String output = "";
		int n = 0;
		for (SpiderLeg leg : legs) {
			output = output + leg.toString();
			if (n < 7)
				output = output + ", ";
			n++;
		}
		
		return output + "\n";
	}
	
	public int getAngleScoreModifier() {
		return legAngleScoreModifier;
	}

	public double getCurrDist() {
		return Math.sqrt(lMotion.getX() * lMotion.getX() + lMotion.getY() + lMotion.getY());
	}

	public int getScore() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getBalanceError() {
		return balanceError;
	}
}
