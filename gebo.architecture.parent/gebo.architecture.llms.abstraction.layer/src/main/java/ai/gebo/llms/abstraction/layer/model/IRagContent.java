/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.abstraction.layer.model;

import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * Gebo.ai comment agent
 * 
 * Interface representing content with methods to manage token and byte counts, 
 * and recalculate size based on child content.
 */
public interface IRagContent {

	/**
	 * Gets the number of tokens.
	 * 
	 * @return the number of tokens
	 */
	public long getNTokens();

	/**
	 * Gets the number of bytes.
	 * 
	 * @return the number of bytes
	 */
	public long getNBytes();

	/**
	 * Sets the number of tokens.
	 * 
	 * @param n the number of tokens to set
	 */
	public void setNTokens(long n);

	/**
	 * Sets the number of bytes.
	 * 
	 * @param n the number of bytes to set
	 */
	public void setNBytes(long n);

	/**
	 * A Consumer implementation that calculates the total number of tokens and bytes
	 * by iterating over a collection of IRagContent objects.
	 */
	class CounterConsumer implements Consumer<IRagContent> {
		long nTokenTotal = 0, nBytesTotal = 0;

		/**
		 * Accepts an IRagContent instance, recalculates its size, and accumulates the 
		 * total number of tokens and bytes.
		 * 
		 * @param t the IRagContent instance
		 */
		@Override
		public void accept(IRagContent t) {
			t.recalculateSize();
			nTokenTotal += t.getNTokens();
			nBytesTotal += t.getNBytes();
		}
	}

	/**
	 * Provides a stream of child IRagContent objects.
	 * 
	 * @return a Stream of IRagContent child objects
	 */
	Stream<IRagContent> streamChilds();

	/**
	 * Recalculates the size of the content by iterating through its children 
	 * with a CounterConsumer to update the total content size in tokens and bytes.
	 */
	public default void recalculateSize() {
		CounterConsumer consumer = new CounterConsumer();
		Stream<IRagContent> childs = streamChilds();
		// For each child, apply the consumer to calculate totals
		childs.forEach(consumer);
		setNBytes(consumer.nBytesTotal);
		setNTokens(consumer.nTokenTotal);
	}
}