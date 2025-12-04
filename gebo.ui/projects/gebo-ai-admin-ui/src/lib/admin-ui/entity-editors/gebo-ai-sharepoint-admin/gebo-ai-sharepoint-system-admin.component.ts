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
 * This file implements a component for administering Sharepoint CMS systems within the Gebo.ai application.
 * It provides a UI for creating, editing, and managing Sharepoint content management system configurations.
 */

import { Component, forwardRef, Injector } from "@angular/core";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { AuthProviderDto, GSharepointContentManagementSystem, SecretInfo, SecretsControllerService, SharepointSystemsControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { BaseEntityEditingComponent, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE, GeboFormGroupsService, GeboUIActionRoutingService, GeboUIOutputForwardingService } from "@Gebo.ai/reusable-ui";
import { ConfirmationService } from "primeng/api";
import { map, Observable, of } from "rxjs";
import { newSecretActionRequest } from "../utils/gebo-ai-create-secret-action-request-factory";
import { SharepointUrlService } from "./confluence-url.service";

/**
 * Constant representing the code identifier for Sharepoint module
 */
const sharepointCode: string = "sharepoint-module";

/**
 * Component for administering Sharepoint systems in the Gebo.ai application.
 * This component provides a form UI for creating and editing Sharepoint system configurations.
 * It extends BaseEntityEditingComponent with GSharepointContentManagementSystem as the entity type.
 */
@Component({
    selector: "gebo-ai-sharepoint-admin-component",
    templateUrl: "gebo-ai-sharepoint-system-admin.component.html",
    providers: [SharepointUrlService,{ provide: GEBO_AI_MODULE, useValue: "GeboAISharepointModule", multi: false }, {
        provide: GEBO_AI_FIELD_HOST, useExisting: forwardRef(() => GeboAISharepointAdminComponent),
        multi: false
    }],
    standalone: false
})
export class GeboAISharepointAdminComponent extends BaseEntityEditingComponent<GSharepointContentManagementSystem> {
    /**
     * Entity name used for identification in the base component
     */
    protected override entityName: string = "GSharepointContentManagementSystem";
    
    /**
     * Form group that binds to the UI form fields for the Sharepoint system
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
        sharepointVersion: new FormControl(),
        secretCode: new FormControl()
    });
    
    /**
     * Identity context code for secrets retrieval and creation
     */
    private actualIdentityContext: string = sharepointCode;
    
    /**
     * Current Sharepoint version selection
     */
    public actualSharepointVersion: GSharepointContentManagementSystem.SharepointVersionEnum = "CLOUD_VERSION";
    
    /**
     * Observable to load available secrets for the Sharepoint system
     */
    identitiesObservable: Observable<SecretInfo[]> = this.secretControllerService.getSecretsByContextCode(this.actualIdentityContext);
    
    /**
     * Available Sharepoint versions for dropdown selection
     */
    sharepointVersions: { code: GSharepointContentManagementSystem.SharepointVersionEnum, description: string }[] = [{ code: "CLOUD_VERSION", description: "Cloud version" }];
    
    /**
     * Action request for creating a new secret
     */
    public newSecretAction = newSecretActionRequest(this.actualIdentityContext, this.entityName, this.entity, ['OAUTH2_STANDARD'],AuthProviderDto.ProviderEnum.MicrosoftMultitenant,true);
    
    /**
     * Constructor initializes the component and sets up form field behaviors
     * Subscribes to form control changes to enforce specific validation rules and behaviors
     * based on the selected Sharepoint version.
     */
    constructor(injector: Injector, geboFormGroupsService: GeboFormGroupsService,
        confirmationService: ConfirmationService,
        private confluenceControllerService: SharepointSystemsControllerService,
        private secretControllerService: SecretsControllerService,
        geboUIActionRoutingService: GeboUIActionRoutingService,
        outputForwardingService?: GeboUIOutputForwardingService) {
        super(injector, geboFormGroupsService, confirmationService, geboUIActionRoutingService, outputForwardingService);
        this.manageOperationStatus = true;
        this.formGroup.controls["contentManagementSystemType"].valueChanges.subscribe(x => {
            if (x !== sharepointCode) {
                this.formGroup.controls["contentManagementSystemType"].setValue(sharepointCode);
            }
        });
        this.formGroup.controls["sharepointVersion"].valueChanges.subscribe({
            next: (cversion: GSharepointContentManagementSystem.SharepointVersionEnum) => {
                if (cversion) {
                    this.actualSharepointVersion = cversion;
                    switch (cversion) {
                        case "CLOUD_VERSION": {
                            this.newSecretAction = newSecretActionRequest(this.actualIdentityContext, this.entityName, this.entity, ["OAUTH2_STANDARD"],AuthProviderDto.ProviderEnum.MicrosoftMultitenant,true);
                            this.requiredAndEnabled("baseUri", false);
                        }; break;
                        case "ONPREMISE2019": {
                            this.requiredAndEnabled("baseUri", true);
                            this.newSecretAction = newSecretActionRequest(this.actualIdentityContext, this.entityName, this.entity, ['USERNAME_PASSWORD']);
                        }; break;
                    }
                }

            }
        });
    }
    
    /**
     * Tests the current Sharepoint system configuration.
     * Currently implemented as an empty placeholder method.
     */
    testSystem(): void {

    }
    
    /**
     * Helper method to dynamically set a form field's validation and enabled state
     * @param field - The name of the form field to modify
     * @param enabled - Whether the field should be enabled and required (true) or disabled (false)
     */
    private requiredAndEnabled(field: string, enabled: boolean) {
        this.formGroup.controls[field].clearValidators();
        if (enabled === true) {

            this.formGroup.controls[field].enable();
            this.formGroup.controls[field].setValidators(Validators.required);


        } else {

            this.formGroup.controls[field].disable();

        }
        this.formGroup.updateValueAndValidity();
    }
    
    /**
     * Fetches a Sharepoint system by its code identifier
     * @param code - The unique code of the Sharepoint system
     * @returns Observable with the found system or null
     */
    override findByCode(code: string): Observable<GSharepointContentManagementSystem | null> {
        return this.confluenceControllerService.findSharepointSystemByCode(code);
    }
    
    /**
     * Updates an existing Sharepoint system
     * @param value - The Sharepoint system data to update
     * @returns Observable with the updated system
     */
    override save(value: GSharepointContentManagementSystem): Observable<GSharepointContentManagementSystem> {
        return this.confluenceControllerService.updateSharepointSystem(value).pipe(map(r => {
            this.updateLastOperationStatus(r);
            return r.result ? r.result : {} as GSharepointContentManagementSystem;
        }));
    }
    
    /**
     * Creates a new Sharepoint system
     * @param value - The new Sharepoint system data
     * @returns Observable with the created system
     */
    override insert(value: GSharepointContentManagementSystem): Observable<GSharepointContentManagementSystem> {
        return this.confluenceControllerService.insertSharepointSystem(value).pipe(map(r => {
            this.updateLastOperationStatus(r);
            return r.result ? r.result : {} as GSharepointContentManagementSystem;
        }));
    }
    
    /**
     * Deletes a Sharepoint system
     * @param value - The Sharepoint system to delete
     * @returns Observable indicating success or failure
     */
    override delete(value: GSharepointContentManagementSystem): Observable<boolean> {
        return this.confluenceControllerService.deleteSharepointSystem(value);
    }
    
    /**
     * Determines if a Sharepoint system can be deleted
     * Currently always returns true as there are no restrictions implemented
     * @param value - The Sharepoint system to check
     * @returns Observable with deletion permission info
     */
    override canBeDeleted(value: GSharepointContentManagementSystem): Observable<{ canBeDeleted: boolean; message: string; }> {
        return of({ canBeDeleted: true, message: "" });
    }

}