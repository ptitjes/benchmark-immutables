package org.jlato.bench;

import org.jlato.def.SeqImplementation;
import org.jlato.util.FactoryCreator;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.io.IOException;
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
public class Seqs {

	@Param({
//			"Java Array",
			"Dexx Array",
			"Dexx Vector",
			"Javaslang Array",
			"Javaslang Vector",
			"Paguro Vector",
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

	private SeqImplementation.Factory factory;

	private Integer[] data;
	private SeqImplementation<Integer> fullSeq;

	@Setup(Level.Iteration)
	public void createData() throws IOException {
		factory = FactoryCreator.factoryForName("org.jlato.impl.seqs." + implementation.replace(" ", "Seq$") + "Factory");

		data = new Integer[size];
		for (int i = 0; i < size; i++) {
			data[i] = (int) (Math.random() * Integer.MAX_VALUE);
		}
		fullSeq = factory.of(data);
	}

	@Benchmark
	public Object build() throws Exception {
		return factory.of(data);
	}

	@Benchmark
	public int size() throws Exception {
		SeqImplementation<Integer> seq = fullSeq;
		return seq.size();
	}

	@Benchmark
	public Object addOneByOne() throws Exception {
		SeqImplementation<Integer> seq = factory.empty();
		for (Integer element : data) {
			seq.add(element);
		}
		return seq;
	}

	@Benchmark
	public Object removeOneByOne() throws Exception {
		SeqImplementation<Integer> seq = fullSeq;
		for (Integer element : data) {
			seq.remove(element);
		}
		return seq;
	}

	@Benchmark
	public void containsOneByOne(Blackhole blackhole) throws Exception {
		SeqImplementation<Integer> seq = fullSeq;
		for (Integer element : data) {
			blackhole.consume(seq.contains(element));
		}
	}

	@Benchmark
	public void getAll(Blackhole blackhole) throws Exception {
		SeqImplementation<Integer> seq = fullSeq;
		for (int i = 0; i < data.length; i++) {
			blackhole.consume(seq.get(i));
		}
	}

	@Benchmark
	public void iterateAll(Blackhole blackhole) throws Exception {
		SeqImplementation<Integer> seq = fullSeq;
		for (Integer element : seq) {
			blackhole.consume(element);
		}
	}
}
