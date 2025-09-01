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
 * BaseEntityEditingComponent is an abstract class that provides a foundation for entity editing components
 * in Angular applications. It handles the common functionality for creating, editing, deleting,
 * and validating entities with form controls. This component also supports wizard-based workflows
 * for multi-step entity editing processes.
 * 
 * The component manages various states including loading, validation, backend operations,
 * and user messages, providing a consistent interface for entity management operations.
 */
import { Component, EventEmitter, Injector, Input, OnChanges, OnDestroy, OnInit, Output, SimpleChanges } from "@angular/core";
import { FormControlStatus, FormGroup } from "@angular/forms";
import { ToastMessageOptions } from "primeng/api";
import { Observable, of, Subject } from "rxjs";
import { GeboUIOutputForwardingService } from "../../architecture/gebo-ui-output-forwarding.service";
import { GeboFormGroupsService } from "../../architecture/gebo-form-groups.service";
import { ConfirmationService } from "primeng/api";
import { GObjectRef } from "@Gebo.ai/gebo-ai-rest-api";
import { GeboAIEntitiesSettingWizardConfiguration, WizardButtonsBarData } from "./entities-modification-wizard";
import { errorStatus, IOperationStatus } from "./operation-status";
import { GeboActionType, GeboUIActionRequest, GeboWizardActionPerformedCallback, GeboWizardActionPerformedEvent, GeboWizardActionType } from "../../architecture/actions.model";
import { GeboUIActionRoutingService } from "../../architecture/gebo-ui-action-routing.service";


/**
 * Base component for entity editing functionality that can be extended by specific entity editors.
 * This component provides standard CRUD operations, validation handling, and wizard step navigation.
 * 
 * @template RecordType - The type of entity being edited, which should include optional code and description properties
 */
@Component({
    selector: "base-entity-component",
    template: "",
    standalone: false
})
export abstract class BaseEntityEditingComponent<RecordType extends { code?: string, description?: string }> implements OnInit, OnChanges, OnDestroy {
  /** Flag to track if a save operation is in progress */
  private savingBackend: boolean = false;
  
  /** Flag to track if a delete operation is in progress */
  private deletingBackend: boolean = false;
  
  /** Flag to track if duplicate checking is in progress */
  private checkingDuplicatesBackend: boolean = false;
  
  /** Flag to track if a can-be-deleted check is in progress */
  private canBeDeletedCheckBackend: boolean = false;
  
  /** Flag to track if controls are being loaded */
  private missingControlLoading: boolean = false;
  
  /** Flag to track if related backend operations are in progress */
  protected loadingRelatedBackend: boolean = false;
  
  //if manageOperationStatus is true than in insert and update calls the extending class MUST call the updateLastOperationStatus method with 
  // OperationStatus returned from backend and aborts the functional save and close logic going further to editing and correct mode
  protected manageOperationStatus: boolean = false;
  
  /** Backend class name for object reference */
  protected backendClassName?: string;
  
  /** Flag to track if code was not present */
  public codeWhasNotPresent: boolean = false;
  
  /** Flag to determine if entity can be deleted */
  public canDelete: boolean = false;
  
  /** Collection of messages to display to the user */
  public userMessages: ToastMessageOptions[] = [];
  
  /** Flag to determine if entity can be saved */
  public canSave: boolean = false;
  
  /** Reference to the object in the backend */
  public objectReference?: GObjectRef;
  
  /** Flag to indicate if entity data has been loaded */
  public entityDataLoaded: boolean = false;
  
  /** Flag to indicate if backend processing is running */
  public relatedBackendProcessingRunning: boolean = false;
  
  /** Flag to enable periodic backend processing checks */
  protected doPeriodicBackendProcessingCheck: boolean = false;
  
  /** Abstract property for the entity name that must be defined by child classes */
  protected abstract entityName: string;
  
  /** Last operation status for tracking results of operations */
  private lastOperationStatus?: IOperationStatus<RecordType>;
  
  /** Timer for periodic validation checks */
  private programmed?: any = undefined;
  
  /**
   * Returns the name of the entity being edited
   */
  public get actualEntityName(): string {
    return this.entityName;
  }
  
