/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.application.messaging;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import ai.gebo.application.messaging.model.ComponentMetaInfo;
import ai.gebo.application.messaging.model.GModuleMetaInfo;

/**
 * Gebo.ai comment agent
 * 
 * Interface to define message broker operations, including managing 
 * system components, retrieving system information, and filtering components.
 */
public interface IGMessageBroker extends IGMessageConsumer {

	/**
	 * Adds a messaging system component to the broker.
	 *
	 * @param system The messaging system component to be added.
	 */
	public void addSystemComponent(IGMessagingSystem system);

	/**
	 * Checks if a messaging system component is registered within the broker.
	 *
	 * @param system The messaging system component to check.
	 * @return true if the system is registered, false otherwise.
	 */
	public boolean isRegistered(IGMessagingSystem system);

	/**
	 * Removes a messaging system component from the broker.
	 *
	 * @param system The messaging system component to be removed.
	 */
	public void removeSystemComponent(IGMessagingSystem system);

	/**
	 * Retrieves the list of registered messaging systems and their information.
	 *
	 * @return A list of GModuleMetaInfo objects representing the systems.
	 */
	public List<GModuleMetaInfo> getSystemsInfo();

	/**
	 * Filters components based on a given predicate.
	 *
	 * @param searchFunction Predicate to filter components.
	 * @return A list of components matching the predicate.
	 */
	public default List<ComponentMetaInfo> getComponentsByPredicate(Predicate<ComponentMetaInfo> searchFunction) {
		List<ComponentMetaInfo> out = new ArrayList<ComponentMetaInfo>();
		List<GModuleMetaInfo> components = getSystemsInfo();
		for (GModuleMetaInfo componentMetaInfo : components) {
			// Collects components that meet the predicate criteria.
			out.addAll(componentMetaInfo.getComponents().stream().filter(searchFunction).toList());
		}
		return out;
	}

	/**
	 * Retrieves components by a specific messaging system ID.
	 *
	 * @param id The messaging system ID to search for.
	 * @return A list of components associated with the given messaging system ID.
	 */
	public default List<ComponentMetaInfo> getComponentsByMessagingSystemId(String id) {
		return this.getComponentsByPredicate(x -> {
			// Filters components based on matching messaging system ID.
			return x.getMessagingSystemId().equals(id);
		});
	}

	/**
	 * Checks if a receiving component is present in a module with the specified ID.
	 *
	 * @param module The name of the module.
	 * @param componentId The ID of the component to check.
	 * @return true if the receiving component is present, false otherwise.
	 */
	public boolean checkReceivingComponentPresent(String module, String componentId);

}