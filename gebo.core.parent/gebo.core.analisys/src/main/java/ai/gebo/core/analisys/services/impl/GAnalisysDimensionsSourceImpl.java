/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.core.analisys.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.core.analisys.model.GStatsHolder;
import ai.gebo.core.analisys.model.GeboInstallation;
import ai.gebo.core.analisys.services.IGAnalisysDimensionsSource;
import ai.gebo.core.analisys.services.IGAnalisysProvider;
import ai.gebo.core.analisys.services.IGAnalisysProviderRepositoryPattern;
import ai.gebo.knowledgebase.repositories.KnowledgeBaseRepository;
import ai.gebo.knowledgebase.repositories.ProjectRepository;

/**
 * AI generated comments
 * Service implementation for analyzing dimensions and providing data insights.
 * This class implements the IGAnalisysDimensionsSource to provide analysis
 * services based on the data sources available.
 */
@Service
public class GAnalisysDimensionsSourceImpl implements IGAnalisysDimensionsSource {

    // Repositories for accessing knowledge base and project data
    @Autowired
    KnowledgeBaseRepository kbRepo;
    @Autowired
    ProjectRepository pRepo;

    // Repository pattern for accessing analysis providers
    @Autowired
    IGAnalisysProviderRepositoryPattern analisysProviders;

    // A constant holding the class name of GeboInstallation
    private static final String geboInstallationClass = GeboInstallation.class.getName();

    /**
     * Default constructor for GAnalisysDimensionsSourceImpl.
     */
    public GAnalisysDimensionsSourceImpl() {

    }

    /**
     * Processes a list of GStatsHolder objects, setting their drill-down capability
     * and applying computations from analysis providers.
     * 
     * @param holders List of GStatsHolder objects to analyze.
     * @return The processed list of GStatsHolder objects.
     */
    private List<GStatsHolder> provideAnalisys(List<GStatsHolder> holders) {
        for (GStatsHolder gStatsHolder : holders) {
            gStatsHolder.setCanDrillDown(hasDrilldown(gStatsHolder)); // Set drill-down capability
        }
        List<IGAnalisysProvider> impls = analisysProviders.getImplementations(); // Get analysis providers
        for (IGAnalisysProvider p : impls) {
            p.compute(holders); // Apply computations
        }
        return holders;
    }

    /**
     * Processes a single GStatsHolder object by wrapping it in a list and delegating
     * analysis.
     * 
     * @param holder The GStatsHolder object to analyze.
     * @return The processed GStatsHolder object.
     */
    private GStatsHolder provideAnalisys(GStatsHolder holder) {
        return provideAnalisys(List.of(holder)).get(0); // Process as list and return the first element
    }

    /**
     * Provides top-level analysis stats for the knowledge base category.
     * 
     * @return A GStatsHolder object that holds the top-level analysis stats.
     */
    @Override
    public GStatsHolder<GeboInstallation> topLevelKnowledgeBaseCategory() {
        return provideAnalisys(GStatsHolder.of(GeboInstallation.instance)); // Provide top-level analysis
    }

    /**
     * Determines whether a given GStatsHolder object has drill-down capability
     * based on its level key.
     * 
     * @param sh The GStatsHolder to check for drill-down capability.
     * @return True if drill-down is possible; false otherwise.
     */
    @Override
    public boolean hasDrilldown(GStatsHolder sh) {
        if (sh.getLevelKey() == null)
            return false;
        switch (sh.getLevelKey()) {
        case "ai.gebo.core.analisys.model.GeboInstallation": {
            return true; // Drill-down possible for GeboInstallation
        }
        case "ai.gebo.knlowledgebase.model.contents.GKnowledgeBase": {
            return true; // Drill-down possible for GKnowledgeBase
        }
        case "ai.gebo.knlowledgebase.model.projects.GProject": {
            return false; // No drill-down for GProject
        }
        }
        return false; // Default to false if no case is matched
    }

    /**
     * Provides drill-down analysis for a given GStatsHolder object based on its
     * level key.
     * 
     * @param sh The GStatsHolder to drill down from.
     * @return A list of GStatsHolder objects derived from the drill-down analysis.
     */
    @Override
    public List<GStatsHolder> drillDown(GStatsHolder sh) {
        if (sh.getLevelKey() == null)
            return List.of(); // Return empty list if no level key

        switch (sh.getLevelKey()) {
        case "ai.gebo.core.analisys.model.GeboInstallation": {
            // Drill down into the knowledge base repository
            return provideAnalisys(kbRepo.findAll().stream().map(x -> {
                return (GStatsHolder) GStatsHolder.of(x);
            }).toList());
        }

        case "ai.gebo.knlowledgebase.model.contents.GKnowledgeBase": {
            // Drill down into projects associated with the knowledge base
            return provideAnalisys(pRepo.findByRootKnowledgeBaseCode(sh.getDimensionValue().getCode()).map(x -> {
                return (GStatsHolder) GStatsHolder.of(x);
            }).toList());
        }

        }
        return List.of(); // Return empty list if no case is matched
    }
}