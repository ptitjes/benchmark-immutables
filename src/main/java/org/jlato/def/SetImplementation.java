package org.jlato.def;

import java.util.Comparator;
import java.util.Iterator;

/**
 * @author Didier Villevalois
 */
public interface SetImplementation<E> extends Iterable<E> {

	interface Factory {

		<E> SetImplementation<E> empty(Comparator<? super E> c);

		<E> SetImplementation<E> of(Comparator<? super E> c, E[] elements);
	}

	int size();

	void add(E element);

	void remove(E element);

	boolean contains(E element);

	Iterator<E> iterator();
}
