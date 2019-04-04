package spider.physical;

import javax.vecmath.Vector3d;

import spider.algorithm.LegMovement;

/*package spider.physical;
import javax.vecmath.Vector3d;

public class SpiderLeg {
	
	private static final double DEFAULT_FEMUR_LENGTH = 0.065;
	private static final double DEFAULT_COXA_LENGTH = 0.05;
	private static final double DEFAULT_TIBIA_LENGTH = 0.095;
	private static final double DEFAULT_HIP_ANGLE = 0.00;
	private static final double DEFAULT_KNEE_ANGLE = 0.4041799;
	private static final double DEFAULT_ANKLE_ANGLE = .957079633;

	private double bodyRadius;
	private double coxa;
	private double femur;
	private double tibia;
	private double angleFromTrue;
	private Vector3d hip;
	private Vector3d knee;
	private Vector3d ankle;
	private Vector3d foot;
	
	public SpiderLeg(double r, double theta, Vector3d myX, Vector3d myY, Vector3d myZ) {
		femur = DEFAULT_FEMUR_LENGTH;
		coxa = DEFAULT_COXA_LENGTH;
		tibia = DEFAULT_TIBIA_LENGTH;
		bodyRadius = r;
		angleFromTrue = theta;
		initializeLeg(theta,
					  DEFAULT_HIP_ANGLE,
					  DEFAULT_KNEE_ANGLE,
					  DEFAULT_ANKLE_ANGLE);
	}

	private void initializeLeg(double theta, double hipAngle, double kneeAngle, double ankleAngle) {
		double actualHipAngle = theta + hipAngle;
		double actualAnkleAngle = kneeAngle + ankleAngle;
		Vector3d r1 = new Vector3d(Math.cos(theta) * bodyRadius,
									-Math.sin(theta) * bodyRadius,
									0);
		Vector3d r2 = new Vector3d(Math.cos(actualHipAngle) * coxa,
								   -Math.sin(actualHipAngle) * coxa,
								   0);
		Vector3d r2Hat = new Vector3d();
		r2Hat.normalize(r2);
		Vector3d r3 = new Vector3d();
		r3.scale(Math.cos(kneeAngle) * femur, r2Hat);
		Vector3d tempVec = new Vector3d(0.0, 0.0, 1.0);
		tempVec.scale(-Math.sin(kneeAngle) * femur);
		r3.add(tempVec);
		Vector3d r4 = new Vector3d();
		r4.scale(Math.cos(actualAnkleAngle) * tibia, r2Hat);
		tempVec.normalize();
		tempVec.scale(-Math.sin(actualAnkleAngle) * femur);
		r4.add(tempVec);
		
		hip = r1;
		tempVec = new Vector3d();
		tempVec.add(r1,r2);
		knee = tempVec;
		tempVec = new Vector3d();
		tempVec.add(r1,r2);
		tempVec.add(r3);
		ankle = tempVec;
		tempVec = new Vector3d();
		tempVec.add(r1,r2);
		tempVec.add(r3);
		tempVec.add(r4);
		foot = tempVec;
	}

	public double getThighLength() {
		return coxa;
	}
	public double getShinLength() {
		return femur;
	}
	public double getFootLength() {
		return tibia;
	}
	public double getAngleFromTrue() {
		return angleFromTrue;
	}
	public Vector3d getEnd() {
		return foot;
	}
	
	
	
}*/

public class SpiderLeg {

	private double bodyRadius;
	private double coxa;
	private double femur;
	private double tibia;
	private double theta;
	private double hipAngle;
	private double kneeAngle;
	private double ankleAngle;
	private Vector3d oldHip;
	private Vector3d oldKnee;
	private Vector3d oldAnkle;
	private Vector3d oldFoot;
	private Vector3d hip;
	private Vector3d knee;
	private Vector3d ankle;
	private Vector3d foot;
	private Vector3d fHat;
	private Vector3d lHat;
	private Vector3d oHat;

