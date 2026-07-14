/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.secrets.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

import ai.gebo.secrets.model.GeboCustomSecretContent;

/**
 * A {@link GeboCustomSecretContent} that survives a decrypt/re-serialise round
 * trip without losing anything.
 *
 * <p>
 * A custom secret is stored as the JSON of a caller-defined
 * {@link GeboCustomSecretContent} <b>subclass</b> (e.g. {@code UserWorkflowSecret},
 * which adds {@code ticket} and {@code email}), and
 * {@code IGeboSecretsAccessService#getCustomSecretContentById(String, Class)}
 * hands the stored JSON straight to that subclass. The secrets microservice
 * cannot know those subclasses - they live in the calling service - so to ship
 * the content across the wire it must reproduce the stored JSON <i>verbatim</i>
 * and let the caller pick the class.
 * </p>
 *
 * <p>
 * Reading the content into this class captures the declared properties normally
 * and every subclass property into {@link #getAdditionalProperties()}; writing it
 * back out emits both, reconstructing the original JSON. Deserialising into the
 * plain {@link GeboCustomSecretContent} instead would silently drop the subclass
 * fields, and the caller would get back a secret with null {@code ticket} /
 * {@code email}.
 * </p>
 *
 * Gebo.ai comment agent
 */
public class GeboRawCustomSecretContent extends GeboCustomSecretContent {

	private final Map<String, Object> additionalProperties = new LinkedHashMap<>();

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		additionalProperties.put(name, value);
	}
}
