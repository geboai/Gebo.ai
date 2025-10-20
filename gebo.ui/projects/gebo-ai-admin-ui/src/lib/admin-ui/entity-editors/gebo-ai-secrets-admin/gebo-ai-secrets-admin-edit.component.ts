/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */




import { Component, forwardRef, Injector, Input, SimpleChanges } from "@angular/core";
import { BaseEntityEditingComponent, GEBO_AI_FIELD_HOST, GeboFormGroupsService, GeboUIActionRoutingService, GeboUIOutputForwardingService } from "@Gebo.ai/reusable-ui";
import { GeboSshKeySecretContent, GeboTokenContent, SecretInfo, GeboUsernamePasswordContent, SecretWrapperGeboSshKeySecretContent, SecretWrapperGeboTokenContent, SecretWrapperGeboUsernamePasswordContent, SecretsControllerService, GeboCustomSecretContent, GeboOauth2SecretContent, GeboGoogleOauth2SecretContent, GeboGoogleJsonSecretContent } from "@Gebo.ai/gebo-ai-rest-api"
import { AbstractControl, FormControl, FormGroup, ValidationErrors, ValidatorFn, Validators } from "@angular/forms";
import { map, Observable, of } from "rxjs";
import { ConfirmationService } from "primeng/api";
/**
 * AI generated comments
 * This file defines components for managing secrets in the Gebo.ai application. It provides functionality
 * for creating different types of secrets like SSH keys, tokens, username/password combinations, and OAuth2 credentials.
 */

/**
 * Interface representing a wrapper for different types of secrets.
 * It provides a unified structure for handling various secret types with different content formats.
 */
export interface SecretWrapper {

    code?: string,
    contextCode?: string;
    description?: string;
    secretType: SecretInfo.SecretTypeEnum;
    sshContent?: GeboSshKeySecretContent;
    tokenContent?: GeboTokenContent;
    usernamePasswordContent?: GeboUsernamePasswordContent;
    customContent?: GeboCustomSecretContent;
    oauth2StandardContent?: GeboOauth2SecretContent;
    googleOauth2Content?: GeboGoogleOauth2SecretContent;
    googleJsonContent?: GeboGoogleJsonSecretContent;
}

/**
 * Component for editing secret configurations in the admin interface.
 * Extends BaseEntityEditingComponent to provide standard CRUD functionality for SecretWrapper objects.
 * Handles different secret types and their specific form requirements dynamically.
 */
@Component({
    selector: "gebo-ai-secrets-admin-edit-component",
    templateUrl: "gebo-ai-secrets-admin-edit.component.html",
    standalone: false, providers: [{
        provide: GEBO_AI_FIELD_HOST, useExisting: forwardRef(() => GeboAiSecretsAdminEditComponent),
        multi: true
    }]
})
export class GeboAiSecretsAdminEditComponent extends BaseEntityEditingComponent<SecretWrapper> {
    /** Identifies the entity type being managed by this component */
    protected override entityName: string = "SecretWrapper";

    /** Configures which secret types are allowed to be created/edited in this instance */
    @Input() allowedTypes: SecretInfo.SecretTypeEnum[] = [SecretInfo.SecretTypeEnum.SSHKEY, SecretInfo.SecretTypeEnum.TOKEN, SecretInfo.SecretTypeEnum.USERNAMEPASSWORD, SecretInfo.SecretTypeEnum.OAUTH2STANDARD];

    @Input() oauth2ChoosableScopes: { code: string, description: string }[] = [];
    @Input() oauth2MandatoryScopes: { code: string, description: string }[] = [];
    @Input() oauth2ForcedProviderName?: string;
    @Input() hideProviderChoice: boolean = false;
    /** All possible secret types with their display descriptions */
    private typesOptions: { code?: SecretInfo.SecretTypeEnum, description?: string }[] = [
        { code: SecretInfo.SecretTypeEnum.USERNAMEPASSWORD, description: "User name & password" },
        { code: SecretInfo.SecretTypeEnum.TOKEN, description: "Token" },
        { code: SecretInfo.SecretTypeEnum.SSHKEY, description: "SSh key" },
        { code: SecretInfo.SecretTypeEnum.OAUTH2GOOGLE, description: "Google Oauth2" },
        { code: SecretInfo.SecretTypeEnum.GOOGLECLOUDJSONCREDENTIALS, description: "Google credentials Json file content" },
        { code: SecretInfo.SecretTypeEnum.OAUTH2STANDARD, description: "Oauth2 standard registered client with scopes" }];

    /** Filtered secret types that can be selected by the user */
    public choosableTypes: { code?: SecretInfo.SecretTypeEnum, description?: string }[] = [];

    /** Currently selected secret type */
    public actualSecretType?: SecretInfo.SecretTypeEnum;

