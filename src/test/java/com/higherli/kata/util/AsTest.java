package com.higherli.kata.util;

import java.util.ArrayList;
import java.util.List;


public class AsTest {
	public static void main(String[] args) {
		List<Integer> a = new ArrayList<Integer>();
		a.add(6);
		a.add(5);
		a.add(4);
		a.add(3);
		a.add(2);
		List<Integer> aasa = new ArrayList<Integer>(a);
		aasa.add(1);
		System.out.println(a);
		System.out.println(aasa);
	}
}
