import { Component, EventEmitter, Output } from "@angular/core";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { FastWebdavSystemInsertRequest, GWebdavContentManagementSystem, SecretInfo, SecretsControllerService, WebdavSystemsControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { ToastMessageOptions } from "primeng/api";
import { fieldHostComponentName, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE } from "@Gebo.ai/reusable-ui";
import { newSecretActionRequest } from "../utils/gebo-ai-create-secret-action-request-factory";
import { Observable } from "rxjs";

const webdavCode: string = "webdab-cms-module";

@Component({
    selector: "gebo-ai-webdav-system-fast-component",
    templateUrl: "gebo-ai-webdav-system-fast.component.html",
    providers: [
        { provide: GEBO_AI_MODULE, useValue: "GeboAIWebdavModule", multi: false },
        { provide: GEBO_AI_FIELD_HOST, multi: false, useValue: fieldHostComponentName("GeboAIWebdavSystemFastComponent") }
    ],
    standalone: false
})
export class GeboAIWebdavSystemFastComponent {
    public loading: boolean = false;

    public formGroup: FormGroup = new FormGroup({
        description: new FormControl("WebDAV system"),
        baseUri: new FormControl("", [Validators.required]),
        authType: new FormControl(null, [Validators.required]),
        secretCode: new FormControl()
    });

    userMessages: ToastMessageOptions[] = [];

    authTypes: { code: GWebdavContentManagementSystem.WebdavAuthTypeEnum, description: string }[] = [
        { code: "NONE", description: "No authentication" },
        { code: "BASIC", description: "Basic (username + password)" },
        { code: "DIGEST", description: "Digest" },
        { code: "NTLM", description: "NTLM (Windows)" },
        { code: "BEARER_TOKEN", description: "Bearer token" }
    ];

    public currentAuthType?: GWebdavContentManagementSystem.WebdavAuthTypeEnum = "BASIC";
    public identitiesObservable: Observable<SecretInfo[]> = this.secretControllerService.getSecretsByContextCode(webdavCode);

    @Output() newWebdavSystemEvent: EventEmitter<GWebdavContentManagementSystem> = new EventEmitter();
    @Output() cancelAction: EventEmitter<boolean> = new EventEmitter();

    constructor(private webdavSystemsService: WebdavSystemsControllerService,
        private secretControllerService: SecretsControllerService) {
        this.formGroup.controls["authType"].setValue(this.currentAuthType);
    }

    get showCredentials(): boolean {
        const type = this.formGroup.controls["authType"].value;
        return type && type !== "NONE";
    }

    get newSecretAction() {
        const type = this.formGroup.controls["authType"].value;
        if (type === "BEARER_TOKEN") {
            return newSecretActionRequest(webdavCode, "GWebdavContentManagementSystem", undefined, [SecretInfo.SecretTypeEnum.TOKEN]);
        }
        return newSecretActionRequest(webdavCode, "GWebdavContentManagementSystem", undefined, [SecretInfo.SecretTypeEnum.USERNAMEPASSWORD]);
    }

    doInsert(): void {
        const data: FastWebdavSystemInsertRequest = {
            baseUri: this.formGroup.controls["baseUri"].value,
            description: this.formGroup.controls["description"].value,
            authType: this.formGroup.controls["authType"].value
        };
        this.loading = true;
        this.webdavSystemsService.fastWebdavConfig(data).subscribe({
            next: (result) => {
                this.userMessages = result.messages as ToastMessageOptions[];
                if (result.result && result.hasErrorMessages !== true) {
                    this.newWebdavSystemEvent.emit(result.result);
                }
            },
            complete: () => {
                this.loading = false;
            }
        });
    }
}