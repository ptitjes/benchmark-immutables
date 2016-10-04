package org.jlato.impl.seqs;

import javaslang.collection.Array;
import javaslang.collection.IndexedSeq;
import javaslang.collection.Vector;
import org.jlato.def.SeqImplementation;

import java.util.Iterator;

/**
 * @author Didier Villevalois
 */
public class JavaslangSeq<E> implements SeqImplementation<E> {

	private static abstract class JavaslangFactory implements Factory {

		@Override
		public <E> SeqImplementation<E> empty() {
			return new JavaslangSeq<>(newEmptySeq());
		}

		@Override
		public <E> SeqImplementation<E> of(E[] elements) {
			return new JavaslangSeq<>(newSeq(elements));
		}

		protected abstract <E> IndexedSeq<E> newEmptySeq();

		protected abstract <E> IndexedSeq<E> newSeq(E[] elements);
	}

	public static class ArrayFactory extends JavaslangFactory {

		@Override
		protected <E> IndexedSeq<E> newEmptySeq() {
			return Array.empty();
		}

		@Override
		protected <E> IndexedSeq<E> newSeq(E[] elements) {
			return Array.of(elements);
		}
	}

	public static class VectorFactory extends JavaslangFactory {

		@Override
		protected <E> IndexedSeq<E> newEmptySeq() {
			return Vector.empty();
		}

		@Override
		protected <E> IndexedSeq<E> newSeq(E[] elements) {
			return Vector.of(elements);
		}
	}

	private IndexedSeq<E> seq;

	public JavaslangSeq(IndexedSeq<E> seq) {
		this.seq = seq;
	}

	@Override
	public int size() {
		return seq.size();
	}

	@Override
	public void add(E element) {
		seq = seq.append(element);
	}

	@Override
	public void remove(E element) {
		seq = seq.remove(element);
	}

	@Override
	public boolean contains(E element) {
		return seq.contains(element);
	}

	@Override
	public E get(int index) {
		return seq.get(index);
	}

	@Override
	public Iterator<E> iterator() {
		return seq.iterator();
	}
}
