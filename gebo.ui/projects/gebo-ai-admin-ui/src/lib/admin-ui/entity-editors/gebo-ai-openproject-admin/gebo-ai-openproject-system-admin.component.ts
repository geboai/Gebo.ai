/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

import { Component, forwardRef, Injector, ChangeDetectionStrategy } from "@angular/core";
import { FormControl, FormGroup } from "@angular/forms";
import { GOpenProjectSystem, OpenProjectSystemsControllerService, SecretInfo, SecretsControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { BaseEntityEditingComponent, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE, GeboFormGroupsService, GeboUIActionRoutingService, GeboUIOutputForwardingService } from "@Gebo.ai/reusable-ui";
import { ConfirmationService, ToastMessageOptions } from "@openng/optimus-ui/api";
import { map, Observable, of } from "rxjs";
import { newSecretActionRequest } from "../utils/gebo-ai-create-secret-action-request-factory";

/** The OpenProject system type code, also the context code of its stored credentials. */
export const OPENPROJECT_SYSTEM_TYPE: string = "OPENPROJECT";

/**
 * Editor of an OpenProject system: the server address and the API key (a token
 * secret) Gebo.ai reads its API v3 with.
 */
@Component({
    selector: "gebo-ai-openproject-admin-component",
    templateUrl: "gebo-ai-openproject-system-admin.component.html",
    providers: [
        { provide: GEBO_AI_MODULE, useValue: "GeboAIOpenProjectModule", multi: false },
        { provide: GEBO_AI_FIELD_HOST, useExisting: forwardRef(() => GeboAIOpenProjectAdminComponent), multi: false }
    ],
    changeDetection: ChangeDetectionStrategy.Eager,
    standalone: false
})
export class GeboAIOpenProjectAdminComponent extends BaseEntityEditingComponent<GOpenProjectSystem> {
    protected override entityName: string = "GOpenProjectSystem";

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

    public identitiesObservable: Observable<SecretInfo[]> = this.secretControllerService.getSecretsByContextCode(OPENPROJECT_SYSTEM_TYPE);

    public newSecretAction = newSecretActionRequest(OPENPROJECT_SYSTEM_TYPE, this.entityName, this.entity, [SecretInfo.SecretTypeEnum.TOKEN]);

    public testing: boolean = false;

    constructor(injector: Injector, geboFormGroupsService: GeboFormGroupsService,
        confirmationService: ConfirmationService,
        private openProjectControllerService: OpenProjectSystemsControllerService,
        private secretControllerService: SecretsControllerService,
        geboUIActionRoutingService: GeboUIActionRoutingService,
        outputForwardingService?: GeboUIOutputForwardingService) {
        super(injector, geboFormGroupsService, confirmationService, geboUIActionRoutingService, outputForwardingService);
        this.manageOperationStatus = true;
        this.formGroup.controls["contentManagementSystemType"].valueChanges.subscribe(x => {
            if (x !== OPENPROJECT_SYSTEM_TYPE) {
                this.formGroup.controls["contentManagementSystemType"].setValue(OPENPROJECT_SYSTEM_TYPE);
            }
        });
    }

    protected override onNewData(actualValue: GOpenProjectSystem): void {
        this.formGroup.controls["contentManagementSystemType"].setValue(OPENPROJECT_SYSTEM_TYPE);
    }

    /** Checks the address and the credentials against the OpenProject server, without saving. */
    doTest(): void {
        const value: GOpenProjectSystem = { ...this.formGroup.value, contentManagementSystemType: OPENPROJECT_SYSTEM_TYPE };
        this.testing = true;
        this.openProjectControllerService.testOpenProjectSystem(value).subscribe({
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

    override findByCode(code: string): Observable<GOpenProjectSystem | null> {
        return this.openProjectControllerService.findOpenProjectSystemByCode(code);
    }

    override save(value: GOpenProjectSystem): Observable<GOpenProjectSystem> {
        return this.openProjectControllerService.updateOpenProjectSystem(value).pipe(map(r => {
            this.updateLastOperationStatus(r);
            return r.result ? r.result : {} as GOpenProjectSystem;
        }));
    }

    override insert(value: GOpenProjectSystem): Observable<GOpenProjectSystem> {
        return this.openProjectControllerService.insertOpenProjectSystem(value).pipe(map(r => {
            this.updateLastOperationStatus(r);
            return r.result ? r.result : {} as GOpenProjectSystem;
        }));
    }

    override delete(value: GOpenProjectSystem): Observable<boolean> {
        return this.openProjectControllerService.deleteOpenProjectSystem(value).pipe(map(() => true));
    }

    override canBeDeleted(value: GOpenProjectSystem): Observable<{ canBeDeleted: boolean; message: string; }> {
        return of({ canBeDeleted: true, message: "" });
    }
}