  /** The mode of operation: NEW, EDIT, or EDIT_OR_NEW */
  @Input() mode: "NEW" | "EDIT" | "EDIT_OR_NEW" = "EDIT";
  
  /** Storage for wizard session data */
  @Input() wizardSessionStorage?: any = undefined;
  
  /** Configuration for wizard steps */
  @Input() wizardStepsConfigurations?: GeboAIEntitiesSettingWizardConfiguration[];
  
  /** Callback for when a wizard action is performed */
  @Input() onWizardActionPerformed?: GeboWizardActionPerformedCallback;
  
  /** ID of the current wizard step configuration */
  @Input() actualWizardStepConfigrationId?: string;
  
  /** The entity being edited */
  @Input() entity?: RecordType;
  
  /** Event emitted when a new entity is created */
  @Output() createdNew: EventEmitter<RecordType> = new EventEmitter();
  
  /** Event emitted when an entity is updated */
  @Output() updated: EventEmitter<RecordType> = new EventEmitter();
  
  /** Event emitted when an entity is deleted */
  @Output() deleted: EventEmitter<boolean> = new EventEmitter();
  
  /** Event emitted when editing is cancelled */
  @Output() cancelAction: EventEmitter<boolean> = new EventEmitter();
  
  /** Subject for tracking form validity */
  public formInvalid: Subject<boolean> = new Subject();
  
  /** Flag to track if entity has been refreshed by code */
  private refreshedByCode: boolean = false;
  
  /**
   * Determines if any backend operation is in progress
   */
  get loading(): boolean {
    return this.missingControlLoading === true || this.loadingRelatedBackend === true || this.savingBackend === true || this.checkingDuplicatesBackend === true || this.canBeDeletedCheckBackend === true || this.deletingBackend === true;
  }
  
  /** The form group for the entity being edited */
  abstract formGroup: FormGroup;

  /**
   * Constructor for BaseEntityEditingComponent
   * 
   * @param injector - Angular injector for dependency injection
   * @param geboFormGroupsService - Service for managing form groups
   * @param confirmationService - Service for handling confirmation dialogs
   * @param geboUIActionRoutingService - Service for routing UI actions
   * @param outputForwardingService - Optional service for forwarding outputs
   */
  constructor(
    private injector: Injector,
    private geboFormGroupsService: GeboFormGroupsService,
    private confirmationService: ConfirmationService,
    private geboUIActionRoutingService: GeboUIActionRoutingService,
    private outputForwardingService?: GeboUIOutputForwardingService) {

  }
  
  /**
   * Lifecycle hook that is called when the component is destroyed
   */
  ngOnDestroy(): void {

  }
  
  /**
   * Updates the object reference based on the entity code and backend class name
   */
  protected updateObjectReference(): void {
    if (this.backendClassName && this.mode === "EDIT" && this.entity?.code) {
      this.objectReference = {
        className: this.backendClassName,
        code: this.entity.code
      };
    } else {
      this.objectReference = undefined;
    }
  }
  
  /**
   * Hook method called when persistent data is loaded
   * Can be overridden by child classes to perform custom logic
   * 
   * @param actualValue - The loaded entity data
   */
  protected onLoadedPersistentData(actualValue: RecordType): void {

  }
  
  /**
   * Hook method called when new data is loaded
   * Can be overridden by child classes to perform custom logic
   * 
   * @param actualValue - The new entity data
   */
  protected onNewData(actualValue: RecordType): void {

  }
  
  /**
   * Updates the last operation status and user messages
   * 
   * @param opStatus - The operation status to update
   */
  protected updateLastOperationStatus(opStatus: IOperationStatus<RecordType>) {
    this.userMessages = opStatus?.messages as ToastMessageOptions[];
    this.lastOperationStatus = opStatus;
  }
  