    /** Main form group containing all form controls for different secret types */
    override formGroup: FormGroup<any> = new FormGroup({
        code: new FormControl(),
        contextCode: new FormControl(),
        description: new FormControl(),
        secretType: new FormControl(),
        sshContent: new FormGroup({
            email: new FormControl(),
            key: new FormControl(),
            pub: new FormControl(),
            passphrase: new FormControl()
        }),
        tokenContent: new FormGroup({
            token: new FormControl(),
            user: new FormControl()
        }),
        usernamePasswordContent: new FormGroup({
            username: new FormControl(),
            password: new FormControl()
        }),
        oauth2StandardContent: new FormControl(),
        googleOauth2Content: new FormGroup(
            {
                token: new FormControl(),
                location: new FormControl(),
                projectId: new FormControl(),
                scopes: new FormControl()
            }
        ),
        googleJsonContent: new FormGroup(
            {
                jsonContent: new FormControl()
            }
        ),
        oauth2Content: new FormGroup({

        })
    });

    /**
     * Constructor initializes the component and sets up form validation based on secret type.
     * Subscribes to secretType value changes to dynamically update form validation requirements.
     */
    constructor(injector: Injector, private secretControllerService: SecretsControllerService, confirmService: ConfirmationService, private geboFsService: GeboFormGroupsService, geboUIActionRoutingService: GeboUIActionRoutingService, outputForwardingService?: GeboUIOutputForwardingService) {
        super(injector, geboFsService, confirmService, geboUIActionRoutingService, outputForwardingService);
        this.formGroup.controls["secretType"].valueChanges.subscribe(type => {
            this.actualSecretType = type;
            this.switchRequired("sshContent", false);
            this.switchRequired("tokenContent", false);
            this.switchRequired("usernamePasswordContent", false);
            this.switchRequired("oauth2StandardContent", false);
            this.switchRequired("googleOauth2Content", false);
            this.switchRequired("googleJsonContent", false);
            const googleJsonContent: FormGroup = this.formGroup.controls["googleJsonContent"] as FormGroup;
            if (googleJsonContent) {
                const jsonContent = googleJsonContent.controls["jsonContent"];
                if (jsonContent)
                    jsonContent.clearValidators();
            }
            if (type) {
                switch (type) {
                    case SecretInfo.SecretTypeEnum.SSHKEY: {
                        this.switchRequired("sshContent", true);
                    }; break;
                    case SecretInfo.SecretTypeEnum.TOKEN: {
                        this.switchRequired("tokenContent", true);
                    }; break;
                    case SecretInfo.SecretTypeEnum.USERNAMEPASSWORD: {
                        this.switchRequired("usernamePasswordContent", true);
                    }; break;
                    case SecretInfo.SecretTypeEnum.OAUTH2STANDARD: {
                        this.switchRequired("oauth2StandardContent", true);
                    }; break;
                    case SecretInfo.SecretTypeEnum.OAUTH2GOOGLE: {
                        this.switchRequired("googleOauth2Content", true);
                    }; break;
                    case SecretInfo.SecretTypeEnum.GOOGLECLOUDJSONCREDENTIALS: {
                        this.switchRequired("googleJsonContent", true);
                        const validJson: ValidatorFn = (control: AbstractControl) => {
                            let returned: ValidationErrors | null = { "jsonContent": "Not valid json" };
                            const value = control.value;
                            try {
                                const jsonObject = JSON.parse(value);
                                console.log(jsonObject);
                                returned = null;
                            } catch (e) {

                            }
                            return returned;
                        };
                        const googleJsonContent: FormGroup = this.formGroup.controls["googleJsonContent"] as FormGroup;
                        if (googleJsonContent) {
                            const jsonContent = googleJsonContent.controls["jsonContent"];
                            if (jsonContent) { jsonContent.clearValidators(); jsonContent.setValidators([Validators.required, validJson]); }

                        }
                    }; break;

                }

            }
            this.formGroup.updateValueAndValidity();
        });

    }

    /**
     * Helper method to enable/disable and set validation requirements for form groups based on selected secret type.
     * When required is true, the form is enabled and all fields are required.
     * When required is false, the form is disabled and validators are cleared.
     * 
     * @param formName The name of the form group to update
     * @param required Whether this form group's fields should be required
     */
    private switchRequired(formName: string, required: boolean) {
        const object = this.formGroup.controls[formName];
        if (object instanceof FormControl) {
            const fc = object as FormControl;
            fc.enable();
            if (required === true) {
                fc.setValidators(Validators.required);
                fc.enable();
            } else {
                fc.clearValidators();
                fc.disable();
            }

        } else {
            const fg: FormGroup = this.formGroup.controls[formName] as FormGroup;
            fg.enable();

            if (fg && fg.controls) {
                const keys = Object.keys(fg.controls);
                if (keys) {
                    keys.forEach(k => {
                        if (required === true) {
                            fg.controls[k].setValidators(Validators.required);
                        } else {
                            fg.controls[k].clearValidators();
                        }
                    });
                }
            }
            fg.updateValueAndValidity();
            if (required === true) {
                fg.enable();
            } else {
                fg.disable();
            }
        }
    }

