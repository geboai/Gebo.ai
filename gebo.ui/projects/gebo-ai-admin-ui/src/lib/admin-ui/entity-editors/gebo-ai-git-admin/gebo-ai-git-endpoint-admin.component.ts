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
 * This is the Git Endpoint Administration Component which allows users to manage Git project endpoints
 * in the Gebo.ai application. It extends the BaseEntityEditingComponent to provide CRUD operations
 * for Git project endpoints.
 */
import { JobLauncherControllerService } from '@Gebo.ai/gebo-ai-rest-api';
import { Component, forwardRef, Injector, Input } from "@angular/core";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { GGitContentManagementSystem, GitSystemsControllerService, GProject, GGitProjectEndpoint, ProjectsControllerService, SecretsControllerService, SecretInfo, GJobStatus } from "@Gebo.ai/gebo-ai-rest-api";
import { map, Observable, of } from "rxjs";
import { BaseEntityEditingComponent, GEBO_AI_FIELD_HOST, GeboActionPerformedEvent, GeboActionType, GeboAIFileType, GeboFormGroupsService, GeboUIActionRequest, GeboUIActionRoutingService, GeboUIOutputForwardingService, IOperationStatus } from "@Gebo.ai/reusable-ui";
import { ConfirmationService, ToastMessageOptions } from 'primeng/api';
import { newSecretActionRequest } from '../utils/gebo-ai-create-secret-action-request-factory';

@Component({
  selector: "gebo-ai-git-endpoint-admin-component",
  templateUrl: "gebo-ai-git-endpoint-admin.component.html",
  standalone: false, providers: [{
    provide: GEBO_AI_FIELD_HOST, useExisting: forwardRef(() => GeboAiGitEndpointAdminComponent),
    multi: true
  }]
})
export class GeboAiGitEndpointAdminComponent extends BaseEntityEditingComponent<GGitProjectEndpoint> {
  /**
   * Defines the entity name for this component as GGitProjectEndpoint
   */
  protected override entityName: string = "GGitProjectEndpoint";

  /**
   * Input property to control whether the Git system can be modified
   */
  @Input() cantModifyGitSystem: boolean = false;

  /**
   * Input property to control whether the project can be modified
   */
  @Input() cantModifyProject: boolean = false;

  /**
   * Form group to manage all the input fields for the Git endpoint
   */
  formGroup: FormGroup<any> = new FormGroup({
    code: new FormControl(),
    description: new FormControl(),
    parentProjectCode: new FormControl(),
    rootObjectCode: new FormControl(),
    contentManagementSystem: new FormControl(),
    repositoryUri: new FormControl(),
    branch: new FormControl(),
    publicAccess: new FormControl(),
    identityCode: new FormControl(),
    localRepository: new FormControl(),
    buildSystemsRefs: new FormControl(),
    published: new FormControl(),
    openZips: new FormControl(),
    vectorizeOnlyExtensions: new FormControl(),
    programmedTables: new FormControl()
  });

  /**
   * Observable to fetch and display available projects
   */
  projectsObservable: Observable<GProject[]> = this.projectsController.getProjects();

  /**
   * Observable to fetch and display available Git systems
   */
  gitSystemsObservable: Observable<GGitContentManagementSystem[]> = this.gitSystemsController.getGitSystems();

  /**
   * Context identifier for Git identity secrets
   */
  private actualIdentityContext: string = "git";

  /**
   * Observable to fetch and display available identities for Git authentication
   */
  identitiesObservable: Observable<SecretInfo[]> = this.secretControllerService.getSecretsByContextCode(this.actualIdentityContext);

  /**
   * Observable to fetch and display available Git branches
   */
  branchesObservable: Observable<{ code: string, description: string }[]> = of([]);

  /**
   * Flag to track whether the repository has public access
   */
  publicAccess: boolean = false;

  /**
   * Flag to track whether the endpoint is published
   */
  published: boolean = false;

  /**
   * Action request for creating a new Git secret
   */
  public newSecretAction = newSecretActionRequest("git", this.entityName, this.entity, ['SSH_KEY', 'USERNAME_PASSWORD']);

  /**
   * Stores the last values of the endpoint to detect changes
   */
  private lastValues?: GGitProjectEndpoint;

  /**
   * List of file types supported by the system
   */
  fileTypesList: GeboAIFileType[] = [];

  /**
   * Constructor for the Git Endpoint Admin Component
   * Initializes services and sets up form control behavior
   */
  constructor(
    injector: Injector,
    private gitSystemsController: GitSystemsControllerService,
    private projectsController: ProjectsControllerService,
    private secretControllerService: SecretsControllerService,
    private JobLauncherControllerService: JobLauncherControllerService,
    private actionsRouter: GeboUIActionRoutingService,
    confirmService: ConfirmationService,
    private geboFGService: GeboFormGroupsService,
    private outForwardingService?: GeboUIOutputForwardingService) {
    super(injector, geboFGService, confirmService, actionsRouter, outForwardingService);

    this.manageOperationStatus = true;
    this.formGroup.controls["publicAccess"].valueChanges.subscribe(publicAccess => {
      this.publicAccess = publicAccess;
      if (this.publicAccess) {
        this.formGroup.controls["identityCode"].disable();
        this.formGroup.controls["identityCode"].clearValidators();
      }
      else {
        this.formGroup.controls["identityCode"].enable();
        this.formGroup.controls["identityCode"].setValidators(Validators.required);
      }
      this.onGitConnectivityChange();
    });
    this.formGroup.controls["published"].valueChanges.subscribe(published => {
      this.published = published;
    });
    this.doPeriodicBackendProcessingCheck = true;

  }

