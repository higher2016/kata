package com.higherli.kata.util;

import java.util.ArrayList;

public class DifferenFromSetAndAddInList {
	public static void main(String[] args) {
		java.util.List<Integer> as = new ArrayList<Integer>();
		as.add(1);
		as.add(2);
		as.add(3);
		as.add(4);
		as.add(5);
		as.add(2, 2); // add插入
		System.out.println(as);

		java.util.List<Integer> as1 = new ArrayList<Integer>();
		as1.add(1);
		as1.add(2);
		as1.add(3);
		as1.add(4);
		as1.add(5);
		as1.set(2, 2); // set是替换
		System.out.println(as1);

	}
}
