import { Component, forwardRef, Injector } from "@angular/core";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { GWebdavContentManagementSystem, SecretInfo, SecretsControllerService, WebdavSystemsControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { BaseEntityEditingComponent, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE, GeboFormGroupsService, GeboUIActionRoutingService, GeboUIOutputForwardingService } from "@Gebo.ai/reusable-ui";
import { ConfirmationService } from "primeng/api";
import { map, Observable, of } from "rxjs";
import { newSecretActionRequest } from "../utils/gebo-ai-create-secret-action-request-factory";

const webdavCode: string = "webdab-cms-module";

@Component({
    selector: "gebo-ai-webdav-admin-component",
    templateUrl: "gebo-ai-webdav-system-admin.component.html",
    providers: [
        { provide: GEBO_AI_MODULE, useValue: "GeboAIWebdavModule", multi: false },
        { provide: GEBO_AI_FIELD_HOST, useExisting: forwardRef(() => GeboAIWebdavAdminComponent), multi: false }
    ],
    standalone: false
})
export class GeboAIWebdavAdminComponent extends BaseEntityEditingComponent<GWebdavContentManagementSystem> {
    protected override entityName: string = "GWebdavContentManagementSystem";

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
        webdavAuthType: new FormControl(),
        secretCode: new FormControl()
    });

    private actualIdentityContext: string = webdavCode;
    public identitiesObservable: Observable<SecretInfo[]> = this.secretControllerService.getSecretsByContextCode(this.actualIdentityContext);

    authTypes: { code: GWebdavContentManagementSystem.WebdavAuthTypeEnum, description: string }[] = [
        { code: "NONE", description: "No authentication" },
        { code: "BASIC", description: "Basic (username + password)" },
        { code: "DIGEST", description: "Digest" },
        { code: "NTLM", description: "NTLM (Windows)" },
        { code: "BEARER_TOKEN", description: "Bearer token" }
    ];

    public get actualAuthType(): GWebdavContentManagementSystem.WebdavAuthTypeEnum {
        return this.formGroup.controls["webdavAuthType"]?.value;
    }

    public get showCredentials(): boolean {
        const type = this.actualAuthType;
        return type && type !== "NONE";
    }

    public newSecretAction = newSecretActionRequest(this.actualIdentityContext, this.entityName, this.entity, [SecretInfo.SecretTypeEnum.USERNAMEPASSWORD]);

    constructor(injector: Injector, geboFormGroupsService: GeboFormGroupsService,
        confirmationService: ConfirmationService,
        private webdavControllerService: WebdavSystemsControllerService,
        private secretControllerService: SecretsControllerService,
        geboUIActionRoutingService: GeboUIActionRoutingService,
        outputForwardingService?: GeboUIOutputForwardingService) {
        super(injector, geboFormGroupsService, confirmationService, geboUIActionRoutingService, outputForwardingService);
        this.manageOperationStatus = true;
        this.formGroup.controls["contentManagementSystemType"].valueChanges.subscribe(x => {
            if (x !== webdavCode) {
                this.formGroup.controls["contentManagementSystemType"].setValue(webdavCode);
            }
        });
        this.formGroup.controls["webdavAuthType"].valueChanges.subscribe({
            next: (atype: GWebdavContentManagementSystem.WebdavAuthTypeEnum) => {
                if (atype) {
                    if (atype === "NONE") {
                        this.requiredAndEnabled("secretCode", false);
                    } else {
                        this.requiredAndEnabled("secretCode", true);
                        if (atype === "BEARER_TOKEN") {
                            this.newSecretAction = newSecretActionRequest(this.actualIdentityContext, this.entityName, this.entity, [SecretInfo.SecretTypeEnum.TOKEN]);
                        } else {
                            this.newSecretAction = newSecretActionRequest(this.actualIdentityContext, this.entityName, this.entity, [SecretInfo.SecretTypeEnum.USERNAMEPASSWORD]);
                        }
                    }
                }
            }
        });
    }

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

    override findByCode(code: string): Observable<GWebdavContentManagementSystem | null> {
        return this.webdavControllerService.findWebdavSystemByCode(code);
    }

    override save(value: GWebdavContentManagementSystem): Observable<GWebdavContentManagementSystem> {
        return this.webdavControllerService.updateWebdavSystem(value).pipe(map(r => {
            this.updateLastOperationStatus(r);
            return r.result ? r.result : {} as GWebdavContentManagementSystem;
        }));
    }

    override insert(value: GWebdavContentManagementSystem): Observable<GWebdavContentManagementSystem> {
        return this.webdavControllerService.insertWebdavSystem(value).pipe(map(r => {
            this.updateLastOperationStatus(r);
            return r.result ? r.result : {} as GWebdavContentManagementSystem;
        }));
    }

    override delete(value: GWebdavContentManagementSystem): Observable<boolean> {
        return this.webdavControllerService.deleteWebdavSystem(value);
    }

    override canBeDeleted(value: GWebdavContentManagementSystem): Observable<{ canBeDeleted: boolean; message: string; }> {
        return of({ canBeDeleted: true, message: "" });
    }
}