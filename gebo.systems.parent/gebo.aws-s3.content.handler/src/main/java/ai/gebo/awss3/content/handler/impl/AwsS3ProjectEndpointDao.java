/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.awss3.content.handler.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import ai.gebo.architecture.patterns.GAbstractRuntimeConfigurationDao;
import ai.gebo.awss3.content.handler.GAwsS3ProjectEndpoint;
import ai.gebo.systems.abstraction.layer.IGProjectEndpointRuntimeConfigurationDao;

@Service
public class AwsS3ProjectEndpointDao extends GAbstractRuntimeConfigurationDao<GAwsS3ProjectEndpoint>
		implements IGProjectEndpointRuntimeConfigurationDao<GAwsS3ProjectEndpoint> {

	public AwsS3ProjectEndpointDao(AwsS3ProjectEndpointSource dynamic) {
		super(List.of(), dynamic);
	}
}