  /**
   * Evaluates whether the branches list needs to be reloaded based on changes to repository URI,
   * identity code, or public access flag
   * @param value The current Git project endpoint values
   */
  private evaluateBranchesListReload(value: GGitProjectEndpoint) {
    if (value.repositoryUri && ((value.identityCode && (value.publicAccess === false || value.publicAccess === undefined || value.publicAccess === null)) || (value.publicAccess === true))) {
      let changeObservable: boolean = false;
      if (value.repositoryUri !== this.lastValues?.repositoryUri || (value.identityCode !== this.lastValues?.identityCode)) {
        changeObservable = true;
      } else if ((value.repositoryUri !== this.lastValues?.repositoryUri || value.publicAccess !== this.lastValues.publicAccess) && value.publicAccess === true) {
        changeObservable = true;
      }
      if (changeObservable === true) {
        this.branchesObservable = this.gitSystemsController.getBranchesList(value).pipe(map(os => {
          this.updateLastOperationStatus(os as IOperationStatus<any>);
          if (os.result) return os.result.map(x => {
            return { code: x, description: x };
          });
          else return [];
        }));
      }

    }
    if (value.repositoryUri && value.publicAccess !== true && !value.identityCode) {
      this.branchesObservable = of([]);
    }
    this.lastValues = value;
  }

  /**
   * Handles changes to Git connectivity parameters and triggers branch list refresh
   */
  onGitConnectivityChange() {
    console.log("onGitConnectivityChange() called");
    const value: GGitProjectEndpoint = this.formGroup.value;
    this.evaluateBranchesListReload(value);
  }

  /**
   * Checks if there are any backend processing jobs running for this entity
   * @param reference The entity reference to check
   * @returns Observable<boolean> indicating if backend jobs are running
   */
  protected override checkBackendProcessing(reference: { className?: string; code?: string; }): Observable<boolean> {
    return this.JobLauncherControllerService.getHasRunningJobs(reference).pipe(map(r => r?.hasRunningJobs === true));
  }

  /**
   * Finds a Git endpoint by its code
   * @param code The endpoint code to search for
   * @returns Observable containing the found Git endpoint or null
   */
  override findByCode(code: string): Observable<GGitProjectEndpoint | null> {
    return this.gitSystemsController.findGitEndpointsByQbe({ code: code }).pipe(map(projs => projs && projs.length ? projs[0] : null));
  }

  /**
   * Saves changes to an existing Git endpoint
   * @param value The Git endpoint to update
   * @returns Observable containing the updated Git endpoint
   */
  override save(value: GGitProjectEndpoint): Observable<GGitProjectEndpoint> {
    return this.gitSystemsController.updateGitEndpoint(value).pipe(map(r => {
      this.updateLastOperationStatus(r);
      return r.result ? r.result : {};
    }));
  }

  /**
   * Deletes a Git endpoint
   * @param value The Git endpoint to delete
   * @returns Observable indicating success
   */
  override delete(value: GGitProjectEndpoint): Observable<boolean> {
    return this.gitSystemsController.deleteGitEndpoint(value).pipe(map(t => true));
  }

  /**
   * Inserts a new Git endpoint
   * @param value The Git endpoint to insert
   * @returns Observable containing the inserted Git endpoint
   */
  override insert(value: GGitProjectEndpoint): Observable<GGitProjectEndpoint> {
    return this.gitSystemsController.insertGitEndpoint(value).pipe(map(r => {
      this.updateLastOperationStatus(r);
      return r.result ? r.result : {};
    }));
  }

  /**
   * Checks if the Git endpoint can be deleted
   * @param value The Git endpoint to check
   * @returns Observable containing the deletion status and message
   */
  override canBeDeleted(value: GGitProjectEndpoint): Observable<{ canBeDeleted: boolean; message: string; }> {
    //TODO: create backend services to check this
    return of({ canBeDeleted: true, message: "" });
  }

  /**
   * Handles actions after loading persistent data
   * @param actualValue The loaded Git endpoint data
   */
  protected override onLoadedPersistentData(actualValue: GGitProjectEndpoint): void {
    this.evaluateBranchesListReload(actualValue);
  }

  /**
   * Handles actions when new data is created
   * @param actualValue The new Git endpoint data
   */
  protected override onNewData(actualValue: GGitProjectEndpoint): void {
    this.evaluateBranchesListReload(actualValue);
  }

  /**
   * Publishes the Git endpoint by creating a job in the backend
   * Saves the data first, then initiates the job and routes to the job status view
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
              contextType: "GGitProjectEndpoint",
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