/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.buildsystems.abstraction.layer.functions;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.beans.factory.annotation.Autowired;

import ai.gebo.architecture.ai.IGToolCallbackSource;
import ai.gebo.architecture.ai.ToolCallbackDeclarationUtil;
import ai.gebo.architecture.ai.model.LLMtInteractionContextThreadLocal;
import ai.gebo.architecture.ai.model.LLMtInteractionContextThreadLocal.CalledFunction;
import ai.gebo.architecture.ai.model.LLMtInteractionContextThreadLocal.KBContext;
import ai.gebo.architecture.ai.model.ToolReference;
import ai.gebo.architecture.ai.model.ToolsCategory;
import ai.gebo.knlowledgebase.model.contents.GDependencyTree;
import ai.gebo.knlowledgebase.model.contents.GSoftwareArtifact;

// AI generated comments

/**
 * Factory class for creating functions to search artifact information.
 * Implements IGToolCallbackSource to provide tool callbacks for searching software artifacts.
 */
public class GArtifactInformationsSearchFunctionsFactory implements IGToolCallbackSource {
    
    /**
     * Logger for the class.
     */
    static final Logger LOGGER = LoggerFactory.getLogger(GArtifactInformationsSearchFunctionsFactory.class);

    // Constant string identifiers used for various function callbacks
    private static final String GET_ALL_SOFTWARE_ARTIFACTS_LIST = "getAllSoftwareArtifactsList";
    private static final String GET_ALL_SOFTWARE_ARTIFACTS_LIST_DESCRIPTION = "Get full software artifacts list";
    private static final String GET_ARTIFACTS_DEPENDING_FROM = "getSoftwareArtifactsDependingFrom";
    private static final String GET_ARTIFACTS_DEPENDING_FROM_DESCRIPTION = "Get list of software artifacts depending from a specific software artifact";
    private static final String GET_FULL_ARTIFACT_DEPENDENCIES_INFOS = "getSoftwareArtifactFullDependenciesInfos";
    private static final String GET_FULL_ARTIFACT_DEPENDENCIES_DESCRIPTION = "Get full software artifacts dependency tree";
    private static final String GET_ARTIFACTS_INFORMATION = "searchSoftwareArtifact";
    private static final String GET_ARTIFACTS_INFORMATION_DESCRIPTION = "Search for software artifact basic informations";

    /**
     * Service for searching artifacts.
     */
    @Autowired
    GArtifactSearcherService searcherService;

    /**
     * Default constructor.
     */
    public GArtifactInformationsSearchFunctionsFactory() {

    }

    /**
     * Gets the unique identifier for this factory.
     * @return the unique identifier string.
     */
    @Override
    public String getId() {
        return "GArtifactInformationsSearchFunctionsFactory";
    }

    /**
     * Custom list type for holding software artifact information.
     */
    public static class ArtifactsList extends ArrayList<GSoftwareArtifact> {
    };

    /**
     * Creates a tool callback for searching artifact information.
     * @return the tool callback for searching artifacts.
     */
    ToolCallback createSearchArtifactInfos() {
        BiFunction<GSoftwareArtifactSearchParameters, ToolContext, ArtifactsList> thisFunction = (x, c) -> {
            ArtifactsList alist = new ArtifactsList();
            KBContext contextVisibility = LLMtInteractionContextThreadLocal.Context.get();
            
            try {
                CalledFunction cf = new CalledFunction();
                cf.setFunctionName(GET_ARTIFACTS_INFORMATION);
                cf.setFunctionDescription(GET_ARTIFACTS_INFORMATION_DESCRIPTION);
                cf.setParamsDescription(x.toParametersList());
                
                // Add called function to context visibility if the context is not null or empty
                if (contextVisibility != null && contextVisibility.getKnowledgeBasesCodes() != null
                        && !contextVisibility.getKnowledgeBasesCodes().isEmpty()) {

                    contextVisibility.getCalledFunctions().add(cf);
                    alist.addAll(searcherService.findSoftwareArtifacts(x, contextVisibility.getKnowledgeBasesCodes()));
                }
                
                // Add function call to context
                if (c != null) {
                    ToolCallbackDeclarationUtil.addCallToContext(c, cf);
                }
            } catch (Throwable th) {
                LOGGER.error("Error in " + GET_ARTIFACTS_INFORMATION, th);
            }
            return alist;
        };
        return ToolCallbackDeclarationUtil.declare(thisFunction, GET_ARTIFACTS_INFORMATION,
                GET_ARTIFACTS_INFORMATION_DESCRIPTION, GSoftwareArtifactSearchParameters.class, ArtifactsList.class);
    }

