/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.aws_bedrock.model;

/**
 * Common contract implemented by every AWS Bedrock model configuration so that
 * the shared credentials/region resolver can obtain the AWS region a model
 * configuration is bound to, independently of the concrete model category.
 */
public interface IBedrockRegionAware {

	/**
	 * The AWS region hosting the Bedrock resources (e.g. {@code us-east-1}).
	 *
	 * @return the configured region id, or {@code null} to fall back to the
	 *         provider default.
	 */
	String getRegion();
}
