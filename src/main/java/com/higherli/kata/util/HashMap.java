package com.higherli.kata.util;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Objects;
import java.util.Set;

/**
 * 加载因子的重要性： As a general rule, the default load factor (.75) offers a good
 * tradeoff between time and space costs. Higher values decrease the space
 * overhead but increase the lookup cost (reflected in most of the operations of
 * the HashMap class, including get and put). The expected number of entries in
 * the map and its load factor should be taken into account when setting its
 * initial capacity, so as to minimize the number of rehash operations. If the
 * initial capacity is greater than the maximum number of entries divided by the
 * load factor, no rehash operations will ever occur.
 */
public class HashMap<K, V> extends java.util.AbstractMap<K, V> implements java.util.Map<K, V>, Cloneable, Serializable {

	private static final long serialVersionUID = -740926842200165021L;

	static final int DEFAULT_INITAL_CAPACITY = 1 << 4; // aka 16

	static final int MAXIMUM_CAPACITY = 1 << 30;

	static final float DEFAULT_LOAD_FACTOR = 0.75f;

	static final int TREEIFY_THRESHOLD = 8;

	static final int UNTREEIFY_THRESHOLD = 6;

	static class Node<K, V> implements java.util.Map.Entry<K, V> {
		final int hash;
		final K key;
		V value;
		Node<K, V> next;

		public Node(int hash, K key, V value, Node<K, V> next) {
			this.hash = hash;
			this.key = key;
			this.value = value;
			this.next = next;
		}

		public K getKey() {
			return key;
		}

		public V getValue() {
			return value;
		}

		public final String toString() {
			return key + "=" + value;
		}

		public final int hashCode() {
			return Objects.hashCode(key) ^ Objects.hashCode(value);
		}

		public V setValue(V newValue) {
			V oldValue = value;
			value = newValue;
			return oldValue;
		}

		public final boolean equals(Object o) {
			if (o == this)
				return true;
			if (o instanceof java.util.Map.Entry) {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>) o;
				if (Objects.equals(key, e.getKey()) && Objects.equals(value, e.getValue()))
					return true;
			}
			return false;
		}
	}

	static final int hash(Object key) {
		int h;
		return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
	}

	/**
	 * 只有当传入对象的运行时类型符合”class C implements Comparable”这个条件时，返回对象的运行时类型，否则返回null。
	 */
	static Class<?> comparableClassFor(Object x) {
		if (x instanceof Comparable) {
			Class<?> c;
			Type[] ts, as;
			Type t;
			ParameterizedType p;
			if ((c = x.getClass()) == String.class)
				return c;
			if ((ts = c.getGenericInterfaces()) != null) {// 判断是否有直接实现的接口
				for (int i = 0; i < ts.length; ++i) {
					// 该接口实现了泛型
					boolean gInterIsPaType = (t = ts[i]) instanceof ParameterizedType;
					// 获取接口不带参数部分的类型对象，且判断该类型是否为Comparable
					boolean paramTypeIsComparable = ((p = (ParameterizedType) t).getRawType() == Comparable.class);
					// 获取泛型参数数组并判断数组是否为空
					boolean isNotNull = (as = p.getActualTypeArguments()) != null;
					// 只有一个泛型参数，且该实现类型是该类型本身
					boolean singleAndSameClass = as.length == 1 && as[0] == c;
					if (gInterIsPaType && paramTypeIsComparable && isNotNull && singleAndSameClass)
						return c;
				}
			}
		}
		return null;
	}

	static final int tableSizeFor(int cap) {
		int n = cap - 1;
		  n |= n >>> 1;
	        n |= n >>> 2;
	        n |= n >>> 4;
	        n |= n >>> 8;
	        n |= n >>> 16;
	        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	static int compareComparables(Class<?> kc, Object k, Object x) {
		return (x == null || x.getClass() != kc ? 0 : ((Comparable) k).compareTo(x));
	}

	@Override
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		// TODO Auto-generated method stub
		return null;
	}

}
