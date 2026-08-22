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
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import ai.gebo.model.base.GeboComponentInfo;
import lombok.Data;

/**
 * What one component does with data, as currently configured: where it reads
 * from, where it writes to, and what it changes on the way.
 *
 * <p>
 * Returned by {@code IGMessagingSystem#getDataFlowMetaInfos()}. Components with
 * no data-flow configuration of their own - the orchestration plumbing, the
 * broker itself - keep the interface default and return null.
 * </p>
 *
 * <p>
 * Collected per component and aggregated cluster-wide through the existing
 * messaging-topology poll, so an administrator can audit which sources feed the
 * installation, which stores retain the result, and which engines and third
 * parties see the content in between.
 * </p>
 */
@Data
public class GDataFlowMetaInfos {

	/**
	 * Separates a component id from an endpoint id in a qualified reference.
	 * Matches the convention already used by
	 * {@code ISearchService.SYSTEM_TYPE_CODE_CONFIG_CODE_SEPARATOR}, which
	 * qualifies a configuration code the same way - repeated here rather than
	 * shared because the search abstraction layer does not depend on this module.
	 */
	public static final String COMPONENT_ENDPOINT_SEPARATOR = "<->";

	/**
	 * The component this report describes. Set it to
	 * {@code new GeboComponentInfo(getMessagingModuleId(), getMessagingSystemId())}
	 * so {@link #qualifiedId(String)} can resolve, and so a report stays
	 * self-identifying if it is ever collected outside the topology aggregation
	 * that would otherwise supply the surrounding identity.
	 */
	private GeboComponentInfo component = null;

	List<DataEndpoint> dataEndpoints = new ArrayList<DataEndpoint>();
	List<DataTransformationMetaInfo> engines = new ArrayList<DataTransformationMetaInfo>();
	List<DataTransformationInfo> transformations = new ArrayList<DataTransformationInfo>();

	/**
	 * Qualifies one of this component's own endpoint ids for use in a
	 * {@link DataTransformationInfo}, yielding
	 * {@code moduleId.systemId<->endpointId}.
	 *
	 * <p>
	 * A {@link DataEndpoint#getId()} is unique only inside this object, while the
	 * flows it takes part in cross components and microservices, so an edge that
	 * points at a bare local id cannot be resolved once the reports are merged.
	 * </p>
	 *
	 * @param endpointId the local endpoint id
	 * @return the qualified id, or the bare id when no {@link #component} was set
	 */
	public String qualifiedId(String endpointId) {
		return qualifiedId(component, endpointId);
	}

	/**
	 * Qualifies an endpoint id belonging to another component - how a component
	 * names the downstream endpoint it hands data to.
	 *
	 * @param owner      the component owning the endpoint
	 * @param endpointId the endpoint id local to that component
	 * @return the qualified id, or the bare id when no owner was given
	 */
	public static String qualifiedId(GeboComponentInfo owner, String endpointId) {
		if (owner == null) {
			return endpointId;
		}
		return owner.getCompleteComponentId() + COMPONENT_ENDPOINT_SEPARATOR + endpointId;
	}

	/**
	 * Combines two reports published under the same messaging identity.
	 *
	 * <p>
	 * One identity can be served by two distinct beans - an emitter class and a
	 * receiver class registered under the same module and component id, as
	 * {@code jobs-master-module.end-of-workflow-compute-service} is by
	 * {@code GComputeEndOfWorkflowReceiverFactory} and
	 * {@code GWorkflowsConcentratorMessagesEmitterImpl}. They are collected into
	 * separate maps by {@code GBaseMessageBroker} and folded into a single
	 * {@link ComponentMetaInfo} by {@link ComponentsTreeUtil}, so without merging
	 * here one of the two reports would be dropped without trace - the one failure
	 * mode a compliance report must not have.
	 * </p>
	 *
	 * <p>
	 * Entries are de-duplicated by id, which also covers the ordinary case where
	 * the same instance is registered as both emitter and receiver and so reports
	 * identical content twice.
	 * </p>
	 *
	 * @param first  the first report, may be null
	 * @param second the second report, may be null
	 * @return the merged report, or null when both were null
	 */
	public static GDataFlowMetaInfos merge(GDataFlowMetaInfos first, GDataFlowMetaInfos second) {
		if (first == null) {
			return second;
		}
		if (second == null) {
			return first;
		}
		GDataFlowMetaInfos merged = new GDataFlowMetaInfos();
		merged.setComponent(first.getComponent() != null ? first.getComponent() : second.getComponent());
		merged.setDataEndpoints(mergeById(first.getDataEndpoints(), second.getDataEndpoints(), DataEndpoint::getId));
		merged.setEngines(mergeById(first.getEngines(), second.getEngines(), DataTransformationMetaInfo::getId));
		merged.setTransformations(
				mergeById(first.getTransformations(), second.getTransformations(), DataTransformationInfo::getId));
		return merged;
	}

	private static <T> List<T> mergeById(List<T> first, List<T> second, Function<T, String> idOf) {
		List<T> out = new ArrayList<T>();
		Set<String> seen = new HashSet<String>();
		for (List<T> source : Arrays.asList(first, second)) {
			if (source == null) {
				continue;
			}
			for (T item : source) {
				if (item == null) {
					continue;
				}
				String id = idOf.apply(item);
				// An entry with no id cannot be de-duplicated; keep it rather than
				// silently discard it, since dropping it would lose a flow.
				if (id != null && seen.contains(id)) {
					continue;
				}
				if (id != null) {
					seen.add(id);
				}
				out.add(item);
			}
		}
		return out;
	}
}
