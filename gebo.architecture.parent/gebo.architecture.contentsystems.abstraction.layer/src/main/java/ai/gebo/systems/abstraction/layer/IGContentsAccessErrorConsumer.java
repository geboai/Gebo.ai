/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.systems.abstraction.layer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import ai.gebo.systems.abstraction.layer.model.ContentsAccessError;

/**
 * Gebo.ai comment agent
 * Interface extending Consumer to handle ContentsAccessError objects.
 */
public interface IGContentsAccessErrorConsumer extends Consumer<ContentsAccessError> {
	
	/**
     * Retrieve a list of encountered ContentsAccessError objects.
     *
     * @return a list of ContentsAccessError objects
     */
	public List<ContentsAccessError> getErrors();

	/**
     * Check if there are any errors.
     *
     * @return true if there are errors, false otherwise
     */
	public default boolean hasErrors() {
		// Get the list of errors and check if it is not null or empty
		List<ContentsAccessError> errs = getErrors();
		return errs != null && !errs.isEmpty();
	}

	/**
     * Provide a default implementation of IGContentsAccessErrorConsumer.
     *
     * @return an instance of HoldingContentsAccessErrorConsumer
     */
	public static IGContentsAccessErrorConsumer defaultImplementation() {
		return new HoldingContentsAccessErrorConsumer();
	};

	/**
     * A static class implementing IGContentsAccessErrorConsumer,
     * which stores received ContentsAccessError objects.
     */
	public static class HoldingContentsAccessErrorConsumer implements IGContentsAccessErrorConsumer {
		
		// List to hold ContentsAccessError objects
		private final List<ContentsAccessError> errors = new ArrayList<ContentsAccessError>();

		/**
         * Accept a ContentsAccessError and store it in the list.
         *
         * @param t the ContentsAccessError object to be accepted
         */
		@Override
		public void accept(ContentsAccessError t) {
			errors.add(t);
		}

		/**
         * Retrieve the list of stored ContentsAccessError objects.
         *
         * @return the list of ContentsAccessError objects
         */
		public List<ContentsAccessError> getErrors() {
			return errors;
		}
	}
}