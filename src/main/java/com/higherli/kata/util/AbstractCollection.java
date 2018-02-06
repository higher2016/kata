package com.higherli.kata.util;

import java.util.Arrays;
import java.util.Iterator;

/**
 * 这里要说一下 skeletal implementation（骨架实现）的概念。
 * 骨架实现类的目的是：最小化实现这个接口所需的工作量，其他类在实现接口的时候直接继承这个抽象类，就可以少写很多接口。 达到快速实现的目的。
 *
 * 根据Collection接口规范中的建议，我们需要提供一个void（无参数）和Collection构造函数。
 */
public abstract class AbstractCollection<E> extends java.util.AbstractCollection<E> implements java.util.Collection<E> {
	protected AbstractCollection() {
	}

	public abstract Iterator<E> iterator();

	public abstract int size();

	public boolean isEmpty() {
		return size() == 0;
	}

	public boolean contains(Object o) {
		Iterator<E> it = iterator();
		if (o == null) {
			while (it.hasNext())
				if (it.next() == null)
					return true;
		} else {
			while (it.hasNext())
				if (o.equals(it.next()))
					return true;
		}
		return false;
	}

	public Object[] toArray() {
		// 从这里可以看出size()返回的值不一定就是迭代器中包含元素数量的值(有可能多也有可能少)，但最终返回的数组的元素以迭代器为准（除非数量大于Integer.MAX_VALUES）
		Object[] r = new Object[size()];
		Iterator<E> it = iterator();
		for (int i = 0; i < r.length; i++) {
			if (!it.hasNext())
				return Arrays.copyOf(r, i);
			r[i] = it.next();
		}
		return it.hasNext() ? finishToArray(r, it) : r;
	}

	/**
	 * This method is equivalent to:
	 * 
	 * <pre>
	 * {
	 * 	&#064;code
	 * 	List&lt;E&gt; list = new ArrayList&lt;E&gt;(size());
	 * 	for (E e : this)
	 * 		list.add(e);
	 * 	return list.toArray(a);
	 * }
	 * </pre>
	 */
	@SuppressWarnings("unchecked")
	public <T> T[] toArray(T[] a) {
		int size = size();
		T[] r = a.length >= size ? a : (T[]) java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), size);
		Iterator<E> it = iterator();

		for (int i = 0; i < r.length; i++) {
			if (!it.hasNext()) {
				if (a == r) {
					r[i] = null;
				} else if (a.length < i) {
					return Arrays.copyOf(r, i);
				} else {
					System.arraycopy(r, 0, a, 0, i);
					if (a.length > i) {
						a[i] = null;
					}
				}
				return a;
			}
			r[i] = (T) it.next();
		}
		return it.hasNext() ? finishToArray(r, it) : r;
	}

	private static final int MAX_ARRAT_SIZE = Integer.MAX_VALUE - 8;

	@SuppressWarnings("unchecked")
	private static <T> T[] finishToArray(T[] r, Iterator<?> it) {
		int i = r.length;
		while (it.hasNext()) {
			int cap = r.length;
			if (i == cap) {
				int newCap = cap + (cap >> 1) + 1;
				if (newCap > MAX_ARRAT_SIZE)
					newCap = hugeCapacity(cap + 1);
				r = Arrays.copyOf(r, newCap);
			}
			r[i++] = (T) it.next();
		}
		return (i == r.length) ? r : Arrays.copyOf(r, i);
	}

	private static int hugeCapacity(int minCapacity) {
		if (minCapacity < 0)
			throw new OutOfMemoryError("Required array size too large");
		return (minCapacity > MAX_ARRAT_SIZE) ? Integer.MAX_VALUE : MAX_ARRAT_SIZE;
	}

	public boolean add(E e) {
		throw new UnsupportedOperationException();
	}

	public boolean remove(Object o) {
		Iterator<E> it = iterator();
		if (o == null) {
			while (it.hasNext()) {
				if (it.next() == null) {
					it.remove();
					return true;
				}
			}
		} else {
			while (it.hasNext()) {
				if (o.equals(it.next())) {
					it.remove();
					return true;
				}
			}
		}
		return false;
	}

	public boolean containsAll(Collection<?> c) {
		for (Object e : c)
			if (!contains(e))
				return false;
		return true;
	}

	public boolean addAll(Collection<? extends E> c) {
		boolean modified = false;
		for (E e : c)
			if (add(e))
				modified = true;
		return modified;
	}
}
