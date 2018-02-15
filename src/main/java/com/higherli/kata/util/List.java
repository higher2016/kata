package com.higherli.kata.util;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.UnaryOperator;

/**
 * List是一个有序的集合(An ordered collection)或者叫序列(sequence)
 *
 */
public interface List<E> extends java.util.Collection<E> {
	int size();

	boolean isEmpty();

	boolean contains(Object o);

	/**
	 * 返回的Iterator是有序的(List是有序的)
	 */
	Iterator<E> iterator();

	/**
	 * 返回的数组也是有序的
	 */
	Object[] toArray();

	<T> T[] toArray(T[] a);

	/**
	 * Appends the specified element to the end of this list (optional
	 * operation). 这个方法是可选操作，使用者可以选择不实现(比如Arrays.asList()返回的List就是),这个时候
	 * List代表一个不可变的List。调用该方法就会抛出UnsupportedOperationException
	 */
	boolean add(E e);

	boolean remove(Object o);

	boolean containsAll(Collection<?> c);

	boolean addAll(Collection<? extends E> c);

	boolean addAll(int index, Collection<? extends E> c);

	boolean removeAll(Collection<?> c);

	boolean retainAll(Collection<?> c);

	default void replaceAll(UnaryOperator<E> operator) {
		Objects.requireNonNull(operator);
		final ListIterator<E> li = this.listIterator();
		while (li.hasNext()) {
			li.set(operator.apply(li.next()));
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	default void sort(Comparator<? super E> c) {
		Object[] a = this.toArray();
		Arrays.sort(a, (Comparator) c);
		ListIterator<E> i = this.listIterator();
		for (Object e : a) {
			i.next();
			i.set((E) e);
		}
	}

	void clear();

	boolean equals(Object o);

	int hashCode();

	E get(int index);

	/**
	 * set方法是替换调下标index的元素为element. 返回的是这个位置上被替换的原来的元素.<br>
	 * 注意如果index < 0 || index > size()都会抛IndexOutOfBoundsException
	 */
	E set(int index, E element);

	/**
	 * 向本集合的index位置insert（插入）元素element。注意这不是替换，而是插队。<br>
	 * 注意如果index < 0 || index > size()都会抛IndexOutOfBoundsException
	 */
	void add(int index, E element);

	E remove(int index);

	/**
	 * 返回实例o在列表中第一次出现的位置
	 */
	int indexOf(Object o);

	/**
	 * 返回实例o在列表中最后一次出现的位置
	 */
	int lastIndexOf(Object o);

	/**
	 * 关于ListIterator的用法：com.higherli.kata.util.ListIteratorTest
	 */
	ListIterator<E> listIterator();

	ListIterator<E> listIterator(int index);

	/**
	 * 返回列表从fromIndex（包含）到toIndex（不包含）的子集合。对该子集合的操作相当于对本对象的操作
	 */
	List<E> subList(int fromIndex, int toIndex);

	@Override
	default Spliterator<E> spliterator() {
		// 在接口中加上恒量参数是一个不好的设计，比如：Spliterator.ORDERED
		return Spliterators.spliterator(this, Spliterator.ORDERED);
	}

}
