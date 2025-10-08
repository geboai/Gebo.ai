package ai.gebo.ragsystem.content.graphrag_processor.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class PredicateGrouping {

	public static <T> Stream<List<T>> groupByPredicate(Stream<T> source, Predicate<? super List<T>> p) {
		Objects.requireNonNull(source);
		Objects.requireNonNull(p);

		Iterator<T> it = source.iterator();

		Iterator<List<T>> grouped = new Iterator<>() {
			private List<T> nextGroup = null;
			private boolean computed = false;

			private void computeNext() {
				if (computed)
					return;
				computed = true;
				List<T> current = new ArrayList<>();
				while (it.hasNext()) {
					T n = it.next();

					// Try adding n; if it still satisfies p, keep it.
					current.add(n);
					if (!p.test(current)) {
						// Adding n broke the predicate: emit the group up to before n,
						// then start a new group from n.
						current.remove(current.size() - 1);
						if (!current.isEmpty()) {
							nextGroup = List.copyOf(current);
							// prepare next group starting with n
							current.clear();
							current.add(n);
							// If even the singleton violates p, you must decide what to do.
							// Here we allow a singleton group even if p==false.
							// To enforce p for singletons, throw or skip as needed.
							buffer = List.copyOf(current);
							// Store singleton for next call and stop here.
							stash = buffer;
							return;
						} else {
							// current was empty -> n alone; emit singleton now.
							nextGroup = List.of(n);
							return;
						}
					}
					// else continue accumulating
					buffer = List.copyOf(current); // remember the latest valid
				}
				// no more elements; emit whatever we accumulated
				nextGroup = (buffer == null || buffer.isEmpty()) ? null : buffer;
			}

			private List<T> buffer = null; // last valid snapshot while scanning
			private List<T> stash = null; // when we split, carry the singleton to be emitted next

			@Override
			public boolean hasNext() {
				if (stash != null)
					return true;
				if (!computed)
					computeNext();
				return nextGroup != null;
			}

			@Override
			public List<T> next() {
				if (stash != null) {
					List<T> out = stash;
					stash = null;
					return out;
				}
				if (!computed)
					computeNext();
				if (nextGroup == null)
					throw new NoSuchElementException();
				List<T> out = nextGroup;
				nextGroup = null;
				computed = false; // continue scanning for the next group
				buffer = null;
				return out;
			}
		};

		// ORDERED, NONNULL; sequential only (stateful)
		return StreamSupport
				.stream(Spliterators.spliteratorUnknownSize(grouped, Spliterator.ORDERED | Spliterator.NONNULL), false);
	}
	/**
	   * Greedy grouping: keeps extending the current group while p holds.
	   * If the next element would break p, emits the current valid group.
	   * A new candidate that is not yet valid (e.g., singleton failing p)
	   * is delayed and kept growing until it becomes valid; if it never
	   * does, it is emitted once at end-of-stream.
	   *
	   * Sequential only.
	   */
	  public static <T> Stream<List<T>> groupGreedyDelayInvalid(Stream<T> source,
	                                                            Predicate<? super List<T>> p) {
	    Objects.requireNonNull(source); Objects.requireNonNull(p);

	    final Iterator<T> src = source.iterator();

	    final Iterator<List<T>> it = new Iterator<>() {
	      private List<T> nextOut;         // next group to return
	      private boolean computed = false;

	      private List<T> valid = null;    // current valid group (p(valid) == true)
	      private List<T> invalid = null;  // pending candidate not yet valid (p(invalid) == false)
	      private T pushed = null;         // one element to reprocess (after emitting)

	      private void computeNext() {
	        if (computed) return;
	        computed = true;

	        while (true) {
	          // If we already have something to emit, stop.
	          if (nextOut != null) return;

	          // Get next element to process, honoring a pushed-back one.
	          final T x;
	          if (pushed != null) {
	            x = pushed; pushed = null;
	          } else if (src.hasNext()) {
	            x = src.next();
	          } else {
	            // End-of-stream: flush what's left. Prefer valid; otherwise emit invalid (even if p==false).
	            if (valid != null && !valid.isEmpty()) {
	              nextOut = List.copyOf(valid);
	              valid = null;
	            } else if (invalid != null && !invalid.isEmpty()) {
	              nextOut = List.copyOf(invalid); // delayed singleton (or larger) finally emitted
	              invalid = null;
	            } else {
	              nextOut = null; // truly done
	            }
	            return;
	          }

	          // 1) If we are waiting for a candidate to become valid, try to grow it.
	          if (invalid != null) {
	            invalid.add(x);
	            if (p.test(invalid)) {
	              // It became valid; start tracking as the current valid group.
	              valid = new ArrayList<>(invalid);
	              invalid = null;
	            }
	            // Either way, we don't emit yet; keep scanning.
	            continue;
	          }

	          // 2) We have a valid group already: try to extend it greedily.
	          if (valid != null) {
	            valid.add(x);
	            if (!p.test(valid)) {
	              // Adding x broke p. Emit the previous valid snapshot and push x for next round.
	              valid.remove(valid.size() - 1);
	              nextOut = List.copyOf(valid);
	              valid = null;
	              pushed = x;
	              return;
	            }
	            // still valid, continue consuming
	            continue;
	          }

	          // 3) No current groups at all: start a new one from x.
	          List<T> start = new ArrayList<>(List.of(x));
	          if (p.test(start)) {
	            valid = start;          // immediately valid
	          } else {
	            invalid = start;        // not yet valid (e.g., singleton); delay it
	          }
	        }
	      }

	      @Override public boolean hasNext() {
	        if (!computed) computeNext();
	        return nextOut != null;
	      }

	      @Override public List<T> next() {
	        if (!computed) computeNext();
	        if (nextOut == null) throw new NoSuchElementException();
	        List<T> out = nextOut;
	        nextOut = null;
	        computed = false;
	        return out;
	      }
	    };

	    return StreamSupport
	        .stream(Spliterators.spliteratorUnknownSize(it, Spliterator.ORDERED | Spliterator.NONNULL), false)
	        .onClose(source::close);
	  }
	public static void main(String[] args) {
		List<Integer> dataArray=List.of(10, 10, 10, 10, 10, 10, 10, 20,10,20, 30, 60, 30, 20, 99, 100);
		dataArray.stream().forEach(x->{
			System.out.println(x);
		});
		Stream<Integer> stream = dataArray.stream();
		Predicate<List<Integer>> lessThan100 = (List<Integer> data) -> {
			int sum = 0;
			for (Integer i : data) {
				sum += i;
			}
			return sum <= 100;
		};
		System.out.println("groupByPredicate");
		groupByPredicate(stream, lessThan100).forEach(x->{
			System.out.println(x);
		});
		System.out.println("groupGreedyDelayInvalid");
		stream = dataArray.stream();
		groupGreedyDelayInvalid(stream, lessThan100).forEach(x->{
			System.out.println(x);
		});
	}
}