    /**
     * Creates a tool callback for retrieving the full list of software artifacts.
     * @return the tool callback for retrieving the full artifacts list.
     */
    ToolCallback createFullArtifactsList() {
        BiFunction<GSoftwareArtifactSearchParameters, ToolContext, ArtifactsList> thisFunction = (
                GSoftwareArtifactSearchParameters x, ToolContext c) -> {
            ArtifactsList out = new ArtifactsList();
            KBContext contextVisibility = LLMtInteractionContextThreadLocal.Context.get();
            
            try {
                CalledFunction cf = new CalledFunction();
                cf.setFunctionName(GET_ALL_SOFTWARE_ARTIFACTS_LIST);
                cf.setFunctionDescription(GET_ALL_SOFTWARE_ARTIFACTS_LIST_DESCRIPTION);
                cf.setParamsDescription(x.toParametersList());
                
                // Add called function to context visibility if the context is not null or empty
                if (contextVisibility != null && contextVisibility.getKnowledgeBasesCodes() != null
                        && !contextVisibility.getKnowledgeBasesCodes().isEmpty()) {

                    contextVisibility.getCalledFunctions().add(cf);
                    out.addAll(searcherService.findAllArtifacts(x, contextVisibility.getKnowledgeBasesCodes()));
                }
                ToolCallbackDeclarationUtil.addCallToContext(c, cf);
            } catch (Throwable th) {
                LOGGER.error("Error in " + GET_ALL_SOFTWARE_ARTIFACTS_LIST, th);
            }
            return out;
        };
        return ToolCallbackDeclarationUtil.declare(thisFunction, GET_ALL_SOFTWARE_ARTIFACTS_LIST,
                GET_ALL_SOFTWARE_ARTIFACTS_LIST_DESCRIPTION, GSoftwareArtifactSearchParameters.class,
                ArtifactsList.class);
    }

    /**
     * Creates a tool callback for searching artifacts by their dependencies.
     * @return the tool callback for searching artifact dependencies.
     */
    ToolCallback createSearchArtifactDependingFromInfos() {
        BiFunction<GSoftwareArtifactSearchParameters, ToolContext, ArtifactsList> thisFunction = (x, c) -> {
            ArtifactsList out = new ArtifactsList();
            KBContext contextVisibility = LLMtInteractionContextThreadLocal.Context.get();
            
            try {
                CalledFunction cf = new CalledFunction();
                cf.setFunctionName(GET_ARTIFACTS_DEPENDING_FROM);
                cf.setFunctionDescription(GET_ARTIFACTS_DEPENDING_FROM_DESCRIPTION);
                cf.setParamsDescription(x.toParametersList());
                
                // Add called function to context visibility if the context is not null or empty
                if (contextVisibility != null && contextVisibility.getKnowledgeBasesCodes() != null
                        && !contextVisibility.getKnowledgeBasesCodes().isEmpty()) {

                    contextVisibility.getCalledFunctions().add(cf);
                    out.addAll(searcherService.findAllArtifactsDependingFrom(x,
                            contextVisibility.getKnowledgeBasesCodes()));
                }
                ToolCallbackDeclarationUtil.addCallToContext(c, cf);
            } catch (Throwable th) {
                LOGGER.error("Error in " + GET_ARTIFACTS_DEPENDING_FROM, th);
            }
            return out;
        };
        return ToolCallbackDeclarationUtil.declare(thisFunction, GET_ARTIFACTS_DEPENDING_FROM,
                GET_ARTIFACTS_DEPENDING_FROM_DESCRIPTION, GSoftwareArtifactSearchParameters.class, ArtifactsList.class);
    }

    /**
     * Creates a tool callback for retrieving detailed artifact dependency information.
     * @return the tool callback for retrieving artifact dependencies.
     */
    ToolCallback createSearchFullArtifactDependenciesInfos() {
        BiFunction<GSoftwareArtifactSearchParameters, ToolContext, GDependencyTree> thisFunction = (x, c) -> {

            KBContext contextVisibility = LLMtInteractionContextThreadLocal.Context.get();
            try {
                CalledFunction cf = new CalledFunction();
                cf.setFunctionName(GET_FULL_ARTIFACT_DEPENDENCIES_INFOS);
                cf.setFunctionDescription(GET_FULL_ARTIFACT_DEPENDENCIES_DESCRIPTION);
                cf.setParamsDescription(x.toParametersList());
                
                // Add called function to context visibility if the context is not null or empty
                if (contextVisibility != null && contextVisibility.getKnowledgeBasesCodes() != null
                        && !contextVisibility.getKnowledgeBasesCodes().isEmpty()) {

                    contextVisibility.getCalledFunctions().add(cf);
                    return searcherService.findFullDependenciesTree(x, contextVisibility.getKnowledgeBasesCodes());
                }
                ToolCallbackDeclarationUtil.addCallToContext(c, cf);
            } catch (Throwable th) {
                LOGGER.error("Error in " + GET_FULL_ARTIFACT_DEPENDENCIES_INFOS, th);
            }
            return new GDependencyTree();
        };

        return ToolCallbackDeclarationUtil.declare(thisFunction, GET_FULL_ARTIFACT_DEPENDENCIES_INFOS,
                GET_FULL_ARTIFACT_DEPENDENCIES_DESCRIPTION, GSoftwareArtifactSearchParameters.class,
                GDependencyTree.class);
    }

    /**
     * Retrieves all tool callbacks provided by this factory.
     * @return a list of tool callbacks.
     */
    @Override
    public List<ToolCallback> getToolCallbacks() {
        List<ToolCallback> functions = new ArrayList<ToolCallback>();
        functions.add(createSearchArtifactInfos());
        functions.add(createSearchFullArtifactDependenciesInfos());
        functions.add(createSearchArtifactDependingFromInfos());
        functions.add(createFullArtifactsList());
        return functions;
    }

    /**
     * Gets the category of tools that this factory provides.
     * @return the tools category.
     */
    @Override
    public ToolsCategory getToolCategory() {
        return ToolsCategory.SOFTWARE_ARTIFACTS_SEARCHES;
    }

    /**
     * Gets the full list of tool references provided by this factory.
     * @return a list of tool references; empty if none.
     */
    @Override
    public List<ToolReference> getFullToolReferences() {
        return List.of();
    }
}