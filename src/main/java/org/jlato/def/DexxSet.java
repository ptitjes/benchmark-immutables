package org.jlato.def;

import com.github.andrewoma.dexx.collection.Builder;
import com.github.andrewoma.dexx.collection.HashSet;
import com.github.andrewoma.dexx.collection.Set;
import com.github.andrewoma.dexx.collection.TreeSet;
import org.openjdk.jmh.infra.Blackhole;

import java.util.Comparator;

/**
 * @author Didier Villevalois
 */
public abstract class DexxSet implements BenchmarkedSet {

	public static final DexxSet HASH_SET = new DexxSet() {
		@Override
		public <E> Set<E> empty(Comparator<? super E> c) {
			return HashSet.empty();
		}

		@Override
		public <E> Builder<E, ?> builder(Comparator<? super E> c) {
			return HashSet.<E>factory().newBuilder();
		}
	};

	public static final DexxSet TREE_SET = new DexxSet() {
		@Override
		public <E> Set<E> empty(Comparator<? super E> c) {
			return new TreeSet<>(c);
		}

		@Override
		public <E> Builder<E, ?> builder(Comparator<? super E> c) {
			return TreeSet.<E>factory(c).newBuilder();
		}
	};

	public abstract <E> Set<E> empty(Comparator<? super E> c);

	public abstract <E> Builder<E, ?> builder(Comparator<? super E> c);

	@Override
	public <E> Object build(E[] elements, Comparator<? super E> c) {
		Builder<E, ?> builder = builder(c);
		for (E element : elements) {
			builder.add(element);
		}
		return builder.build();
	}

	@Override
	public <E> Object add(E[] elements, Comparator<? super E> c) {
		Set<E> vector = empty(c);
		for (E element : elements) {
			vector = vector.add(element);
		}
		return vector;
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
