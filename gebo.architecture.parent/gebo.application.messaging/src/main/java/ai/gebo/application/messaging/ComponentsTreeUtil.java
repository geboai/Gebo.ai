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
import java.util.Map;

import ai.gebo.application.messaging.model.ComponentMetaInfo;
import ai.gebo.application.messaging.model.GModuleMetaInfo;

/**
 * Utility class to handle operations related to components tree structure.
 * AI generated comments
 */
public class ComponentsTreeUtil {
    
    /**
     * Constructs a combined list of module meta information objects based on provided 
     * receivers and emitters.
     * 
     * @param receivers a map containing message receiver objects categorized by their types
     * @param emitters a map containing message emitter objects categorized by their types
     * @return a list of combined module meta information objects for emitters and receivers
     */
    static List<GModuleMetaInfo> componentsTree(Map<String, Map<String, IGMessageReceiver>> receivers,
            Map<String, Map<String, IGMessageEmitter>> emitters) {
        List<GModuleMetaInfo> receiversList = new ArrayList<GModuleMetaInfo>();
        List<GModuleMetaInfo> emittersList = new ArrayList<GModuleMetaInfo>();
        // Process each receiver entry to collect its meta-information
        receivers.entrySet().forEach(x -> {
            GModuleMetaInfo module = new GModuleMetaInfo(x.getKey(), x.getValue().values().stream().map(y -> {
                ComponentMetaInfo component = new ComponentMetaInfo(x.getKey(), y.getMessagingSystemId(), true, false);
                component.setLocalSystem(y.isLocalSystem()); // Set local system flag for receiver
                return component;
            }).toList());
            receiversList.add(module);
        });
        // Process each emitter entry to collect its meta-information
        emitters.entrySet().forEach(x -> {
            GModuleMetaInfo module = new GModuleMetaInfo(x.getKey(), x.getValue().values().stream().map(y -> {
                ComponentMetaInfo component = new ComponentMetaInfo(x.getKey(), y.getMessagingSystemId(), false, true);
                component.setLocalSystem(y.isLocalSystem()); // Set local system flag for emitter
                return component;
            }).toList());
            emittersList.add(module);
        });
        // Combine emitter and receiver trees
        return ComponentsTreeUtil.joinTrees(emittersList, receiversList);
    }

    /**
     * Combines separate emitter and receiver module lists into a unified list, 
     * joining modules with the same messaging module ID.
     * 
     * @param emittersList list of emitter modules
     * @param receiversList list of receiver modules
     * @return a combined list of module meta-information
     */
    private static List<GModuleMetaInfo> joinTrees(List<GModuleMetaInfo> emittersList,
            List<GModuleMetaInfo> receiversList) {
        List<GModuleMetaInfo> cumulated = new ArrayList<GModuleMetaInfo>();
        // Loop through each emitter module to find and join with the corresponding receiver module
        for (GModuleMetaInfo emitterModule : emittersList) {
            GModuleMetaInfo receiverModule = null;
            for (GModuleMetaInfo module : receiversList) {
                if (emitterModule.getMessagingModuleId().equals(module.getMessagingModuleId())) {
                    receiverModule = module;
                    break;
                }
            }
            cumulated.add(joinModules(emitterModule, receiverModule));
        }
        // Ensure any remaining receiver modules are added, joining with matching emitters if present
        for (GModuleMetaInfo receiverModule : receiversList) {
            if (cumulated.stream().anyMatch(x -> {
                return x.getMessagingModuleId().equals(receiverModule.getMessagingModuleId());
            })) {
                continue;
            }
            GModuleMetaInfo emitterModule = null;
            for (GModuleMetaInfo module : emittersList) {
                if (module.getMessagingModuleId().equals(receiverModule.getMessagingModuleId())) {
                    emitterModule = module;
                    break;
                }
            }
            cumulated.add(joinModules(emitterModule, receiverModule));
        }
        return cumulated;
    }

    /**
     * Joins two module meta-information objects into a single module, 
     * merging their component meta-information where applicable.
     * 
     * @param emitterModule module containing emitter objects
     * @param receiverModule module containing receiver objects
     * @return a unified module meta-information object
     */
    private static GModuleMetaInfo joinModules(GModuleMetaInfo emitterModule, GModuleMetaInfo receiverModule) {
        if (emitterModule == null)
            return receiverModule;
        if (receiverModule == null)
            return emitterModule;
        final List<ComponentMetaInfo> joinedComponents = new ArrayList<ComponentMetaInfo>();
        List<ComponentMetaInfo> emittingComponents = emitterModule.getComponents();

        List<ComponentMetaInfo> receivingComponents = receiverModule.getComponents();
        for (ComponentMetaInfo component : emittingComponents) {
            // Check if the emitter component matches any receiver component
            if (receivingComponents.stream().anyMatch(x -> {
                return x.getMessagingSystemId().equals(component.getMessagingSystemId());
            })) {
                ComponentMetaInfo newComponent = new ComponentMetaInfo(component.getMessagingModuleId(),
                        component.getMessagingSystemId(), true, true);
                newComponent.setLocalSystem(component.isLocalSystem()); // Preserve local system status
                joinedComponents.add(newComponent);
            } else {
                joinedComponents.add(component);
            }
        }
        // Add any unmatched receiver components to the joined list
        for (ComponentMetaInfo component : receivingComponents) {
            if (!joinedComponents.stream().anyMatch(x -> {
                return x.getMessagingSystemId().equals(component.getMessagingSystemId());
            })) {
                joinedComponents.add(component);
            }
        }

        GModuleMetaInfo newModule = new GModuleMetaInfo(emitterModule.getMessagingModuleId(), joinedComponents);
        return newModule;
    }

}