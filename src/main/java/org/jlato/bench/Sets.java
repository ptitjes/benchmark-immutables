package org.jlato.bench;

import org.jlato.def.SetImplementation;
import org.jlato.util.FactoryCreator;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.io.IOException;
import java.util.Comparator;
import java.util.concurrent.TimeUnit;

/**
 * @author Didier Villevalois
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
@Fork(2)
@Warmup(iterations = 20)
@Measurement(iterations = 10)
public class Sets {

	@Param({
//			"Java HashSet",
//			"Java TreeSet",
			"Dexx HashSet",
			"Dexx TreeSet",
			"Javaslang HashSet",
			"Javaslang TreeSet",
			"Paguro HashSet",
			"Paguro TreeSet",
			"AFundation HashSet",
			"AFundation TreeSet",
	})
	private String implementation;

	@Param({
			"5",
			"10",
			"100",
			"1000",
			"10000",
	})
	private int size;

	private SetImplementation.Factory factory;

	private Integer[] data;
	private SetImplementation<Integer> fullSet;

	@Setup(Level.Iteration)
	public void createData() throws IOException {
		factory = FactoryCreator.factoryForName("org.jlato.impl.sets." + implementation.replace(" ", "Set$") + "Factory");

		data = new Integer[size];
		for (int i = 0; i < size; i++) {
			data[i] = (int) (Math.random() * Integer.MAX_VALUE);
		}
		fullSet = factory.of(INTEGER_COMPARATOR, data);
	}

	@Benchmark
	public Object build() throws Exception {
		return factory.of(INTEGER_COMPARATOR, data);
	}

	@Benchmark
	public int size() throws Exception {
		SetImplementation<Integer> set = fullSet;
		return set.size();
	}

	@Benchmark
	public Object addOneByOne() throws Exception {
		SetImplementation<Integer> set = factory.empty(INTEGER_COMPARATOR);
		for (Integer element : data) {
			set.add(element);
		}
		return set;
	}

	@Benchmark
	public Object removeOneByOne() throws Exception {
		SetImplementation<Integer> set = fullSet;
		for (Integer element : data) {
			set.remove(element);
		}
		return set;
	}

	@Benchmark
	public void containsOneByOne(Blackhole blackhole) throws Exception {
		SetImplementation<Integer> set = fullSet;
		for (Integer element : data) {
			blackhole.consume(set.contains(element));
		}
	}

	@Benchmark
	public void iterateAll(Blackhole blackhole) throws Exception {
		SetImplementation<Integer> set = fullSet;
		for (Integer element : set) {
			blackhole.consume(element);
		}
	}

	static final Comparator<Integer> INTEGER_COMPARATOR = Integer::compareTo;
}
