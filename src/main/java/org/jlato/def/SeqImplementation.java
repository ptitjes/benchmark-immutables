package org.jlato.def;

import java.util.Iterator;

/**
 * @author Didier Villevalois
 */
public interface SeqImplementation<E> extends Iterable<E> {

	interface Factory {

		<E> SeqImplementation<E> empty();

		<E> SeqImplementation<E> of(E[] elements);
	}

	int size();

	void add(E element);

	void remove(E element);

	boolean contains(E element);

	E get(int index);

	Iterator<E> iterator();
}
