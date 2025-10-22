/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */




/**
 * AI generated comments
 * 
 * Main dashboard component for the Gebo.ai application. This component displays
 * visualization of data through charts using the TotalHistogramBar structure.
 * It communicates with the GeboCoreAnalisysControllerService to fetch data.
 */
import { Component, OnInit } from "@angular/core";
import { GeboCoreAnalisysControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { TotalHistogramBar, transformData } from "./graphics-data";
import { fieldHostComponentName, GEBO_AI_FIELD_HOST } from "@Gebo.ai/reusable-ui";

@Component({
    selector: "gebo-ai-main-dashboard-component",
    templateUrl: "gebo-dashboard.component.html",
    standalone: false,
    providers: [{
        provide: GEBO_AI_FIELD_HOST, useValue: fieldHostComponentName("GeboAIDashboardComponent"),
        multi: true
    }]
})
export class GeboAIDashboardComponent implements OnInit {
    /**
     * Indicates whether data is currently being loaded
     */
    public loading: boolean = false;

    /**
     * Stores the root data for histogram visualization
     */
    public rootValue?: TotalHistogramBar;

    /**
     * Configuration options for the chart display
     * Sets up the legend styling with appropriate color scheme
     */
    chartOptions = {
        plugins: {
            legend: {
                labels: {
                    color: '#495057'
                }
            }
        }
    }

    /**
     * Constructor for the dashboard component
     * @param geboStatsService Service for retrieving analysis data from the Gebo core
     */
    constructor(private geboStatsService: GeboCoreAnalisysControllerService) {

    }

    /**
     * Lifecycle hook that is called after component initialization
     * Used to perform initial data loading and setup
     */
    ngOnInit(): void {

    }

}