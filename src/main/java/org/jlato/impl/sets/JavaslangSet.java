package org.jlato.impl.sets;

import javaslang.collection.HashSet;
import javaslang.collection.Set;
import javaslang.collection.TreeSet;
import org.jlato.def.SetImplementation;

import java.util.Comparator;
import java.util.Iterator;

/**
 * @author Didier Villevalois
 */
public class JavaslangSet<E> implements SetImplementation<E> {

	private static abstract class JavaslangSetFactory implements Factory {

		@Override
		public <E> SetImplementation<E> empty(Comparator<? super E> c) {
			return new JavaslangSet<>(newEmptySet(c));
		}

		@Override
		public <E> SetImplementation<E> of(Comparator<? super E> c, E[] elements) {
			return new JavaslangSet<>(newSet(c, elements));
		}

		protected abstract <E> Set<E> newEmptySet(Comparator<? super E> c);

		protected abstract <E> Set<E> newSet(Comparator<? super E> c, E[] elements);
	}

	public static class HashSetFactory extends JavaslangSetFactory {

		@Override
		protected <E> Set<E> newEmptySet(Comparator<? super E> c) {
			return HashSet.empty();
		}

		@Override
		protected <E> Set<E> newSet(Comparator<? super E> c, E[] elements) {
			return HashSet.of(elements);
		}
	}

	public static class TreeSetFactory extends JavaslangSetFactory {

		@Override
		protected <E> Set<E> newEmptySet(Comparator<? super E> c) {
			return TreeSet.empty(c);
		}

		@Override
		protected <E> Set<E> newSet(Comparator<? super E> c, E[] elements) {
			return TreeSet.of(c, elements);
		}
	}

	private Set<E> set;

	public JavaslangSet(Set<E> set) {
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
