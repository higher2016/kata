package com.higherli.kata.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GenericTest {
	public static void main(String[] args) {
		List<String> strings = new ArrayList<>();
		unsafeAdd(strings, new Integer(21));
		String s = strings.get(0);
		
//		Set<?> a = new HashSet<>();
//		a.add(null);
//		a.add(new Integer(1));
		
	}

	private static void unsafeAdd(List list, Object o) {
		System.out.println(o.getClass());
		list.add(o);
	}
	private static void unsafeAdd1(List<Object> list, Object o) {
		System.out.println(o.getClass());
		list.add(o);
//		list.add(new Integer(9));
	}
}
