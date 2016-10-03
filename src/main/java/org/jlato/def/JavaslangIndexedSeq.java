package org.jlato.def;

import javaslang.collection.Array;
import javaslang.collection.IndexedSeq;
import javaslang.collection.Vector;
import org.openjdk.jmh.infra.Blackhole;

/**
 * @author Didier Villevalois
 */
public abstract class JavaslangIndexedSeq implements BenchmarkedIndexedSeq {

	public static final JavaslangIndexedSeq ARRAY = new JavaslangIndexedSeq() {
		@Override
		public <E> IndexedSeq<E> empty() {
			return Array.empty();
		}

		@Override
		public <E> Object build(E[] elements) {
			return Array.of(elements);
		}
	};

	public static final JavaslangIndexedSeq VECTOR = new JavaslangIndexedSeq() {
		@Override
		public <E> IndexedSeq<E> empty() {
			return Vector.empty();
		}

		@Override
		public <E> Object build(E[] elements) {
			return Vector.of(elements);
		}
	};

	public abstract <E> IndexedSeq<E> empty();

	@Override
	public <E> Object append(E[] elements) {
		IndexedSeq<E> vector = empty();
		for (E element : elements) {
			vector = vector.append(element);
		}
		return vector;
	}

	@Override
	public <E> void iterate(Object indexedSeq, Blackhole blackhole) {
		IndexedSeq<E> vector1 = (IndexedSeq<E>) indexedSeq;
		for (E e : vector1) {
			blackhole.consume(e);
		}
	}
}
