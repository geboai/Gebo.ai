/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.application.messaging;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

import ai.gebo.application.messaging.model.ComponentMetaInfo;
import ai.gebo.application.messaging.model.DataEndpoint;
import ai.gebo.application.messaging.model.DataFlowPersonalDataPropagation;
import ai.gebo.application.messaging.model.DataTransformationInfo;
import ai.gebo.application.messaging.model.DataTransformationMetaInfo;
import ai.gebo.application.messaging.model.GDataFlowMetaInfos;
import ai.gebo.application.messaging.model.GDataFlowReport;
import ai.gebo.application.messaging.model.GModuleMetaInfo;
import ai.gebo.application.messaging.model.MetaEndpointType;
import ai.gebo.model.base.GeboComponentInfo;

/**
 * Pins {@link DataFlowPersonalDataPropagation}: a personal-data source must
 * carry its status forward to every store it flows into (across components,
 * undirected), while an unrelated flow stays untouched.
 */
public class DataFlowPersonalDataPropagationTest {

	private static DataEndpoint endpoint(String id, boolean personal, MetaEndpointType type) {
		DataEndpoint endpoint = new DataEndpoint();
		endpoint.setId(id);
		endpoint.setDescription(id);
		endpoint.setProduct("test");
		endpoint.setTypes(List.of(type));
		endpoint.setPersonalData(personal);
		return endpoint;
	}

	private static ComponentMetaInfo component(GDataFlowMetaInfos flow) {
		ComponentMetaInfo component = new ComponentMetaInfo();
		component.setDataFlowMetaInfos(flow);
		return component;
	}

	private static GDataFlowReport reportOf(GModuleMetaInfo... modules) {
		return new GDataFlowReport("test-node", new Date(), new ArrayList<>(List.of(modules)));
	}

	/**
	 * source (personal) -> chunk store (not personal) -> vector store (not
	 * personal), reported by three different components. After propagation every
	 * store is personal; a fourth, unconnected business source stays clean.
	 */
	@Test
	public void propagatesDownstreamAndLeavesUnrelatedFlowsAlone() {
		GeboComponentInfo srcComp = new GeboComponentInfo("src-module", "content-handler");
		GeboComponentInfo chunkComp = new GeboComponentInfo("tokenizer-module", "chunker");
		GeboComponentInfo vecComp = new GeboComponentInfo("vectorizator-module", "vectorizer");
		GeboComponentInfo bizComp = new GeboComponentInfo("git-module", "business-handler");

		GDataFlowMetaInfos src = new GDataFlowMetaInfos();
		src.setComponent(srcComp);
		src.getDataEndpoints().add(endpoint("source", true, MetaEndpointType.DOCUMENTS));

		GDataFlowMetaInfos chunk = new GDataFlowMetaInfos();
		chunk.setComponent(chunkComp);
		chunk.getDataEndpoints().add(endpoint("chunk-cache", false, MetaEndpointType.CHUNK));

		GDataFlowMetaInfos vector = new GDataFlowMetaInfos();
		vector.setComponent(vecComp);
		vector.getDataEndpoints().add(endpoint("vector-store", false, MetaEndpointType.VECTORIAL_DATABASE));

		GDataFlowMetaInfos biz = new GDataFlowMetaInfos();
		biz.setComponent(bizComp);
		biz.getDataEndpoints().add(endpoint("company-repo", false, MetaEndpointType.DOCUMENTS));

		DataTransformationMetaInfo engine = DataTransformationMetaInfo.of("engine", "processes",
				List.of(MetaEndpointType.DOCUMENTS), List.of(MetaEndpointType.CHUNK));
		// source -> chunk (owned by the chunker), chunk -> vector (owned by the vectorizer).
		chunk.getTransformations().add(DataTransformationInfo.of("ingest", "chunking", engine,
				GDataFlowMetaInfos.qualifiedId(srcComp, "source"),
				GDataFlowMetaInfos.qualifiedId(chunkComp, "chunk-cache")));
		vector.getTransformations().add(DataTransformationInfo.of("embed", "embedding", engine,
				GDataFlowMetaInfos.qualifiedId(chunkComp, "chunk-cache"),
				GDataFlowMetaInfos.qualifiedId(vecComp, "vector-store")));

		GDataFlowReport report = reportOf(
				new GModuleMetaInfo("src-module", List.of(component(src))),
				new GModuleMetaInfo("tokenizer-module", List.of(component(chunk))),
				new GModuleMetaInfo("vectorizator-module", List.of(component(vector))),
				new GModuleMetaInfo("git-module", List.of(component(biz))));

		DataFlowPersonalDataPropagation.apply(report);

		assertTrue(src.getDataEndpoints().get(0).isPersonalData(), "flagged source stays personal");
		assertTrue(chunk.getDataEndpoints().get(0).isPersonalData(), "chunk cache inherits personal data");
		assertTrue(vector.getDataEndpoints().get(0).isPersonalData(), "vector store inherits personal data transitively");
		assertFalse(biz.getDataEndpoints().get(0).isPersonalData(), "unconnected business source stays clean");
	}

	/** With no flagged source nothing becomes personal data. */
	@Test
	public void leavesEverythingCleanWhenNoSourceIsFlagged() {
		GeboComponentInfo srcComp = new GeboComponentInfo("src-module", "content-handler");
		GeboComponentInfo chunkComp = new GeboComponentInfo("tokenizer-module", "chunker");

		GDataFlowMetaInfos src = new GDataFlowMetaInfos();
		src.setComponent(srcComp);
		src.getDataEndpoints().add(endpoint("source", false, MetaEndpointType.DOCUMENTS));

		GDataFlowMetaInfos chunk = new GDataFlowMetaInfos();
		chunk.setComponent(chunkComp);
		chunk.getDataEndpoints().add(endpoint("chunk-cache", false, MetaEndpointType.CHUNK));
		chunk.getTransformations().add(DataTransformationInfo.of("ingest", "chunking",
				DataTransformationMetaInfo.of("engine", "processes", List.of(MetaEndpointType.DOCUMENTS),
						List.of(MetaEndpointType.CHUNK)),
				GDataFlowMetaInfos.qualifiedId(srcComp, "source"),
				GDataFlowMetaInfos.qualifiedId(chunkComp, "chunk-cache")));

		GDataFlowReport report = reportOf(
				new GModuleMetaInfo("src-module", List.of(component(src))),
				new GModuleMetaInfo("tokenizer-module", List.of(component(chunk))));

		DataFlowPersonalDataPropagation.apply(report);

		assertFalse(src.getDataEndpoints().get(0).isPersonalData());
		assertFalse(chunk.getDataEndpoints().get(0).isPersonalData());
	}

	/** A null report is a no-op, not a crash. */
	@Test
	public void toleratesNullReport() {
		DataFlowPersonalDataPropagation.apply(null);
	}
}
