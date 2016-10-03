package org.jlato.bench;

import com.github.ptitjes.jmh.report.annotations.Axis;
import com.github.ptitjes.jmh.report.annotations.AxisType;
import com.github.ptitjes.jmh.report.annotations.BenchmarkReport;
import com.github.ptitjes.jmh.report.annotations.Plot;
import org.jlato.def.BenchmarkedSet;
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

	private Integer[] data;
	private Object set;

	@Setup(Level.Iteration)
	public void createData() throws IOException {
		data = new Integer[size];
		for (int i = 0; i < size; i++) {
			data[i] = (int) (Math.random() * Integer.MAX_VALUE);
		}

		BenchmarkedSet lib = BenchmarkedSet.All.get(this.implementation);
		set = lib.build(data, INTEGER_COMPARATOR);
	}

	@Benchmark
	public Object build() throws Exception {
		BenchmarkedSet impl = BenchmarkedSet.All.get(this.implementation);
		return impl.build(data, INTEGER_COMPARATOR);
	}

	@Benchmark
	public Object add() throws Exception {
		BenchmarkedSet impl = BenchmarkedSet.All.get(this.implementation);
		return impl.add(data, INTEGER_COMPARATOR);
	}

	@Benchmark
	public Object remove() throws Exception {
		BenchmarkedSet impl = BenchmarkedSet.All.get(this.implementation);
		return impl.remove(set, data);
	}

	@Benchmark
	public void iterate(Blackhole blackhole) throws Exception {
		BenchmarkedSet impl = BenchmarkedSet.All.get(this.implementation);
		impl.iterate(set, blackhole);
	}

	@Benchmark
	@BenchmarkReport(
			plot = @Plot(
					horizontalAxis = @Axis(type = AxisType.LOGARITHMIC),
					verticalAxis = @Axis(type = AxisType.LOGARITHMIC)
			)
	)
	public void contains(Blackhole blackhole) throws Exception {
		BenchmarkedSet impl = BenchmarkedSet.All.get(this.implementation);
		impl.contains(set, data, blackhole);
	}

	public static final Comparator<Integer> INTEGER_COMPARATOR = new Comparator<Integer>() {
		@Override
		public int compare(Integer o1, Integer o2) {
			return o1.compareTo(o2);
		}
	};
}
