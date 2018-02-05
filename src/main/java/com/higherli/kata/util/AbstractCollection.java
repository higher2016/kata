package com.higherli.kata.util;

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
}
