package com.higherli.kata.util;

import java.util.Iterator;
import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * 继承java.util.Collection是因为避免不必要的麻烦
 */
public interface Collection<E> extends Iterable<E>, java.util.Collection<E> {
	int size();

	boolean isEmpty();

	boolean contains(Object o);

	/**
	 * 并不保证返回的元素的顺序（除非实现类有这个保证）
	 */
	Iterator<E> iterator();

	/**
	 * 如果这个集合保证了它的迭代器返回的元素的顺序，这个方法返回的数组也必须是相同的顺序。
	 * <p>
	 * 返回的数组将是“安全的”，因为这个集合没有引用它。 （换句话说，即使这个集合是由数组支持的，这个方法也必须分配一个新的数组）。
	 * 调用者可以自由修改返回的数组。而不改变该集合
	 */
	Object[] toArray();

	/**
	 * 该方法返回指定类型的数组，数组包含本集合中所有对象。如果数组长度小于集合size就自动加长数组，
	 * 如果数组长度大于集合size则数组多余部分赋null
	 */
	<T> T[] toArray(T[] a);

	boolean add(E e);

	/**
	 * 即使集合中有多个相同的元素也只会按顺序删除掉其中一个（只删除一个）
	 */
	boolean remove(Object o);

	/**
	 * Returns true if this collection contains all of the elements in the
	 * specified collection. 就是说c中每个元素都在该集合中存在就返回true（和顺序无关和数量无关）
	 * 
	 * @example {@link CollectionExampleTest containAllExample()}
	 */
	boolean containsAll(Collection<?> c);

	/**
	 * 目标集合调用该方法后，将不包含c集合中的任何元素
	 * 
	 * @param c
	 * @return
	 */
	boolean removeAll(Collection<?> c);

	/**
	 * default关键字：在接口内部包含了一些默认的方法实现（也就是接口中可以包含方法体，这打破了Java之前版本对接口的语法限制），
	 * 从而使得接口在进行扩展的时候，不会破坏与接口相关的实现类代码。
	 * 
	 * @param filter
	 * @return
	 */
	default boolean removeIf(Predicate<? super E> filter) {
		Objects.requireNonNull(filter);
		boolean removed = false;
		final Iterator<E> each = iterator();
		while (each.hasNext()) {
			if (filter.test(each.next())) {
				each.remove();
				removed = true;
			}
		}
		return removed;
	}

	/**
	 * 判断集合c中所有元素是否都存在于本集合中
	 * 
	 * @param c
	 * @return
	 */
	boolean retainAll(Collection<?> c);

	/**
	 * 删除集合所有元素
	 */
	void clear();

	boolean equals(Object o);

	int hashCode();

	/**
	 * Spliterator和Stream都涉及了并行遍历和函数式编程思想
	 * 关于Spliterator的使用参考：http://blog.163.com/silver9886@126/blog/static/
	 * 359718622017818916446/
	 */
	@Override
	default Spliterator<E> spliterator() {
		return Spliterators.spliterator(this, 0);
	}

	default Stream<E> stream() {
		return StreamSupport.stream(spliterator(), false);
	}

	default Stream<E> parallelStream() {
		return StreamSupport.stream(spliterator(), false);
	}
}
