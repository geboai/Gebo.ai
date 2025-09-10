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
 * Module for managing and displaying job status information, including content reading and vectorization tasks.
 * This component provides visualization of job progress, task completion statistics, and log messages.
 */
import { Component, Injector, SimpleChanges } from '@angular/core';

import { FormControl, FormGroup } from "@angular/forms";
import { CompanySystemsControllerService, DataPage, GJobStatus, JobLauncherControllerService, JobSummary, LogViewControllerService, PageGUserMessage, SystemInfos } from "@Gebo.ai/gebo-ai-rest-api";
import { BaseEntityEditingComponent, GeboFormGroupsService, GeboUIActionRoutingService, GeboUIOutputForwardingService } from "@Gebo.ai/reusable-ui";
import { ConfirmationService, ToastMessageOptions } from 'primeng/api';
import { PaginatorState } from 'primeng/paginator';
import { forkJoin, map, Observable, of } from "rxjs";

/**
 * Type definition for PrimeNG pie chart data structure.
 * Represents a structured format for chart visualization with labels, datasets, and styling options.
 */
export type NGPieChart = {
  labels: string[];
  datasets: { label: string, data: number[], backgroundColor?: string[]; borderColor?: string[]; borderWith?: number }[];
};
@Component({
    selector: "gebo-ai-job-status-viewer-component", templateUrl: "gebo-ai-job-status-viewer.component.html",
    standalone: false
})
/**
 * Component responsible for displaying and managing job status information.
 * Extends the BaseEntityEditingComponent with GJobStatus as its entity type.
 * Provides functionality to view job progress, statistics, and log messages.
 */
export class GeboAIJobStatusViewerComponent extends BaseEntityEditingComponent<GJobStatus> {
  /**
   * The name of the entity this component manages
   */
  protected override entityName: string = "GJobStatus";
  /**
   * Title displayed for the job, changes based on job type
   */
  public jobTitle: string = "Contents reading task ";
  /**
   * Form group for managing form controls
   */
  override formGroup: FormGroup<any> = new FormGroup({ code: new FormControl() });
  /**
   * Information about the system the job is running on
   */
  public systemInfos?: SystemInfos;
  /**
   * Summary information about the current job
   */
  public jobSummary?: JobSummary;
  /**
   * Collection of log messages from the job execution
   */
  public logMessages: ToastMessageOptions[] = [];
  /**
   * Number of rows to display in the paginated view
   */
  public nRows: number = 20;
  /**
   * Current pagination state
   */
  public actualPage: DataPage = {
    numrecords: 0,
    page: 0,
    pageSize: 20
  };
  /**
   * Standard configuration options for charts
   */
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
  /**
   * Configuration options specific to pie charts
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
   * Chart data for content reading process
   */
  contentsReadData?: NGPieChart;
  /**
   * Chart data for content vectorization process
   */
  contentsVectorizationData?: NGPieChart;
  /**
   * Chart data showing volume metrics for vectorization
   */
  contentsVectorizationVolume?: NGPieChart;
  /**
   * Pie chart showing results summary
   */
  resultsPiechart?: NGPieChart;

  /**
   * Current page of user messages
   */
  public actualPageData?: PageGUserMessage;

  /**
   * Component constructor that initializes services and triggers initial data refresh
   */
  constructor(injector: Injector, geboFormGroupsService: GeboFormGroupsService,
    private JobLauncherControllerService: JobLauncherControllerService,
    confirmService: ConfirmationService,
    private logViewControllerService: LogViewControllerService,
    geboUIActionRoutingService: GeboUIActionRoutingService,
    private companySystemsControllerService: CompanySystemsControllerService,
    outputForwardingService?: GeboUIOutputForwardingService) {
    super(injector, geboFormGroupsService, confirmService, geboUIActionRoutingService, outputForwardingService)
    this.refreshGraphics();
  }

  /**
   * Lifecycle hook for component initialization
   */
  override ngOnInit(): void {
    super.ngOnInit();
  }

  /**
   * Responds to changes in component inputs.
   * Loads system information when entity changes.
   */
  override ngOnChanges(changes: SimpleChanges): void {
    super.ngOnChanges(changes);
    if (this.entity && changes["entity"]) {
      this.onLoadedPersistentData(this.entity);

      if (!this.systemInfos && this.entity.projectEndpointReference) {
        this.companySystemsControllerService.getProjectEndpointSystemInfos(this.entity.projectEndpointReference).subscribe({
          next: (value) => {
            this.systemInfos = value;
          }
        });
      }
    }
  }

