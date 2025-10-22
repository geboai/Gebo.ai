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
 * LogTableComponent is responsible for displaying a paginated table of job status items.
 * This component can fetch logs based on either className or jobType parameters, and handles
 * pagination, selection, deletion, and displaying details of log entries.
 */
import { Component, Input, OnChanges, SimpleChanges } from "@angular/core";
import { FormGroup } from "@angular/forms";
import { DataPage, GJobStatusItem, GObjectRefGProjectEndpoint, JobsEntriesForJobType, LogViewControllerService, PageableObject, PageGJobStatusItem } from "@Gebo.ai/gebo-ai-rest-api";
import { fieldHostComponentName, GEBO_AI_FIELD_HOST, GeboActionType, GeboUIActionRequest, GeboUIActionRoutingService } from "@Gebo.ai/reusable-ui";
import { PaginatorState } from "primeng/paginator";
import { Observable } from "rxjs";

@Component({
  selector: "gebo-ai-log-table-component",
  templateUrl: "log-table.component.html",
  standalone: false, providers: [{
    provide: GEBO_AI_FIELD_HOST, useExisting: fieldHostComponentName("LogTableComponent"),
    multi: true
  }]
})
export class LogTableComponent implements OnChanges {
  /** Class name used to filter log entries */
  @Input() className?: string;
  /** Job type enumeration to filter log entries */
  @Input() jobType?: JobsEntriesForJobType.JobTypeEnum;
  /** Project endpoint reference to filter log entries */
  @Input() endPointReference?: GObjectRefGProjectEndpoint;
  /** Indicates whether data is currently being loaded */
  public loading: boolean = false;
  /** Pagination information */
  public page: DataPage = { page: 0, pageSize: 20, numrecords: 0 };
  /** Raw paginated data returned from the API */
  data?: PageGJobStatusItem;
  /** Processed job status items */
  actualData: GJobStatusItem[] = [];
  /** Form group for the component */
  formGroup: FormGroup = new FormGroup({});

  /**
   * Initializes the component with required services
   * @param logViewControllerService Service to interact with log-related API endpoints
   * @param geboUIActionRoutingService Service to handle UI action routing
   */
  public constructor(private logViewControllerService: LogViewControllerService, private geboUIActionRoutingService: GeboUIActionRoutingService) {

  }

  /**
   * Lifecycle hook that responds to input property changes
   * Loads data when className or jobType changes
   * @param changes Object containing changed properties
   */
  ngOnChanges(changes: SimpleChanges): void {
    if (this.className && changes["className"] || ((!this.className) && changes["jobType"] && this.jobType)) {
      this.loadData();
    }
  }

  /**
   * Handles pagination events and reloads data with new pagination parameters
   * @param value PaginatorState containing page information
   */
  onPageChange(value: PaginatorState) {
    this.page.page = value.page;
    this.page.pageSize = value.rows;
    this.loadData();
  }

  /**
   * Opens a detailed view of a job status item
   * @param item The job status item to display details for
   */
  showItem(item: GJobStatusItem) {
    const action: GeboUIActionRequest = {
      actionType: GeboActionType.OPEN,
      context: {},
      contextType: "",
      target: item,
      targetType: "GJobStatus",
      onActionPerformed: (actionR) => {
        this.loadData();
      }

    };
    this.geboUIActionRoutingService.routeEvent(action);
  }

  /**
   * Selects all items in the current view
   * Note: Placeholder method - implementation pending
   */
  selectAll() {

  }

  /**
   * Deletes all selected job status items
   * Filters items marked as selected and calls the API to delete them
   */
  deleteSelected() {
    const toBeDeleted = this.actualData.filter(d => ((d as any)["selected"]) === true);
    if (toBeDeleted && toBeDeleted.length) {
      this.loading = true;
      const ids = toBeDeleted.map(x => x.code).filter(x => x ? true : false);
      this.logViewControllerService.deleteJobStatus(ids as string[]).subscribe({
        next: (v) => {
          this.loadData();
        },
        complete: () => {
          this.loading = false;
        }
      });
    }
  }

  /**
   * Resets pagination and reloads data
   * Useful for refreshing the table after operations that modify the dataset
   */
  public reloadData(): void {
    this.page = { page: 0, pageSize: 20, numrecords: 0 };
    this.loadData();
  }

  /**
   * Fetches job status data based on input parameters
   * Uses either endpoint reference or className to retrieve data
   * Updates component state with the fetched data
   */
  private loadData(): void {

    let observable: Observable<PageGJobStatusItem> | undefined = undefined;

    if (this.endPointReference) {
      // Fetch jobs for a specific project endpoint
      observable = this.logViewControllerService.getJobsEntriesForProjectEndpoint(
        {
          endpointRef: this.endPointReference,
          jobType: this.jobType,
          page: this.page
        }
      );
    } else {
      // Fetch jobs for a specific class name
      observable = this.logViewControllerService.getJobsEntriesForClassName({
        className: this.className,
        jobType: this.jobType,
        page: this.page
      });
    }
    if (observable) {
      this.loading = true;
      observable.subscribe({
        next: (value) => {
          this.data = value;
          this.actualData = this.data?.content ? this.data.content : [];

          // Handle different API response formats
          const anyVersion = value as any;
          if (anyVersion && anyVersion?.page?.totalElements) {
            this.data.totalElements = anyVersion?.page?.totalElements;
          }
        },
        error: (error) => { },
        complete: () => {
          this.loading = false;
        }
      });
    }

  }
}