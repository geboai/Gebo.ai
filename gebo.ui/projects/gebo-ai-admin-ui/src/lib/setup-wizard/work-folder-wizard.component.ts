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
 * This file implements components and services for the Work Folder setup wizard in Gebo.ai application.
 * It handles the configuration of work directories including checking their status and enabling/configuring them.
 */

import { Component, Injectable } from "@angular/core";
import { FormControl, FormGroup } from "@angular/forms";
import { BrowseParam, ComponentEnabledStatus, FastWorkDirectorySetupData, FileSystemSharesSettingControllerService, GeboFastWorkFolderSetupControllerService, WorkFolderSetupStatus } from "@Gebo.ai/gebo-ai-rest-api";
import { AbstractStatusService, BaseWizardSectionComponent, browsePathObservableCallback, loadRootsObservableCallback, SetupWizardComunicationService } from "@Gebo.ai/reusable-ui";
import { ToastMessageOptions } from "primeng/api";
import { forkJoin, map, Observable, of } from "rxjs";

/**
 * Service that checks whether the work folder setup is enabled in the system.
 * Extends AbstractStatusService to provide status information about the work folder configuration.
 */
@Injectable()
export class WorkFolderWizardEnabledService extends AbstractStatusService {
    /**
     * Creates an instance of WorkFolderWizardEnabledService.
     * @param workFolderFastSetupService - Service for interacting with work folder setup API
     */
    constructor(private workFolderFastSetupService: GeboFastWorkFolderSetupControllerService) {
        super();
    }

    /**
     * Overrides the base getBooleanStatus method to check if work directory setup is enabled.
     * @returns An Observable that emits true if the work directory setup is enabled, false otherwise
     */
    public override getBooleanStatus(): Observable<boolean> {
        return this.workFolderFastSetupService.getWorkDirectorySetupEnabled().pipe(map(x => x.isEnabled === true));
    }
}

/**
 * Service that checks whether the work folder has been successfully set up.
 * Extends AbstractStatusService to provide status information about the work folder setup completion.
 */
@Injectable()
export class WorkFolderWizardStatusService extends AbstractStatusService {
    /**
     * Creates an instance of WorkFolderWizardStatusService.
     * @param workFolderFastSetupService - Service for interacting with work folder setup API
     */
    constructor(private workFolderFastSetupService: GeboFastWorkFolderSetupControllerService) {
        super();
    }

    /**
     * Overrides the base getBooleanStatus method to check if work directory setup is completed.
     * @returns An Observable that emits true if the work directory is set up, false otherwise
     */
    public override getBooleanStatus(): Observable<boolean> {
        return this.workFolderFastSetupService.getWorkDirectorySetupStatus().pipe(map(x => x.isSetup === true));
    }
}

/**
 * Component that implements the UI for the work folder configuration wizard.
 * Extends BaseWizardSectionComponent to integrate with the wizard framework.
 * Provides functionality to view, browse, and set up work directories.
 */
@Component({
    selector: "gebo-work-folder-wizard-component",
    templateUrl: "work-folder-wizard.component.html",
    standalone: false
})
export class WorkFolderWizardComponent extends BaseWizardSectionComponent {
    /**
     * Flag indicating if the work folder configuration is enabled
     */
    public enabled: boolean = false;
    
    /**
     * Form group for capturing the work directory path
     */
    formGroup: FormGroup = new FormGroup({ workDirectory: new FormControl() });
    
    /**
     * Observable callback for loading root filesystem nodes
     */
    public loadRootsObservable: loadRootsObservableCallback = () => of({});
    
    /**
     * Observable callback for browsing filesystem paths
     */
    public browsePathObservable: browsePathObservableCallback = (param: BrowseParam) => of({});

    /**
     * Creates an instance of WorkFolderWizardComponent.
     * @param setupWizardComunicationService - Service for wizard communication
     * @param workFolderFastSetupService - Service for work folder setup operations
     * @param fileSystemSharesSettingControllerService - Service for filesystem operations
     */
    constructor(setupWizardComunicationService: SetupWizardComunicationService,
        private workFolderFastSetupService: GeboFastWorkFolderSetupControllerService,
        private fileSystemSharesSettingControllerService: FileSystemSharesSettingControllerService) {
        super(setupWizardComunicationService);
        this.loadRootsObservable = () => this.fileSystemSharesSettingControllerService.getRootGFileSystemNodes();
        this.browsePathObservable = (param: BrowseParam) => this.fileSystemSharesSettingControllerService.getGFileSystemNodeChildrens(param);
    }

    /**
     * Loads current work folder configuration data from the backend.
     * Overrides the base reloadData method to fetch both enabled status and setup status.
     */
    public override reloadData(): void {
        const observables: [Observable<ComponentEnabledStatus>, Observable<WorkFolderSetupStatus>] = [this.workFolderFastSetupService.getWorkDirectorySetupEnabled(), this.workFolderFastSetupService.getWorkDirectorySetupStatus()];
        this.loading = true
        forkJoin(observables).subscribe({
            next: (values) => {
                this.enabled = values[0]?.isEnabled === true;
                this.isSetupCompleted = values[1]?.isSetup === true;
                
                this.formGroup.patchValue({
                    workDirectory: values[1]?.workDirectory
                });
            },
            complete: () => {
                this.loading = false;
            }
        })
    }

    /**
     * Saves the configured work folder path to the backend.
     * Updates the UI with any messages or errors returned from the backend.
     */
    public saveWorkFolder(): void {
        const body: FastWorkDirectorySetupData = this.formGroup.value;
        this.loading = true;
        this.workFolderFastSetupService.configureWorkDirectory(body).subscribe(
            {
                next: (value) => {
                    this.userMessages = value?.messages as ToastMessageOptions[];
                    this.isSetupCompleted = value?.result?.isSetup === true;
                    this.loading = false;
                    if (value.hasErrorMessages !== true) {
                        this.formGroup.patchValue({
                            workDirectory: value?.result?.workDirectory
                        });
                    }
                },
                complete: () => {
                    // Complete handler is empty but maintained for structure
                }
            }
        );
    }
}