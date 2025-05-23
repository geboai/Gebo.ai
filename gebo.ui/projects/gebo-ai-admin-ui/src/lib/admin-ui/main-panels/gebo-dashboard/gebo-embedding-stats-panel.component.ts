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
 * This component is responsible for displaying embedding statistics in a panel format.
 * It provides functionality to view hierarchical data about embeddings, with the ability
 * to drill down into categories for more detailed information.
 */
import { Component, Input, OnChanges, OnInit, SimpleChanges } from "@angular/core";
import { GeboCoreAnalisysControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { TotalHistogramBar, transformData } from "./graphics-data";

@Component({
    selector: "gebo-ai-embedding-stats-panel",
    templateUrl: "gebo-embedding-stats-panel.component.html",
    standalone: false
})
export class GeboAIEmbeddingStatsPanelComponent implements OnInit,OnChanges{
    /** Indicates whether data is currently being loaded */
    public loading: boolean = false;
    
    /** Determines if this instance is the root component in a hierarchy */
    @Input()  public isRoot?:boolean;
    
    /** Reference to the parent histogram bar when in drill-down mode */
    @Input()  public parent?:TotalHistogramBar|null|undefined;
    
    /** Contains the top-level histogram data to be displayed */
    @Input()  public rootValue: TotalHistogramBar[]=[];
    
    /** Tracks whether drill-down data is currently being shown */
    public drilldownShowed:boolean=false;
    
    /**
     * Constructor initializes the component with required services
     * @param geboStatsService Service to fetch embedding statistics data
     */
    constructor(private geboStatsService: GeboCoreAnalisysControllerService) {

    }
    
    /**
     * Lifecycle hook that responds to input property changes.
     * If this is the root component, it loads the top-level knowledge base categories.
     * @param changes The changes that occurred on the input properties
     */
    ngOnChanges(changes: SimpleChanges): void {
        if (this.isRoot!==undefined && this.isRoot===true) {
            this.loading = true;
            this.geboStatsService.getTopLevelKnowledgeBaseCategory().subscribe({
                next: (value) => {
                    this.rootValue = [transformData(value)];
                }, error: (error) => {
    
                },
                complete: () => {
                    this.loading = false;
                }
            });
        }
        
    }
    
    /**
     * Lifecycle hook for component initialization
     */
    ngOnInit(): void {
       
    }
    
    /**
     * Fetches and displays drill-down data for a selected histogram bar
     * When a user selects a category, this method retrieves more detailed subcategories
     * @param value The histogram bar selected for drill-down
     */
    public drillDown(value: TotalHistogramBar) {
        this.drilldownShowed=true;
        this.loading = true;
        this.geboStatsService.drillDown(value.data).subscribe({
            next: (drillData) => {
                value.drillDowns = drillData.map(x => {
                    return transformData(x);
                });
            }

            , complete: () => {
                this.loading = false;
                
            }
        })
    }
}