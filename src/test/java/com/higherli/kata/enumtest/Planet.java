package com.higherli.kata.enumtest;

/**
 * 枚举类，枚举类中为什么有方法，为什么有域（将数据与它的常量关联起来）
 */
public enum Planet {
	MERCURY(3.302e+23, 2.439e6), VENUS(4.869e+24, 6.052E6), EARTH(5.975e+24, 6.378e6), MARS(6.419e23, 3.393e6), JUPITER(
			1.899e+27, 7.149e7), SATURN(5.685e+26, 6.027e7), URANUS(8.683e+25, 2.556e7), NEPTUNE(1.024e+26, 2.477e7),;

	private final double mass;
	private final double radius;
	private final double surfaceGravity;
	private static final double G = 6.67300E-11;

	Planet(double mass, double radius) {
		this.mass = mass;
		this.radius = radius;
		this.surfaceGravity = G * mass / (radius * radius);
	}

	public double getMass() {
		return mass;
	}

	public double getRadius() {
		return radius;
	}

	public double getSurfaceGravity() {
		return surfaceGravity;
	}

	public double surfaceWeight(double mass) {
		return mass * surfaceGravity;
	}

}
