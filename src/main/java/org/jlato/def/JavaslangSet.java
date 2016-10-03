package org.jlato.def;

import javaslang.collection.HashSet;
import javaslang.collection.Set;
import javaslang.collection.TreeSet;
import org.openjdk.jmh.infra.Blackhole;

import java.util.Comparator;

/**
 * @author Didier Villevalois
 */
public abstract class JavaslangSet implements BenchmarkedSet {

	public static final JavaslangSet HASH_SET = new JavaslangSet() {
		@Override
		public <E> Set<E> empty(Comparator<? super E> c) {
			return HashSet.empty();
		}

		@Override
		public <E> Object build(E[] elements, Comparator<? super E> c) {
			return HashSet.of(elements);
		}
	};

	public static final JavaslangSet TREE_SET = new JavaslangSet() {
		@Override
		public <E> Set<E> empty(Comparator<? super E> c) {
			return TreeSet.of(c);
		}

		@Override
		public <E> Object build(E[] elements, Comparator<? super E> c) {
			return TreeSet.of(c, elements);
		}
	};

	public abstract <E> Set<E> empty(Comparator<? super E> c);

	@Override
	public <E> Object add(E[] elements, Comparator<? super E> c) {
		Set<E> set = empty(c);
		for (E element : elements) {
			set = set.add(element);
		}
		return set;
	}

	@Override
	public <E> Object remove(Object set, E[] elements) {
		Set<E> set1 = (Set<E>) set;
		for (E element : elements) {
			set1 = set1.remove(element);
		}
		return set1;
	}

	@Override
	public <E> void iterate(Object set, Blackhole blackhole) {
		Set<E> set1 = (Set<E>) set;
		for (E e : set1) {
			blackhole.consume(e);
		}
	}

	@Override
	public <E> void contains(Object set, E[] elements, Blackhole blackhole) {
		Set<E> set1 = (Set<E>) set;
		for (E element : elements) {
			blackhole.consume(set1.contains(element));
		}
	}
}
