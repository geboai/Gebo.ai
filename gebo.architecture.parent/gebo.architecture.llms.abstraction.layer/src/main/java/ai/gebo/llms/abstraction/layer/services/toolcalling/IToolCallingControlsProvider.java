/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.abstraction.layer.services.toolcalling;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.ai.chat.model.ToolContext;

/**
 * What a tool receives of the software controlling the model call it runs in: the
 * instances of the interfaces that software loaded for the call (see
 * {@link GeboToolCalling.Builder#provide(Class, Object)}).
 * <p>
 * They are its callbacks, deferred: a tool calls them while the model call runs, the
 * implementation stores what it is told, and the controlling software reads what was
 * stored once the call returns - to suspend an agent that waits for a person's answer
 * or for an external event, to collect what the tools found, to be told what the
 * agent decided. The provider loads nothing of its own: a tool that asks for an
 * interface nobody loaded gets null, and tells the model that what it was asked is
 * not available here.
 * <p>
 * A tool finds its provider in the tool context of the call
 * ({@link GeboToolCallingManagerFactory#controlsOf(ToolContext)}); outside a call made
 * with a {@link GeboToolCalling} it finds {@link #NONE}.
 */
public interface IToolCallingControlsProvider {
	/** The tools context key the provider of a model call travels under. */
	public static final String CONTEXT_KEY = "geboToolCallingControls";

	/** The provider of a call nobody loaded anything for. */
	public static final IToolCallingControlsProvider NONE = new IToolCallingControlsProvider() {
		@Override
		public <T> T get(Class<T> contract) {
			return null;
		}

		@Override
		public Set<Class<?>> contracts() {
			return Set.of();
		}

		@Override
		public String caller() {
			return null;
		}

		@Override
		public String toString() {
			return "IToolCallingControlsProvider.NONE";
		}
	};

	/**
	 * The instance the controlling software loaded for the given interface, null when
	 * it loaded none.
	 */
	public <T> T get(Class<T> contract);

	public default <T> Optional<T> find(Class<T> contract) {
		return Optional.ofNullable(get(contract));
	}

	public default boolean has(Class<?> contract) {
		return get(contract) != null;
	}

	/** The interfaces the controlling software loaded, in the order it loaded them. */
	public Set<Class<?>> contracts();

	/**
	 * Who is calling the model, as the controlling software said (an agent's name in
	 * its network, a service); null when it said nothing.
	 */
	public String caller();

	/** The provider of the call a tool runs in, {@link #NONE} outside one. */
	public static IToolCallingControlsProvider from(ToolContext toolContext) {
		return from(toolContext != null ? toolContext.getContext() : null);
	}

	public static IToolCallingControlsProvider from(Map<String, Object> toolsContext) {
		return toolsContext != null && toolsContext.get(CONTEXT_KEY) instanceof IToolCallingControlsProvider provider
				? provider
				: NONE;
	}
}
