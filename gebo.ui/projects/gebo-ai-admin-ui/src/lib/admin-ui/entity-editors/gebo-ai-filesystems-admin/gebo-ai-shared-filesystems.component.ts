/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */




import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges } from "@angular/core";
import { FileSystemSharesSettingControllerService, GFileSystemShareReference, SharedFilesystemUIConfig } from "@Gebo.ai/gebo-ai-rest-api";
import { fieldHostComponentName, GEBO_AI_FIELD_HOST, GeboActionType, GeboUIActionRoutingService } from "@Gebo.ai/reusable-ui";

/**
 * AI generated comments
 * 
 * Component that manages the display and interaction with shared filesystems.
 * This component allows users to view, open, and create new filesystem shares.
 * It communicates with the backend services to fetch shared filesystem configurations
 * and uses the GeboUIActionRoutingService to handle navigation and actions.
 */
@Component({
    selector: "gebo-ai-shared-filesystem-component",
    templateUrl: "gebo-ai-shared-filesystems.component.html",
    standalone: false,
    providers: [{
        provide: GEBO_AI_FIELD_HOST, useValue: fieldHostComponentName("GeboAISharedFilesystemsComponent"),
        multi: true
    }]
})
export class GeboAISharedFilesystemsComponent implements OnInit, OnChanges {
    /** Flag to indicate if data is currently being loaded */
    public loading: boolean = false;
    /** Controls the visibility of the component */
    @Input() visible: boolean = false;
    /** Event emitter that fires when the component is closed */
    @Output() closed: EventEmitter<boolean> = new EventEmitter();
    /** Configuration for the shared filesystem UI */
    sharedFilesystemConfig?: SharedFilesystemUIConfig;
    /** List of available filesystem shares */
    shares: GFileSystemShareReference[] = [];

    /**
     * Initializes the component with necessary services
     * @param fileSystemSharesSettingControllerService Service to interact with filesystem shares API
     * @param geboUIRoutinService Service to handle UI routing actions
     */
    constructor(private fileSystemSharesSettingControllerService: FileSystemSharesSettingControllerService, private geboUIRoutinService: GeboUIActionRoutingService) {

    }

    /**
     * Lifecycle hook that is called after component initialization
     */
    ngOnInit(): void {

    }

    /**
     * Handles the closing of the component and emits an event to the parent
     * @param e Event that triggered the close
     */
    closeMe(e: any) {
        this.closed.emit(true);
    }

    /**
     * Loads the shared filesystem configuration data from the backend service.
     * Updates the component with the fetched shares and configurations.
     */
    public loadData(): void {
        this.loading = true;
        this.fileSystemSharesSettingControllerService.getSharedFileSystemsActualConfiguration().subscribe({
            next: (value) => {
                this.sharedFilesystemConfig = value;
                if (value.shares) {
                    this.shares = value.shares;
                }
            },
            complete: () => {
                this.loading = false;
            }
        });
    }

    /**
     * Angular lifecycle hook that responds to changes in component inputs.
     * Loads data when the component becomes visible.
     * @param changes Object containing all changed input properties
     */
    ngOnChanges(changes: SimpleChanges): void {
        if (this.visible === true && changes["visible"]) {
            this.loadData();
        }
    }

    /**
     * Opens a specific file share for viewing or editing
     * Uses the GeboUIRoutingService to navigate to the appropriate view
     * @param value The file share reference to be opened
     */
    openFileShare(value: GFileSystemShareReference) {
        this.geboUIRoutinService.routeEvent({
            actionType: GeboActionType.OPEN,
            context: {},
            contextType: "ShareList",
            target: value,
            targetType: "GFileSystemShareReference",
            onActionPerformed: (value) => {
                this.loadData();
            }
        });
    }

    /**
     * Creates a new file share
     * Initializes with default MongoDB configuration and routes to the creation view
     */
    newFileShare() {
        const value: any = {
            mongoConfigured: true
        };
        this.geboUIRoutinService.routeEvent({
            actionType: GeboActionType.NEW,
            context: {},
            contextType: "ShareList",
            target: value,
            targetType: "GFileSystemShareReference",
            onActionPerformed: (value) => {
                this.loadData();
            }
        });
    }
}