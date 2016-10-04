package org.jlato.impl.seqs;

import com.github.andrewoma.dexx.collection.*;
import org.jlato.def.SeqImplementation;

import java.util.Iterator;

/**
 * @author Didier Villevalois
 */
public class DexxSeq<E> implements SeqImplementation<E> {

	private static abstract class JavaslangFactory implements Factory {

		@Override
		public <E> SeqImplementation<E> empty() {
			return new DexxSeq<>(newEmptySeq());
		}

		@Override
		public <E> SeqImplementation<E> of(E[] elements) {
			return new DexxSeq<>(newSeq(elements));
		}

		protected abstract <E> IndexedList<E> newEmptySeq();

		protected abstract <E> IndexedList<E> newSeq(E[] elements);
	}

	public static class ArrayFactory extends JavaslangFactory {

		@Override
		protected <E> IndexedList<E> newEmptySeq() {
			return ArrayList.empty();
		}

		@Override
		protected <E> IndexedList<E> newSeq(E[] elements) {
			return ArrayLists.of(elements);
		}
	}

	public static class VectorFactory extends JavaslangFactory {

		@Override
		protected <E> IndexedList<E> newEmptySeq() {
			return Vector.empty();
		}

		@Override
		protected <E> IndexedList<E> newSeq(E[] elements) {
			return IndexedLists.copyOf(elements);
		}
	}

	private IndexedList<E> seq;

	public DexxSeq(IndexedList<E> seq) {
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
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean contains(E element) {
		throw new UnsupportedOperationException();
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
