/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

/* AI generated comments
 * Component for managing Jira system administration in the Gebo.ai application.
 * This component extends BaseEntityEditingComponent to handle CRUD operations for Jira systems,
 * allowing users to create, view, update, and delete Jira system configurations.
 * It provides form controls for various Jira system properties and integrates with
 * secret management for authentication.
 */
import { Component, forwardRef, Injector } from "@angular/core";
import { FormControl, FormGroup } from "@angular/forms";
import { GConfluenceSystem, GJiraSystem, JiraSystemsControllerService, SecretInfo, SecretsControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { BaseEntityEditingComponent, GEBO_AI_FIELD_HOST, GeboFormGroupsService, GeboUIActionRoutingService, GeboUIOutputForwardingService } from "@Gebo.ai/reusable-ui";
import { ConfirmationService } from "primeng/api";
import { map, Observable, of } from "rxjs";
import { newSecretActionRequest } from "../utils/gebo-ai-create-secret-action-request-factory";


@Component({
    selector: "gebo-ai-jira-admin-component",
    templateUrl: "gebo-ai-jira-system-admin.component.html",
    standalone: false,
    providers:[ {
        provide: GEBO_AI_FIELD_HOST, useExisting: forwardRef(() => GeboAIJiraAdminComponent),
        multi: true
    }]
})
export class GeboAIJiraAdminComponent extends BaseEntityEditingComponent<GJiraSystem> {
    /**
     * Name of the entity being managed by this component
     */
    protected override entityName: string = "GJiraSystem";

    /**
     * Form group containing all the form controls for the Jira system configuration
     * Includes fields for system identification, connection details, and associated secret
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
        secretCode: new FormControl()
    });

    /**
     * Identity context used for secret management with Jira
     * This identifies the type of secrets that are compatible with this system
     */
    private actualIdentityContext: string = "ATLASSIAN-JIRA";

    /**
     * Observable that provides the list of available secrets for Jira authentication
     */
    public identitiesObservable: Observable<SecretInfo[]> = this.secretControllerService.getSecretsByContextCode(this.actualIdentityContext);

    /**
     * Action definition for creating a new secret for Jira integration
     * Uses USERNAME_PASSWORD authentication type
     */
    public newSecretAction = newSecretActionRequest(this.actualIdentityContext, this.entityName, this.entity, ['TOKEN']);

    /**
     * Constructor initializes services and sets up form control behaviors
     * Enforces that the contentManagementSystemType is always 'ATLASSIAN-JIRA'
     */
    constructor(injector: Injector, geboFormGroupsService: GeboFormGroupsService,
        confirmationService: ConfirmationService,
        private jiraControllerService: JiraSystemsControllerService,
        private secretControllerService: SecretsControllerService,
        geboUIActionRoutingService: GeboUIActionRoutingService,
        outputForwardingService?: GeboUIOutputForwardingService) {
        super(injector, geboFormGroupsService, confirmationService, geboUIActionRoutingService, outputForwardingService);
        this.manageOperationStatus = true;
        this.formGroup.controls["contentManagementSystemType"].valueChanges.subscribe(x => {
            if (x !== 'ATLASSIAN-JIRA') {
                this.formGroup.controls["contentManagementSystemType"].setValue('ATLASSIAN-JIRA');
            }
        });

    }

    /**
     * Tests the connection to the configured Jira system
     * Currently implemented as an empty method - likely to be enhanced with actual testing logic
     */
    testSystem(): void {

    }

    /**
     * Retrieves a Jira system by its code identifier
     * @param code The unique code of the Jira system to find
     * @returns An Observable containing the found system or null if not found
     */
    override findByCode(code: string): Observable<GConfluenceSystem | null> {
        return this.jiraControllerService.findJiraSystemByCode(code);
    }

    /**
     * Updates an existing Jira system configuration
     * @param value The updated Jira system object
     * @returns An Observable with the updated Jira system
     */
    override save(value: GConfluenceSystem): Observable<GConfluenceSystem> {
        return this.jiraControllerService.updateJiraSystem(value).pipe(map(r => {
            this.updateLastOperationStatus(r);
            return r.result ? r.result : {};
        }));
    }

    /**
     * Creates a new Jira system configuration
     * @param value The new Jira system object to create
     * @returns An Observable with the created Jira system
     */
    override insert(value: GConfluenceSystem): Observable<GConfluenceSystem> {
        return this.jiraControllerService.insertJiraSystem(value).pipe(map(r => {
            this.updateLastOperationStatus(r);
            return r.result ? r.result : {};
        }));
    }

    /**
     * Deletes a Jira system configuration
     * @param value The Jira system object to delete
     * @returns An Observable indicating whether the deletion was successful
     */
    override delete(value: GConfluenceSystem): Observable<boolean> {
        return this.jiraControllerService.deleteJiraSystem(value);
    }

    /**
     * Checks if a Jira system can be safely deleted
     * Currently always returns true without performing any checks
     * @param value The Jira system to check
     * @returns An Observable with deletion permission info
     */
    override canBeDeleted(value: GConfluenceSystem): Observable<{ canBeDeleted: boolean; message: string; }> {
        return of({ canBeDeleted: true, message: "" });
    }

}