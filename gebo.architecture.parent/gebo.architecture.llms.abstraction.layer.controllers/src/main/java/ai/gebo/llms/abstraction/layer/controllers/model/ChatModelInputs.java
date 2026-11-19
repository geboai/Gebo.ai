/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.llms.abstraction.layer.controllers.model;

import java.util.List;

import ai.gebo.llms.abstraction.layer.model.GModelCarriedMediaTypes;
import ai.gebo.llms.abstraction.layer.model.GModelInputModality;
import lombok.Data;

/**
 * What a configured chat model takes as input, for the administrator: what its
 * configuration declares, what its model's stored metadata know, what its provider's
 * integration carries, and what comes of the two.
 */
@Data
public class ChatModelInputs {
	/** The configuration's code. */
	private String code = null;
	private String description = null;
	private String modelTypeCode = null;
	/** The model the requests name. */
	private String modelCode = null;
	/** What the configuration declares; null when it declares nothing, which is text only. */
	private List<GModelInputModality> inputModalities = null;
	/**
	 * What the metadata stored with the configuration's model know; null when they know
	 * nothing, which is not the same as text only.
	 */
	private List<GModelInputModality> metadataInputModalities = null;
	/** What the provider's integration can deliver to this model. */
	private GModelCarriedMediaTypes carriedMediaTypes = null;
	/** The MIME types of the files a request of this model may carry. */
	private List<String> effectiveInputMimeTypes = null;
	/** The declared modalities the provider's integration carries nothing for. */
	private List<GModelInputModality> declaredNotCarried = null;
}
