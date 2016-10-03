package org.jlato.def;

import org.openjdk.jmh.infra.Blackhole;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Didier Villevalois
 */
public interface BenchmarkedSet {

	<E> Object build(E[] elements, Comparator<? super E> c);

	<E> Object add(E[] elements, Comparator<? super E> c);

	<E> void iterate(Object set, Blackhole blackhole);

	<E> void contains(Object set, E[] elements, Blackhole blackhole);

	<E> Object remove(Object set, E[] elements);


	Map<String, BenchmarkedSet> All = AllInit.get();

	class AllInit {
		static Map<String, BenchmarkedSet> get() {
			Map<String, BenchmarkedSet> all = new HashMap<>();

			all.put("Dexx HashSet", DexxSet.HASH_SET);
			all.put("Dexx TreeSet", DexxSet.TREE_SET);

			all.put("Javaslang HashSet", JavaslangSet.HASH_SET);
			all.put("Javaslang TreeSet", JavaslangSet.TREE_SET);

			all.put("Paguro HashSet", PaguroImSet.HASH_SET);
			all.put("Paguro TreeSet", PaguroImSet.TREE_SET);

			all.put("AFundation HashSet", AFundationASet.HASH_SET);
			all.put("AFundation TreeSet", AFundationASet.TREE_SET);

			return all;
		}
	}
}
