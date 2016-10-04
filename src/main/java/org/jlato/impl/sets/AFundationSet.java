package org.jlato.impl.sets;

import com.ajjpj.afoundation.collection.immutable.AHashSet;
import com.ajjpj.afoundation.collection.immutable.ARedBlackTreeSet;
import com.ajjpj.afoundation.collection.immutable.ASet;
import org.jlato.def.SetImplementation;

import java.util.Comparator;
import java.util.Iterator;

/**
 * @author Didier Villevalois
 */
public class AFundationSet<E> implements SetImplementation<E> {

	private static abstract class AFundationSetFactory implements Factory {

		@Override
		public <E> SetImplementation<E> empty(Comparator<? super E> c) {
			return new AFundationSet<>(newEmptySet(c));
		}

		@Override
		public <E> SetImplementation<E> of(Comparator<? super E> c, E[] elements) {
			return new AFundationSet<>(newSet(c, elements));
		}

		protected abstract <E> ASet<E> newEmptySet(Comparator<? super E> c);

		protected abstract <E> ASet<E> newSet(Comparator<? super E> c, E[] elements);
	}

	public static class HashSetFactory extends AFundationSetFactory {

		@Override
		protected <E> ASet<E> newEmptySet(Comparator<? super E> c) {
			return AHashSet.empty();
		}

		@Override
		protected <E> ASet<E> newSet(Comparator<? super E> c, E[] elements) {
			return AHashSet.create(elements);
		}
	}

	public static class TreeSetFactory extends AFundationSetFactory {

		@Override
		protected <E> ASet<E> newEmptySet(Comparator<? super E> c) {
			return ARedBlackTreeSet.empty(c);
		}

		@Override
		protected <E> ASet<E> newSet(Comparator<? super E> c, E[] elements) {
			return ARedBlackTreeSet.create(c, elements);
		}
	}

	private ASet<E> set;

	public AFundationSet(ASet<E> set) {
		this.set = set;
	}

	@Override
	public int size() {
		return set.size();
	}

	@Override
	public void add(E element) {
		set = set.added(element);
	}

	@Override
	public void remove(E element) {
		set = set.removed(element);
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
