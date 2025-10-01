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
      data: [],
      backgroundColor:[]
    }, {
      label: "Processed docs",
      data: [],
      backgroundColor:[]
    }, {
      label: "Error docs",
      data: [],
      backgroundColor:[]
    }, {
      label: "Discarded docs",
      data: [],
      backgroundColor:[]
    }]
  };
  if (foundProcessStepSummary) {
    foundProcessStepSummary.timesamples?.forEach(data => {
      if (data.startDateTime) {
        chart.labels.push(shortTime(data.startDateTime));
      }
      chart.datasets[0].data.push(n(data.batchDocumentsInput));
      chart.datasets[0].backgroundColor?.push("orange");
      chart.datasets[1].data.push(n(data.batchDocumentsProcessed));
      chart.datasets[1].backgroundColor?.push("green");
      chart.datasets[2].data.push(n(data.batchDocumentsProcessingErrors));
      chart.datasets[2].backgroundColor?.push("red");
      chart.datasets[3].data.push(n(data.batchDiscardedInput));
      chart.datasets[3].backgroundColor?.push("gray");
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
      data: [],
      backgroundColor:[]
    },{
      label:"Error processing tokens",
      data: [],
      backgroundColor:[]
    }]
  };
  let hasNotZeroTokens = foundProcessStepSummary?.timesamples?.filter(ts => (ts.tokensProcessed && ts.tokensProcessed > 0 ) || (ts.errorChunks && ts.errorChunks>0) );
  if (foundProcessStepSummary) {
    foundProcessStepSummary.timesamples?.forEach(data => {
      if (data.startDateTime) {
        chart.labels.push(shortTime(data.startDateTime));
      }

      chart.datasets[0].data.push(n(data.tokensProcessed));
      chart.datasets[0].backgroundColor?.push("green");
      chart.datasets[1].data.push(n(data.errorTokens));
      chart.datasets[1].backgroundColor?.push("red");
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

function shortTime(startDateTime: Date): string {
  const input:Date|undefined=startDateTime?new Date(startDateTime):undefined;
  if (input) {
    const hour= input.getHours();
    const minutes=input.getMinutes();
    return (hour<10?"0"+hour:""+hour)+":"+(minutes<10?"0"+minutes:""+minutes);
  }else return "";
}

