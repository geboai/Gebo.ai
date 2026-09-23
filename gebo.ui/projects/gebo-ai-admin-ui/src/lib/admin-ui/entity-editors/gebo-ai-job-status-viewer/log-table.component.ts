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
 * The entries can be restricted to a single project endpoint, to one or more datasource
 * types (class names) and to a job type, and can be freely searched and ordered. Paging,
 * ordering and searching are all resolved server side, so the table always shows a single
 * page of the whole matching set.
 */
import { Component, Input, OnChanges, OnDestroy, SimpleChanges } from "@angular/core";
import { FormGroup } from "@angular/forms";
import { GJobStatusItem, GObjectRefGProjectEndpoint, JobsEntriesFilter, LogViewControllerService, PageGJobStatusItem } from '@Gebo.ai/brain';
import { fieldHostComponentName, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE, GeboActionType, GeboUIActionRequest, GeboUIActionRoutingService } from "@Gebo.ai/reusable-ui";
import { TableLazyLoadEvent } from "primeng/table";
import { Subject, Subscription } from "rxjs";
import { debounceTime } from "rxjs/operators";

/** Property the entries are ordered by when the user did not choose any */
const DEFAULT_SORT_FIELD = "startDateTime";
/** Number of entries shown in a page before the user changes it */
const DEFAULT_PAGE_SIZE = 20;
/** Delay, in milliseconds, applied before a typed search text reaches the server */
const SEARCH_DEBOUNCE_TIME = 400;

@Component({
  selector: "gebo-ai-log-table-component",
  templateUrl: "log-table.component.html",
  standalone: false, providers: [{ provide: GEBO_AI_MODULE, useValue: "GeboAIJobStatusModule", multi: false }, {
    provide: GEBO_AI_FIELD_HOST, multi: false, useValue: fieldHostComponentName("LogTableComponent")
  }]
})
export class LogTableComponent implements OnChanges, OnDestroy {
  /** Class name of a single datasource type used to filter log entries */
  @Input() className?: string;
  /** Class names of the datasource types used to filter log entries, empty meaning every type */
  @Input() classNames?: string[];
  /** Job type enumeration to filter log entries */
  @Input() jobType?: JobsEntriesFilter.JobTypeEnum;
  /** Project endpoint reference to filter log entries */
  @Input() endPointReference?: GObjectRefGProjectEndpoint;
  /** Indicates whether data is currently being loaded */
  public loading: boolean = false;
  /** Index of the first shown entry inside the whole matching set */
  public first: number = 0;
  /** Number of entries shown in a page */
  public pageSize: number = DEFAULT_PAGE_SIZE;
  /** Total number of entries matching the current filter */
  public totalRecords: number = 0;
  /** Property the entries are currently ordered by */
  public sortField: string = DEFAULT_SORT_FIELD;
  /** Ordering direction, 1 for ascending and -1 for descending */
  public sortOrder: number = -1;
  /** Free text the entries are currently searched with */
  public searchText: string = "";
  /** Raw paginated data returned from the API */
  data?: PageGJobStatusItem;
  /** Processed job status items */
  actualData: GJobStatusItem[] = [];
  /** Form group for the component */
  formGroup: FormGroup = new FormGroup({});
  /**
   * True as soon as the hosting template binds the project endpoint reference,
   * telling the entries have to stay scoped to that single endpoint.
   */
  private scopedToProjectEndpoint: boolean = false;
  /** Emits every text typed in the search box, so that only the last one is sent */
  private readonly searchTextChanges: Subject<string> = new Subject<string>();
  /** Subscription of the debounced search, released when the component is destroyed */
  private readonly searchSubscription: Subscription;

  /**
   * Initializes the component with required services
   * @param logViewControllerService Service to interact with log-related API endpoints
   * @param geboUIActionRoutingService Service to handle UI action routing
   */
  public constructor(private logViewControllerService: LogViewControllerService, private geboUIActionRoutingService: GeboUIActionRoutingService) {
    this.searchSubscription = this.searchTextChanges.pipe(debounceTime(SEARCH_DEBOUNCE_TIME)).subscribe((text) => {
      this.searchText = text;
      this.first = 0;
      this.loadData();
    });
  }

  /**
   * Lifecycle hook that responds to input property changes.
   * Restarts the listing from its first page whenever the filtered set changes.
   * @param changes Object containing changed properties
   */
  ngOnChanges(changes: SimpleChanges): void {
    if (changes["endPointReference"]) {
      this.scopedToProjectEndpoint = true;
    }
    if (changes["className"] || changes["classNames"] || changes["jobType"] || changes["endPointReference"]) {
      this.first = 0;
      this.loadData();
    }
  }

  /**
   * Lifecycle hook releasing the debounced search subscription
   */
  ngOnDestroy(): void {
    this.searchSubscription.unsubscribe();
  }

  /**
   * Handles the paging and ordering events raised by the table and reloads the
   * matching page from the server
   * @param event Lazy load event carrying the requested page and ordering
   */
  onLazyLoad(event: TableLazyLoadEvent): void {
    this.first = event.first ?? 0;
    this.pageSize = event.rows ? event.rows : DEFAULT_PAGE_SIZE;
    const sortField = Array.isArray(event.sortField) ? event.sortField[0] : event.sortField;
    this.sortField = sortField ? sortField : DEFAULT_SORT_FIELD;
    this.sortOrder = event.sortOrder === 1 ? 1 : -1;
    this.loadData();
  }

  /**
   * Notifies a change of the search box content, the actual search being delayed
   * so that a reload is not issued for every typed character
   * @param text The text currently typed in the search box
   */
  onSearchTextChanged(text: string): void {
    this.searchTextChanges.next(text ? text : "");
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
   * Restarts the listing from its first page
   * Useful for refreshing the table after operations that modify the dataset
   */
  public reloadData(): void {
    this.first = 0;
    this.loadData();
  }

  /**
   * Fetches the current page of job status entries, applying the configured
   * filters, the free text search and the chosen ordering.
   */
  private loadData(): void {
    if (this.scopedToProjectEndpoint && !this.endPointReference) {
      // The hosting editor scopes this table to a single project endpoint whose
      // reference is not known yet, most often because its entity is still being
      // loaded or has never been saved. Listing anything here would show the jobs
      // of every other datasource, so nothing is listed until it is known.
      this.data = undefined;
      this.actualData = [];
      this.totalRecords = 0;
      return;
    }

    const filter: JobsEntriesFilter = {
      classNames: this.resolveClassNames(),
      endpointRef: this.endPointReference,
      jobType: this.jobType,
      searchText: this.searchText ? this.searchText : undefined,
      sortField: this.sortField,
      sortDescending: this.sortOrder !== 1,
      page: { page: Math.floor(this.first / this.pageSize), pageSize: this.pageSize }
    };

    this.loading = true;
    this.logViewControllerService.getJobsEntries(filter).subscribe({
      next: (value) => {
        this.data = value;
        this.actualData = this.data?.content ? this.data.content : [];
        this.totalRecords = this.data?.totalElements ? this.data.totalElements : 0;
      },
      error: (error) => { },
      complete: () => {
        this.loading = false;
      }
    });
  }

  /**
   * Merges the single class name and the class names list inputs into the list of
   * datasource types the entries have to belong to.
   * @returns The class names to filter with, undefined when every type is wanted
   */
  private resolveClassNames(): string[] | undefined {
    const names: string[] = [];
    if (this.className) {
      names.push(this.className);
    }
    if (this.classNames) {
      this.classNames.filter(name => name && names.indexOf(name) < 0).forEach(name => names.push(name));
    }
    return names.length ? names : undefined;
  }
}
