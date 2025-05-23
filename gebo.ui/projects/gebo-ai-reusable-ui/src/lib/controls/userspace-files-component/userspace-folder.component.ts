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
 * This file defines a component for managing userspace folders in the Gebo.ai application.
 * It extends the BaseEntityEditingComponent class to provide CRUD operations for UserspaceFolderDto objects.
 */

import { Component, Injector } from "@angular/core";
import { FormControl, FormGroup } from "@angular/forms";
import { UserspaceControllerService, UserspaceFolderDto, UserspaceKnowledgebaseDto } from "@Gebo.ai/gebo-ai-rest-api";
import { BaseEntityEditingComponent, GeboFormGroupsService, GeboUIActionRoutingService, GeboUIOutputForwardingService } from "@Gebo.ai/reusable-ui";
import { ConfirmationService } from "primeng/api";
import { Observable, of } from "rxjs";

/**
 * Component responsible for editing and managing userspace folders.
 * Provides a form interface for creating, updating, and deleting UserspaceFolderDto objects.
 * Extends BaseEntityEditingComponent to inherit common editing functionality.
 */
@Component({
    selector: "gebo-ai-userspace-edit-component",
    templateUrl: "userspace-folder.component.html",
    standalone: false
})
export class GeboAIUserspaceFolderComponent extends BaseEntityEditingComponent<UserspaceFolderDto> {
    /**
     * The entity name used for identification and form generation
     */
    protected override entityName: string = "UserspaceFolderDto";
    
    /**
     * FormGroup containing form controls for the userspace folder properties
     * Includes controls for code, description, parent knowledgebase code, upload code, and owner
     */
    override formGroup: FormGroup<any> = new FormGroup({
        code: new FormControl(),
        description: new FormControl(),
        parentUserspaceKnowledgebaseCode: new FormControl(),
        uploadCode: new FormControl(),
        owner: new FormControl()
    });
    
    /**
     * Constructor initializes the component with required services
     * 
     * @param injector Angular injector for dependency injection
     * @param geboFormGroupsService Service for handling form groups
     * @param confirmationService Service for user confirmations
     * @param geboUIActionRoutingService Service for UI action routing
     * @param userspaceControllerService Service for userspace CRUD operations
     * @param outputForwardingService Optional service for forwarding outputs
     */
    constructor(injector: Injector,
        geboFormGroupsService: GeboFormGroupsService,
        confirmationService: ConfirmationService,
        geboUIActionRoutingService: GeboUIActionRoutingService,
        private userspaceControllerService: UserspaceControllerService,
        outputForwardingService?: GeboUIOutputForwardingService) {
        super(injector, geboFormGroupsService, confirmationService, geboUIActionRoutingService, outputForwardingService);
    }
    
    /**
     * Retrieves a userspace folder by its unique code
     * 
     * @param code The unique identifier for the userspace folder
     * @returns An Observable containing the found UserspaceFolderDto or null
     */
    override findByCode(code: string): Observable<UserspaceFolderDto | null> {
        return this.userspaceControllerService.findUserspaceFolderByCode(code);
    }
    
    /**
     * Updates an existing userspace folder
     * 
     * @param value The UserspaceFolderDto with updated values
     * @returns An Observable containing the updated UserspaceFolderDto
     */
    override save(value: UserspaceFolderDto): Observable<UserspaceFolderDto> {
        return this.userspaceControllerService.updateUserspaceFolder(value);
    }
    
    /**
     * Creates a new userspace folder
     * 
     * @param value The UserspaceFolderDto to create
     * @returns An Observable containing the newly created UserspaceFolderDto
     */
    override insert(value: UserspaceFolderDto): Observable<UserspaceFolderDto> {
        return this.userspaceControllerService.newUserspaceFolder(value);
    }
    
    /**
     * Deletes a userspace folder
     * 
     * @param value The UserspaceFolderDto to delete
     * @returns An Observable containing a boolean indicating success
     */
    override delete(value: UserspaceFolderDto): Observable<boolean> {
        return this.userspaceControllerService.deleteUserspaceFolder(value);
    }
    
    /**
     * Checks if a userspace knowledgebase can be deleted
     * Currently always returns true with no message
     * 
     * @param value The UserspaceKnowledgebaseDto to check
     * @returns An Observable containing an object with canBeDeleted flag and message
     */
    override canBeDeleted(value: UserspaceKnowledgebaseDto): Observable<{ canBeDeleted: boolean; message: string; }> {
        return of({ canBeDeleted: true, message: "" });
    }

}