	public SpiderLeg(double br, double c, double f, double t, double thet, double hA, double kA, double aA, Vector3d x, Vector3d y, Vector3d z) {
		bodyRadius = br;
		coxa = c;
		femur = f;
		tibia = t;
		theta = thet;
		hipAngle = hA;
		kneeAngle = kA;
		ankleAngle = aA;
		fHat = x;
		fHat.normalize();
		lHat = y;
		lHat.normalize();
		oHat = z;
		oHat.normalize();
		calculateJointLocations();
		oldHip = (Vector3d) hip.clone();
		oldKnee = (Vector3d) knee.clone();
		oldAnkle= (Vector3d) ankle.clone();
		oldFoot = (Vector3d) foot.clone();
	}
	
	public void move(double dHip, double dKnee, double dAnkle) {
		oldHip = (Vector3d) hip.clone();
		oldKnee = (Vector3d) knee.clone();
		oldAnkle= (Vector3d) ankle.clone();
		oldFoot = (Vector3d) foot.clone();
		hipAngle += dHip;
		kneeAngle += dKnee;
		ankleAngle += dAnkle;
		calculateJointLocations();
	}
	
	public void setCoordinateSystem(Vector3d f, Vector3d l, Vector3d o) {
		fHat = f;
		fHat.normalize();
		lHat = l;
		lHat.normalize();
		oHat = o;
		oHat.normalize();
	}

	private void calculateJointLocations() {
		double actualHipAngle = theta + hipAngle;
		double actualAnkleAngle = kneeAngle + ankleAngle;
		
		
		
		
		//Calculates r1 = fHat * cos(theta) * bodyRadius + lHat * -sin(theta) * bodyRadius
		//temp1 = left half of sum
		Vector3d temp1 = (Vector3d) fHat.clone();
		temp1.scale(Math.cos(theta) * bodyRadius);
		//temp2 = right half of sum
		Vector3d temp2 = (Vector3d) lHat.clone();
		temp2.add(lHat);
		temp2.scale(Math.sin(theta) * bodyRadius);
		
		//Creates and implements r1 = temp1 + temp2
		Vector3d r1 = new Vector3d(temp1);
		r1.add(temp2);
		
		//hip = r1
		hip = (Vector3d) r1.clone();
		
		
		
		
		//Calculates r2 = fHat * cos(actualHipAngle) * coxa + lHat * -sin(actualHipAngle * coxa
		//temp1 = left half of sum
		temp1 = (Vector3d) fHat.clone();
		temp1.scale(Math.cos(actualHipAngle) * coxa);
		//temp2 = right half of sum
		temp2 = (Vector3d) lHat.clone();
		temp2.scale(Math.sin(actualHipAngle) * coxa);
		
		//Creates and implements r2 = temp1 + temp2
		Vector3d r2 = new Vector3d(temp1);
		r2.add(temp2);
		
		//knee = r1 + r2
		knee = (Vector3d) r1.clone();
		knee.add(r2);
		
		
		
		
		
		//Calculates r2Hat
		Vector3d r2Hat = (Vector3d) r2.clone();
		r2Hat.normalize();
		
		//Calculates r3 = r2Hat * cos(kneeAngle) * femur + oHat * -sin(kneeAngle)* femur
		//temp1 = left half of sum
		temp1 = (Vector3d) r2Hat.clone();
		temp1.scale(Math.cos(kneeAngle) * femur);
		
		//temp2 = right half of sum
		temp2 = (Vector3d) oHat.clone();
		temp2.scale(Math.sin(kneeAngle) * femur);
		
		//Creates and implements r3 = temp1 + temp2
		Vector3d r3 = (Vector3d) temp1.clone();
		r3.add(temp2);
		
		//ankle = r1 + r2 + r3
		ankle = (Vector3d) r1.clone();
		ankle.add(r2);
		ankle.add(r3);
		
		
		
		
		
		
		//Calculates r4 = r2Hat * cos(actualAnkleAngle) * tibia + oHat * -sin(actualAnkleAngle) * tibia)
		//temp1 = left half of sum
		temp1 = (Vector3d) r2Hat.clone();
		temp1.scale(Math.cos(actualAnkleAngle) * tibia);
		
		//temp2 = right half of sum
		temp2 = (Vector3d) oHat.clone();
		temp2.scale(Math.sin(actualAnkleAngle) * tibia);
		
		//Creates and implements r4 = temp1 + temp2
		Vector3d r4 = (Vector3d) temp1.clone();
		r4.add(temp2);
		
		//Foot = r1 + r2 + r3 + r4
		foot = (Vector3d) r1.clone();
		foot.add(r2);
		foot.add(r3);
		foot.add(r4);
		
		/*Vector3d r2 = new Vector3d(Math.cos(actualHipAngle) * coxa,
								   -Math.sin(actualHipAngle) * coxa,
								   0);
		Vector3d r2Hat = new Vector3d();
		r2Hat.normalize(r2);
		
		
		Vector3d r3 = new Vector3d();
		r3.scale(Math.cos(kneeAngle) * femur, r2Hat);
		Vector3d tempVec = new Vector3d(0.0, 0.0, 1.0);
		tempVec.scale(-Math.sin(kneeAngle) * femur);
		r3.add(tempVec);
		Vector3d r4 = new Vector3d();
		r4.scale(Math.cos(actualAnkleAngle) * tibia, r2Hat);
		tempVec.normalize();
		tempVec.scale(-Math.sin(actualAnkleAngle) * femur);
		r4.add(tempVec);
		
		hip = r1;
		tempVec = new Vector3d();
		tempVec.add(r1,r2);
		knee = tempVec;
		tempVec = new Vector3d();
		tempVec.add(r1,r2);
		tempVec.add(r3);
		ankle = tempVec;
		tempVec = new Vector3d();
		tempVec.add(r1,r2);
		tempVec.add(r3);
		tempVec.add(r4);
		foot = tempVec;
		*/
	}
	
