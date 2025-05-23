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
 * This component manages filesystem share references in the Gebo.ai system.
 * It extends BaseEntityEditingComponent to provide CRUD operations for GFileSystemShareReference entities.
 * The component handles validation, filesystem navigation, and communication with backend services.
 */
import { Component, Injector } from "@angular/core";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { BrowseParam, FileSystemSharesSettingControllerService, GFileSystemShareReference } from "@Gebo.ai/gebo-ai-rest-api";
import { BaseEntityEditingComponent, browsePathObservableCallback, GeboFormGroupsService, GeboUIActionRoutingService, GeboUIOutputForwardingService, loadRootsObservableCallback, reconstructNavigationObservableCallback, VFilesystemReference } from "@Gebo.ai/reusable-ui";
import { ConfirmationService } from "primeng/api";
import { map, Observable, of } from "rxjs";

/**
 * Component responsible for administering GFileSystemShareReference entities
 * Provides UI for creating, viewing, editing, and deleting filesystem share references
 * Uses Angular Forms for data entry and validation
 */
@Component({
    selector: "gebo-ai-filesystem-share-reference-admin-component",
    templateUrl: "gebo-ai-filesystem-share-reference-admin.component.html",
    standalone: false
})
export class GeboAIGFileSystemShareReferenceAdminComponent extends BaseEntityEditingComponent<GFileSystemShareReference> {

    /**
     * Defines the entity name for this component
     */
    protected override entityName: string = "GFileSystemShareReference";
    
    /**
     * Form group that captures and validates user input for the filesystem share reference
     * Contains fields for code, description, mongoConfigured flag, and the actual reference
     */
    override formGroup: FormGroup<any> = new FormGroup({
        code: new FormControl(),
        description: new FormControl(),
        mongoConfigured: new FormControl(),
        reference: new FormControl(Validators.required)
    });
    
    /**
     * Observable callback to load the root filesystem nodes
     * Used for navigation tree population
     */
    public loadRootsObservable: loadRootsObservableCallback = () => this.fileSystemSharesSettingControllerService.getRootGFileSystemNodes();
    
    /**
     * Observable callback to browse filesystem path
     * Used for navigating through the filesystem hierarchy
     */
    public browsePathObservable: browsePathObservableCallback = (param: BrowseParam) => { return this.fileSystemSharesSettingControllerService.getGFileSystemNodeChildrens(param) };
    
    /**
     * Observable callback to reconstruct navigation from points
     * Used for restoring navigation state
     */
    public reconstructNavigationObservableCallback: reconstructNavigationObservableCallback = (navigationPoints: VFilesystemReference[]) => { return this.fileSystemSharesSettingControllerService.getGFileSystemNodeNavigationStatus(navigationPoints); }
    
    /**
     * Component constructor that injects required services
     * 
     * @param injector Angular injector for dependency injection
     * @param fileSystemSharesSettingControllerService Service for filesystem share operations
     * @param geboFormGroupsService Service for form group management
     * @param confirmationService Service for user confirmations
     * @param geboUIActionRoutingService Service for UI action routing
     * @param outputForwardingService Optional service for output forwarding
     */
    constructor(injector: Injector, private fileSystemSharesSettingControllerService: FileSystemSharesSettingControllerService, geboFormGroupsService: GeboFormGroupsService, confirmationService: ConfirmationService, geboUIActionRoutingService: GeboUIActionRoutingService, outputForwardingService?: GeboUIOutputForwardingService) {
        super(injector, geboFormGroupsService, confirmationService, geboUIActionRoutingService, outputForwardingService);
    }
    
    /**
     * Angular lifecycle hook that initializes the component
     * Calls the parent class initialization
     */
    override ngOnInit(): void {
        super.ngOnInit();

    }
    
    /**
     * Checks if the provided filesystem share reference can be inserted
     * Validates the entity against backend rules
     * 
     * @param value The filesystem share reference to validate
     * @returns Observable containing validation result and message
     */
    override canBeInserted(value: GFileSystemShareReference): Observable<{ canBeInserted: boolean; message: string; }> {
        return this.fileSystemSharesSettingControllerService.checkCanBeInsertedFileSystemShareReference(value).pipe(map(r => {
            if (r?.result) {
                return { canBeInserted: true, message: "Ok" };
            } else {
                let message: string = "";
                if (r.messages) {
                    r.messages.forEach((v, i) => {
                        message += v.detail + "\n,"
                    });
                }
                return { canBeInserted: false, message: message };
            }

        }));
    }
    
    /**
     * Retrieves a filesystem share reference by its code
     * 
     * @param code The unique code of the filesystem share reference
     * @returns Observable with the found entity or null
     */
    override findByCode(code: string): Observable<GFileSystemShareReference | null> {
        return this.fileSystemSharesSettingControllerService.getFileSystemShareReferenceByCode(code);
    }
    
    /**
     * Updates an existing filesystem share reference
     * This method is not implemented and throws an error when called
     * 
     * @param value The filesystem share reference to update
     * @throws Error indicating the method is not implemented
     */
    override save(value: GFileSystemShareReference): Observable<GFileSystemShareReference> {
        throw new Error("Method not implemented.");
    }
    
    /**
     * Creates a new filesystem share reference
     * 
     * @param value The filesystem share reference to create
     * @returns Observable with the created entity
     */
    override insert(value: GFileSystemShareReference): Observable<GFileSystemShareReference> {
        return this.fileSystemSharesSettingControllerService.insertFileSystemShareReference(value);
    }
    
    /**
     * Deletes a filesystem share reference
     * 
     * @param value The filesystem share reference to delete
     * @returns Observable indicating success/failure
     */
    override delete(value: GFileSystemShareReference): Observable<boolean> {
        return this.fileSystemSharesSettingControllerService.deleteFileSystemShareReference(value);
    }
    
    /**
     * Checks if the provided filesystem share reference can be deleted
     * Currently always returns true with no validation
     * 
     * @param value The filesystem share reference to check
     * @returns Observable with deletion permission status
     */
    override canBeDeleted(value: GFileSystemShareReference): Observable<{ canBeDeleted: boolean; message: string; }> {
        return of({ canBeDeleted: true, message: "" });
    }


}