package com.higherli.kata.util;

import java.util.Iterator;
import java.util.Set;

/**
 * 必须要子类实现的方法entrySet()
 */
public abstract class AbstractMap<K, V> implements java.util.Map<K, V> {

	protected AbstractMap() {
		super();
	}

	public int size() {
		return entrySet().size();
	}

	public boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * This implementation iterates over entrySet() searching for an entry with
	 * the specified value.
	 */
	public boolean containsValue(Object value) {
		Iterator<Entry<K, V>> i = entrySet().iterator();
		if (value == null) {
			while (i.hasNext()) {
				Entry<K, V> e = i.next();
				if (e.getValue() == null)
					return true;
			}
		} else {
			while (i.hasNext()) {
				Entry<K, V> e = i.next();
				if (value.equals(e.getValue()))
					return true;
			}
		}
		return false;
	}

	/**
	 * This implementation iterates over entrySet() searching for an entry with
	 * the specified value.
	 */
	public boolean containsKey(Object key) {
		Iterator<Entry<K, V>> i = entrySet().iterator();
		if (key == null) {
			while (i.hasNext()) {
				Entry<K, V> e = i.next();
				if (e.getKey() == null)
					return true;
			}
		} else {
			while (i.hasNext()) {
				Entry<K, V> e = i.next();
				if (key.equals(e.getKey()))
					return true;
			}
		}
		return false;
	}

	/**
	 * This implementation iterates over entrySet() searching for an entry with
	 * the specified value. <br>
	 * 现在才知道原来AbstractMap的get方法是遍历实现的,还以为会是HashMap那样
	 */
	public V get(Object key) {
		Iterator<Entry<K, V>> i = entrySet().iterator();
		if (key == null) {
			while (i.hasNext()) {
				Entry<K, V> e = i.next();
				if (e.getKey() == null)
					return e.getValue();
			}
		} else {
			while (i.hasNext()) {
				Entry<K, V> e = i.next();
				if (key.equals(e.getKey()))
					return e.getValue();
			}
		}
		return null;
	}

	/**
	 * AbstractMap默认是不现实put方法的，也就是说默认实现的是一个不可变的集合类
	 */
	public V put(K key, V value) {
		throw new UnsupportedOperationException();
	}

	/**
	 * 这个方法写的挺有意思的。注意这里即使返回的是null也不代表他没有从Map中移除键值对,value可以是null
	 */
	public V remove(Object key) {
		Iterator<Entry<K, V>> i = entrySet().iterator();
		Entry<K, V> correctEntry = null;
		if (key == null) {
			while (correctEntry == null && i.hasNext()) {
				Entry<K, V> e = i.next();
				if (e.getKey() == null)
					correctEntry = e;
			}
		} else {
			while (correctEntry == null && i.hasNext()) {
				Entry<K, V> e = i.next();
				if (key.equals(e.getKey()))
					correctEntry = e;
			}
		}
		V oldValue = null;
		if (correctEntry != null) {
			oldValue = correctEntry.getValue();
			i.remove();
		}
		return oldValue;
	}

	/**
	 * This implementation iterates over the specified map's entrySet()
	 * collection, and calls this map's put operation once for each entry
	 * returned by the iteration.
	 */
	public void putAll(Map<? extends K, ? extends V> m) {
		for (Map.Entry<? extends K, ? extends V> e : m.entrySet()) {
			put(e.getKey(), e.getValue());
		}
	}

	public void clear() {
		entrySet().clear();
	}

	transient Set<K> keySet;
	transient java.util.Collection<V> values;

	public Set<K> keySet() {
		Set<K> ks = keySet;
		if (ks == null) {
			ks = new java.util.AbstractSet<K>() {
				public Iterator<K> iterator() {
					return new Iterator<K>() {
						private Iterator<Entry<K, V>> i = entrySet().iterator();

						public boolean hasNext() {
							return i.hasNext();
						}

						public K next() {
							return i.next().getKey();
						}

						public void remove() {
							i.remove();
						}
					};
				}

				public int size() {
					return AbstractMap.this.size();
				}

				public boolean isEmpty() {
					return AbstractMap.this.isEmpty();
				}

				public void clear() {
					AbstractMap.this.clear();
				}

				public boolean contains(Object k) {
					return AbstractMap.this.containsKey(k);
				}
			};
			keySet = ks;
		}
		return ks;
	}

