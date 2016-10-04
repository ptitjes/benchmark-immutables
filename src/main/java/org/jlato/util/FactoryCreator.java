package org.jlato.util;

import org.openjdk.jmh.runner.BenchmarkException;

/**
 * @author Didier Villevalois
 */
public class FactoryCreator {

	@SuppressWarnings("unchecked")
	public static <F> F factoryForName(String name) {
		try {
			Class<F> factoryClass = (Class<F>) Class.forName(name);
			return factoryClass.newInstance();
		} catch (Exception e) {
			throw new BenchmarkException(e);
		}
	}
}
