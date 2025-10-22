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
 * This file manages shared filesystem configurations through Angular components.
 * It handles configuration status tracking and UI components for the setup wizard.
 */

import { Component, Injectable } from "@angular/core";
import { BrowseParam, FileSystemSharesSettingControllerService, GFileSystemShareReference, PathInfo } from "@Gebo.ai/gebo-ai-rest-api";
import { AbstractStatusService, BaseWizardSectionComponent, browsePathObservableCallback, fieldHostComponentName, GEBO_AI_FIELD_HOST, loadRootsObservableCallback, SetupWizardComunicationService } from "@Gebo.ai/reusable-ui";
import { map, Observable } from "rxjs";
import { FormControl, FormGroup } from "@angular/forms";
import { ConfirmationService } from "primeng/api";

/**
 * Service that checks if shared filesystem is already configured.
 * Extends AbstractStatusService to provide status monitoring capabilities.
 * Determines if any shared filesystem configurations already exist.
 */
@Injectable()
export class SharedFilesystemAlreadySetupService extends AbstractStatusService {
    constructor(private sharedFilesystemConfigService: FileSystemSharesSettingControllerService) {
        super();
    }

    /**
     * Overrides the parent method to check if any shared filesystem references are configured.
     * Returns an Observable that emits true if at least one share is configured.
     * @returns Observable<boolean> indicating whether shared filesystem is already set up
     */
    public override getBooleanStatus(): Observable<boolean> {
        return this.sharedFilesystemConfigService.getSharedFileSystemsActualConfiguration().pipe(map(data => (data?.shares && data?.shares?.length > 0) ? true : false));
    }
}

/**
 * Service that checks if the shared filesystem feature is enabled in the system.
 * Extends AbstractStatusService to provide status monitoring capabilities.
 */
@Injectable()
export class SharedFilesystemEnabledService extends AbstractStatusService {
    constructor(private sharedFilesystemConfigService: FileSystemSharesSettingControllerService) {
        super();
    }

    /**
     * Overrides the parent method to check if shared filesystem feature is enabled.
     * @returns Observable<boolean> indicating whether the feature is enabled
     */
    public override getBooleanStatus(): Observable<boolean> {
        return this.sharedFilesystemConfigService.getSharedFileSystemsActualConfiguration().pipe(map(data => data?.uiSettingsEnabled === true));
    }
}

/**
 * Interface extending GFileSystemShareReference to track if a file system share is in use.
 * Adds a notUsed flag to indicate if the share is not being used by any part of the system.
 */
interface ExtendedGFileSystemShareReference extends GFileSystemShareReference {
    notUsed?: boolean;
};

/**
 * Component for configuring shared filesystem in the setup wizard.
 * Provides UI for enabling/disabling shared filesystem functionality and managing filesystem shares.
 * Allows users to add, view, and delete filesystem references.
 */
@Component({
    selector: "gebo-shared-filesystem-wizard-component",
    templateUrl: "shared-filesystem-wizard.component.html",
    standalone: false,
    providers: [{ provide: GEBO_AI_FIELD_HOST, useValue: fieldHostComponentName("SharedFilesystemWizardComponent") }]
})
export class SharedFilesystemWizardComponent extends BaseWizardSectionComponent {
    /** Flag indicating if the shared filesystem setting is enabled */
    settingEnabled: boolean = false;

    /** Collection of filesystem shares configured in the system */
    shares?: ExtendedGFileSystemShareReference[] = [];

    /** Form for adding new filesystem shares */
    formGroup: FormGroup = new FormGroup({
        reference: new FormControl(),
        description: new FormControl()
    });

    /**
     * Callback to load root filesystem nodes 
     * Used for browsing the filesystem hierarchy
     */
    public loadRootsObservable: loadRootsObservableCallback = () => this.sharedFilesystemConfigService.getRootGFileSystemNodes();

    /**
     * Callback to browse filesystem paths
     * @param param Parameters for browsing a specific path
     */
    public browsePathObservable: browsePathObservableCallback = (param: BrowseParam) => { return this.sharedFilesystemConfigService.getGFileSystemNodeChildrens(param) };

    constructor(setupWizardComunicationService: SetupWizardComunicationService,
        private sharedFilesystemConfigService: FileSystemSharesSettingControllerService,
        private confirmService: ConfirmationService) {
        super(setupWizardComunicationService);
    }

    /**
     * Reloads the component data from the backend services.
     * Updates the settings status and fetches all configured filesystem shares.
     * Also checks which shares are in use and marks unused ones.
     */
    public override reloadData(): void {
        this.loading = true;
        this.sharedFilesystemConfigService.getSharedFileSystemsActualConfiguration().subscribe({
            next: (value) => {
                this.settingEnabled = value.uiSettingsEnabled == true;
                this.isSetupCompleted = value?.shares?.length ? true : false;
                this.shares = value.shares;
                const codes: (string | undefined)[] = this.shares ? this.shares.filter(x => x.code && x.mongoConfigured === true).map(w => w.code) : [];
                this.sharedFilesystemConfigService.getUsedFilesystemShares(codes as string[]).subscribe({
                    next: x => {
                        if (x && x.length && this.shares) {
                            const sh: ExtendedGFileSystemShareReference[] = [...this.shares];
                            x.forEach(y => {
                                const found = sh.find(w => w.code === y.code);
                                if (found) {
                                    found.notUsed = y.used <= 0;
                                }
                            });
                            this.shares = sh;
                        }
                    }
                });
            },
            complete: () => {
                this.loading = false;
            }
        });
    }

    /**
     * Saves a new filesystem path reference to the configuration.
     * Takes the form values and sends them to the backend service.
     */
    public saveNewPath(): void {
        const _2set: GFileSystemShareReference = this.formGroup.value;
        _2set.mongoConfigured = true;
        this.loading = true;
        this.sharedFilesystemConfigService.insertFileSystemShareReference(_2set).subscribe({
            next: (value) => {
                this.reloadData();
                this.addFolderOpened = false;
            },
            complete: () => {
                this.loading = false;
            }
        });
    }

    /** Flag to control the visibility of the add folder dialog */
    public addFolderOpened: boolean = false;

    /**
     * Opens the dialog for adding a new folder reference
     */
    public openAddFolderDialog(): void {
        this.addFolderOpened = true;
    }

    /**
     * Deletes a filesystem path reference from the configuration.
     * Shows a confirmation dialog before deletion.
     * @param share The filesystem share reference to delete
     */
    public deletePath(share: ExtendedGFileSystemShareReference) {
        this.confirmService.confirm({
            header: "Delete a shared filesystem reference configuration",
            message: "Delete the shared filesystem reference to " + (share.reference.path ? share.reference.path.absolutePath : share.reference.root.absolutePath),
            accept: () => {
                const ref: ExtendedGFileSystemShareReference = { ...share };
                ref.notUsed = undefined;
                this.loading = true;
                this.sharedFilesystemConfigService.deleteFileSystemShareReference(ref).subscribe({
                    next: (value) => {
                        this.reloadData();
                    },
                    complete: () => {
                        this.loading = false;
                    }
                });
            }
        })
    }
}