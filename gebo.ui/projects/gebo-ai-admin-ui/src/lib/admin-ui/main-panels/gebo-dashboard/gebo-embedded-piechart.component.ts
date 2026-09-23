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
 * This file contains a component that renders a pie chart for embedded documents.
 * It transforms input data from EmbeddingData format into a format compatible with
 * the pie chart visualization.
 */

import { Component, EventEmitter, Input, OnChanges, Output, SimpleChanges } from "@angular/core";
import { EmbeddingData } from "./graphics-data";
import { GStatsHolder } from "@Gebo.ai/gebo-ai-rest-api";

/**
 * TypeScript interface defining the structure for pie chart data.
 * Consists of labels array and datasets array with configuration options
 * for customizing the chart appearance.
 */
export type NGPieChart = {
    labels: string[];
    datasets: { label: string, data: number[], backgroundColor?: string[]; borderColor?: string[]; borderWith?: number }[];
};

/**
 * Component responsible for displaying embedded document data as a pie chart.
 * Takes EmbeddingData as input and transforms it into a format suitable for
 * pie chart visualization. Provides drill-down capability through EventEmitter.
 */
@Component({
    selector: "gebo-ai-embedded-documents-piechart",
    templateUrl: "gebo-embedded-piechart.component.html",
    standalone: false
})
export class GeboAiEmbeddedDocumentsPiechartComponent implements OnChanges{
    /**
     * Responds to changes in the input properties.
     * Transforms the input data (EmbeddingData) into a structured format (NGPieChart)
     * that can be rendered by the pie chart library.
     * 
     * @param changes Object containing all changed properties with current and previous values
     */
    ngOnChanges(changes: SimpleChanges): void {
        if (changes["item"] && this.item) {
            const item:NGPieChart={
                    labels:[],
                    datasets:[{
                        label:"",
                        data:[]
                    }]
            };
            const total=this.item.total?this.item.total:0;
            if (this.item.labelValues) {
                this.item.labelValues.forEach((label,index)=>{
                    item.labels.push(label);
                    item.datasets[0].data.push(this.item?.dataValues[index]?this.item?.dataValues[index]:0);
                });
            }

            this.pieChartData=item;
        }
    }

    /**
     * Configuration options for the pie chart appearance.
     * Sets the legend style to use point style with black color for labels.
     */
    pieOptions = {
        plugins: {
          legend: {
            labels: {
              usePointStyle: true,
              color: "black"
            }
          }
        }
      };

    /**
     * Holds the processed chart data ready for rendering
     */
    pieChartData?: NGPieChart;

    /**
     * Input property receiving the embedding data to be visualized
     */
    @Input() item?: EmbeddingData;

    /**
     * Output event emitter for drill-down functionality
     * Emits a GStatsHolder object when a segment of the chart is selected for detailed view
     */
    @Output() drillDown: EventEmitter<GStatsHolder> = new EventEmitter();
}