  /**
   * Periodically checks if the backend is processing the viewed data
   */
  protected periodicBackendProcessingCheck(): void {
    if (this.objectReference && this.doPeriodicBackendProcessingCheck === true) {
      console.log("Checking if backend is processing viewed data");
      this.checkBackendProcessing(this.objectReference).subscribe({
        next: (running) => {
          this.relatedBackendProcessingRunning = running === true;
          if (this.relatedBackendProcessingRunning === true) {
            console.log("Backend IS processing viewed data");
            this.userMessages = [{
              severity: "warn",
              summary: "Backend is elaborating this data",
              detail: "The backend is working on data processing related to this configuration data, wait before trying to modify or save/delete/publish it"
            }];
          }
          setTimeout(() => {
            this.periodicBackendProcessingCheck();
          }, 10000);
        }
      });
    }
  }
  
  /**
   * Sets up periodic form validation
   */
  private pollValidation(): void {

    this.programmed = setTimeout(() => {
      this.formGroup.updateValueAndValidity();
      this.pollValidation();
    }, 1500);
  }
  
  /**
   * Registers the component as a guest with the output forwarding service
   */
  private registerGuest(): void {
    if (this.outputForwardingService) {
      let toBeNotified: WizardButtonsBarData | undefined;
      if (this.isWizardMode) {
        toBeNotified = {
          hasNextStep: this.hasNextStep,
          hasPreviusStep: this.hasPreviusStep,
          nextStepIcon: this.nextStepIcon,
          previusStepIcon: this.previusStepIcon,
          nextStepLabel: this.nextStepLabel,
          previusStepLabel: this.previusStepLabel,
          actualWizardStepConfigrationId: this.actualWizardStepConfigrationId,
          wizardStepsConfigurations: this.wizardStepsConfigurations,
          nextStepAction: () => {
            this.nextStep();
          },
          previusStepAction: () => {
            this.previusStep();
          }
        };
      }
      this.outputForwardingService.registerGuest(this, toBeNotified);
    }
  }
  
  /**
   * Lifecycle hook that is called when the component is initialized
   */
  ngOnInit(): void {
    this.registerGuest();

    if (this.entityName) {
      this.missingControlLoading = true;
      this.geboFormGroupsService.addFormGroupMissingControls(this.formGroup, this.entityName).subscribe({
        next: (info) => {
          this.backendClassName = info?.className;
          this.updateObjectReference();
        },
        error: (error) => { },
        complete: () => {
          this.missingControlLoading = false;
        }
      });
    }
    if (this.doPeriodicBackendProcessingCheck == true) {
      this.periodicBackendProcessingCheck();
    }
    this.formInvalid.next(true);
    this.formGroup.statusChanges.subscribe((status: FormControlStatus) => {
      this.formInvalid.next(status === "INVALID");
    });
    this.pollValidation();

  }
  
  /**
   * Gets the current wizard step configuration
   * 
   * @returns The current wizard step configuration or undefined
   */
  public getActualWizardStep(): GeboAIEntitiesSettingWizardConfiguration | undefined {
    return this.actualWizardStepConfigrationId ? this.wizardStepsConfigurations?.find(x => x.id === this.actualWizardStepConfigrationId) : undefined;
  }
  
  /**
   * Determines if the component is in wizard mode
   */
  public get isWizardMode(): boolean {
    return this.wizardStepsConfigurations ? true : false;
  }
  
  /**
   * Determines if there is a next step in the wizard
   */
  public get hasNextStep(): boolean {
    return this.getActualWizardStep()?.nextStepNavigation ? true : false;
  }

  /**
   * Determines if there is a previous step in the wizard
   */
  public get hasPreviusStep(): boolean {
    return this.getActualWizardStep()?.previusStepNavigation ? true : false;
  }
  
  /**
   * Gets the icon for the previous step button
   */
  public get previusStepIcon(): string {
    const actualWizard = this.getActualWizardStep();
    return actualWizard?.previusStepIcon ? actualWizard.previusStepIcon : "pi pi-arrow-left";
  }
  
  /**
   * Gets the icon for the next step button
   */
  public get nextStepIcon(): string {
    const actualWizard = this.getActualWizardStep();
    return actualWizard?.nextStepIcon ? actualWizard.nextStepIcon : "pi pi-arrow-right";
  }
  
  /**
   * Gets the label for the previous step button
   */
  public get previusStepLabel(): string {
    const actualWizard = this.getActualWizardStep();
    return actualWizard?.previusStepLabel ? actualWizard.previusStepLabel : "Back";
  }
  
