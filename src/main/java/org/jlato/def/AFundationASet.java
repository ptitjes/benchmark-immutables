package org.jlato.def;

import com.ajjpj.afoundation.collection.immutable.AHashSet;
import com.ajjpj.afoundation.collection.immutable.ARedBlackTreeSet;
import com.ajjpj.afoundation.collection.immutable.ASet;
import org.openjdk.jmh.infra.Blackhole;

import java.util.Comparator;

/**
 * @author Didier Villevalois
 */
public abstract class AFundationASet implements BenchmarkedSet {

	public static final AFundationASet HASH_SET = new AFundationASet() {
		@Override
		public <E> ASet<E> empty(Comparator<? super E> c) {
			return AHashSet.empty();
		}

		@Override
		public <E> Object build(E[] elements, Comparator<? super E> c) {
			return AHashSet.create(elements);
		}
	};

	public static final AFundationASet TREE_SET = new AFundationASet() {
		@Override
		public <E> ASet<E> empty(Comparator<? super E> c) {
			return ARedBlackTreeSet.empty(c);
		}

		@Override
		public <E> Object build(E[] elements, Comparator<? super E> c) {
			return ARedBlackTreeSet.create(c, elements);
		}
	};

	public abstract <E> ASet<E> empty(Comparator<? super E> c);

	@Override
	public <E> Object add(E[] elements, Comparator<? super E> c) {
		ASet<E> set = empty(c);
		for (E element : elements) {
			set = set.added(element);
		}
		return set;
	}

	@Override
	public <E> Object remove(Object set, E[] elements) {
		ASet<E> set1 = (ASet<E>) set;
		for (E element : elements) {
			set1 = set1.removed(element);
		}
		return set1;
	}

	@Override
	public <E> void iterate(Object set, Blackhole blackhole) {
		ASet<E> set1 = (ASet<E>) set;
		for (E e : set1) {
			blackhole.consume(e);
		}
	}

	@Override
	public <E> void contains(Object set, E[] elements, Blackhole blackhole) {
		ASet<E> set1 = (ASet<E>) set;
		for (E element : elements) {
			blackhole.consume(set1.contains(element));
		}
	}
}
