package org.jlato.impl.seqs;

import org.jlato.def.SeqImplementation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @author Didier Villevalois
 */
public class JavaSeq<E> implements SeqImplementation<E> {

	private static abstract class JavaslangFactory implements Factory {

		@Override
		public <E> SeqImplementation<E> empty() {
			return new JavaSeq<>(newEmptySeq());
		}

		@Override
		public <E> SeqImplementation<E> of(E[] elements) {
			return new JavaSeq<>(newSeq(elements));
		}

		protected abstract <E> List<E> newEmptySeq();

		protected abstract <E> List<E> newSeq(E[] elements);
	}

	public static class ArrayFactory extends JavaslangFactory {

		@Override
		protected <E> List<E> newEmptySeq() {
			return new ArrayList<>();
		}

		@Override
		protected <E> List<E> newSeq(E[] elements) {
			return Arrays.asList(elements);
		}
	}

	private List<E> seq;

	public JavaSeq(List<E> seq) {
		this.seq = seq;
	}

	@Override
	public int size() {
		return seq.size();
	}

	@Override
	public void add(E element) {
		seq.add(element);
	}

	@Override
	public void remove(E element) {
		seq.remove(element);
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
