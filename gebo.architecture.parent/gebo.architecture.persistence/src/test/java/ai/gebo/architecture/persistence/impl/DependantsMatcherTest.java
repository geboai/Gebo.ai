/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.persistence.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.ExampleMatcher;

/**
 * The matcher the deletable check finds the dependants of an object with: a dependant
 * type whose fresh instance holds nothing but the reference (a chat profile, dependant
 * of a chat model) has no property to ignore, and Spring Data refuses an empty ignore
 * list - which failed the check and left the admin UI's delete button disabled.
 */
class DependantsMatcherTest {

	@Test
	void aDependantHoldingOnlyTheReferenceIsMatchedAsItIs() {
		ExampleMatcher matcher = GPersistentObjectManagerImpl.matchingOnlyTheReference(new String[0]);

		assertTrue(matcher.getIgnoredPaths().isEmpty());
		assertEquals(ExampleMatcher.NullHandler.IGNORE, matcher.getNullHandler(),
				"its null properties do not take part in the match");
	}

	@Test
	void theInitializedPropertiesOfADependantAreIgnored() {
		ExampleMatcher matcher = GPersistentObjectManagerImpl
				.matchingOnlyTheReference(new String[] { "interactions", "accessibleToAll" });

		assertEquals(Set.of("interactions", "accessibleToAll"), matcher.getIgnoredPaths());
	}
}
