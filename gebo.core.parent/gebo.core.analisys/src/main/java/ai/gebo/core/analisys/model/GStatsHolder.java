/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.core.analisys.model;

import java.util.HashMap;
import java.util.List;

import ai.gebo.model.base.GBaseObject;
import ai.gebo.model.base.GLookupEntry;

/**
 * AI generated comments
 * 
 * The GStatsHolder class is a container for statistical data associated with a GBaseObject. 
 * It provides methods to manage and retrieve statistical information structured as key-value pairs.
 *
 * @param <G> a type that extends GBaseObject
 */
public final class GStatsHolder<G extends GBaseObject> {

    /**
     * GStatsLine is a simple data structure used to hold a single line of statistics data.
     * It contains a statsKey which acts as the identifier for each statistical entry, 
     * and a list of GStatsLabelValue objects which hold the actual data values.
     */
    public static class GStatsLine {
        // Key associated with a specific line of statistics
        public String statsKey = null;
        // Data values for the statistics identified by the statsKey
        public List<GStatsLabelValue> data = null;
    }

    // The key representing the hierarchical level in the statistics
    private String levelKey = null;
    // The dimension value extracted from the GBaseObject to categorize statistics
    private GLookupEntry dimensionValue = null;
    // Boolean flag indicating if further drilling into sub-level statistics is possible
    private boolean canDrillDown = false;
    // Container holding statistics data organized by their keys
    private HashMap<String, List<GStatsLabelValue>> statsContainer = new HashMap<String, List<GStatsLabelValue>>();

    /**
     * Default constructor creates an empty GStatsHolder object.
     */
    public GStatsHolder() {

    }

    /**
     * Constructs a GStatsHolder object initialized with a specific GBaseObject.
     * The levelKey and dimensionValue are set based on the properties of the given object.
     *
     * @param g the GBaseObject used to initialize the GStatsHolder
     */
    public GStatsHolder(G g) {
        levelKey = g.getClass().getName();
        dimensionValue = GLookupEntry.of(g);
    }

    /**
     * Returns the level key which represents the object level in statistics.
     *
     * @return the current levelKey
     */
    public String getLevelKey() {
        return levelKey;
    }

    /**
     * Sets the level key with the given value.
     *
     * @param levelKey the new level key to be set
     */
    public void setLevelKey(String levelKey) {
        this.levelKey = levelKey;
    }

    /**
     * Returns the dimension value derived from the GBaseObject.
     *
     * @return the current dimensionValue
     */
    public GLookupEntry getDimensionValue() {
        return dimensionValue;
    }

    /**
     * Factory method to create a new GStatsHolder instance based on a GBaseObject.
     *
     * @param g the GBaseObject to base the stats holder on
     * @return a new instance of GStatsHolder
     */
    public static <G extends GBaseObject> GStatsHolder<G> of(G g) {
        return new GStatsHolder<G>(g);
    }

    /**
     * Returns the container holding the statistics data.
     *
     * @return the current statsContainer
     */
    public HashMap<String, List<GStatsLabelValue>> getStatsContainer() {
        return statsContainer;
    }

    /**
     * Sets the statistics container with the provided data structure.
     *
     * @param statsContainer the new statistics data to be set
     */
    public void setStatsContainer(HashMap<String, List<GStatsLabelValue>> statsContainer) {
        this.statsContainer = statsContainer;
    }

    /**
     * Checks if further drilling into sub-level statistics is enabled.
     *
     * @return true if can drill down, false otherwise
     */
    public boolean isCanDrillDown() {
        return canDrillDown;
    }

    /**
     * Sets the drill-down capability flag.
     *
     * @param canDrillDown true to enable drill-down, false to disable
     */
    public void setCanDrillDown(boolean canDrillDown) {
        this.canDrillDown = canDrillDown;
    }

    /**
     * Generates and returns a list of GStatsLine based on the entries in the statsContainer.
     *
     * @return a list of GStatsLine objects representing statistical entries
     */
    public List<GStatsLine> getStatsLines() {
        return this.statsContainer.entrySet().stream().map(x -> {
            GStatsLine line = new GStatsLine();
            line.data = x.getValue();
            line.statsKey = x.getKey();
            return line;
        }).toList();
    }

    /**
     * Sets the list of GStatsLine in the stats holder. (Currently a placeholder)
     *
     * @param lines the list of GStatsLine to be set (not implemented)
     */
    public void setStatsLines(List<GStatsLine> lines) {
        // Placeholder method; currently no implementation necessary
    }
}