package com.higherli.kata.stacktest;

import java.util.Arrays;
import java.util.Iterator;

public class StackGenericTest<E> {
	private E[] elements;
	private int size = 0;
	private static final int DEFAULT_INITIAL_CAPACITY = 16;

	@SuppressWarnings("unchecked")
	public StackGenericTest() {
		// 不可能创建不可具体化的类型的数组（这里E只不过是泛型参数，而不是具体化的类）
		elements = new E[DEFAULT_INITIAL_CAPACITY];
		// 解决方案一：创建一个Object的数组，并将它转换成泛型数组类型
		elements = (E[]) new Object[DEFAULT_INITIAL_CAPACITY];
		// 解决方案二：将elements数组改为Object[]
	}

	public void push(E e) {
		ensureCapacity();
		elements[size++] = e;
	}

	public E pop() {
		if (size == 0)
			;
		E result = elements[--size];
		elements[size] = null;
		return result;
	}

	public void pushAll(Iterable<? extends E> iterable) {
		for (E e : iterable)
			push(e);
	}

	public void popAll(java.util.Collection<? super E> collection) {
		while (!isEmpty()) {
			collection.add(pop());
		}
	}

	public boolean isEmpty() {
		return elements.length == 0;
	}

	private void ensureCapacity() {
		if (elements.length == size)
			elements = Arrays.copyOf(elements, 2 * size + 1);
	}
}
