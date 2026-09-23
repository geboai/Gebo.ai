import { Component, Input, OnChanges, OnInit, SimpleChanges } from "@angular/core";
import { ComputedWorkflowStatus } from "@Gebo.ai/gebo-ai-rest-api";
import { NGPieChart, renderData, StatusRendering } from "./graphic-rendering";
import { findMatchingTranlations, GeboAITranslationService, UIExistingText } from "@Gebo.ai/reusable-ui";
import { map, Observable, of, Subscription } from "rxjs";
const moduleId: string = "GeboAIJobStatsVisualizerModule";
const entityId: string = "GeboAIJobGraphicVisualizerComponent";
@Component({
  templateUrl: "graphic-visualizer.component.html",
  selector: "gebo-ai-jobsummary-graphic-visualizer",
  standalone: false
})
export class GeboAIJobGraphicVisualizerComponent implements OnInit, OnChanges {
  constructor(private geboTranslationService: GeboAITranslationService) {

  }

  @Input() rendered?: StatusRendering;
  protected viewed?: StatusRendering;
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
  basicOptions = {
    plugins: {
      legend: {
        labels: {
          color: "black",
        },
      },
    },
    scales: {
      x: {
        ticks: {
          color: "black",
        },
        grid: {
          color: "black",
        },
      },
      y: {
        beginAtZero: true,
        ticks: {
          color: "black",
        },
        grid: {
          color: "black",
        },
      },
    },
  };
  ngOnInit(): void {

  }
  private subscription?: Subscription;
  private treeTranslate(data: StatusRendering): Observable<StatusRendering> {
    const resources: UIExistingText[] = this.stripTextResources(data);
    if (data.id)
      return this.geboTranslationService.translateOnActualLanguage(resources).pipe(map(translations => {
        return translations ? this.reapplyTextResources(data, findMatchingTranlations(resources, translations)) : data;
      }));
    return of(data);
  }
  private reapplyTextResources(data: StatusRendering, translations: UIExistingText[] | undefined): StatusRendering {
    const descriptionTranslation = translations?.find(x => x.componentId === data.id && x.fieldId === "description");
    if (descriptionTranslation) {
      data.description = descriptionTranslation.translation;
    }
    if (data.piechart) {
      this.applyResources(translations, data.piechart);
    }
    if (data.tokensChart) {
      this.applyResources(translations, data.tokensChart);
    }
    if (data.documentsChart) {
      this.applyResources(translations, data.documentsChart);
    }
    return data;
  }

  private stripTextResources(data: StatusRendering): UIExistingText[] {
    const resources: UIExistingText[] = [];
    if (data.description && data.id) {
      resources.push({
        moduleId: moduleId,
        entityId: entityId,
        componentId: data.id,
        key: "description",
        fieldId: "description",
        text: data.description
      });
    }
    if (data.piechart && data.id) {
      this.addResources(resources, data.piechart, true);
    }
    if (data.tokensChart && data.id) {
      this.addResources(resources, data.tokensChart, false);
    }
    if (data.documentsChart && data.id) {
      this.addResources(resources, data.documentsChart, false);
    }
    return resources;
  }
  private addResources(resources: UIExistingText[], chart: NGPieChart, translateLabels: boolean) {
    if (chart.labels && chart.labels.length && chart.id && translateLabels) {
      chart.labels.forEach((x, index) => {
        resources.push({
          moduleId: moduleId,
          entityId: entityId,
          componentId: chart.id,
          key: "label-" + index,
          fieldId: "label-" + index,
          text: x
        });
      });
    }
    if (chart.datasets && chart.datasets.length && chart.id) {
      chart.datasets.forEach((ds, index) => {
        if (ds.label) {
          resources.push({
            moduleId: moduleId,
            entityId: entityId,
            componentId: chart.id,
            key: "dataset-" + index + "-label",
            fieldId: "dataset-" + index + "-label",
            text: ds.label
          });
        }
      });
    }
  }
  applyResources(translations: UIExistingText[] | undefined, chart: NGPieChart) {

    if (translations && translations.length) {
      const filtered = translations.filter(x => x.componentId === chart.id);
      if (chart.labels && chart.labels.length) {
        chart.labels.forEach((x, index) => {
          if (x) {
            const fieldId: string = "label-" + index;
            const res = translations.find(x => x.fieldId === fieldId);
            if (res && res.translation) {
              chart.labels[index] = res.translation;
            }
          }
        });
      }
      if (chart.datasets && chart.datasets.length) {
        chart.datasets.forEach((ds, index) => {
          const fieldId: string = "dataset-" + index + "-label";
          const res = translations.find(x => x.fieldId === fieldId);
          if (res && res.translation) {
            chart.datasets[index].label = res.translation;
          }
        });
      }
    }
  }
  ngOnChanges(changes: SimpleChanges): void {
    if (changes["rendered"] && this.rendered) {
      if (this.subscription) {
        this.subscription.unsubscribe();
        this.subscription = undefined;
      }
      this.treeTranslate(this.rendered).subscribe({
        next: (translated) => {
          this.viewed = translated;
        }
      })
    }
  }
}