package org.jlato.def;

import org.openjdk.jmh.infra.Blackhole;
import org.organicdesign.fp.StaticImports;
import org.organicdesign.fp.collections.ImSet;
import org.organicdesign.fp.collections.PersistentHashSet;
import org.organicdesign.fp.collections.PersistentTreeSet;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @author Didier Villevalois
 */
public abstract class PaguroImSet implements BenchmarkedSet {

	public static final PaguroImSet HASH_SET = new PaguroImSet() {
		@Override
		public <E> ImSet<E> empty(Comparator<? super E> c) {
			return PersistentHashSet.empty();
		}

		@Override
		public <E> Object build(E[] elements, Comparator<? super E> c) {
			return StaticImports.set(elements);
		}
	};

	public static final PaguroImSet TREE_SET = new PaguroImSet() {
		@Override
		public <E> ImSet<E> empty(Comparator<? super E> c) {
			return PersistentTreeSet.ofComp(c);
		}

		@Override
		public <E> Object build(E[] elements, Comparator<? super E> c) {
			return PersistentTreeSet.ofComp(c, Arrays.asList(elements));
		}
	};

	public abstract <E> ImSet<E> empty(Comparator<? super E> c);

	@Override
	public <E> Object add(E[] elements, Comparator<? super E> c) {
		ImSet<E> set = empty(c);
		for (E element : elements) {
			set = set.put(element);
		}
		return set;
	}

	@Override
	public <E> Object remove(Object set, E[] elements) {
		ImSet<E> set1 = (ImSet<E>) set;
		for (E element : elements) {
			set1 = set1.without(element);
		}
		return set1;
	}

	@Override
	public <E> void iterate(Object set, Blackhole blackhole) {
		ImSet<E> set1 = (ImSet<E>) set;
		for (E e : set1) {
			blackhole.consume(e);
		}
	}

	@Override
	public <E> void contains(Object set, E[] elements, Blackhole blackhole) {
		ImSet<E> set1 = (ImSet<E>) set;
		for (E element : elements) {
			blackhole.consume(set1.contains(element));
		}
	}
}