  /**
   * Gets the label for the next step button
   */
  public get nextStepLabel(): string {
    const actualWizard = this.getActualWizardStep();
    return actualWizard?.nextStepLabel ? actualWizard.nextStepLabel : "Forward";
  }
  
  /**
   * Navigates to the next step in the wizard
   * Saves the current data before proceeding
   */
  public nextStep(): void {
    const actualStep = this.getActualWizardStep();
    let successfulActionCallback: (data: RecordType) => void = (data: any) => { };
    if (actualStep && actualStep.nextStepNavigation) {
      successfulActionCallback = (data: RecordType) => {
        if (this.onWizardActionPerformed) {
          try {
            const geboWizardAction: GeboWizardActionPerformedEvent = {
              actualWizardStepId: this.actualWizardStepConfigrationId,
              target: data,
              entityName: this.entityName,
              wizardActionType: GeboWizardActionType.GO_FORWARD,
              targetWizardStepId: actualStep.nextStepConfigurationId
            };
            this.onWizardActionPerformed(geboWizardAction);
          } catch (e) { }
        }
        if (actualStep && actualStep.nextStepNavigation) {
          const nextStepObservable = actualStep.nextStepNavigation(this, this.injector, data);
          this.loadingRelatedBackend = true;
          nextStepObservable.subscribe({
            next: (action: GeboUIActionRequest) => {
              let nextUIInputs: any = {};
              if (!action.onWizardActionPerformed) {
                action.onWizardActionPerformed=this.onWizardActionPerformed;
              }
              if (action.targetFormInputs) {
                nextUIInputs = action.targetFormInputs;
              }
              if (actualStep.nextStepConfigurationId) {
                nextUIInputs.wizardStepsConfigurations = this.wizardStepsConfigurations;
                nextUIInputs.actualWizardStepConfigrationId = actualStep.nextStepConfigurationId;
                nextUIInputs.wizardSessionStorage = this.wizardSessionStorage ? this.wizardSessionStorage : {};
                nextUIInputs.wizardSessionStorage[actualStep.id] = data;
                if (nextUIInputs.wizardSessionStorage[actualStep.nextStepConfigurationId]) {
                  //going forward on already existing data in this session rewrites the operation to be
                  //OPEN_OR_NEW with cached entry as target entry
                  action.target = nextUIInputs.wizardSessionStorage[actualStep.nextStepConfigurationId];
                  action.actionType = GeboActionType.OPEN_OR_NEW;
                }
              }

              action.targetFormInputs = nextUIInputs;
              this.geboUIActionRoutingService.routeEvent(action);
            },
            complete: () => {
              this.loadingRelatedBackend = false;
            }
          });
        }
      };
    }
    this.doSave(successfulActionCallback);
  }
  
  /**
   * Navigates to the previous step in the wizard
   */
  private previusStep(): void {
    const actualStep = this.getActualWizardStep();
    if (actualStep && actualStep.previusStepNavigation) {
      const value: RecordType = this.formGroup.value;
      if (this.onWizardActionPerformed) {
        try {
          const geboWizardAction: GeboWizardActionPerformedEvent = {
            actualWizardStepId: this.actualWizardStepConfigrationId,
            target: this.formGroup.value,
            entityName: this.entityName,
            wizardActionType: GeboWizardActionType.GO_BACWKARD,
            targetWizardStepId: actualStep.previusStepConfigurationId
          };
          this.onWizardActionPerformed(geboWizardAction);
        } catch (e) { }
      }
      const previusStepValue = this.wizardSessionStorage && actualStep.previusStepConfigurationId ? this.wizardSessionStorage[actualStep.previusStepConfigurationId] : undefined;
      const previusObservable = actualStep.previusStepNavigation(this, this.injector, value);
      this.loadingRelatedBackend = true;
      previusObservable.subscribe({
        next: (action: GeboUIActionRequest) => {
          let lastUIInputs: any = {};
          if (!action.onWizardActionPerformed) {
            action.onWizardActionPerformed=this.onWizardActionPerformed;
          }
          if (action.targetFormInputs) {
            lastUIInputs = action.targetFormInputs;
          }
          if (actualStep.previusStepConfigurationId) {
            lastUIInputs.wizardStepsConfigurations = this.wizardStepsConfigurations;
            lastUIInputs.actualWizardStepConfigrationId = actualStep.previusStepConfigurationId;
            lastUIInputs.wizardSessionStorage = this.wizardSessionStorage;
            if (previusStepValue) {
              action.target = previusStepValue;
              action.actionType = GeboActionType.OPEN;
            }
            action.targetFormInputs = lastUIInputs;
            this.geboUIActionRoutingService.routeEvent(action);
            this.cancelAction.emit(true);
          }

        },
        complete: () => {
          this.loadingRelatedBackend = false;
        }
      })
    }
  }
  
