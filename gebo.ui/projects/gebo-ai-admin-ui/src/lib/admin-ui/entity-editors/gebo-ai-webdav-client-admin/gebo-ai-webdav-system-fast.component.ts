import { Component, EventEmitter, Output } from "@angular/core";
import { takeUntilDestroyed } from "@angular/core/rxjs-interop";
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

    // "systems" lists known commercial/open-source WebDAV servers observed to use this
    // authentication method, to help the admin pick the right one for their remote system.
    authTypes: { code: GWebdavContentManagementSystem.WebdavAuthTypeEnum, description: string, systems: string }[] = [
        {
            code: "NONE", description: "No authentication",
            systems: "Public/anonymous WebDAV shares (e.g. Nextcloud/ownCloud public links, an Apache mod_dav or nginx-dav mount with no auth configured)"
        },
        {
            code: "BASIC", description: "Basic (username + password)",
            systems: "Nextcloud / ownCloud classic (use an app password when 2FA or SSO is enabled), Seafile / SeafDAV (HTTPS only; account password or a dedicated WebDAV secret), Pydio Cells (username + password, or username + Personal Access Token as the password), ONLYOFFICE Workspace (portal login), Synology DSM WebDAV Server, generic Apache mod_dav / nginx WebDAV"
        },
        {
            code: "DIGEST", description: "Digest",
            systems: "Synology DSM WebDAV Server (selectable in DSM's WebDAV Server package), Apache mod_dav / mod_dav_svn, IIS WebDAV Publishing"
        },
        {
            code: "NTLM", description: "NTLM (Windows)",
            systems: "IIS WebDAV Publishing on Windows Server and other Windows-integrated-auth WebDAV shares"
        },
        {
            code: "BEARER_TOKEN", description: "Bearer token",
            systems: "OpenCloud / ownCloud Infinite Scale (oCIS) — Basic auth is disabled by default, so an OIDC/OAuth2 access token is required; Nextcloud/ownCloud classic when configured with the user_oidc app"
        }
    ];

    public currentAuthType?: GWebdavContentManagementSystem.WebdavAuthTypeEnum = "BASIC";

    @Output() newWebdavSystemEvent: EventEmitter<GWebdavContentManagementSystem> = new EventEmitter();
    @Output() cancelAction: EventEmitter<boolean> = new EventEmitter();

    constructor(private webdavSystemsService: WebdavSystemsControllerService) {
        this.formGroup.controls["authType"].valueChanges
            .pipe(takeUntilDestroyed())
            .subscribe((type) => this.applyRequiredValidators(type));
        this.formGroup.controls["authType"].setValue(this.currentAuthType);
    }

    // username+password and token are only required for the auth types that actually
    // use them; [required] on the field markup is a display-only asterisk, so the real
    // Validators.required must be toggled here whenever authType changes, or a field
    // left empty for an inapplicable auth type would still pass validation.
    private applyRequiredValidators(type?: GWebdavContentManagementSystem.WebdavAuthTypeEnum): void {
        const username = this.formGroup.controls["username"];
        const password = this.formGroup.controls["password"];
        const token = this.formGroup.controls["token"];

        switch (type) {
            case "BASIC":
            case "DIGEST":
            case "NTLM":
                username.setValidators([Validators.required]);
                password.setValidators([Validators.required]);
                token.clearValidators();
                break;
            case "BEARER_TOKEN":
                username.clearValidators();
                password.clearValidators();
                token.setValidators([Validators.required]);
                break;
            default:
                username.clearValidators();
                password.clearValidators();
                token.clearValidators();
                break;
        }
        username.updateValueAndValidity();
        password.updateValueAndValidity();
        token.updateValueAndValidity();
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