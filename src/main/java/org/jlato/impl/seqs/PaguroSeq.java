package org.jlato.impl.seqs;

import org.jlato.def.SeqImplementation;
import org.organicdesign.fp.collections.ImList;
import org.organicdesign.fp.collections.PersistentVector;

import java.util.Arrays;
import java.util.Iterator;

/**
 * @author Didier Villevalois
 */
public class PaguroSeq<E> implements SeqImplementation<E> {

	private static abstract class PaguroFactory implements Factory {

		@Override
		public <E> SeqImplementation<E> empty() {
			return new PaguroSeq<>(newEmptySeq());
		}

		@Override
		public <E> SeqImplementation<E> of(E[] elements) {
			return new PaguroSeq<>(newSeq(elements));
		}

		protected abstract <E> ImList<E> newEmptySeq();

		protected abstract <E> ImList<E> newSeq(E[] elements);
	}

	public static class VectorFactory extends PaguroFactory {

		@Override
		protected <E> ImList<E> newEmptySeq() {
			return PersistentVector.empty();
		}

		@Override
		protected <E> ImList<E> newSeq(E[] elements) {
			return PersistentVector.ofIter(Arrays.asList(elements));
		}
	}

	private ImList<E> seq;

	public PaguroSeq(ImList<E> seq) {
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
