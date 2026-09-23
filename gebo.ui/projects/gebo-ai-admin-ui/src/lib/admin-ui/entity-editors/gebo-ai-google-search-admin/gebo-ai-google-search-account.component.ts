/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */




import { Component, forwardRef, Injector } from "@angular/core";
import { FormControl, FormGroup } from "@angular/forms";
import { GGoogleSearchApiCredentials, GoogleSearchConfigurationControllerService, SecretsControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { BaseEntityEditingComponent, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE, GeboFormGroupsService, GeboUIActionRoutingService, GeboUIOutputForwardingService } from "@Gebo.ai/reusable-ui";
import { ConfirmationService } from "primeng/api";
import { Observable, of } from "rxjs";
import { newSecretActionRequest } from "../utils/gebo-ai-create-secret-action-request-factory";

/**
 * AI generated comments
 * This file contains implementation for GeboAIGoogleSearchAccountComponent which manages Google Search API credentials.
 * It provides functionality to create, read, update, and delete Google Search API credential entities.
 */

/**
 * A constant that defines the context code for Google Search secrets in the system.
 * This is used when retrieving or creating secrets related to Google Search functionality.
 */
const googleSearchContextCode: string = "google-search";

/**
 * Component responsible for managing Google Search API credentials.
 * This component extends BaseEntityEditingComponent to provide CRUD operations
 * for Google Search API credentials. It includes a form for editing credential
 * properties like code, description, secret code, and custom search engine ID.
 */
@Component({
    selector: "gebo-ai-google-search-account-component",
    templateUrl: "gebo-ai-google-search-account.component.html",
    standalone: false, providers: [
        { provide: GEBO_AI_MODULE, useValue: "GeboAIGoogleSearchAccountModule", multi: false }, 
        {
        provide: GEBO_AI_FIELD_HOST, useExisting: forwardRef(() => GeboAIGoogleSearchAccountComponent),
        multi: false
    }]
})
export class GeboAIGoogleSearchAccountComponent extends BaseEntityEditingComponent<GGoogleSearchApiCredentials> {
    /**
     * The entity name used for identification in the system
     */
    protected override entityName: string = "GGoogleSearchApiCredentials";

    /**
     * Form group that contains the form controls for the Google Search API credentials
     * Includes fields for code, description, secretCode, and customSearchEngineId
     */
    override formGroup: FormGroup<any> = new FormGroup({
        code: new FormControl(),
        description: new FormControl(),
        secretCode: new FormControl(),
        customSearchEngineId: new FormControl()
    });

    /**
     * Observable that provides a list of available secret identities from the Google Search context
     */
    public identitiesObservable: Observable<any[]> = this.secretsControllerService.getSecretsByContextCode(googleSearchContextCode);

    /**
     * Action configuration for creating a new secret, specifying the Google Search context,
     * entity name, entity object, and the TOKEN type
     */
    public newSecretAction = newSecretActionRequest(googleSearchContextCode, this.entityName, this.entity, ['TOKEN']);

    /**
     * Constructor that initializes the component with necessary dependencies
     * 
     * @param injector Angular injector for dependency injection
     * @param googleSearchConfigService Service to interact with Google Search configuration API
     * @param secretsControllerService Service to manage secrets
     * @param geboFormGroupsService Service to handle form groups
     * @param confirmationService Service to handle confirmation dialogs
     * @param geboUIActionRoutingService Service to handle UI action routing
     * @param outputForwardingService Optional service for output forwarding
     */
    constructor(
        injector: Injector,
        private googleSearchConfigService: GoogleSearchConfigurationControllerService,
        private secretsControllerService: SecretsControllerService,
        geboFormGroupsService: GeboFormGroupsService,
        confirmationService: ConfirmationService,
        geboUIActionRoutingService: GeboUIActionRoutingService,
        outputForwardingService?: GeboUIOutputForwardingService) {
        super(injector, geboFormGroupsService, confirmationService, geboUIActionRoutingService, outputForwardingService);
    }

    /**
     * Retrieves a Google Search API credential by its code
     * 
     * @param code The unique code of the credential to find
     * @returns An Observable containing the found credential or null if not found
     */
    override findByCode(code: string): Observable<GGoogleSearchApiCredentials | null> {
        return this.googleSearchConfigService.searchGGoogleSearchApiCredentialsByCode(code);
    }

    /**
     * Updates an existing Google Search API credential
     * 
     * @param value The credential with updated values
     * @returns An Observable containing the updated credential
     */
    override save(value: GGoogleSearchApiCredentials): Observable<GGoogleSearchApiCredentials> {
        return this.googleSearchConfigService.updateGGoogleSearchApiCredentials(value);
    }

    /**
     * Creates a new Google Search API credential
     * 
     * @param value The new credential to insert
     * @returns An Observable containing the newly created credential
     */
    override insert(value: GGoogleSearchApiCredentials): Observable<GGoogleSearchApiCredentials> {
        return this.googleSearchConfigService.insertGGoogleSearchApiCredentials(value);
    }

    /**
     * Deletes a Google Search API credential
     * 
     * @param value The credential to delete
     * @returns An Observable containing a boolean indicating success
     */
    override delete(value: GGoogleSearchApiCredentials): Observable<boolean> {
        return this.googleSearchConfigService.deleteGGoogleSearchApiCredentials(value);
    }

    /**
     * Checks if a Google Search API credential can be deleted
     * Currently always returns true with an empty message
     * 
     * @param value The credential to check
     * @returns An Observable containing an object with canBeDeleted flag and a message
     */
    override canBeDeleted(value: GGoogleSearchApiCredentials): Observable<{ canBeDeleted: boolean; message: string; }> {
        return of({ canBeDeleted: true, message: "" });
    }
}