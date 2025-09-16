import { ComputedWorkflowStatus } from '@Gebo.ai/gebo-ai-rest-api';


export function renderData(result: ComputedWorkflowStatus): StatusRendering {
  const out: StatusRendering = {
    ...result, piechart: {
      labels: ["Not treated docs","Error processing docs","Processed docs"],
      datasets: [{
        label: "",
        data: [
               (result.batchDiscardedInput ? result.batchDiscardedInput : 0),
               (result.batchDocumentsProcessingErrors ? result.batchDocumentsProcessingErrors : 0),
               (result.batchDocumentsProcessed ? result.batchDocumentsProcessed : 0)],
        backgroundColor: ["orange","red","green"]
      }]
    },
    childs: result?.childs?.map(renderData)
  };
  return out;
}/**
 * Type definition for PrimeNG pie chart data structure.
 * Represents a structured format for chart visualization with labels, datasets, and styling options.
 */

export type NGPieChart = {
  labels: string[];
  datasets: { label: string; data: number[]; backgroundColor?: string[]; borderColor?: string[]; borderWith?: number; }[];
};
export interface StatusRendering extends ComputedWorkflowStatus {
  piechart: NGPieChart;
  childs?: StatusRendering[];
}

