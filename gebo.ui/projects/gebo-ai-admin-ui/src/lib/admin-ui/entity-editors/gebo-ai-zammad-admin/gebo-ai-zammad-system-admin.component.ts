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
import { GZammadSystem, ZammadSystemsControllerService, SecretInfo, SecretsControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { BaseEntityEditingComponent, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE, GeboFormGroupsService, GeboUIActionRoutingService, GeboUIOutputForwardingService } from "@Gebo.ai/reusable-ui";
import { ConfirmationService, ToastMessageOptions } from "@openng/optimus-ui/api";
import { map, Observable, of } from "rxjs";
import { newSecretActionRequest } from "../utils/gebo-ai-create-secret-action-request-factory";

/** The Zammad system type code, also the context code of its stored credentials. */
export const ZAMMAD_SYSTEM_TYPE: string = "ZAMMAD";

/**
 * Editor of a Zammad system: the server address and the credentials (an access
 * token, or a username and password) Gebo.ai reads its REST API with.
 */
@Component({
    selector: "gebo-ai-zammad-admin-component",
    templateUrl: "gebo-ai-zammad-system-admin.component.html",
    providers: [
        { provide: GEBO_AI_MODULE, useValue: "GeboAIZammadModule", multi: false },
        { provide: GEBO_AI_FIELD_HOST, useExisting: forwardRef(() => GeboAIZammadAdminComponent), multi: false }
    ],
    changeDetection: ChangeDetectionStrategy.Eager,
    standalone: false
})
export class GeboAIZammadAdminComponent extends BaseEntityEditingComponent<GZammadSystem> {
    protected override entityName: string = "GZammadSystem";

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

    public identitiesObservable: Observable<SecretInfo[]> = this.secretControllerService.getSecretsByContextCode(ZAMMAD_SYSTEM_TYPE);

    public newSecretAction = newSecretActionRequest(ZAMMAD_SYSTEM_TYPE, this.entityName, this.entity, [SecretInfo.SecretTypeEnum.TOKEN, SecretInfo.SecretTypeEnum.USERNAMEPASSWORD]);

    public testing: boolean = false;

    constructor(injector: Injector, geboFormGroupsService: GeboFormGroupsService,
        confirmationService: ConfirmationService,
        private zammadControllerService: ZammadSystemsControllerService,
        private secretControllerService: SecretsControllerService,
        geboUIActionRoutingService: GeboUIActionRoutingService,
        outputForwardingService?: GeboUIOutputForwardingService) {
        super(injector, geboFormGroupsService, confirmationService, geboUIActionRoutingService, outputForwardingService);
        this.manageOperationStatus = true;
        this.formGroup.controls["contentManagementSystemType"].valueChanges.subscribe(x => {
            if (x !== ZAMMAD_SYSTEM_TYPE) {
                this.formGroup.controls["contentManagementSystemType"].setValue(ZAMMAD_SYSTEM_TYPE);
            }
        });
    }

    protected override onNewData(actualValue: GZammadSystem): void {
        this.formGroup.controls["contentManagementSystemType"].setValue(ZAMMAD_SYSTEM_TYPE);
    }

    /** Checks the address and the credentials against the Zammad server, without saving. */
    doTest(): void {
        const value: GZammadSystem = { ...this.formGroup.value, contentManagementSystemType: ZAMMAD_SYSTEM_TYPE };
        this.testing = true;
        this.zammadControllerService.testZammadSystem(value).subscribe({
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

    override findByCode(code: string): Observable<GZammadSystem | null> {
        return this.zammadControllerService.findZammadSystemByCode(code);
    }

    override save(value: GZammadSystem): Observable<GZammadSystem> {
        return this.zammadControllerService.updateZammadSystem(value).pipe(map(r => {
            this.updateLastOperationStatus(r);
            return r.result ? r.result : {} as GZammadSystem;
        }));
    }

    override insert(value: GZammadSystem): Observable<GZammadSystem> {
        return this.zammadControllerService.insertZammadSystem(value).pipe(map(r => {
            this.updateLastOperationStatus(r);
            return r.result ? r.result : {} as GZammadSystem;
        }));
    }

    override delete(value: GZammadSystem): Observable<boolean> {
        return this.zammadControllerService.deleteZammadSystem(value).pipe(map(() => true));
    }

    override canBeDeleted(value: GZammadSystem): Observable<{ canBeDeleted: boolean; message: string; }> {
        return of({ canBeDeleted: true, message: "" });
    }
}
