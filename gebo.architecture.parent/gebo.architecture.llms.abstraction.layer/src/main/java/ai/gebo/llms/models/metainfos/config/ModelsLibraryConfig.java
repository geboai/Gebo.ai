/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.models.metainfos.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import ai.gebo.llms.models.metainfos.ModelMetaInfo;
import ai.gebo.llms.models.metainfos.ModelParameterRules;
import lombok.Getter;
import lombok.Setter;

/**
 * The models library of an installation, from the properties prefixed with
 * "ai.gebo.models.library": its entries come before the bundled ones the provider
 * modules ship (see {@link ModelsLibraryBundleConfig}).
 */
@Configuration
@ConfigurationProperties(value = "ai.gebo.models.library")
@Getter
@Setter
public class ModelsLibraryConfig {

	/** The models information of the installation. */
	private List<ModelMetaInfo> modelsInfo = new ArrayList<ModelMetaInfo>();

	/** The parameter rules of the installation (see {@link ModelParameterRules}). */
	private List<ModelParameterRules> parameterRules = new ArrayList<ModelParameterRules>();
}
