import { Component, Input, OnChanges, SimpleChanges } from "@angular/core";
import { ComputedWorkflowStatus } from "@Gebo.ai/gebo-ai-rest-api";
import { renderData, StatusRendering } from "./graphic-rendering";

@Component({
    templateUrl: "graphic-visualizer.component.html",
    selector: "gebo-ai-jobsummary-graphic-visualizer",
    standalone: false
})
export class GeboAIStatsVisualizerComponent {
    
    @Input() rendered?: StatusRendering;
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
}