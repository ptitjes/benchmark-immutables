package org.jlato.bench;

import org.jlato.def.BenchmarkedIndexedSeq;
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
public class IndexedSequences {

	@Param({
			"Dexx ArrayList",
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

	private Integer[] data;
	private Object seq;

	@Setup(Level.Iteration)
	public void createData() throws IOException {
		data = new Integer[size];
		for (int i = 0; i < size; i++) {
			data[i] = (int) (Math.random() * Integer.MAX_VALUE);
		}

		BenchmarkedIndexedSeq impl = BenchmarkedIndexedSeq.All.get(this.implementation);
		seq = impl.build(data);
	}

	@Benchmark
	public Object build() throws Exception {
		BenchmarkedIndexedSeq impl = BenchmarkedIndexedSeq.All.get(this.implementation);
		return impl.build(data);
	}

	@Benchmark
	public Object append() throws Exception {
		BenchmarkedIndexedSeq impl = BenchmarkedIndexedSeq.All.get(this.implementation);
		return impl.append(data);
	}

	@Benchmark
	public void iterate(Blackhole blackhole) throws Exception {
		BenchmarkedIndexedSeq impl = BenchmarkedIndexedSeq.All.get(this.implementation);
		impl.iterate(seq, blackhole);
	}
}
