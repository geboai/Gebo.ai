/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.application.messaging.model;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Propagates personal-data scope across a {@link GDataFlowReport}.
 *
 * <p>
 * A data source is classified as holding personal data by an administrator (the
 * {@code personalData} flag on its {@code GProjectEndpoint}, surfaced as
 * {@link DataEndpoint#isPersonalData()}). Everything a personal-data source
 * flows into - the chunk cache, the vector store, the full-text index and on
 * through the rest of the pipeline - then carries personal data as well. That
 * transitive scope is a property of the merged flow graph, not of any single
 * component, so it is computed here over the whole report and written back onto
 * every reachable {@link DataEndpoint}. This makes the report itself the
 * authoritative answer, rather than leaving each consumer (the admin UI, an
 * exported record of processing activities, a cluster-wide aggregation) to
 * re-derive the same reachability independently.
 * </p>
 *
 * <p>
 * Reachability is undirected: a store downstream of a personal-data source
 * carries personal data, and a source feeding a store already known to hold
 * personal data is in scope too. The edges are the {@link DataTransformationInfo}
 * source-to-destination pairs, whose ids are already qualified
 * ({@link GDataFlowMetaInfos#qualifiedId(String)}) so they resolve across
 * components and microservices once the reports are merged.
 * </p>
 */
public final class DataFlowPersonalDataPropagation {

	private DataFlowPersonalDataPropagation() {
	}

	/**
	 * Marks every {@link DataEndpoint} reachable from an administrator-flagged
	 * personal-data source as holding personal data, mutating the report in place.
	 *
	 * @param report the report to annotate; {@code null} and empty are no-ops
	 */
	public static void apply(GDataFlowReport report) {
		if (report == null || report.getModules() == null) {
			return;
		}

		// Qualified endpoint id -> the endpoint object(s) under it. A qualified id is
		// unique per owning component, but the same id can surface in more than one
		// merged report entry, so keep every instance to flag them all.
		Map<String, List<DataEndpoint>> endpointsById = new HashMap<>();
		// Undirected flow graph: source endpoint <-> destination endpoint per edge.
		Map<String, Set<String>> adjacency = new HashMap<>();

		for (GModuleMetaInfo module : report.getModules()) {
			if (module == null || module.getComponents() == null) {
				continue;
			}
			for (ComponentMetaInfo component : module.getComponents()) {
				if (component == null) {
					continue;
				}
				GDataFlowMetaInfos flow = component.getDataFlowMetaInfos();
				if (flow == null) {
					continue;
				}
				if (flow.getDataEndpoints() != null) {
					for (DataEndpoint endpoint : flow.getDataEndpoints()) {
						if (endpoint == null) {
							continue;
						}
						endpointsById.computeIfAbsent(flow.qualifiedId(endpoint.getId()), k -> new ArrayList<>())
								.add(endpoint);
					}
				}
				if (flow.getTransformations() != null) {
					for (DataTransformationInfo transformation : flow.getTransformations()) {
						if (transformation == null || transformation.getDataSourceId() == null
								|| transformation.getDataDestinationId() == null) {
							continue;
						}
						link(adjacency, transformation.getDataSourceId(), transformation.getDataDestinationId());
					}
				}
			}
		}

		// Seeds: the endpoints an administrator has flagged as personal data.
		Deque<String> queue = new ArrayDeque<>();
		Set<String> reached = new HashSet<>();
		for (Map.Entry<String, List<DataEndpoint>> entry : endpointsById.entrySet()) {
			for (DataEndpoint endpoint : entry.getValue()) {
				if (endpoint.isPersonalData()) {
					if (reached.add(entry.getKey())) {
						queue.add(entry.getKey());
					}
					break;
				}
			}
		}

		// Undirected breadth-first walk across the transformation edges.
		while (!queue.isEmpty()) {
			Set<String> neighbours = adjacency.get(queue.poll());
			if (neighbours == null) {
				continue;
			}
			for (String next : neighbours) {
				if (reached.add(next)) {
					queue.add(next);
				}
			}
		}

		// Write the propagated scope back onto every reachable endpoint. Ids in the
		// reached set that name no endpoint (a store referenced only as an edge end
		// but not itself reported) are simply skipped.
		for (String qualifiedId : reached) {
			List<DataEndpoint> endpoints = endpointsById.get(qualifiedId);
			if (endpoints == null) {
				continue;
			}
			for (DataEndpoint endpoint : endpoints) {
				endpoint.setPersonalData(true);
			}
		}
	}

	private static void link(Map<String, Set<String>> adjacency, String first, String second) {
		adjacency.computeIfAbsent(first, k -> new HashSet<>()).add(second);
		adjacency.computeIfAbsent(second, k -> new HashSet<>()).add(first);
	}
}
