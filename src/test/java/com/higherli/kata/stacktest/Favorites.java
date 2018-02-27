package com.higherli.kata.stacktest;

import java.util.HashMap;
import java.util.Map;

public class Favorites {
	private Map<Class<?>, Object> favorites = new HashMap<>();

	public <T> void putFavorites(Class<T> type, T instance) {
		if (type == null)
			throw new NullPointerException("Type is null");
		favorites.put(type, instance);
	}

	public <T> T getFavorites(Class<T> type) {
		return type.cast(favorites.get(type));
	}
}