	public Vector3d getLowestPoint() {
		double min = hip.getZ();
		double num = knee.getZ();
		Vector3d lowest = hip;
		if (num < min)
			lowest = knee;
		num = ankle.getZ();
		if (num < min)
			lowest = ankle;
		num = foot.getZ();
		if (num < min)
			lowest = foot;
		return lowest;
	}
	
	@Override 
	public String toString() {
		String result = "";
		result = result + hip.getX() + ", " + 
						  hip.getY() + ", " + 
						  hip.getZ() + ", " + 
						  knee.getX() + ", " + 
						  knee.getY() + ", " + 
						  knee.getZ() + ", " + 
						  ankle.getX() + ", " + 
						  ankle.getY() + ", " + 
						  ankle.getZ() + ", " + 
						  foot.getX() + ", " + 
						  foot.getY() + ", " + 
						  foot.getZ();
				
		return result;
	}

	public Vector3d getOldHip() {
		return oldHip;
	}

	public Vector3d getOldKnee() {
		return oldKnee;
	}

	public Vector3d getOldAnkle() {
		return oldAnkle;
	}

	public Vector3d getOldFoot() {
		return oldFoot;
	}

	public Vector3d getHip() {
		return hip;
	}

	public Vector3d getKnee() {
		return knee;
	}

	public Vector3d getAnkle() {
		return ankle;
	}

	public Vector3d getFoot() {
		return foot;
	}

	public Vector3d getfHat() {
		return fHat;
	}

	public Vector3d getlHat() {
		return lHat;
	}

	public Vector3d getoHat() {
		return oHat;
	}
	
	public double getHipAngle() {
		return hipAngle;
	}

	public double getKneeAngle() {
		return kneeAngle;
	}

	public double getAnkleAngle() {
		return ankleAngle;
	}
	
	public void setHipAngle(double a) {
		hipAngle = a;
	}
	
	public void setAnkleAngle(double a) {
		ankleAngle = a;
	}
	
	public void setKneeAngle(double a) {
		kneeAngle = a;
	}

	public LegMovement getLowestMovement() {
		double min = hip.getZ();
		double num = knee.getZ();
		LegMovement currMovement = new LegMovement(hip, oldHip);
		if (num < min) 
			currMovement = new LegMovement(knee, oldKnee);
		num = ankle.getZ();
		if (num < min) 
			currMovement = new LegMovement(ankle, oldAnkle);
		num = foot.getZ(); 
		if (num < min) 
			currMovement = new LegMovement(foot, oldFoot);
		return currMovement;
	}
	
}