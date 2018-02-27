package com.higherli.kata.stacktest;

/**
 * 类型安全的异构容器
 */
public class FavoriteTest {
	public static void main(String[] args) {
		Favorites f = new Favorites();
		f.putFavorites(String.class, "Java");
		f.putFavorites(Integer.class, 0xcafebabe);
		f.putFavorites(Class.class, Favorites.class);
		System.out.printf("%s, %x, %s%n", f.getFavorites(String.class), f.getFavorites(Integer.class),
				f.getFavorites(Class.class));
	}
}
