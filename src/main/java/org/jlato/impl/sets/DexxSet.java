package org.jlato.impl.sets;

import com.github.andrewoma.dexx.collection.Builder;
import com.github.andrewoma.dexx.collection.HashSet;
import com.github.andrewoma.dexx.collection.Set;
import com.github.andrewoma.dexx.collection.TreeSet;
import org.jlato.def.SetImplementation;

import java.util.Comparator;
import java.util.Iterator;

/**
 * @author Didier Villevalois
 */
public class DexxSet<E> implements SetImplementation<E> {

	private static abstract class DexxSetFactory implements Factory {

		@Override
		public <E> SetImplementation<E> empty(Comparator<? super E> c) {
			return new DexxSet<>(newEmptySet(c));
		}

		@Override
		public <E> SetImplementation<E> of(Comparator<? super E> c, E[] elements) {
			Builder<E, ? extends Set<E>> builder = newBuilder(c);
			for (E element : elements) {
				builder.add(element);
			}
			return new DexxSet<>(builder.build());
		}

		protected abstract <E> Set<E> newEmptySet(Comparator<? super E> c);

		protected abstract <E> Builder<E, ? extends Set<E>> newBuilder(Comparator<? super E> c);
	}

	public static class HashSetFactory extends DexxSetFactory {

		@Override
		protected <E> Set<E> newEmptySet(Comparator<? super E> c) {
			return HashSet.empty();
		}

		@Override
		protected <E> Builder<E, ? extends Set<E>> newBuilder(Comparator<? super E> c) {
			return HashSet.<E>factory().newBuilder();
		}
	}

	public static class TreeSetFactory extends DexxSetFactory {

		@Override
		protected <E> Set<E> newEmptySet(Comparator<? super E> c) {
			return new TreeSet<>(c);
		}

		@Override
		protected <E> Builder<E, ? extends Set<E>> newBuilder(Comparator<? super E> c) {
			return TreeSet.<E>factory(c).newBuilder();
		}
	}

	private Set<E> set;

	public DexxSet(Set<E> set) {
		this.set = set;
	}

	@Override
	public int size() {
		return set.size();
	}

	@Override
	public void add(E element) {
		set = set.add(element);
	}

	@Override
	public void remove(E element) {
		set = set.remove(element);
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
