package com.higherli.kata.util;

import java.util.Random;
import java.util.TreeMap;

public class MutexRemoveRandomUtil<T> {
	private TreeMap<Double, T> map = new TreeMap<Double, T>();
	private double endRate = 0;
	private static Random r = new Random();

	public MutexRemoveRandomUtil() {
	}

	public void registerData(double rate, T t) {
		if (rate <= 0) {
			return;
		}
		map.put(endRate, t);
		endRate += rate;
	}

	public T random() {
		double r = avgRandom(0, endRate);
		Double key = map.floorKey(r);
		if (key != null) {
			return map.remove(key);
		} else {
			return null;
		}
	}

	public int size() {
		return map.size();
	}

	/**
	 * Select an <code>double</code> value between <code>min</code> and
	 * <code>max</code> by random. Both <code>min</code> and <code>max</code>
	 * might be selected.
	 */
	public static double avgRandom(double min, double max) {
		if (min > max) {
			double temp = max;
			max = min;
			min = temp;
		}
		double rNum = r.nextDouble() * (max - min);
		return rNum + min;
	}
}
