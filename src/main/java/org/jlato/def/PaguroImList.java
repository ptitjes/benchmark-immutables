package org.jlato.def;

import org.openjdk.jmh.infra.Blackhole;
import org.organicdesign.fp.StaticImports;
import org.organicdesign.fp.collections.ImList;
import org.organicdesign.fp.collections.PersistentVector;

/**
 * @author Didier Villevalois
 */
public abstract class PaguroImList implements BenchmarkedIndexedSeq {

	public static final PaguroImList VECTOR = new PaguroImList() {
		@Override
		public <E> ImList<E> empty() {
			return PersistentVector.empty();
		}

		@Override
		public <E> Object build(E[] elements) {
			return StaticImports.vec(elements);
		}
	};

	public abstract <E> ImList<E> empty();

	@Override
	public <E> Object append(E[] elements) {
		ImList<E> vector = empty();
		for (E element : elements) {
			vector = vector.append(element);
		}
		return vector;
	}

	@Override
	public <E> void iterate(Object indexedSeq, Blackhole blackhole) {
		ImList<E> vector1 = (ImList<E>) indexedSeq;
		for (E e : vector1) {
			blackhole.consume(e);
		}
	}
}
