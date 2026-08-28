/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.application.messaging;

import ai.gebo.application.messaging.model.GDataFlowMetaInfos;

/**
 * Gebo.ai comment agent
 * 
 * Represents an interface for the messaging system within the application. It
 * defines methods to retrieve identifiers for the messaging module and system,
 * and provides details about the component type as well as the complete ID.
 */
public interface IGMessagingSystem {

	/**
	 * Determines if the messaging system is a local system.
	 * 
	 * @return always returns true, indicating a local system.
	 */
	public default boolean isLocalSystem() {
		return true;
	}

	/**
	 * Describes what this component does with data, as it is <b>currently
	 * configured</b>: where it reads from, where it writes to, and what it
	 * transforms on the way.
	 *
	 * <p>
	 * Collected per component and aggregated for the administrator audit view, so
	 * that GDPR / NIS2 questions - which sources feed this installation, which
	 * stores retain the result, which engines and third parties see the content in
	 * between, and what can erase it - are answered from what is actually running
	 * rather than from a declared topology.
	 * </p>
	 *
	 * <p>
	 * Implementations must read live configuration rather than return constants:
	 * the point is to report the deployment as it stands. Two rules apply.
	 * </p>
	 *
	 * <ul>
	 * <li><b>Never return a credential.</b> Set endpoint locators through
	 * {@code DataEndpoint.setEndpoint(...)}, which sanitizes them - the
	 * configuration objects these values come from carry passwords and API keys
	 * inline. Point at a credential by the code of its secret instead.</li>
	 * <li><b>Qualify cross-component references</b> with
	 * {@code GDataFlowMetaInfos.qualifiedId(...)}: an endpoint id is unique only
	 * within one component's report, while the flows being described span
	 * components and microservices.</li>
	 * </ul>
	 *
	 * @return this component's data-flow configuration, or null - the default - for
	 *         the many components that have none of their own, such as the
	 *         orchestration and routing plumbing.
	 */
	public default GDataFlowMetaInfos getDataFlowMetaInfos() {
		return null;
	}

	/**
	 * Retrieves the identifier for the messaging module.
	 * 
	 * @return the ID of the messaging module as a String.
	 */
	public String getMessagingModuleId();

	/**
	 * Retrieves the identifier for the messaging system.
	 * 
	 * @return the ID of the messaging system as a String.
	 */
	public String getMessagingSystemId();

	/**
	 * Gets the type of component represented by this system.
	 * 
	 * @return the component type as a SystemComponentType.
	 */
	public SystemComponentType getComponentType();

	/**
	 * Constructs a complete identifier using both the messaging module and system
	 * identifiers.
	 * 
	 * @return the complete ID as a concatenated String in the format
	 *         "moduleId.systemId".
	 */
	public default String getCompleteId() {
		return getMessagingModuleId() + "." + getMessagingSystemId();
	}
}