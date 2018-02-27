package com.higherli.kata.util;

import java.util.ArrayList;
import java.util.List;

public class ArrayAndList {
	public static void main(String[] args) {
		// 下面两个例子就显示出数组和List的区别
		Object[] o = new Long[2];
		o[0] = "SS";

		List<Object> s = new ArrayList<Long>();
		
		List[] sa = new List[1];
		// 创建泛型数组是非法的
		List<String>[] sas = new List<String>[1];
	}
}
