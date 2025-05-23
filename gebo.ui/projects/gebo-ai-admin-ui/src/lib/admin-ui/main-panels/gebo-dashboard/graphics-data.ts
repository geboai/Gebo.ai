/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

import { GStatsHolder, GStatsLabelValue } from "@Gebo.ai/gebo-ai-rest-api";
/**
 * AI generated comments
 * File containing interfaces and utility function to transform statistical data 
 * for visualization in histogram charts, primarily focusing on document counts
 * and embedding statistics.
 */

/**
 * Interface representing a bar in a total histogram chart.
 * Contains statistical data, labels, totals, and optional nested data for
 * further visualization like embeddings and drill-downs.
 */
export interface TotalHistogramBar {
    data: GStatsHolder;
    label?: string;
    total?: number;
    embeddingData?: EmbeddingData[];
    drillDowns?: TotalHistogramBar[];

}

/**
 * Interface representing embedding data for visualization.
 * Contains statistical values, labels, and visualization properties
 * for rendering charts (typically pie charts) that show embedding statistics.
 */
export interface EmbeddingData {
    parentData?: TotalHistogramBar;
    data: GStatsLabelValue;
    label?: string;
    total?: number;
    percent?: number;
    dataValues: number[];
    labelValues: string[];
    colorValues: string[];
    chart?: any;
    responsive?: any;

}

/**
 * Transforms raw statistical data into a structured format for histogram visualization.
 * 
 * This function processes GStatsHolder data to create a TotalHistogramBar object with:
 * - Basic histogram information (label, total document count)
 * - Embedding statistics transformed into pie chart ready format
 * - Calculated percentages for embedded vs non-embedded content
 * 
 * @param data The raw statistical data to transform
 * @returns A structured TotalHistogramBar object ready for visualization
 */
export function transformData(data: GStatsHolder): TotalHistogramBar {
    const histo: TotalHistogramBar = {
        data: data,
        label: data.dimensionValue?.description,
        total: data.statsLines?.find(x => x.statsKey === 'countDocuments')?.data?.find(x => x.label === 'countDocuments')?.value,
        embeddingData: data.statsLines?.find(x => x.statsKey === 'embeddings')?.data?.map(y => {
            const piedata: EmbeddingData = {
                data: y,
                label: y.label,
                total: y.value,
                chart: {
                    width: '100%',
                    type: "pie"
                },
                responsive: [
                    {
                        breakpoint: 480,
                        options: {
                            chart: {
                                width: 200
                            },
                            legend: {
                                position: "bottom"
                            }
                        }
                    }
                ],
                dataValues: y.value ? [y.value] : [],
                labelValues: y.value && y.label ? ["embedded with: " + y.label] : [],
                colorValues: y.value ? ["#00FF00"] : []
            };

            return piedata;

        })
    };
    if (histo.embeddingData) {
        histo.embeddingData.forEach(p => {
            p.parentData = histo;
            if (histo.total && p.total) {
                p.percent = p.total / histo.total; // Calculate the percentage of total documents that are embedded
            }
            if (histo.total !== undefined && p.total !== undefined && (histo.total - p.total) > 0) {
                p.dataValues.push(histo.total - p.total); // Add the count of non-embedded documents
                p.labelValues.push("Not embedded"); // Label for non-embedded documents
                p.colorValues.push("#FF0000"); // Red color for non-embedded documents
            }
        });
    }
    return histo;
}