  /**
   * Periodically reloads job status data until the job is completed.
   * Fetches job status, messages, and summary information.
   */
  private reloadPeriodically(): void {
    if (this.entity?.code) {

      const observables: [Observable<GJobStatus>, Observable<PageGUserMessage>, Observable<JobSummary>] = [this.JobLauncherControllerService.getJobStatus(this.entity.code), this.logViewControllerService.getJobMessagesPaged({
        jobId: this.entity?.code,
        dataPage: this.actualPage
      }), this.JobLauncherControllerService.getJobSummary(this.entity.code, true)];
      this.loadingRelatedBackend = true;
      forkJoin(observables).subscribe({
        next: (values) => {
          this.entity = values[0];
          this.actualPageData = values[1];
          this.jobSummary = values[2];
          this.logMessages = values[1]?.content as ToastMessageOptions[];
          this.refreshGraphics();
          const anyVersion = values[1] as any;
          if (anyVersion && anyVersion?.page?.totalElements) {
            this.actualPageData.totalElements = anyVersion?.page?.totalElements;
          }
          
            let delayInMilliseconds: number = 10000; // 2000 milliseconds (2 seconds) delay
            if (this.actualPageData && this.actualPageData.totalElements !== undefined && this.actualPageData.totalElements < 3) {
              delayInMilliseconds = 2000;
            }
            setTimeout(() => {
              this.reloadPeriodically();
            }, delayInMilliseconds);
          
        },
        complete: () => {
          this.loadingRelatedBackend = false;
        }
      })
    }
  }

  /**
   * Refreshes all chart graphics with the latest job summary data.
   * Creates and updates chart data structures for visualization.
   */
  private refreshGraphics(): void {

    const contentsGraphicData: NGPieChart = {
      labels: [],
      datasets: [{
        label: "Documents read",
        data: [],
        backgroundColor:[]

      }, {
        label: "Sent to embedding",
        data: [],
        backgroundColor:[]
      }, {
        label: "Read errors",
        data: [],
        backgroundColor:[]
      }]
    };
    const vectorizationGraphicData: NGPieChart = {
      labels: [],
      datasets: [{
        label: "Received for embedding",
        data: [],
        backgroundColor:[]
      },
      {
        label: "Successfully embedded",
        data: [],
        backgroundColor:[]
      },
      {
        label: "Embedding errors",
        data: [],
        backgroundColor:[]
      }]
    };
    const contentsVectorizationVolume: NGPieChart = {
      labels: [],
      datasets: [
        {
          label: "Nr. embedded tokenized segments",
          data: [],
          backgroundColor:[]
        },
        {
          label: "Embedded Token X 1000",
          data: [],
          backgroundColor:[]
        }],
    }
    const pichartResults: NGPieChart = {
      labels: ["Documents to embed", "Successfully embedded docs"],
      datasets: [
        { data: [], label: "",backgroundColor:[] }
      ]
    };
    if (this.jobSummary ) {

      const categories: string[] = [];
      /*
      this.jobSummary.contentsProcessingData.forEach(x => {

        contentsGraphicData.datasets[0].data.push(x.howManyBatchDocuments ? x.howManyBatchDocuments : 0);
        contentsGraphicData.datasets[0].backgroundColor?.push("#EBB921");
        contentsGraphicData.datasets[1].data.push(x.howManyBatchSentToVectorization ? x.howManyBatchSentToVectorization : 0);
        contentsGraphicData.datasets[1].backgroundColor?.push("#36A2EB");
        contentsGraphicData.datasets[2].data.push(x.howManyBatchContentsReadingErrors ? x.howManyBatchContentsReadingErrors : 0);
        contentsGraphicData.datasets[2].backgroundColor?.push("red");
        categories.push(this.totsString(x.timestamp));
      });*/
      contentsGraphicData.labels = categories;


    }
    this.contentsReadData = contentsGraphicData;
    /*
    if (this.jobSummary && this.jobSummary.vectorizationProcessingData && this.jobSummary.vectorizationProcessingData.length) {
      this.jobSummary.vectorizationProcessingData.forEach(x => {

        vectorizationGraphicData.datasets[0].data.push(x.currentBatchDocumentReceviedCounter ? x.currentBatchDocumentReceviedCounter : 0);
        vectorizationGraphicData.datasets[0].backgroundColor?.push("#5BEBE1"); 
        vectorizationGraphicData.datasets[1].data.push(x.currentBatchDocumentVectorizedCounter ? x.currentBatchDocumentVectorizedCounter : 0);
        vectorizationGraphicData.datasets[1].backgroundColor?.push("#32EB8C");
        vectorizationGraphicData.datasets[2].data.push(x.vectorizationErrors ? x.vectorizationErrors : 0);
        vectorizationGraphicData.datasets[2].backgroundColor?.push("#EB2BD5");

        contentsVectorizationVolume.datasets[0].data.push(x.vectorizedSegments ? x.vectorizedSegments : 0);
        const vectorizedTokens = Math.round((x.vectorizedTokens ? x.vectorizedTokens : 0) / 1000);
        contentsVectorizationVolume.datasets[1].data.push(vectorizedTokens);
        contentsVectorizationVolume.datasets[0].backgroundColor?.push("#C0C0C0");
        contentsVectorizationVolume.datasets[1].backgroundColor?.push("#75163F");
        vectorizationGraphicData.labels.push(this.totsString(x.timestamp));
        contentsVectorizationVolume.labels.push(this.totsString(x.timestamp));
      });
    }
    */
    this.contentsVectorizationData = vectorizationGraphicData;
    this.contentsVectorizationVolume = contentsVectorizationVolume;
    if (this.jobSummary) {
      /*
      const howMuchTotalToVectorize: number = this.jobSummary.howManyBatchSentToVectorization ? this.jobSummary.howManyBatchSentToVectorization : 0;
      const howMuchAreVectorized: number = this.jobSummary.currentBatchDocumentVectorizedCounter ? this.jobSummary.currentBatchDocumentVectorizedCounter : 0;
      const notYetVectorized = howMuchTotalToVectorize - howMuchAreVectorized;
      if (notYetVectorized >= 0 && howMuchTotalToVectorize >= 0) {
        pichartResults.datasets[0].data.push(notYetVectorized);
        pichartResults.datasets[0].backgroundColor?.push("#36A2EB");
        pichartResults.datasets[0].data.push(howMuchAreVectorized);
        pichartResults.datasets[0].backgroundColor?.push("#32EB8C");
        this.resultsPiechart = pichartResults;
      }*/
    }
  }