    /**
     * Handles input property changes, specifically to update the available secret types
     * based on the allowedTypes input property.
     */
    override ngOnChanges(changes: SimpleChanges): void {
        super.ngOnChanges(changes);
        if (this.allowedTypes && changes["allowedTypes"]) {
            const types: { code?: SecretInfo.SecretTypeEnum, description?: string }[] = [];
            this.allowedTypes.forEach(type => {
                const entry = this.typesOptions.find(x => x.code === type);
                if (entry) {
                    types.push(entry);
                }
            });
            this.choosableTypes = types;
            if (this.choosableTypes && this.choosableTypes.length === 1) {
                this.formGroup.controls["secretType"].setValue(this.choosableTypes[0].code);
                this.actualSecretType = this.choosableTypes[0].code;
            }
        }
    }

    /**
     * Loads a secret by its code. Not implemented in this component.
     */
    override findByCode(code: string): Observable<SecretWrapper | null> {
        throw new Error("Method not implemented.");
    }

    /**
     * Updates an existing secret. Not implemented in this component.
     */
    override save(value: SecretWrapper): Observable<SecretWrapper> {
        throw new Error("Method not implemented.");
    }

    /**
     * Creates a new secret based on the provided SecretWrapper.
     * Dispatches to different API methods based on the secret type.
     * 
     * @param value The secret configuration to save
     * @returns An Observable with the created SecretWrapper including server-generated fields
     */
    override insert(value: SecretWrapper): Observable<SecretWrapper> {
        if (!value?.secretType) {
            this.userMessages = [{ severity: "error", detail: "The field secret type is mandatory" }];
            return of(value);
        }
        switch (value.secretType) {
            case "USERNAME_PASSWORD": {
                return this.secretControllerService.createUsernamePasswordSecret({ contextCode: value.contextCode, description: value.description, secretContent: value.usernamePasswordContent }).pipe(map(returned => {
                    value.code = returned.code;
                    value.description = returned.description;
                    return value;
                }));
            } break;
            case "TOKEN": {
                return this.secretControllerService.createTokenSecret({ contextCode: value.contextCode, description: value.description, secretContent: value.tokenContent }).pipe(map(returned => {
                    value.code = returned.code;
                    value.description = returned.description;
                    return value;
                }));
            } break;
            case "SSH_KEY": {
                return this.secretControllerService.createSshKeySecret({ contextCode: value.contextCode, description: value.description, secretContent: value.sshContent }).pipe(map(returned => {
                    value.code = returned.code;
                    value.description = returned.description;
                    return value;
                }));
            } break;
            case "CUSTOM_SECRET": {
                return this.secretControllerService.createCustomSecret({ contextCode: value.contextCode, description: value.description, secretContent: value.customContent }).pipe(map(returned => {
                    value.code = returned.code;
                    value.description = returned.description;
                    return value;
                }));
            }
            case "OAUTH2_STANDARD": {
                return this.secretControllerService.createOauth2StandardSecret({ contextCode: value.contextCode, description: value.description, secretContent: value.oauth2StandardContent }).pipe(map(returned => {
                    value.code = returned.code;
                    value.description = returned.description;
                    return value;
                }));
            }
            case "OAUTH2_GOOGLE": {
                return this.secretControllerService.createGoogleOauth2Secret({ contextCode: value.contextCode, description: value.description, secretContent: value.googleOauth2Content }).pipe(map(returned => {
                    value.code = returned.code;
                    value.description = returned.description;
                    return value;
                }));
            }
            case "GOOGLE_CLOUD_JSON_CREDENTIALS": {
                return this.secretControllerService.createGoogleJsonCredentialsSecret({ contextCode: value.contextCode, description: value.description, secretContent: value.googleJsonContent }).pipe(map(returned => {
                    value.code = returned.code;
                    value.description = returned.description;
                    return value;
                }));
            }
            case "OAUTH2_AUTHORIZED_CLIENT": {
                const secretWrapper: SecretWrapper = {
                    secretType: "OAUTH2_AUTHORIZED_CLIENT",

                }
                return of(secretWrapper);
            }
        }
    }

    /**
     * Deletes an existing secret. Not implemented in this component.
     */
    override delete(value: SecretWrapper): Observable<boolean> {
        throw new Error("Method not implemented.");
    }

    /**
     * Checks if a secret can be deleted. Not implemented in this component.
     */
    override canBeDeleted(value: SecretWrapper): Observable<{ canBeDeleted: boolean; message: string; }> {
        throw new Error("Method not implemented.");
    }
}