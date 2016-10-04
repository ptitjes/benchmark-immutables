package org.jlato.impl.sets;

import org.jlato.def.SetImplementation;
import org.organicdesign.fp.collections.ImSet;
import org.organicdesign.fp.collections.PersistentHashSet;
import org.organicdesign.fp.collections.PersistentTreeSet;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;

/**
 * @author Didier Villevalois
 */
public class PaguroSet<E> implements SetImplementation<E> {

	private static abstract class PaguroSetFactory implements Factory {

		@Override
		public <E> SetImplementation<E> empty(Comparator<? super E> c) {
			return new PaguroSet<>(newEmptySet(c));
		}

		@Override
		public <E> SetImplementation<E> of(Comparator<? super E> c, E[] elements) {
			return new PaguroSet<>(newSet(c, elements));
		}

		protected abstract <E> ImSet<E> newEmptySet(Comparator<? super E> c);

		protected abstract <E> ImSet<E> newSet(Comparator<? super E> c, E[] elements);
	}

	public static class HashSetFactory extends PaguroSetFactory {

		@Override
		protected <E> ImSet<E> newEmptySet(Comparator<? super E> c) {
			return PersistentHashSet.empty();
		}

		@Override
		protected <E> ImSet<E> newSet(Comparator<? super E> c, E[] elements) {
			return PersistentHashSet.of(Arrays.asList(elements));
		}
	}

	public static class TreeSetFactory extends PaguroSetFactory {

		@Override
		protected <E> ImSet<E> newEmptySet(Comparator<? super E> c) {
			return PersistentTreeSet.ofComp(c);
		}

		@Override
		protected <E> ImSet<E> newSet(Comparator<? super E> c, E[] elements) {
			return PersistentTreeSet.ofComp(c, Arrays.asList(elements));
		}
	}

	private ImSet<E> set;

	public PaguroSet(ImSet<E> set) {
		this.set = set;
	}

	@Override
	public int size() {
		return set.size();
	}

	@Override
	public void add(E element) {
		set = set.put(element);
	}

	@Override
	public void remove(E element) {
		set = set.without(element);
	}

	@Override
	public Iterator<E> iterator() {
		return set.iterator();
	}

	@Override
	public boolean contains(E element) {
		return set.contains(element);
	}
}
