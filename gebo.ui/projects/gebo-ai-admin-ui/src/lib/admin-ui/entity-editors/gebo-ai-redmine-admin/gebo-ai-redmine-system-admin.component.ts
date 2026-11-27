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
import { GRedmineSystem, RedmineSystemsControllerService, SecretInfo, SecretsControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { BaseEntityEditingComponent, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE, GeboFormGroupsService, GeboUIActionRoutingService, GeboUIOutputForwardingService } from "@Gebo.ai/reusable-ui";
import { ConfirmationService, ToastMessageOptions } from "@openng/optimus-ui/api";
import { map, Observable, of } from "rxjs";
import { newSecretActionRequest } from "../utils/gebo-ai-create-secret-action-request-factory";

/** The Redmine system type code, also the context code of its stored credentials. */
export const REDMINE_SYSTEM_TYPE: string = "REDMINE";

/**
 * Editor of a Redmine system: the server address and the credentials Gebo.ai
 * uses, an API key (token) or a username and password.
 */
@Component({
    selector: "gebo-ai-redmine-admin-component",
    templateUrl: "gebo-ai-redmine-system-admin.component.html",
    providers: [
        { provide: GEBO_AI_MODULE, useValue: "GeboAIRedmineModule", multi: false },
        { provide: GEBO_AI_FIELD_HOST, useExisting: forwardRef(() => GeboAIRedmineAdminComponent), multi: false }
    ],
    changeDetection: ChangeDetectionStrategy.Eager,
    standalone: false
})
export class GeboAIRedmineAdminComponent extends BaseEntityEditingComponent<GRedmineSystem> {
    protected override entityName: string = "GRedmineSystem";

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

    public identitiesObservable: Observable<SecretInfo[]> = this.secretControllerService.getSecretsByContextCode(REDMINE_SYSTEM_TYPE);

    public newSecretAction = newSecretActionRequest(REDMINE_SYSTEM_TYPE, this.entityName, this.entity, [SecretInfo.SecretTypeEnum.TOKEN, SecretInfo.SecretTypeEnum.USERNAMEPASSWORD]);

    public testing: boolean = false;

    constructor(injector: Injector, geboFormGroupsService: GeboFormGroupsService,
        confirmationService: ConfirmationService,
        private redmineControllerService: RedmineSystemsControllerService,
        private secretControllerService: SecretsControllerService,
        geboUIActionRoutingService: GeboUIActionRoutingService,
        outputForwardingService?: GeboUIOutputForwardingService) {
        super(injector, geboFormGroupsService, confirmationService, geboUIActionRoutingService, outputForwardingService);
        this.manageOperationStatus = true;
        this.formGroup.controls["contentManagementSystemType"].valueChanges.subscribe(x => {
            if (x !== REDMINE_SYSTEM_TYPE) {
                this.formGroup.controls["contentManagementSystemType"].setValue(REDMINE_SYSTEM_TYPE);
            }
        });
    }

    protected override onNewData(actualValue: GRedmineSystem): void {
        this.formGroup.controls["contentManagementSystemType"].setValue(REDMINE_SYSTEM_TYPE);
    }

    /** Checks the address and the credentials against the Redmine server, without saving. */
    doTest(): void {
        const value: GRedmineSystem = { ...this.formGroup.value, contentManagementSystemType: REDMINE_SYSTEM_TYPE };
        this.testing = true;
        this.redmineControllerService.testRedmineSystem(value).subscribe({
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

    override findByCode(code: string): Observable<GRedmineSystem | null> {
        return this.redmineControllerService.findRedmineSystemByCode(code);
    }

    override save(value: GRedmineSystem): Observable<GRedmineSystem> {
        return this.redmineControllerService.updateRedmineSystem(value).pipe(map(r => {
            this.updateLastOperationStatus(r);
            return r.result ? r.result : {} as GRedmineSystem;
        }));
    }

    override insert(value: GRedmineSystem): Observable<GRedmineSystem> {
        return this.redmineControllerService.insertRedmineSystem(value).pipe(map(r => {
            this.updateLastOperationStatus(r);
            return r.result ? r.result : {} as GRedmineSystem;
        }));
    }

    override delete(value: GRedmineSystem): Observable<boolean> {
        return this.redmineControllerService.deleteRedmineSystem(value).pipe(map(() => true));
    }

    override canBeDeleted(value: GRedmineSystem): Observable<{ canBeDeleted: boolean; message: string; }> {
        return of({ canBeDeleted: true, message: "" });
    }
}