  /**
   * Lifecycle hook that is called when component inputs change
   * 
   * @param changes - The changes that occurred
   */
  ngOnChanges(changes: SimpleChanges): void {
    if (this.actualWizardStepConfigrationId && this.wizardStepsConfigurations && (changes["actualWizardStepConfigrationId"] || changes["wizardStepsConfigurations"])) {
      this.registerGuest();
    }
    if (changes["entity"] || changes["mode"]) {
      if (this.entity) {
        this.formGroup.patchValue(this.entity);
        this.codeWhasNotPresent = this.entity.code ? false : true;
        this.canSave = true;
        if (this.mode === "EDIT" && !this.codeWhasNotPresent) {
          if (this.refreshedByCode === false) {
            this.refreshedByCode = true;
            this.doReloadByCode();
          } else {
            this.onLoadedPersistentData(this.entity);
            this.checkCanBeDeleted(this.entity);
          }
        } else if (this.mode === 'NEW') {
          this.entityDataLoaded = true;
          this.onNewData(this.entity);
          this.formGroup.updateValueAndValidity();
        } else if (this.mode === "EDIT_OR_NEW") {
          if (!this.entity?.code) throw new Error("Cannot run with EDIT_OR_NEW with no entity.code");
          this.doCheckEditOrNew();
        }
      } else {
        this.formGroup.patchValue({});
        this.codeWhasNotPresent = true;
        this.canSave = true;
      }
    }
  }
  
  /**
   * Checks whether to edit an existing entity or create a new one
   * based on whether the entity exists in the backend
   */
  private doCheckEditOrNew(): void {
    if (this.entity?.code) {
      this.loadingRelatedBackend = true;
      this.findByCode(this.entity.code).subscribe({
        next: (value) => {
          if (value) {
            this.mode = "EDIT";
            this.refreshedByCode = true;
            this.entity = value;
            this.formGroup.patchValue(value);
            this.updateObjectReference();
            this.onLoadedPersistentData(value);
            this.periodicBackendProcessingCheck();
          } else {
            this.mode = "NEW";
            this.refreshedByCode = false;
            if (this.entity) {
              this.formGroup.patchValue(this.entity);
              this.onNewData(this.entity);
            }
          }
        },
        complete: () => {
          this.loadingRelatedBackend = false;
        }
      });
    }
  }
  
  /**
   * Checks if the entity can be deleted and updates UI accordingly
   * 
   * @param value - The entity to check
   */
  private checkCanBeDeleted(value: RecordType): void {
    this.canBeDeletedCheckBackend = true;
    this.canBeDeleted(value).subscribe({
      next: (returned) => {
        this.canDelete = returned.canBeDeleted;
        if (!this.canDelete) this.userMessages = [{ id: "CANNOT-DELETE", detail: returned.message, severity: "WARN" }];
      },
      error: (error) => {
        this.userMessages = [{
          id: "SERVERERROR",
          detail: "Server error saving: " + error,
          severity: "error"
        }];
      },
      complete: () => {
        this.canBeDeletedCheckBackend = false;
      }

    });
  }
  
  /**
   * Creates an artificial operation status for success scenarios
   * 
   * @param value - The entity data
   */
  protected callArtificiallyOperationStatus(value: RecordType) {
    const ios: IOperationStatus<RecordType> = {
      messages: [{ summary: "Saved with success", detail: "Update operation done successfully", severity: "success", jobId: "OK", id: "OK", timestamp: 0 }],
      result: value
    };
    this.updateLastOperationStatus(ios);
  }
  
