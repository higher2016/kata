package com.higherli.kata.util;

import java.util.LinkedList;
import java.util.ListIterator;

public class ListIteratorTest {
	public static void main(String[] args) {
		java.util.List<Integer> staff = new LinkedList<>();
		staff.add(1);
		staff.add(2);
		staff.add(3);
		staff.add(4);
		staff.add(5);
		ListIterator<Integer> itor = staff.listIterator();
		while(itor.hasNext()){
			System.out.println(itor.next());
			itor.set(1); // set的替换作用
		}
		System.out.println(staff);
	}
}