  /**
   * Formats timestamp into a readable time string
   * @param timestamp The timestamp to format
   * @returns Formatted time string
   */
  totsString(timestamp: Date | undefined): any {
    let ts: any = "";
    if (timestamp && (typeof timestamp === 'string')) {
      ts = new Date(timestamp).toLocaleTimeString();
    } else if (timestamp && (timestamp as any)?.toLocaleTimeString) {
      ts = timestamp.toLocaleTimeString();
    }
    return ts;
  }

  /**
   * Handles paginator state changes and reloads data with new pagination parameters
   * @param value The new paginator state
   */
  onPageChange(value: PaginatorState) {
    this.actualPage.page = value.page ? value.page : 0;
    this.actualPage.pageSize = value.rows ? value.rows : 0;
    this.reloadPeriodically();
  }

  /**
   * Processes the loaded job status data and updates the job title based on job type
   * @param actualValue The loaded GJobStatus entity
   */
  protected override onLoadedPersistentData(actualValue: GJobStatus): void {
    this.jobTitle = actualValue?.jobType === 'VECTORIZING_CONTENTS' ? " Embedding contents (semantic search preparation) " : "Contents reading task ";
    this.reloadPeriodically();
  }

  /**
   * Manually triggers data reload
   */
  reloadData(): void {
    this.reloadPeriodically();
  }

  /**
   * Retrieves a job status by its code
   * @param code The job code to lookup
   * @returns Observable containing the job status or null
   */
  override findByCode(code: string): Observable<GJobStatus | null> {
    return this.JobLauncherControllerService.getJobStatus(code);
  }

  /**
   * Not implemented - saving is not supported for job status
   */
  override save(value: GJobStatus): Observable<GJobStatus> {
    throw new Error("Method not implemented.");
  }

  /**
   * Not implemented - insertion is not supported for job status
   */
  override insert(value: GJobStatus): Observable<GJobStatus> {
    throw new Error("Method not implemented.");
  }

  /**
   * Deletes a job status entry by its code
   * @param value The job status to delete
   * @returns Observable indicating success or failure
   */
  override delete(value: GJobStatus): Observable<boolean> {
    if (value.code) {
      return this.logViewControllerService.deleteJobStatus([value.code]).pipe(map(r => true));
    }
    return of(false);
  }

  /**
   * Determines if a job status can be deleted based on whether it's finished
   * @param value The job status to check
   * @returns Observable containing deletion permission and message
   */
  override canBeDeleted(value: GJobStatus): Observable<{ canBeDeleted: boolean; message: string; }> {
    return of({ canBeDeleted: this.entity?.finished ? true : false, message: "" })
  }
}