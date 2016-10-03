package org.jlato.def;

import com.github.andrewoma.dexx.collection.*;
import org.openjdk.jmh.infra.Blackhole;

/**
 * @author Didier Villevalois
 */
public abstract class DexxIndexedList implements BenchmarkedIndexedSeq {

	public static final DexxIndexedList ARRAY_LIST = new DexxIndexedList() {
		@Override
		public <E> IndexedList<E> empty() {
			return ArrayList.empty();
		}

		@Override
		public <E> Object build(E[] elements) {
			return ArrayLists.copyOf(elements);
		}
	};

	public static final DexxIndexedList VECTOR = new DexxIndexedList() {
		@Override
		public <E> IndexedList<E> empty() {
			return Vector.empty();
		}

		@Override
		public <E> Object build(E[] elements) {
			return IndexedLists.copyOf(elements);
		}
	};

	public abstract <E> IndexedList<E> empty();

	@Override
	public <E> Object append(E[] elements) {
		IndexedList<E> vector = empty();
		for (E element : elements) {
			vector = vector.append(element);
		}
		return vector;
	}

	@Override
	public <E> void iterate(Object indexedSeq, Blackhole blackhole) {
		IndexedList<E> vector1 = (IndexedList<E>) indexedSeq;
		for (E e : vector1) {
			blackhole.consume(e);
		}
	}
}
