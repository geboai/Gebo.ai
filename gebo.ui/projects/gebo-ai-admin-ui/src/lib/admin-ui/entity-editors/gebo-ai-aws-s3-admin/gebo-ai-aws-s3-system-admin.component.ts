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
 * This file implements a component for administering AWS S3 content systems within the Gebo.ai application.
 * It provides a UI for creating, editing, and managing AWS S3 content management system configurations,
 * mirroring the SharePoint administration component but backed by an AWS_CONNECTION secret.
 */

import { Component, forwardRef, Injector } from "@angular/core";
import { FormControl, FormGroup } from "@angular/forms";
import { AwsS3SystemsControllerService, GAwsS3System, SecretInfo, SecretsControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { BaseEntityEditingComponent, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE, GeboFormGroupsService, GeboUIActionRoutingService, GeboUIOutputForwardingService } from "@Gebo.ai/reusable-ui";
import { ConfirmationService } from "primeng/api";
import { map, Observable, of } from "rxjs";
import { newSecretActionRequest } from "../utils/gebo-ai-create-secret-action-request-factory";

/**
 * Constant representing the code identifier for the AWS S3 content handler module
 */
const awsS3Code: string = "aws-s3-handler";

/**
 * Component for administering AWS S3 content systems in the Gebo.ai application.
 * This component provides a form UI for creating and editing AWS S3 system configurations.
 * It extends BaseEntityEditingComponent with GAwsS3System as the entity type.
 */
@Component({
    selector: "gebo-ai-aws-s3-admin-component",
    templateUrl: "gebo-ai-aws-s3-system-admin.component.html",
    providers: [{ provide: GEBO_AI_MODULE, useValue: "GeboAIAwsS3Module", multi: false }, {
        provide: GEBO_AI_FIELD_HOST, useExisting: forwardRef(() => GeboAIAwsS3AdminComponent),
        multi: false
    }],
    standalone: false
})
export class GeboAIAwsS3AdminComponent extends BaseEntityEditingComponent<GAwsS3System> {
    /**
     * Entity name used for identification in the base component
     */
    protected override entityName: string = "GAwsS3System";

    /**
     * Form group that binds to the UI form fields for the AWS S3 system
     */
    override formGroup: FormGroup<any> = new FormGroup({
        code: new FormControl(),
        description: new FormControl(),
        creationDate: new FormControl(),
        modificationDate: new FormControl(),
        version: new FormControl(),
        contentManagementSystemType: new FormControl(),
        readonly: new FormControl(),
        usedCapabilities: new FormControl(),
        awsEndpoint: new FormControl(),
        s3SecretCode: new FormControl()
    });

    /**
     * Identity context code for secrets retrieval and creation
     */
    private actualIdentityContext: string = awsS3Code;

    /**
     * Observable to load available AWS_CONNECTION secrets for the AWS S3 system
     */
    identitiesObservable: Observable<SecretInfo[]> = this.secretControllerService.getSecretsByContextCode(this.actualIdentityContext);

    /**
     * Action request for creating a new AWS connection secret
     */
    public newSecretAction = newSecretActionRequest(this.actualIdentityContext, this.entityName, this.entity, ['AWS_CONNECTION']);

    /**
     * Constructor initializes the component and forces the content management system type
     * to the AWS S3 handler code.
     */
    constructor(injector: Injector, geboFormGroupsService: GeboFormGroupsService,
        confirmationService: ConfirmationService,
        private awsS3ControllerService: AwsS3SystemsControllerService,
        private secretControllerService: SecretsControllerService,
        geboUIActionRoutingService: GeboUIActionRoutingService,
        outputForwardingService?: GeboUIOutputForwardingService) {
        super(injector, geboFormGroupsService, confirmationService, geboUIActionRoutingService, outputForwardingService);
        this.manageOperationStatus = true;
        this.formGroup.controls["contentManagementSystemType"].valueChanges.subscribe(x => {
            if (x !== awsS3Code) {
                this.formGroup.controls["contentManagementSystemType"].setValue(awsS3Code);
            }
        });
    }

    /**
     * Handles special processing when new data is provided to the component
     * @param actualValue The new AWS S3 system data
     */
    protected override onNewData(actualValue: GAwsS3System): void {
        this.formGroup.controls["contentManagementSystemType"].setValue(awsS3Code);
    }

    /**
     * Fetches an AWS S3 system by its code identifier
     * @param code - The unique code of the AWS S3 system
     * @returns Observable with the found system or null
     */
    override findByCode(code: string): Observable<GAwsS3System | null> {
        return this.awsS3ControllerService.findAwsS3SystemByCode(code);
    }

    /**
     * Updates an existing AWS S3 system
     * @param value - The AWS S3 system data to update
     * @returns Observable with the updated system
     */
    override save(value: GAwsS3System): Observable<GAwsS3System> {
        return this.awsS3ControllerService.updateAwsS3System(value).pipe(map(r => {
            this.updateLastOperationStatus(r);
            return r.result ? r.result : {} as GAwsS3System;
        }));
    }

    /**
     * Creates a new AWS S3 system
     * @param value - The new AWS S3 system data
     * @returns Observable with the created system
     */
    override insert(value: GAwsS3System): Observable<GAwsS3System> {
        return this.awsS3ControllerService.insertAwsS3System(value).pipe(map(r => {
            this.updateLastOperationStatus(r);
            return r.result ? r.result : {} as GAwsS3System;
        }));
    }

    /**
     * Deletes an AWS S3 system
     * @param value - The AWS S3 system to delete
     * @returns Observable indicating success or failure
     */
    override delete(value: GAwsS3System): Observable<boolean> {
        return this.awsS3ControllerService.deleteAwsS3System(value) as unknown as Observable<boolean>;
    }

    /**
     * Determines if an AWS S3 system can be deleted
     * @param value - The AWS S3 system to check
     * @returns Observable with deletion permission info
     */
    override canBeDeleted(value: GAwsS3System): Observable<{ canBeDeleted: boolean; message: string; }> {
        return of({ canBeDeleted: true, message: "" });
    }

}