	public java.util.Collection<V> values() {
		java.util.Collection<V> vals = values;
		if (vals == null) {
			vals = new java.util.AbstractCollection<V>() {
				public Iterator<V> iterator() {
					return new Iterator<V>() {
						private Iterator<Entry<K, V>> i = entrySet().iterator();

						public boolean hasNext() {
							return i.hasNext();
						}

						public V next() {
							return i.next().getValue();
						}

						public void remove() {
							i.remove();
						}
					};
				}

				public int size() {
					return AbstractMap.this.size();
				}

				public boolean isEmpty() {
					return AbstractMap.this.isEmpty();
				}

				public void clear() {
					AbstractMap.this.clear();
				}

				public boolean contains(Object k) {
					return AbstractMap.this.containsKey(k);
				}
			};
			values = vals;
		}
		return vals;
	}

	public abstract Set<Entry<K, V>> entrySet();

	public boolean equals(Object o) {
		if (o == this)
			return true;

		if (!(o instanceof Map))
			return false;
		Map<?, ?> m = (Map<?, ?>) o;
		if (m.size() != size())
			return false;
		try {
			Iterator<Entry<K, V>> i = entrySet().iterator();
			while (i.hasNext()) {
				Entry<K, V> e = i.next();
				K key = e.getKey();
				V value = e.getValue();
				if (value == null) {
					if (!(m.get(key) == null && m.containsKey(key)))
						return false;
				} else {
					if (!value.equals(m.get(key)))
						return false;
				}
			}
		} catch (ClassCastException unused) {
			return false;
		} catch (NullPointerException unused) {
			return false;
		}

		return true;
	}

	public int hashCode() {
		int h = 0;
		Iterator<Entry<K, V>> i = entrySet().iterator();
		while (i.hasNext())
			h += i.next().hashCode();
		return h;
	}

	public String toString() {
		Iterator<Entry<K, V>> i = entrySet().iterator();
		if (!i.hasNext())
			return "{}";

		StringBuilder sb = new StringBuilder();
		sb.append('{');
		for (;;) {
			Entry<K, V> e = i.next();
			K key = e.getKey();
			V value = e.getValue();
			sb.append(key == this ? "(this Map)" : key);
			sb.append('=');
			sb.append(value == this ? "(this Map)" : value);
			if (!i.hasNext())
				return sb.append('}').toString();
			sb.append(',').append(' ');
		}
	}

	protected Object clone() throws CloneNotSupportedException {
		AbstractMap<?, ?> result = (AbstractMap<?, ?>) super.clone();
		result.keySet = null;
		result.values = null;
		return result;
	}

	/**
	 * Utility method for SimpleEntry and SimpleImmutableEntry. Test for
	 * equality, checking for nulls.
	 *
	 * NB: Do not replace with Object.equals until JDK-8015417 is resolved.
	 */
	private static boolean eq(Object o1, Object o2) {
		return o1 == null ? o2 == null : o1.equals(o2);
	}

	public static class SimpleEntry<K, V> implements java.util.Map.Entry<K, V>, java.io.Serializable {
		private static final long serialVersionUID = -3843005668294678801L;

		private final K key;
		private V value;

		public SimpleEntry(K key, V value) {
			this.key = key;
			this.value = value;
		}

		public SimpleEntry(Entry<? extends K, ? extends V> entry) {
			this.key = entry.getKey();
			this.value = entry.getValue();
		}

		public K getKey() {
			return key;
		}

		public V getValue() {
			return value;
		}

		public V setValue(V value) {
			V oldValue = this.value;
			this.value = value;
			return oldValue;
		}

		public boolean equals(Object o) {
			if (!(o instanceof java.util.Map.Entry))
				return false;
			Map.Entry<?, ?> e = (Map.Entry<?, ?>) o;
			return eq(key, e.getKey()) && eq(value, e.getValue());
		}

		public int hashCode() {
			return (key == null ? 0 : key.hashCode()) ^ (value == null ? 0 : value.hashCode());
		}

		public String toString() {
			return key + "=" + value;
		}
	}

	public static class SimpleImmutableEntry<K, V> implements java.util.Map.Entry<K, V>, java.io.Serializable {
		private static final long serialVersionUID = -3843005668294678801L;
		
		private final K key;
		private final V value;
		
		public SimpleImmutableEntry(K key, V value) {
			this.key = key;
			this.value = value;
		}
		
		public SimpleImmutableEntry(Entry<? extends K, ? extends V> entry) {
			this.key = entry.getKey();
			this.value = entry.getValue();
		}
		
		public K getKey() {
			return key;
		}
		
		public V getValue() {
			return value;
		}
		
		public V setValue(V value) {
			throw new UnsupportedOperationException();
		}
		
		public boolean equals(Object o) {
			if (!(o instanceof java.util.Map.Entry))
				return false;
			Map.Entry<?, ?> e = (Map.Entry<?, ?>) o;
			return eq(key, e.getKey()) && eq(value, e.getValue());
		}
		
		public int hashCode() {
			return (key == null ? 0 : key.hashCode()) ^ (value == null ? 0 : value.hashCode());
		}
		
		public String toString() {
			return key + "=" + value;
		}
	}
}