  /**
   * Calls the update operation for an existing entity
   * 
   * @param value - The entity to update
   * @param successfulActionCallback - Optional callback to execute on success
   */
  private callUpdate(value: RecordType, successfulActionCallback?: (data: RecordType) => void): void {
    this.savingBackend = true;
    this.save(value).subscribe({
      next: (returned) => {
        if (!this.manageOperationStatus) {
          this.callArtificiallyOperationStatus(returned);
        }
        if (!errorStatus(this.lastOperationStatus)) {
          this.mode = "EDIT";
        }
        this.entity = returned;
        this.formGroup.patchValue(this.entity);
        if (!errorStatus(this.lastOperationStatus)) {
          this.updateObjectReference();
          this.onLoadedPersistentData(this.entity);
          this.entityDataLoaded = true;
          if (successfulActionCallback) {
            try {
              successfulActionCallback(returned);
            } catch (e) {
              console.error(e);
            }
          }
          this.updated.emit(this.entity);

          this.codeWhasNotPresent = false;
          this.cancelAction.emit(true);

        }
      },
      error: (error) => {
        this.userMessages = [{
          id: "SERVERERROR",
          detail: "Server error saving: " + error,
          severity: "error"
        }];
      },
      complete: () => {
        this.savingBackend = false;
      }
    })
  }
  
  /**
   * Calls the insert operation for a new entity
   * 
   * @param value - The entity to insert
   * @param successfulActionCallback - Optional callback to execute on success
   */
  private callInsert(value: RecordType, successfulActionCallback?: (data: RecordType) => void): void {
    this.savingBackend = true;
    this.insert(value).subscribe({
      next: (returned) => {
        if (!this.manageOperationStatus) {
          this.callArtificiallyOperationStatus(returned);
        }
        if (!errorStatus(this.lastOperationStatus)) {
          this.mode = "EDIT";
        }
        this.entity = returned;
        this.formGroup.patchValue(this.entity);
        if (!errorStatus(this.lastOperationStatus)) {
          this.entityDataLoaded = true;
          this.updateObjectReference();
          this.onLoadedPersistentData(this.entity);
          this.codeWhasNotPresent = false;
          if (successfulActionCallback) {
            try {
              successfulActionCallback(returned);
            } catch (e) {
              console.error(e);
            }
          }

          this.createdNew.emit(this.entity);

          this.cancelAction.emit(true);
        }
      },
      error: (error) => {
        this.userMessages = [{
          id: "SERVERERROR",
          detail: "Server error saving: " + error,
          severity: "error"
        }];
      },
      complete: () => {
        this.savingBackend = false;
      }
    })
  }
  
  /**
   * Saves the entity (creates a new one or updates an existing one)
   * 
   * @param successfulActionCallback - Optional callback to execute on success
   */
  doSave(successfulActionCallback?: (data: RecordType) => void): void {
    const value: RecordType = this.formGroup.value;
    if (this.mode === "NEW") {
      this.checkingDuplicatesBackend = true;
      this.canBeInserted(value).subscribe({
        next: (returned) => {
          if (returned.canBeInserted === true) {
            this.callInsert(value, successfulActionCallback);
          } else {
            this.canSave = false;
            this.userMessages = [
              {
                severity: "error",
                detail: returned.message,
                id: "CANNOT_INSERT"
              }
            ]
          }
        },
        error: (error) => {
          this.userMessages = [{
            id: "SERVERERROR",
            detail: "Error while checking if can be inserted : " + error,
            severity: "error"
          }];
        },
        complete: () => {
          this.checkingDuplicatesBackend = false;
        }
      })
    } else {
      this.callUpdate(value, successfulActionCallback);
    }
  }
  
