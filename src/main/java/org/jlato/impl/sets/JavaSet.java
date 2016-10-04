package org.jlato.impl.sets;

import org.jlato.def.SetImplementation;

import java.util.*;

/**
 * @author Didier Villevalois
 */
public class JavaSet<E> implements SetImplementation<E> {

	private static abstract class JavaSetFactory implements Factory {

		@Override
		public <E> SetImplementation<E> empty(Comparator<? super E> c) {
			return new JavaSet<>(newEmptySet(c));
		}

		@Override
		public <E> SetImplementation<E> of(Comparator<? super E> c, E[] elements) {
			return new JavaSet<>(newSet(c, elements));
		}

		protected abstract <E> Set<E> newEmptySet(Comparator<? super E> c);

		protected abstract <E> Set<E> newSet(Comparator<? super E> c, E[] elements);
	}

	public static class HashSetFactory extends JavaSetFactory {

		@Override
		protected <E> Set<E> newEmptySet(Comparator<? super E> c) {
			return new HashSet<>();
		}

		@Override
		protected <E> Set<E> newSet(Comparator<? super E> c, E[] elements) {
			return new HashSet<>(Arrays.asList(elements));
		}
	}

	public static class TreeSetFactory extends JavaSetFactory {

		@Override
		protected <E> Set<E> newEmptySet(Comparator<? super E> c) {
			return new TreeSet<>(c);
		}

		@Override
		protected <E> Set<E> newSet(Comparator<? super E> c, E[] elements) {
			TreeSet<E> set = new TreeSet<>(c);
			set.addAll(Arrays.asList(elements));
			return set;
		}
	}

	private Set<E> set;

	public JavaSet(Set<E> set) {
		this.set = set;
	}

	@Override
	public int size() {
		return set.size();
	}

	@Override
	public void add(E element) {
		set.add(element);
	}

	@Override
	public void remove(E element) {
		set.remove(element);
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
