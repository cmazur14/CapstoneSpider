package spider.algorithm;

import javax.vecmath.Vector3d;

public class LegMovement {
	
	private Vector3d move;
	private Vector3d oldPosition;
	private Vector3d newPosition;
		
	public LegMovement(Vector3d oldPos, Vector3d newPos) {
		oldPosition = oldPos;
		newPosition = newPos;
		move = (Vector3d) newPos.clone();
		Vector3d tempVec = (Vector3d) oldPos.clone();
		tempVec.negate();
		move.add(tempVec);
	}
	
	public Vector3d getMove() {
		return move;
	}

	public Vector3d getOldPosition() {
		return oldPosition;
	}

	public Vector3d getNewPosition() {
		return newPosition;
	}
	
	

}
