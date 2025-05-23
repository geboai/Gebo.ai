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
import { ConfluenceSystemsControllerService, GConfluenceSystem, SecretInfo, SecretsControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { BaseEntityEditingComponent, GeboFormGroupsService, GeboUIActionRoutingService, GeboUIOutputForwardingService } from "@Gebo.ai/reusable-ui";
import { ConfirmationService } from "primeng/api";
import { map, Observable, of } from "rxjs";
import { newSecretActionRequest } from "../utils/gebo-ai-create-secret-action-request-factory";

/**
 * AI generated comments
 * This component manages Confluence systems in Gebo.ai platform.
 * It provides UI for creating, updating, and deleting Confluence system configurations.
 * The component extends BaseEntityEditingComponent to handle basic CRUD operations
 * and implements form controls for all Confluence system properties.
 */
@Component({
    selector: "gebo-ai-confluence-admin-component",
    templateUrl: "gebo-ai-confluence-system-admin.component.html",
    standalone: false
})
export class GeboAIConfluenceAdminComponent extends BaseEntityEditingComponent<GConfluenceSystem> {
    /**
     * Entity name used for identification in various operations
     */
    protected override entityName: string = "GConfluenceSystem";
    
    /**
     * Form group containing all editable fields for a Confluence system
     */
    override formGroup: FormGroup<any> = new FormGroup({
        code: new FormControl(),
        description: new FormControl(),
        creationDate: new FormControl(),
        modificationDate: new FormControl(),
        version: new FormControl(),
        contentManagementSystemType: new FormControl(),
        readonly: new FormControl(),
        baseUri: new FormControl(),
        usedCapabilities: new FormControl(),
        confluenceVersion: new FormControl(),
        secretCode: new FormControl()
    });
    
    /**
     * Identity context used for secret management
     */
    private actualIdentityContext: string = "ATLASSIAN-CONFLUENCE";
    
    /**
     * Observable that returns available secrets for the Confluence context
     */
    identitiesObservable: Observable<SecretInfo[]> = this.secretControllerService.getSecretsByContextCode(this.actualIdentityContext);
    
    /**
     * Available Confluence versions for selection in the UI
     */
    confluenceVersions: { code: GConfluenceSystem.ConfluenceVersionEnum, description: string }[] = [{ code: "ONPREMISE7X", description: "On premise 6.X/7.X versions" }, { code: "CLOUD", description: "Cloud version" }];
    
    /**
     * Action request for creating a new secret, defaulting to USERNAME_PASSWORD authentication
     */
    public newSecretAction = newSecretActionRequest(this.actualIdentityContext, this.entityName, this.entity, ['USERNAME_PASSWORD']);

    /**
     * Constructor initializes the component with necessary services for CRUD operations
     * and sets up value change subscriptions to handle form control interactions
     */
    constructor(injector:Injector,geboFormGroupsService: GeboFormGroupsService,
        confirmationService: ConfirmationService,
        private confluenceControllerService: ConfluenceSystemsControllerService,
        private secretControllerService: SecretsControllerService,
        geboUIActionRoutingService: GeboUIActionRoutingService,
        outputForwardingService?: GeboUIOutputForwardingService) {
        super(injector,geboFormGroupsService, confirmationService,geboUIActionRoutingService, outputForwardingService);
        this.manageOperationStatus=true;
        
        // Ensures content management system type is always ATLASSIAN-CONFLUENCE
        this.formGroup.controls["contentManagementSystemType"].valueChanges.subscribe(x => {
            if (x !== 'ATLASSIAN-CONFLUENCE') {
                this.formGroup.controls["contentManagementSystemType"].setValue('ATLASSIAN-CONFLUENCE');
            }
        });
        
        // Changes the secret action request type based on the selected Confluence version
        this.formGroup.controls["confluenceVersion"].valueChanges.subscribe({
            next: (cversion: GConfluenceSystem.ConfluenceVersionEnum) => {
                if (cversion) {
                    switch (cversion) {
                        case "CLOUD": {
                            this.newSecretAction = newSecretActionRequest(this.actualIdentityContext, this.entityName, this.entity, ['TOKEN']);
                        }; break;
                        case "ONPREMISE7X": {
                            this.newSecretAction = newSecretActionRequest(this.actualIdentityContext, this.entityName, this.entity, ['USERNAME_PASSWORD']);
                        }; break;
                    }
                } 

            }
        });
    }
    
    /**
     * Tests the configured Confluence system connection
     */
    testSystem() : void {
        
    }
    
    /**
     * Finds a Confluence system by its code
     * @param code The unique identifier for the Confluence system
     * @returns An Observable containing the found Confluence system or null
     */
    override findByCode(code: string): Observable<GConfluenceSystem | null> {
        return this.confluenceControllerService.findConfluenceSystemByCode(code);
    }
    
    /**
     * Saves changes to an existing Confluence system
     * @param value The Confluence system with updated values
     * @returns An Observable containing the updated Confluence system
     */
    override save(value: GConfluenceSystem): Observable<GConfluenceSystem> {
        return this.confluenceControllerService.updateConfluenceSystem(value).pipe(map(r=>{
            this.updateLastOperationStatus(r);
            return r.result?r.result:{};
        }));
    }
    
    /**
     * Creates a new Confluence system
     * @param value The new Confluence system to insert
     * @returns An Observable containing the created Confluence system
     */
    override insert(value: GConfluenceSystem): Observable<GConfluenceSystem> {
        return this.confluenceControllerService.insertConfluenceSystem(value).pipe(map(r=>{
            this.updateLastOperationStatus(r);
            return r.result?r.result:{};
        }));
    }
    
    /**
     * Deletes a Confluence system
     * @param value The Confluence system to delete
     * @returns An Observable indicating whether the deletion was successful
     */
    override delete(value: GConfluenceSystem): Observable<boolean> {
        return this.confluenceControllerService.deleteConfluenceSystem(value);
    }
    
    /**
     * Checks if a Confluence system can be deleted
     * Currently always returns true with no validation
     * @param value The Confluence system to check
     * @returns An Observable containing the deletion status and any related message
     */
    override canBeDeleted(value: GConfluenceSystem): Observable<{ canBeDeleted: boolean; message: string; }> {
        return of({ canBeDeleted: true, message: "" });
    }
}