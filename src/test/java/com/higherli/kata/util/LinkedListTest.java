package com.higherli.kata.util;

import java.util.LinkedList;

public class LinkedListTest {
	public static void main(String[] args) {
		LinkedList<Integer> li = new LinkedList<>();
		li.add(1);
		li.add(1);
		li.add(1);
		li.add(1);
		li.add(1);
		li.add(1);
		li.add(1);
		System.out.println(li.size());
		li.subList(0, 3).clear(); // 返回指定范围的子子集合测试
		System.out.println(li.size());
		try {
			System.out.println("11");
			return;
		} finally {
			System.out.println("21123");
		}
	}
}