  /**
   * Deletes the entity after confirmation
   * 
   * @param successfulActionCallback - Optional callback to execute on success
   */
  doDelete(successfulActionCallback?: (data: RecordType) => void): void {
    const value: RecordType = this.formGroup.value;

    if (this.canDelete) {
      const confirmCallback: () => void = () => {
        this.deletingBackend = true;
        this.delete(value).subscribe({
          next: (returned) => {


            if (returned === false) {
              this.canDelete = false;
              this.canSave = false;
              this.userMessages = [
                {
                  severity: "error",
                  detail: "Cannot delete these informations",
                  id: "CANNOT_DELETE"
                }
              ];
            } else {
              if (successfulActionCallback) {

                try {
                  successfulActionCallback(value);
                } catch (e) {
                  console.error(e);
                }
              }
              this.deleted.emit(true);
              this.cancelAction.emit(true);
            }

          },
          error: (error) => {
            this.userMessages = [{
              id: "SERVERERROR",
              detail: "Server checking can be deleted: " + error,
              severity: "error"
            }];
          },
          complete: () => {
            this.deletingBackend = false;
          }
        });
      };
      this.confirmationService.confirm({
        accept: confirmCallback,
        icon: 'pi pi-exclamation-triangle',
        header: "Delete confirm",
        message: "Are you shure you want to delete this data?",
        closeOnEscape: true
      });
    }

  }
  
  /**
   * Finds an entity by its code
   * Must be implemented by child classes
   * 
   * @param code - The code to look up
   * @returns An Observable that resolves to the entity or null
   */
  abstract findByCode(code: string): Observable<RecordType | null>;
  
  /**
   * Saves an existing entity
   * Must be implemented by child classes
   * 
   * @param value - The entity to save
   * @returns An Observable that resolves to the saved entity
   */
  abstract save(value: RecordType): Observable<RecordType>;
  
  /**
   * Inserts a new entity
   * Must be implemented by child classes
   * 
   * @param value - The entity to insert
   * @returns An Observable that resolves to the inserted entity
   */
  abstract insert(value: RecordType): Observable<RecordType>;
  
  /**
   * Deletes an entity
   * Must be implemented by child classes
   * 
   * @param value - The entity to delete
   * @returns An Observable that resolves to a boolean indicating success
   */
  abstract delete(value: RecordType): Observable<boolean>;

  /**
   * Checks if the backend is processing the referenced entity
   * Can be overridden by child classes to implement custom logic
   * 
   * @param reference - The object reference to check
   * @returns An Observable that resolves to a boolean indicating if processing is happening
   */
  protected checkBackendProcessing(reference: { className?: string, code?: string }): Observable<boolean> {
    return of(false);
  }
  
  /**
   * Checks if an entity can be inserted
   * Can be overridden by child classes to implement custom validation
   * 
   * @param value - The entity to check
   * @returns An Observable that resolves to an object indicating if insertion is allowed
   */
  canBeInserted(value: RecordType): Observable<{ canBeInserted: boolean, message: string }> {
    return of({ canBeInserted: true, message: "" })
  }
  
  /**
   * Checks if an entity can be deleted
   * Must be implemented by child classes
   * 
   * @param value - The entity to check
   * @returns An Observable that resolves to an object indicating if deletion is allowed
   */
  abstract canBeDeleted(value: RecordType): Observable<{ canBeDeleted: boolean, message: string }>;
  
  /**
   * Creates a reference to the backend object
   * 
   * @returns An object with code and class name
   */
  protected createBackendObjectReference(): { code?: string, className?: string } {
    const reference: { code?: string, className?: string } = {
      code: this.formGroup.controls["code"].value,
      className: this.backendClassName
    };
    return reference;
  }
  
  /**
   * Reloads the entity data by its code
   */
  public doReloadByCode() {
    if (this.entity && this.entity.code) {
      this.loadingRelatedBackend = true;
      this.findByCode(this.entity.code).subscribe({
        next: (value) => {
          if (value) {
            this.entity = value;
            this.formGroup.patchValue(value);
            this.onLoadedPersistentData(this.entity);
            this.checkCanBeDeleted(this.entity);
            this.entityDataLoaded = true;
            this.updateObjectReference();
            this.periodicBackendProcessingCheck();
            this.formGroup.updateValueAndValidity();
          }
        },
        error: (error) => { },
        complete: () => {
          this.loadingRelatedBackend = false;
        }
      });
    }
  }
}