/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

import { Component, forwardRef, Injector, ChangeDetectionStrategy } from "@angular/core";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { AuthProviderDto, GGlpiSystem, GlpiSystemsControllerService, SecretInfo, SecretsControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { BaseEntityEditingComponent, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE, GeboFormGroupsService, GeboUIActionRoutingService, GeboUIOutputForwardingService } from "@Gebo.ai/reusable-ui";
import { ConfirmationService, ToastMessageOptions } from "@openng/optimus-ui/api";
import { map, Observable, of } from "rxjs";
import { newSecretActionRequest } from "../utils/gebo-ai-create-secret-action-request-factory";

/** The GLPI system type code, also the secrets context code of the GLPI credentials */
export const GLPI_SYSTEM_TYPE: string = "GLPI";

/**
 * Editor of a GLPI system: its url and the two credentials of the OAuth2
 * password grant, the OAuth client registered in GLPI and the GLPI user.
 */
@Component({
    selector: "gebo-ai-glpi-admin-component",
    templateUrl: "gebo-ai-glpi-system-admin.component.html",
    providers: [
        { provide: GEBO_AI_MODULE, useValue: "GeboAIGlpiModule", multi: false },
        { provide: GEBO_AI_FIELD_HOST, useExisting: forwardRef(() => GeboAIGlpiAdminComponent), multi: false }
    ],
    changeDetection: ChangeDetectionStrategy.Eager,
    standalone: false
})
export class GeboAIGlpiAdminComponent extends BaseEntityEditingComponent<GGlpiSystem> {
    protected override entityName: string = "GGlpiSystem";

    override formGroup: FormGroup<any> = new FormGroup({
        code: new FormControl(),
        description: new FormControl(null, [Validators.required]),
        creationDate: new FormControl(),
        modificationDate: new FormControl(),
        version: new FormControl(),
        contentManagementSystemType: new FormControl(),
        readonly: new FormControl(),
        baseUri: new FormControl(null, [Validators.required]),
        usedCapabilities: new FormControl(),
        oauthClientSecretCode: new FormControl(null, [Validators.required]),
        secretCode: new FormControl(null, [Validators.required])
    });

    /** The OAuth client secrets of the GLPI context */
    public oauthClientsObservable: Observable<SecretInfo[]> = this.secretControllerService.getSecretsByContextCode(GLPI_SYSTEM_TYPE)
        .pipe(map(secrets => secrets.filter(s => s.secretType === SecretInfo.SecretTypeEnum.OAUTH2STANDARD)));

    /** The GLPI user secrets of the GLPI context */
    public usersObservable: Observable<SecretInfo[]> = this.secretControllerService.getSecretsByContextCode(GLPI_SYSTEM_TYPE)
        .pipe(map(secrets => secrets.filter(s => s.secretType === SecretInfo.SecretTypeEnum.USERNAMEPASSWORD)));

    /** The OAuth client registered in GLPI (Setup > OAuth clients): a generic OAuth2 client id and secret */
    public newOauthClientSecretAction = newSecretActionRequest(GLPI_SYSTEM_TYPE, this.entityName, this.entity,
        [SecretInfo.SecretTypeEnum.OAUTH2STANDARD], AuthProviderDto.ProviderEnum.Oauth2Generic, true);

    /** The GLPI user the system reads as */
    public newUserSecretAction = newSecretActionRequest(GLPI_SYSTEM_TYPE, this.entityName, this.entity,
        [SecretInfo.SecretTypeEnum.USERNAMEPASSWORD]);

    public testing: boolean = false;

    constructor(injector: Injector, geboFormGroupsService: GeboFormGroupsService,
        confirmationService: ConfirmationService,
        private glpiControllerService: GlpiSystemsControllerService,
        private secretControllerService: SecretsControllerService,
        geboUIActionRoutingService: GeboUIActionRoutingService,
        outputForwardingService?: GeboUIOutputForwardingService) {
        super(injector, geboFormGroupsService, confirmationService, geboUIActionRoutingService, outputForwardingService);
        this.manageOperationStatus = true;
        this.formGroup.controls["contentManagementSystemType"].valueChanges.subscribe(x => {
            if (x !== GLPI_SYSTEM_TYPE) {
                this.formGroup.controls["contentManagementSystemType"].setValue(GLPI_SYSTEM_TYPE);
            }
        });
    }

    protected override onNewData(actualValue: GGlpiSystem): void {
        this.formGroup.controls["contentManagementSystemType"].setValue(GLPI_SYSTEM_TYPE);
    }

    /** Checks the url and both credentials on GLPI without saving */
    doTest(): void {
        const value: GGlpiSystem = { ...this.formGroup.value, contentManagementSystemType: GLPI_SYSTEM_TYPE };
        this.testing = true;
        this.glpiControllerService.testGlpiSystem(value).subscribe({
            next: (status) => {
                this.userMessages = status.messages as ToastMessageOptions[];
            },
            error: () => {
                this.testing = false;
            },
            complete: () => {
                this.testing = false;
            }
        });
    }

    override findByCode(code: string): Observable<GGlpiSystem | null> {
        return this.glpiControllerService.findGlpiSystemByCode(code);
    }

    override save(value: GGlpiSystem): Observable<GGlpiSystem> {
        return this.glpiControllerService.updateGlpiSystem({ ...value, contentManagementSystemType: GLPI_SYSTEM_TYPE }).pipe(map(r => {
            this.updateLastOperationStatus(r);
            return r.result ? r.result : {} as GGlpiSystem;
        }));
    }

    override insert(value: GGlpiSystem): Observable<GGlpiSystem> {
        return this.glpiControllerService.insertGlpiSystem({ ...value, contentManagementSystemType: GLPI_SYSTEM_TYPE }).pipe(map(r => {
            this.updateLastOperationStatus(r);
            return r.result ? r.result : {} as GGlpiSystem;
        }));
    }

    override delete(value: GGlpiSystem): Observable<boolean> {
        return this.glpiControllerService.deleteGlpiSystem(value);
    }

    override canBeDeleted(value: GGlpiSystem): Observable<{ canBeDeleted: boolean; message: string; }> {
        return of({ canBeDeleted: true, message: "" });
    }
}
