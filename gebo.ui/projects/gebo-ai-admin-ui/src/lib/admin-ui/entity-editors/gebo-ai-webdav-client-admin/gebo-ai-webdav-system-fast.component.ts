import { Component, EventEmitter, Output } from "@angular/core";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { FastWebdavSystemInsertRequest, GWebdavContentManagementSystem, WebdavSystemsControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { ToastMessageOptions } from "primeng/api";
import { fieldHostComponentName, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE } from "@Gebo.ai/reusable-ui";

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
        username: new FormControl(),
        password: new FormControl(),
        token: new FormControl()
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

    @Output() newWebdavSystemEvent: EventEmitter<GWebdavContentManagementSystem> = new EventEmitter();
    @Output() cancelAction: EventEmitter<boolean> = new EventEmitter();

    constructor(private webdavSystemsService: WebdavSystemsControllerService) {
        this.formGroup.controls["authType"].setValue(this.currentAuthType);
    }

    get showCredentials(): boolean {
        const type = this.formGroup.controls["authType"].value;
        return type && type !== "NONE";
    }

    get isBearerToken(): boolean {
        return this.formGroup.controls["authType"].value === "BEARER_TOKEN";
    }

    doInsert(): void {
        const data: FastWebdavSystemInsertRequest = this.formGroup.value;
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