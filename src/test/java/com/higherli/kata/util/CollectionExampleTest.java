package com.higherli.kata.util;

import java.util.ArrayList;
import java.util.List;

public class CollectionExampleTest {
	public static void main(String[] args) {
//		containAllExample();
		removeAllExample();
	}

	/**
	 *  targetCollection:{1,2,3},  exampleCollection:{1,1,1} will be return true
	 */
	public static void containAllExample() {
		List<Integer> targetList = new ArrayList<Integer>();
		targetList.add(1);
		targetList.add(2);
		targetList.add(3);
		targetList.add(4);
		List<Integer> exampleList = new ArrayList<Integer>();
		exampleList.add(1);
		exampleList.add(1);
		System.out.println(targetList.containsAll(exampleList));
	}
	
	public static void removeAllExample(){
		List<Integer> targetList = new ArrayList<Integer>();
		targetList.add(1);
		targetList.add(1);
		targetList.add(3);
		targetList.add(3);
		targetList.add(3);
		targetList.add(4);
		targetList.add(4);
		List<Integer> exampleList = new ArrayList<Integer>();
		exampleList.add(1);
		exampleList.add(1);
		exampleList.add(3);
		targetList.removeAll(exampleList);
		System.out.println(targetList);
	}
}