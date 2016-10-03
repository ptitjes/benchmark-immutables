package org.jlato.def;

import org.openjdk.jmh.infra.Blackhole;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Didier Villevalois
 */
public interface BenchmarkedIndexedSeq {

	<E> Object build(E[] elements);

	<E> Object append(E[] elements);

	<E> void iterate(Object indexedSeq, Blackhole blackhole);


	Map<String, BenchmarkedIndexedSeq> All = AllInit.get();

	class AllInit {
		static Map<String, BenchmarkedIndexedSeq> get() {
			Map<String, BenchmarkedIndexedSeq> all = new HashMap<>();

			all.put("Dexx ArrayList", DexxIndexedList.ARRAY_LIST);
			all.put("Dexx Vector", DexxIndexedList.VECTOR);

			all.put("Javaslang Array", JavaslangIndexedSeq.ARRAY);
			all.put("Javaslang Vector", JavaslangIndexedSeq.VECTOR);

			all.put("Paguro Vector", PaguroImList.VECTOR);

			return all;
		}
	}
}
