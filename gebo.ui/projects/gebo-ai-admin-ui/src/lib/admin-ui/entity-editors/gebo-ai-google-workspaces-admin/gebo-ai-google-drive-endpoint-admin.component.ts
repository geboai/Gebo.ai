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
 * This component manages Google Drive project endpoints in the Gebo.ai system.
 * It handles creating, editing, and deleting Google Drive endpoints that connect
 * projects with Google Drive systems. The component provides functionality for
 * browsing Google Drive contents, managing synchronization settings, and handling
 * authentication with Google Workspace.
 */
import { Component, forwardRef, Injector } from "@angular/core";
import { FormControl, FormGroup } from "@angular/forms";
import { BrowseParam, GGoogleDriveProjectEndpoint, GGoogleDriveSystem, GoogleDriveBrowsingControllerService, GoogleDriveSystemsControllerService, GProject, JobLauncherControllerService, ProjectsControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { BaseEntityEditingComponent, browsePathObservableCallback, GEBO_AI_FIELD_HOST, GeboActionPerformedEvent, GeboActionType, GeboAIFileType, GeboFormGroupsService, GeboUIActionRequest, GeboUIActionRoutingService, GeboUIOutputForwardingService, loadRootsObservableCallback } from "@Gebo.ai/reusable-ui";
import { ConfirmationService, ToastMessageOptions } from "primeng/api";
import { map, Observable, of } from "rxjs";

@Component({
  selector: "gebo-ai-google-drive-endpoint-admin-component",
  templateUrl: "gebo-ai-google-drive-endpoint-admin.component.html",
  standalone: false, providers: [{
    provide: GEBO_AI_FIELD_HOST, useExisting: forwardRef(() => GeboAiGoogleDriveProjectEndpointAdminComponent),
    multi: true
  }]
})
export class GeboAiGoogleDriveProjectEndpointAdminComponent extends BaseEntityEditingComponent<GGoogleDriveProjectEndpoint> {
  /**
   * Entity name identifier for the Google Drive project endpoint
   */
  protected override entityName: string = "GGoogleDriveProjectEndpoint";

  /**
   * Context identifier for Google Workspace authentication
   */
  private actualIdentityContext: string = "GOOGLE_WORKSPACE_CONTEXT";

  /**
   * Form group containing all editable fields for the Google Drive project endpoint
   */
  override formGroup: FormGroup<any> = new FormGroup({
    code: new FormControl(),
    description: new FormControl(),
    parentProjectCode: new FormControl(),
    readonly: new FormControl(),
    published: new FormControl(),
    catalogingCriteria: new FormControl(),
    synchPeriodically: new FormControl(),
    buildSystemsRefs: new FormControl(),
    synchroStrategy: new FormControl(),
    driveSystemCode: new FormControl(),
    vectorizeOnlyExtensions: new FormControl(),
    paths: new FormControl(),
    openZips: new FormControl(),
    programmedTables: new FormControl()
  });

  /**
   * Observable that fetches the list of available projects
   */
  projectsObservable: Observable<GProject[]> = this.projectsController.getProjects();

  /**
   * Array of available Google Drive systems
   */
  drivesObservable = this.googleDriveSystemsControllerService.getGoogleDriveSystems();

  /**
   * UI action request template for creating a new Google Drive system
   */
  newGoogleDriveRequest: GeboUIActionRequest = {
    actionType: GeboActionType.NEW,
    context: {},
    contextType: this.entityName,
    targetType: "GGoogleDriveSystem",
    target: { contentManagementSystemType: "google-drive-handler" } as GGoogleDriveSystem
  };

  /**
   * Flag indicating if Google Drive systems exist in the environment
   */
  googleDriveSystemsExisting: boolean = false;

  /**
   * Flag indicating if the endpoint is published
   */
  published: boolean = false;

  /**
   * Observable callback for loading root directories from Google Drive
   */
  public loadRootsObservable: loadRootsObservableCallback = () => of({});

  /**
   * Observable callback for browsing directories within a Google Drive path
   */
  public browsePathObservable: browsePathObservableCallback = (param: BrowseParam) => of({});



  /**
   * URL to forward to on successful operations
   */
  public uiSuccessForward: string = "";

  /**
   * URL to forward to on failed operations
   */
  public uiErrorForward: string = "";

  /**
   * Tracks the last selected Google Drive system code for change detection
   */
  private lastDriveSystemCode: string = "";



  /**
   * List of supported file types for vectorization
   */
  fileTypesList: GeboAIFileType[] = [];

  /**
   * Component constructor that initializes services and sets up subscriptions
   * for reactive form handling and Google Drive browsing functionality.
   */
  constructor(
    injector: Injector,
    geboFormGroupsService: GeboFormGroupsService,
    private _confirmationService: ConfirmationService,
    private projectsController: ProjectsControllerService,
    private googleDriveBrowsing: GoogleDriveBrowsingControllerService,
    private googleDriveSystemsControllerService: GoogleDriveSystemsControllerService,
    private JobLauncherControllerService: JobLauncherControllerService,
    private actionsRouter: GeboUIActionRoutingService,
    outputForwardingService: GeboUIOutputForwardingService) {
    super(injector, geboFormGroupsService, _confirmationService, actionsRouter, outputForwardingService);
    this.doPeriodicBackendProcessingCheck = true;
    this.formGroup.controls["driveSystemCode"].valueChanges.subscribe(driveSystemCode => {
      if (driveSystemCode !== this.lastDriveSystemCode) {
        this.lastDriveSystemCode = driveSystemCode;
        if (driveSystemCode) {
          this.loadRootsObservable = () => this.googleDriveBrowsing.getGoogleDriveRoots(this.lastDriveSystemCode);
          this.browsePathObservable = (param: BrowseParam) => this.googleDriveBrowsing.browseGoogleDrivePath(param, this.lastDriveSystemCode);
        } else {
          this.loadRootsObservable = () => of({});
          this.browsePathObservable = (param: BrowseParam) => of({});
        }

      }
    });
  }

  /**
   * Retrieves a Google Drive project endpoint by its code
   * @param code The unique identifier code of the endpoint
   * @returns An observable of the found endpoint or null
   */
  override findByCode(code: string): Observable<GGoogleDriveProjectEndpoint | null> {
    return this.googleDriveSystemsControllerService.findGoogleDriveProjectEndpointByCode(code);
  }

  /**
   * Saves changes to an existing Google Drive project endpoint
   * @param value The endpoint object with updated values
   * @returns An observable of the updated endpoint
   */
  override save(value: GGoogleDriveProjectEndpoint): Observable<GGoogleDriveProjectEndpoint> {
    return this.googleDriveSystemsControllerService.updateGoogleDriveProjectEndpoint(value);
  }

  /**
   * Creates a new Google Drive project endpoint
   * @param value The new endpoint object to create
   * @returns An observable of the created endpoint
   */
  override insert(value: GGoogleDriveProjectEndpoint): Observable<GGoogleDriveProjectEndpoint> {
    return this.googleDriveSystemsControllerService.insertGoogleDriveProjectEndpoint(value);
  }

  /**
   * Deletes a Google Drive project endpoint
   * @param value The endpoint object to delete
   * @returns An observable indicating success (true) or failure
   */
  override delete(value: GGoogleDriveProjectEndpoint): Observable<boolean> {
    return this.googleDriveSystemsControllerService.deleteGoogleDriveProjectEndpoint(value).pipe(map(x => true));
  }

  /**
   * Checks if an endpoint can be safely deleted
   * @param value The endpoint to check
   * @returns An observable with deletion status and explanation message
   */
  override canBeDeleted(value: GGoogleDriveProjectEndpoint): Observable<{ canBeDeleted: boolean; message: string; }> {
    return of({ canBeDeleted: true, message: "Can be deleted" });
  }



  /**
   * Initializes the component and loads required data
   */
  override ngOnInit(): void {
    super.ngOnInit();

  }

  /**
   * Publishes the endpoint by creating a background job to process it
   * This triggers synchronization and indexing of Google Drive content
   */
  doPublish(): void {
    const callback: (d: any) => void = (d: any) => {
      const objectReference = this.createBackendObjectReference();
      this.loadingRelatedBackend = true;
      this.JobLauncherControllerService.createJob(objectReference).subscribe({
        next: (jobStatus) => {
          if (jobStatus.result) {
            this.periodicBackendProcessingCheck();
            const action: GeboUIActionRequest = {
              actionType: GeboActionType.OPEN,
              context: this.entity ? this.entity : {},
              contextType: "GGoogleDriveProjectEndpoint",
              target: jobStatus.result,
              targetType: "GJobStatus",
              onActionPerformed: (event: GeboActionPerformedEvent) => {

              }
            };
            this.actionsRouter.routeEvent(action);
            this.cancelAction.emit(true);
          } else {
            this.userMessages = jobStatus.messages as ToastMessageOptions[];
          }
        },
        error: (error) => { },
        complete: () => {
          this.loadingRelatedBackend = false;
        }
      });
    }
    this.doSave(callback);
  }

}