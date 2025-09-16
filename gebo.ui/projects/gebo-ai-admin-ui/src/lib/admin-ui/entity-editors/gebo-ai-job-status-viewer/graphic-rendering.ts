import { ComputedWorkflowStatus, JobWorkflowStepSummary } from '@Gebo.ai/gebo-ai-rest-api';
function n(d?: number): number {
  if (d && d > 0) return d;
  else return 0;
}
function documentsChart(foundProcessStepSummary?: JobWorkflowStepSummary): NGPieChart | undefined {
  const chart: NGPieChart = {
    labels: [],
    datasets: [{
      label: "Received docs",
      data: []
    }, {
      label: "Processed docs",
      data: []
    }, {
      label: "Error docs",
      data: []
    }, {
      label: "Discarded docs",
      data: []
    }]
  };
  if (foundProcessStepSummary) {
    foundProcessStepSummary.timesamples?.forEach(data => {
      if (data.startDateTime) {
        chart.labels.push(new Date(data.startDateTime).toLocaleTimeString());
      }
      chart.datasets[0].data.push(n(data.batchDocumentsInput));
      chart.datasets[1].data.push(n(data.batchDocumentsProcessed));
      chart.datasets[2].data.push(n(data.batchDocumentsProcessingErrors));
      chart.datasets[3].data.push(n(data.batchDiscardedInput));
    });
    return chart;
  }
  return undefined;

}
function tokensChart(foundProcessStepSummary?: JobWorkflowStepSummary): NGPieChart | undefined {
  const chart: NGPieChart = {
    labels: [],
    datasets: [{
      label: "Processed tokens",
      data: []
    }]
  };
  let hasNotZeroTokens = foundProcessStepSummary?.timesamples?.filter(ts => ts.tokensProcessed && ts.tokensProcessed > 0);
  if (foundProcessStepSummary) {
    foundProcessStepSummary.timesamples?.forEach(data => {
      if (data.startDateTime) {
        chart.labels.push(new Date(data.startDateTime).toLocaleTimeString());
      }

      chart.datasets[0].data.push(n(data.tokensProcessed));

    });

  }
  if (hasNotZeroTokens && hasNotZeroTokens.length > 0) {
    return chart;
  } else {
    return undefined;
  }
}

export function renderData(result: ComputedWorkflowStatus, workflowStepsSummaries: JobWorkflowStepSummary[] | undefined): StatusRendering {
  const foundProcessStepSummary = workflowStepsSummaries?.find(x => x.workflowId === result.workflowId && x.workflowType === result.workflowType && x.workflowStepId === result.workflowStepId);
  const out: StatusRendering = {
    ...result, piechart: {
      labels: ["Not treated docs", "Error processing docs", "Processed docs"],
      datasets: [{
        label: "",
        data: [
          (result.batchDiscardedInput ? result.batchDiscardedInput : 0),
          (result.batchDocumentsProcessingErrors ? result.batchDocumentsProcessingErrors : 0),
          (result.batchDocumentsProcessed ? result.batchDocumentsProcessed : 0)],
        backgroundColor: ["orange", "red", "green"]
      }]
    },
    documentsChart: documentsChart(foundProcessStepSummary),
    tokensChart: tokensChart(foundProcessStepSummary),
    childs: result?.childs?.map(x => {
      return renderData(x, workflowStepsSummaries);
    })
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
  documentsChart?: NGPieChart;
  tokensChart?: NGPieChart;
  childs?: StatusRendering[];
}

