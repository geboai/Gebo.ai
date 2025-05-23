/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.patterns.model;

import java.util.Date;

/**
 * Gebo.ai comment agent
 *
 * GModuleTrafficInfo class extends GModuleUseInfo to include traffic
 * accounting information, traffic sample start time, and traffic unit.
 */
public class GModuleTrafficInfo extends GModuleUseInfo {
    
    // Stores the amount of traffic accounted
    private Double trafficAccounting = null;
    
    // The start date of the traffic sample
    private Date trafficSampleStart = null;
    
    // The unit of traffic measurement
    private String trafficUnity = null;
    
    /**
     * Default constructor for GModuleTrafficInfo.
     */
    public GModuleTrafficInfo() {}

    /**
     * Constructor that initializes GModuleTrafficInfo using an instance of GModuleUseInfo.
     *
     * @param u instance of GModuleUseInfo used for initialization
     */
    public GModuleTrafficInfo(GModuleUseInfo u) {
        super(u);
    }
    
    /**
     * Gets the traffic accounting.
     *
     * @return the traffic accounting value
     */
    public Double getTrafficAccounting() {
        return trafficAccounting;
    }
    
    /**
     * Sets the traffic accounting.
     *
     * @param trafficAccounting the traffic accounting value to set
     */
    public void setTrafficAccounting(Double trafficAccounting) {
        this.trafficAccounting = trafficAccounting;
    }

    /**
     * Gets the start date of the traffic sample.
     *
     * @return the date when the traffic sample starts
     */
    public Date getTrafficSampleStart() {
        return trafficSampleStart;
    }
    
    /**
     * Sets the start date of the traffic sample.
     *
     * @param trafficSampleStart the date to set as the start of the traffic sample
     */
    public void setTrafficSampleStart(Date trafficSampleStart) {
        this.trafficSampleStart = trafficSampleStart;
    }
    
    /**
     * Gets the traffic unit of measurement.
     *
     * @return the traffic unit
     */
    public String getTrafficUnity() {
        return trafficUnity;
    }
    
    /**
     * Sets the traffic unit of measurement.
     *
     * @param trafficUnity the unit to set for measuring traffic
     */
    public void setTrafficUnity(String trafficUnity) {
        this.trafficUnity = trafficUnity;
    }
}