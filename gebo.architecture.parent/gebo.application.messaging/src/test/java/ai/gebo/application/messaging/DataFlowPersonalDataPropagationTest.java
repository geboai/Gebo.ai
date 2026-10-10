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
 * Pins {@link DataFlowPersonalDataPropagation}: a personal-data source carries
 * its status along the data direction to every store it flows into and to every
 * reader retrieving from those stores (across components), while an unrelated
 * flow, another source feeding the same store and a step carrying only a request
 * stay untouched.
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

	/**
	 * Two sources feed the same chunk cache, only one flagged. The data flow from
	 * each source into the cache, never back: the other source holds none of the
	 * flagged one's content and stays clean.
	 */
	@Test
	public void anotherSourceFeedingTheSameStoreStaysClean() {
		GeboComponentInfo flaggedComp = new GeboComponentInfo("uploads-module", "flagged-handler");
		GeboComponentInfo otherComp = new GeboComponentInfo("git-module", "other-handler");
		GeboComponentInfo chunkComp = new GeboComponentInfo("tokenizer-module", "chunker");
		DataTransformationMetaInfo engine = DataTransformationMetaInfo.of("engine", "chunks",
				List.of(MetaEndpointType.DOCUMENTS), List.of(MetaEndpointType.CHUNK));

		GDataFlowMetaInfos flagged = new GDataFlowMetaInfos();
		flagged.setComponent(flaggedComp);
		flagged.getDataEndpoints().add(endpoint("source", true, MetaEndpointType.DOCUMENTS));
		flagged.getTransformations().add(DataTransformationInfo.of("ingest", "chunking", engine,
				GDataFlowMetaInfos.qualifiedId(flaggedComp, "source"),
				GDataFlowMetaInfos.qualifiedId(chunkComp, "chunk-cache")));
		GDataFlowMetaInfos other = new GDataFlowMetaInfos();
		other.setComponent(otherComp);
		other.getDataEndpoints().add(endpoint("source", false, MetaEndpointType.DOCUMENTS));
		other.getTransformations().add(DataTransformationInfo.of("ingest", "chunking", engine,
				GDataFlowMetaInfos.qualifiedId(otherComp, "source"),
				GDataFlowMetaInfos.qualifiedId(chunkComp, "chunk-cache")));
		GDataFlowMetaInfos chunk = new GDataFlowMetaInfos();
		chunk.setComponent(chunkComp);
		chunk.getDataEndpoints().add(endpoint("chunk-cache", false, MetaEndpointType.CHUNK));

		DataFlowPersonalDataPropagation.apply(reportOf(
				new GModuleMetaInfo("uploads-module", List.of(component(flagged))),
				new GModuleMetaInfo("git-module", List.of(component(other))),
				new GModuleMetaInfo("tokenizer-module", List.of(component(chunk)))));

		assertTrue(chunk.getDataEndpoints().get(0).isPersonalData(), "the shared chunk cache holds the flagged content");
		assertFalse(other.getDataEndpoints().get(0).isPersonalData(), "the other source receives nothing back");
	}

	/**
	 * A chat retrieves from a store fed by a flagged source (the retrieval reported
	 * from the store to the chat) and answers with its responder model: both receive
	 * the content. Its question goes to an embedding model as a request only: the
	 * embedding model receives no content of the source.
	 */
	@Test
	public void aReaderOfTheStoreIsReachedAndARequestIsNotFollowed() {
		GeboComponentInfo srcComp = new GeboComponentInfo("src-module", "content-handler");
		GeboComponentInfo vecComp = new GeboComponentInfo("vectorizator-module", "vectorizer");
		GeboComponentInfo chatComp = new GeboComponentInfo("chat-pipeline-module", "chat");
		DataTransformationMetaInfo engine = DataTransformationMetaInfo.of("engine", "processes",
				List.of(MetaEndpointType.DOCUMENTS), List.of(MetaEndpointType.VECTORIAL_DATABASE));

		GDataFlowMetaInfos src = new GDataFlowMetaInfos();
		src.setComponent(srcComp);
		src.getDataEndpoints().add(endpoint("source", true, MetaEndpointType.DOCUMENTS));
		GDataFlowMetaInfos vector = new GDataFlowMetaInfos();
		vector.setComponent(vecComp);
		vector.getDataEndpoints().add(endpoint("vector-store", false, MetaEndpointType.VECTORIAL_DATABASE));
		vector.getTransformations().add(DataTransformationInfo.of("embed", "embedding", engine,
				GDataFlowMetaInfos.qualifiedId(srcComp, "source"), GDataFlowMetaInfos.qualifiedId(vecComp, "vector-store")));
		GDataFlowMetaInfos chat = new GDataFlowMetaInfos();
		chat.setComponent(chatComp);
		chat.getDataEndpoints().add(endpoint("query", false, MetaEndpointType.CHAT_SESSION));
		chat.getDataEndpoints().add(endpoint("responder", false, MetaEndpointType.LLM_ENDPOINT));
		chat.getDataEndpoints().add(endpoint("query-embedding", false, MetaEndpointType.LLM_ENDPOINT));
		chat.getTransformations().add(DataTransformationInfo.of("retrieval", "fragments retrieved", engine,
				GDataFlowMetaInfos.qualifiedId(vecComp, "vector-store"), chat.qualifiedId("query")));
		chat.getTransformations().add(DataTransformationInfo.of("answer", "answer", engine, chat.qualifiedId("query"),
				chat.qualifiedId("responder")));
		chat.getTransformations().add(DataTransformationInfo.request("embed", "question embedded", engine,
				chat.qualifiedId("query"), chat.qualifiedId("query-embedding")));

		DataFlowPersonalDataPropagation.apply(reportOf(
				new GModuleMetaInfo("src-module", List.of(component(src))),
				new GModuleMetaInfo("vectorizator-module", List.of(component(vector))),
				new GModuleMetaInfo("chat-pipeline-module", List.of(component(chat)))));

		assertTrue(chat.getDataEndpoints().get(0).isPersonalData(), "the chat retrieving the content");
		assertTrue(chat.getDataEndpoints().get(1).isPersonalData(), "the responder answering with it");
		assertFalse(chat.getDataEndpoints().get(2).isPersonalData(), "the question's embedding is a request only");
	}

	/**
	 * Two chats share a web search provider: the first one, reading a store fed by a
	 * flagged source, writes the provider its queries (processed there); the second one
	 * reads the provider's results. The provider processes the first chat's content and
	 * passes it to no one: it is marked, the second chat is not.
	 */
	@Test
	public void aSharedProcessorPassesNothingOnToTheOtherChats() {
		GeboComponentInfo srcComp = new GeboComponentInfo("src-module", "content-handler");
		GeboComponentInfo chatComp = new GeboComponentInfo("chat-pipeline-module", "chat");
		DataTransformationMetaInfo engine = DataTransformationMetaInfo.of("engine", "processes",
				List.of(MetaEndpointType.DOCUMENTS), List.of(MetaEndpointType.CHAT_SESSION));

		GDataFlowMetaInfos src = new GDataFlowMetaInfos();
		src.setComponent(srcComp);
		src.getDataEndpoints().add(endpoint("source", true, MetaEndpointType.DOCUMENTS));
		GDataFlowMetaInfos chat = new GDataFlowMetaInfos();
		chat.setComponent(chatComp);
		chat.getDataEndpoints().add(endpoint("first-chat", false, MetaEndpointType.CHAT_SESSION));
		chat.getDataEndpoints().add(endpoint("web-provider", false, MetaEndpointType.WEB_SEARCH));
		chat.getDataEndpoints().add(endpoint("second-chat", false, MetaEndpointType.CHAT_SESSION));
		chat.getTransformations().add(DataTransformationInfo.of("read", "read", engine,
				GDataFlowMetaInfos.qualifiedId(srcComp, "source"), chat.qualifiedId("first-chat")));
		chat.getTransformations().add(DataTransformationInfo.processed("queries", "queries written", engine,
				chat.qualifiedId("first-chat"), chat.qualifiedId("web-provider")));
		chat.getTransformations().add(DataTransformationInfo.of("results", "results read", engine,
				chat.qualifiedId("web-provider"), chat.qualifiedId("second-chat")));

		DataFlowPersonalDataPropagation.apply(reportOf(new GModuleMetaInfo("src-module", List.of(component(src))),
				new GModuleMetaInfo("chat-pipeline-module", List.of(component(chat)))));

		assertTrue(chat.getDataEndpoints().get(0).isPersonalData(), "the chat reading the flagged content");
		assertTrue(chat.getDataEndpoints().get(1).isPersonalData(), "the provider processing what it writes");
		assertFalse(chat.getDataEndpoints().get(2).isPersonalData(), "nothing comes back to the other chat");
	}

	/** A null report is a no-op, not a crash. */
	@Test
	public void toleratesNullReport() {
		DataFlowPersonalDataPropagation.apply(null);
	}
}
