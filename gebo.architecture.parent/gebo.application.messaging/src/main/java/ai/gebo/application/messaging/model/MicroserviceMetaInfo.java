/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.application.messaging.model;

import java.util.ArrayList;
import java.util.List;

/**
 * One microservice's local messaging topology: its id plus the local-only
 * modules/components its broker reports. The top level of the global topology
 * hierarchy ({@code MicroserviceMetaInfo -> GModuleMetaInfo -> ComponentMetaInfo})
 * aggregated across the cluster.
 */
public class MicroserviceMetaInfo {

	/** The microservice id (its {@code spring.application.name}, dot-free form). */
	private String microserviceId = null;

	/** The local-only modules this microservice's broker reports. */
	private List<GModuleMetaInfo> modules = new ArrayList<GModuleMetaInfo>();

	public MicroserviceMetaInfo() {
	}

	public MicroserviceMetaInfo(String microserviceId, List<GModuleMetaInfo> modules) {
		this.microserviceId = microserviceId;
		this.modules = modules != null ? modules : new ArrayList<GModuleMetaInfo>();
	}

	public String getMicroserviceId() {
		return microserviceId;
	}

	public void setMicroserviceId(String microserviceId) {
		this.microserviceId = microserviceId;
	}

	public List<GModuleMetaInfo> getModules() {
		return modules;
	}

	public void setModules(List<GModuleMetaInfo> modules) {
		this.modules = modules;
	}
}
