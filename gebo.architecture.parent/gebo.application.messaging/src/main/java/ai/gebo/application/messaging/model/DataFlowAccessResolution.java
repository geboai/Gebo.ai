/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.application.messaging.model;

import java.util.Map;
import java.util.function.IntFunction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Completes the access rules of a collected register: the access model in force
 * system-wide, the ACL aliases decoded into entries, the groups named.
 *
 * <p>
 * Done once on the collected report rather than in each reporter: the access
 * model, the ACL aliases store and the users directory are the security layer's,
 * which the reporters do not depend on, and an alias decodes the same whichever
 * component reported it.
 * </p>
 */
public final class DataFlowAccessResolution {
	private static final Logger LOGGER = LoggerFactory.getLogger(DataFlowAccessResolution.class);
	/** How an ACL principal names a group: {@code group:<code>}. */
	static final String GROUP_PRINCIPAL_PREFIX = "group:";

	private DataFlowAccessResolution() {
	}

	/**
	 * Completes the access rules of every endpoint of the report.
	 *
	 * @param report      the collected register
	 * @param policy      the access model in force system-wide, null when unknown
	 * @param aclDecoder  decodes an ACL alias into its entry, null when the alias is
	 *                    unknown; null when no ACL store is reachable here
	 * @param groupNames  the description of every group, by code; may be null
	 */
	public static void apply(GDataFlowReport report, String policy, IntFunction<DataEndpointAccess.AclEntry> aclDecoder,
			Map<String, String> groupNames) {
		if (report == null) {
			return;
		}
		report.setContentAccessPolicy(policy);
		int rules = 0;
		int decoded = 0;
		for (GModuleMetaInfo module : report.getModules()) {
			if (module == null || module.getComponents() == null) {
				continue;
			}
			for (ComponentMetaInfo component : module.getComponents()) {
				GDataFlowMetaInfos flow = component != null ? component.getDataFlowMetaInfos() : null;
				if (flow == null || flow.getDataEndpoints() == null) {
					continue;
				}
				for (DataEndpoint endpoint : flow.getDataEndpoints()) {
					if (endpoint == null || endpoint.getAccess() == null) {
						continue;
					}
					for (DataEndpointAccess access : endpoint.getAccess()) {
						if (access == null) {
							continue;
						}
						rules++;
						decoded += decode(access, aclDecoder);
						name(report, access, groupNames);
					}
				}
			}
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Register access rules completed: policy=" + policy + " rules=" + rules
					+ " decoded ACL entries=" + decoded + " groups named=" + report.getGroupDescriptions().size());
		}
	}

	private static int decode(DataEndpointAccess access, IntFunction<DataEndpointAccess.AclEntry> aclDecoder) {
		if (aclDecoder == null || access.getAclAliases() == null || !access.getAclEntries().isEmpty()) {
			return 0;
		}
		int decoded = 0;
		for (Integer alias : access.getAclAliases()) {
			if (alias == null) {
				continue;
			}
			DataEndpointAccess.AclEntry entry = aclDecoder.apply(alias.intValue());
			if (entry != null) {
				access.getAclEntries().add(entry);
				decoded++;
			} else if (LOGGER.isTraceEnabled()) {
				LOGGER.trace("ACL alias " + alias + " of " + access.getGrantedBy() + " is unknown");
			}
		}
		return decoded;
	}

	private static void name(GDataFlowReport report, DataEndpointAccess access, Map<String, String> groupNames) {
		if (groupNames == null || groupNames.isEmpty()) {
			return;
		}
		for (String group : access.getGroups()) {
			nameGroup(report, group, groupNames);
		}
		for (DataEndpointAccess.AclEntry entry : access.getAclEntries()) {
			if (entry.getPrincipal() != null && entry.getPrincipal().startsWith(GROUP_PRINCIPAL_PREFIX)) {
				nameGroup(report, entry.getPrincipal().substring(GROUP_PRINCIPAL_PREFIX.length()), groupNames);
			}
		}
	}

	private static void nameGroup(GDataFlowReport report, String code, Map<String, String> groupNames) {
		if (code != null && groupNames.containsKey(code) && groupNames.get(code) != null) {
			report.getGroupDescriptions().put(code, groupNames.get(code));
		}
	}
}
