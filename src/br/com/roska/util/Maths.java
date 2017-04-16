package br.com.roska.util;

import java.awt.Point;

public class Maths {

	public static float getAngle(Point target, Point target2) {
		float angle = (float) Math.toDegrees(Math.atan2(target.y - target2.y, target.x - target2.x));

		if (angle < 0) {
			angle += 360;
		}

		return angle;
	}
}
