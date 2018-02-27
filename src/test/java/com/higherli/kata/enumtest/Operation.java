package com.higherli.kata.enumtest;

/**
 * 枚举类
 */
public enum Operation {
	PLUS {
		double apply(double x, double y) {
			return x + y;
		}
	},
	MINUS {
		double apply(double x, double y) {
			return x - y;
		}
	},
	TIMES {
		double apply(double x, double y) {
			return x * y;
		}
	},
	DIVIDE {
		double apply(double x, double y) {
			if (y == 0) {
				throw new RuntimeException();
			}
			return x / y;
		}
	};
	abstract double apply(double x, double y);
}
