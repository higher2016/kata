package com.higherli.kata.util;

import java.io.Serializable;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Map中包含3个集合视图——set of keys, collection values, set of key-value mappings
 */
public interface Map<K, V> {
	int size();

	boolean isEmpty();

	boolean containsKey(Object key);

	boolean containValue(Object value);

	V get(Object key);

	V put(K key, V value);

	V remove(Object key);

	/**
	 * The effect of this call is equivalent to that of calling put(k, v) on
	 * this map once <strong>for each mapping from key k to value v in the
	 * specified map</strong>.
	 */
	void putAll(Map<? extends K, ? extends V> m);

	void clear();

	/**
	 * The set is backed by the map, <strong>so changes to the map are reflected
	 * in the set, and vice-versa.</strong>也就是说修改这个方法返回的Set中的会导致其原Map对象的键值对改变.
	 * 比如删除Set中的某个值会导致Map中所对应的键值对也被删除.
	 */
	java.util.Set<K> keySet();

	/**
	 * 这个方法和keySet方法差不多The collection is backed by the map, so changes to the
	 * map are reflected in the collection, and vice-versa.
	 */
	java.util.Collection<V> values();

	/**
	 * The set is backed by the map, so changes to the map are reflected in the
	 * set, and vice-versa.
	 */
	java.util.Set<Map.Entry<K, V>> entrySet();

	interface Entry<K, V> {
		K getKey();

		V getValue();

		V setValue(V value);

		boolean equals(Object o);

		int hashCode();

		public static <K extends Comparable<? super K>, V> Comparator<Map.Entry<K, V>> comparingByKey() {
			return (Comparator<Map.Entry<K, V>> & Serializable) (c1, c2) -> c1.getKey().compareTo(c2.getKey());
		}

		public static <K, V extends Comparable<? super V>> Comparator<Map.Entry<K, V>> comparingByValue() {
			return (Comparator<Map.Entry<K, V>> & Serializable) (c1, c2) -> c1.getValue().compareTo(c2.getValue());
		}

		public static <K, V> Comparator<Map.Entry<K, V>> comparingByKey(Comparator<? super K> cmp) {
			Objects.requireNonNull(cmp);
			return (Comparator<Map.Entry<K, V>> & Serializable) (c1, c2) -> cmp.compare(c1.getKey(), c2.getKey());
		}

		public static <K, V> Comparator<Map.Entry<K, V>> comparingByValue(Comparator<? super V> cmp) {
			Objects.requireNonNull(cmp);
			return (Comparator<Map.Entry<K, V>> & Serializable) (c1, c2) -> cmp.compare(c1.getValue(), c2.getValue());
		}
	}

	boolean equals(Object o);

	int hashCode();

	/**
	 * 以前还没发现过这个方法，挺实用的
	 */
	default V getOrDefault(Object key, V defaultValue) {
		V v;
		return ((v = get(key)) != null || containsKey(key)) ? v : defaultValue;
	}

	/**
	 * 这个方法是用于Lambda表达式的而创建的。<br>
	 * 如： m.forEach((k, v) -> System.out.println(k + ": " + v); });
	 */
	default void forEach(BiConsumer<? super K, ? super V> action) {
		Objects.requireNonNull(action);
		for (Map.Entry<K, V> entry : entrySet()) {
			K k;
			V v;
			try {
				k = entry.getKey();
				v = entry.getValue();
			} catch (IllegalStateException e) {
				throw new ConcurrentModificationException(e);
			}
			action.accept(k, v);
		}
	}

	default void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
		Objects.requireNonNull(function);
		for (Map.Entry<K, V> entry : entrySet()) {
			K k;
			V v;
			try {
				k = entry.getKey();
				v = entry.getValue();
			} catch (IllegalStateException ise) {
				// this usually means the entry is no longer in the map.
				throw new ConcurrentModificationException(ise);
			}

			v = function.apply(k, v);

			try {
				entry.setValue(v);
			} catch (IllegalStateException ise) {
				// this usually means the entry is no longer in the map.
				throw new ConcurrentModificationException(ise);
			}
		}
	}

	default V putIfAbsent(K key, V value) {
		V v = get(key);
		if (v == null) {
			v = put(key, value);
		}
		return v;
	}

	default boolean remove(Object key, Object value) {
		Object curValue = get(key);
		if (!Objects.equals(curValue, value) || (curValue == null && !containsKey(key))) {
			return false;
		}
		remove(key);
		return true;
	}

	default boolean replace(K key, V oldValue, V newValue) {
		Object curValue = get(key);
		if (!Objects.equals(curValue, oldValue) || (curValue == null && !containsKey(key))) {
			return false;
		}
		put(key, newValue);
		return true;
	}

	default V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
		Objects.requireNonNull(mappingFunction);
		V v;
		if ((v = get(key)) == null) {
			V newValue;
			if ((newValue = mappingFunction.apply(key)) != null) {
				put(key, newValue);
				return newValue;
			}
		}
		return v;
	}

	default V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		V oldValue;
		if ((oldValue = get(key)) != null) {
			V newValue = remappingFunction.apply(key, oldValue);
			if (newValue != null) {
				put(key, newValue);
				return newValue;
			} else {
				remove(key);
				return null;
			}
		} else {
			return null;
		}
	}

	default V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		V oldValue = get(key);

		V newValue = remappingFunction.apply(key, oldValue);
		if (newValue == null) {
			// delete mapping
			if (oldValue != null || containsKey(key)) {
				// something to remove
				remove(key);
				return null;
			} else {
				// nothing to do. Leave things as they were.
				return null;
			}
		} else {
			// add or replace old mapping
			put(key, newValue);
			return newValue;
		}
	}

	default V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		Objects.requireNonNull(value);
		V oldValue = get(key);
		V newValue = (oldValue == null) ? value : remappingFunction.apply(oldValue, value);
		if (newValue == null) {
			remove(key);
		} else {
			put(key, newValue);
		}
		return newValue;
	}
}
