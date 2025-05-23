/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

import { Component, Injector } from "@angular/core";
import { FormControl, FormGroup } from "@angular/forms";
import { GroupInfo, UserControllerService, UserspaceControllerService, UserspaceKnowledgebaseDto } from "@Gebo.ai/gebo-ai-rest-api";
import { BaseEntityEditingComponent, GeboFormGroupsService, GeboUIActionRoutingService, GeboUIOutputForwardingService } from "@Gebo.ai/reusable-ui";
import { ConfirmationService } from "primeng/api";
import { Observable, of } from "rxjs";

/**
 * AI generated comments
 * 
 * Component responsible for managing userspace knowledgebase entities.
 * Provides UI for creating, reading, updating, and deleting userspace knowledgebases.
 * Extends the BaseEntityEditingComponent to leverage common CRUD functionality.
 * Connects to backend services to perform operations on knowledgebase entities.
 */
@Component({
    selector: "gebo-ai-userspace-knowledgebase-component",
    templateUrl: "user-knowledgebase.component.html",
    standalone: false
})
export class GeboAIUserspaceKnowledgebaseComponent extends BaseEntityEditingComponent<UserspaceKnowledgebaseDto> {
    /**
     * Identifies the type of entity being edited
     */
    protected override entityName: string = "UserspaceKnowledgebaseDto";
    
    /**
     * Form group that contains all form controls for the knowledgebase entity
     */
    override formGroup: FormGroup<any> = new FormGroup({
        code: new FormControl(),
        description: new FormControl(),
        owned: new FormControl(),
        parentKnowledgebaseCode: new FormControl(),
        accessibleGroups: new FormControl()
    });
    
    /**
     * Stores the user groups that can be assigned to the knowledgebase
     */
    public userGroups: GroupInfo[]=[];

    /**
     * Constructor initializes the component with required services
     * @param injector Angular dependency injector
     * @param geboFormGroupsService Service for form group management
     * @param confirmationService Service for confirmation dialogs
     * @param geboUIActionRoutingService Service for UI action routing
     * @param userspaceControllerService Service for userspace operations
     * @param userControllerService Service for user-related operations
     * @param outputForwardingService Optional service for output forwarding
     */
    constructor(injector: Injector,
        geboFormGroupsService: GeboFormGroupsService,
        confirmationService: ConfirmationService,
        geboUIActionRoutingService: GeboUIActionRoutingService,
        private userspaceControllerService: UserspaceControllerService,
        private userControllerService:UserControllerService,
        outputForwardingService?: GeboUIOutputForwardingService) {
        super(injector, geboFormGroupsService, confirmationService, geboUIActionRoutingService, outputForwardingService);
    }
    
    /**
     * Lifecycle hook that initializes the component.
     * Loads user groups from the backend service.
     */
    override ngOnInit(): void {
        super.ngOnInit();
        this.loadingRelatedBackend=true;
        this.userControllerService.getMyGroups().subscribe({
            next:(values)=>{
                this.userGroups=values;
            },
            complete:()=>{
                this.loadingRelatedBackend=false;
            }
        });
    }

    /**
     * Retrieves a userspace knowledgebase by its code
     * @param code The unique identifier for the knowledgebase
     * @returns An Observable containing the found knowledgebase or null
     */
    override findByCode(code: string): Observable<UserspaceKnowledgebaseDto | null> {
        return this.userspaceControllerService.findUserKnowledgebaseByCode(code);
    }

    /**
     * Updates an existing userspace knowledgebase
     * @param value The knowledgebase data to update
     * @returns An Observable containing the updated knowledgebase
     */
    override save(value: UserspaceKnowledgebaseDto): Observable<UserspaceKnowledgebaseDto> {
        return this.userspaceControllerService.updateUserKnowledgebase(value);
    }

    /**
     * Creates a new userspace knowledgebase
     * @param value The knowledgebase data to create
     * @returns An Observable containing the newly created knowledgebase
     */
    override insert(value: UserspaceKnowledgebaseDto): Observable<UserspaceKnowledgebaseDto> {
        return this.userspaceControllerService.newUserKnowledgebase(value);
    }

    /**
     * Deletes a userspace knowledgebase
     * @param value The knowledgebase to delete
     * @returns An Observable containing a boolean indicating success
     */
    override delete(value: UserspaceKnowledgebaseDto): Observable<boolean> {
        return this.userspaceControllerService.deleteUserKnowledgebase(value);
    }

    /**
     * Determines if a knowledgebase can be deleted
     * Currently always returns true with no validation
     * @param value The knowledgebase to check
     * @returns An Observable containing deletion eligibility information
     */
    override canBeDeleted(value: UserspaceKnowledgebaseDto): Observable<{ canBeDeleted: boolean; message: string; }> {
        return of({ canBeDeleted: true, message: "" });
    }
}