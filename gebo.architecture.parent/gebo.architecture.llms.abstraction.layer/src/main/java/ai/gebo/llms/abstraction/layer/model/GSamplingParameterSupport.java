/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.llms.abstraction.layer.model;

import lombok.Getter;
import lombok.Setter;

/**
 * What a chat model takes of one sampling parameter (temperature, top_p): when it is
 * sent, the range its provider's api reference states, and what an empty value means.
 */
@Getter
@Setter
public class GSamplingParameterSupport {
	/** When the parameter is sent. */
	private GSamplingAvailability availability = GSamplingAvailability.ALWAYS;
	/** The least value the provider takes; null when unknown. */
	private Double min = null;
	/** The greatest value the provider takes; null when unknown. */
	private Double max = null;
	/**
	 * Whether an empty value leaves the provider's default; false when Gebo sends its
	 * deterministic default (temperature 0) where the parameter is sent.
	 */
	private boolean providerDefaultWhenEmpty = true;

	public GSamplingParameterSupport() {
	}

	public GSamplingParameterSupport(GSamplingAvailability availability) {
		this.availability = availability;
	}
}
