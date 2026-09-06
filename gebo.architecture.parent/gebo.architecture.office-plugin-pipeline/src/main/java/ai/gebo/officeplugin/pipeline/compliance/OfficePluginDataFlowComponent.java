/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.officeplugin.pipeline.compliance;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import ai.gebo.application.messaging.IGMessageEmitter;
import ai.gebo.application.messaging.SystemComponentType;
import ai.gebo.application.messaging.model.DataEndpoint;
import ai.gebo.application.messaging.model.DataEndpointLocality;
import ai.gebo.application.messaging.model.DataTransformationInfo;
import ai.gebo.application.messaging.model.DataTransformationMetaInfo;
import ai.gebo.application.messaging.model.GDataFlowMetaInfos;
import ai.gebo.application.messaging.model.MetaEndpointType;
import ai.gebo.llms.agent.standard.services.GAgentsNetworkDataFlowComponent;
import ai.gebo.model.base.GeboComponentInfo;
import ai.gebo.officeplugin.pipeline.OfficeAssistantConstants;

/**
 * Symbolic messaging component that records, in the compliance data-flow register,
 * the inflow specific to the office plugin: the document fragments the user is
 * editing ({@code GeboChatRequest.additionalContents}) travel from the external
 * office document editor into the office assistant chat session / agents network.
 *
 * <p>
 * The office agents network's own query fan-out (internal knowledge-base and web
 * search) is already reported for free by
 * {@link GAgentsNetworkDataFlowComponent}, which enumerates every dynamic agents
 * network. This component only adds the missing edge - the editor document source
 * feeding the network's query endpoint - so the register shows where the user's
 * document content enters the system.
 */
@ConditionalOnProperty(prefix = "ai.gebo.officeplugin", name = "enabled", havingValue = "true")
@Component
public class OfficePluginDataFlowComponent implements IGMessageEmitter {

	public static final String OFFICE_PLUGIN_MODULE = "office-plugin-module";
	public static final String OFFICE_ASSISTANT_INFLOW_COMPONENT = "office-assistant-inflow";
	private static final String EDITOR_DOCUMENT_ENDPOINT = "office-editor-document";

	@Override
	public String getMessagingModuleId() {
		return OFFICE_PLUGIN_MODULE;
	}

	@Override
	public String getMessagingSystemId() {
		return OFFICE_ASSISTANT_INFLOW_COMPONENT;
	}

	@Override
	public SystemComponentType getComponentType() {
		return SystemComponentType.APPLICATION_COMPONENT;
	}

	@Override
	public List<String> getEmittedPayloadTypes() {
		return List.of();
	}

	@Override
	public GDataFlowMetaInfos getDataFlowMetaInfos() {
		GDataFlowMetaInfos flow = new GDataFlowMetaInfos();
		flow.setComponent(new GeboComponentInfo(getMessagingModuleId(), getMessagingSystemId()));

		// The document the user is editing, carried into the request by the plugin. It
		// originates in the external editor and may contain personal / business data.
		DataEndpoint editorDocument = new DataEndpoint();
		editorDocument.setId(EDITOR_DOCUMENT_ENDPOINT);
		editorDocument.setDescription("Office document fragments the user is editing (plugin additionalContents)");
		editorDocument.setProduct("Office document editor");
		editorDocument.setEndpoint("office-editor", "document", null, null);
		editorDocument.setInput(true);
		editorDocument.setOutput(true);
		editorDocument.setTypes(list(MetaEndpointType.DOCUMENTS));
		editorDocument.setPersonalData(true);
		editorDocument.setLocality(DataEndpointLocality.EXTERNAL_PROVIDER);
		flow.getDataEndpoints().add(editorDocument);

		// Destination: the office agents-network query endpoint, registered by the
		// agents-network data-flow component for the office network code.
		String networkQueryQualifiedId = GDataFlowMetaInfos.qualifiedId(
				new GeboComponentInfo(GAgentsNetworkDataFlowComponent.AGENT_NETWORK_MODULE,
						GAgentsNetworkDataFlowComponent.AGENTS_NETWORK_RESPONDER_COMPONENT),
				"network-query-" + OfficeAssistantConstants.OFFICE_ASSISTANT_AGENTS_NETWORK);

		DataTransformationMetaInfo engine = DataTransformationMetaInfo.of("office-document-inflow",
				"Office plugin: document fragments carried into the assistant chat session",
				list(MetaEndpointType.DOCUMENTS), list(MetaEndpointType.CHAT_SESSION));
		flow.getEngines().add(engine);
		flow.getTransformations().add(DataTransformationInfo.of("office-document-inflow-flow",
				"Office plugin: document fragments carried into the assistant chat session", engine,
				flow.qualifiedId(editorDocument.getId()), networkQueryQualifiedId));

		return flow;
	}

	private static List<MetaEndpointType> list(MetaEndpointType... types) {
		return new ArrayList<>(List.of(types));
	}
}
