/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

import { Component, Inject, OnInit, Optional } from "@angular/core";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { 
    BASE_PATH, 
    GeboMcpServerUserControllerService, 
    GeneratedUserApiKeyControllerService, 
    UserAccessibleMcpServerView, 
    GeneratedApiKeyInfo 
} from "@Gebo.ai/gebo-ai-rest-api";
import { ConfirmationService, ToastMessageOptions } from "primeng/api";
import { forkJoin, of } from "rxjs";
import { catchError, finalize } from "rxjs/operators";
import { fieldHostComponentName, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE } from "../../controls/field-host-component-iface/field-host-component-iface";

@Component({
    selector: "gebo-ai-user-integrations",
    templateUrl: "user-integrations.component.html",
    standalone: false,
    providers: [
        { provide: GEBO_AI_MODULE, useValue: "GeboAIUserIntegrationsModule", multi: false },
        { provide: GEBO_AI_FIELD_HOST, multi: false, useValue: fieldHostComponentName("GeboAIUserIntegrationsComponent") }
    ]
})
export class GeboAIUserIntegrationsComponent implements OnInit {
    loading: boolean = false;
    checkingAccess: boolean = true;
    accessDenied: boolean = false;

    mcpServers: UserAccessibleMcpServerView[] = [];
    apiKeys: GeneratedApiKeyInfo[] = [];

    // Notification messages to show in the view
    userMessages: ToastMessageOptions[] = [];

    // Key creation dialog visibility
    showCreateDialog: boolean = false;

    // Raw generated key display dialog
    showRawKeyDialog: boolean = false;
    generatedRawKey: string = "";

    // Expiration presets
    expirationPresets = [
        { label: "7 Days", value: 7 },
        { label: "30 Days", value: 30 },
        { label: "90 Days", value: 90 },
        { label: "Custom Date", value: 0 }
    ];

    formGroup: FormGroup = new FormGroup({
        description: new FormControl("", [Validators.required, Validators.maxLength(100)]),
        expirationPreset: new FormControl(30, Validators.required),
        customExpiration: new FormControl(null)
    });

    constructor(
        private geboMcpServerUserControllerService: GeboMcpServerUserControllerService,
        private generatedUserApiKeyControllerService: GeneratedUserApiKeyControllerService,
        private confirmationService: ConfirmationService,
        @Optional() @Inject(BASE_PATH) private basePath: string
    ) {
        if (!this.basePath) {
            this.basePath = window.location.origin;
        }
    }

    ngOnInit(): void {
        this.checkUserAccess();
    }

    private checkUserAccess(): void {
        this.checkingAccess = true;
        this.loading = true;

        forkJoin([
            this.geboMcpServerUserControllerService.getUsersCanAccessMcpServersList().pipe(catchError(() => of(false))),
            this.generatedUserApiKeyControllerService.isUserGeneratedApiKeyGenerationAllowed().pipe(catchError(() => of(false)))
        ]).pipe(
            finalize(() => {
                this.checkingAccess = false;
                this.loading = false;
            })
        ).subscribe(([mcpAllowed, apiKeyAllowed]) => {
            if (mcpAllowed && apiKeyAllowed) {
                this.accessDenied = false;
                this.loadData();
            } else {
                this.accessDenied = true;
                this.showMessage("error", "Access Denied", "You do not have permission to access user integrations.");
            }
        });
    }

    private loadData(): void {
        this.loading = true;
        
        forkJoin([
            this.geboMcpServerUserControllerService.listAccessibleMcpServers().pipe(catchError(() => of([]))),
            this.generatedUserApiKeyControllerService.getUserGeneratedApiKeyPagedList({ page: 0, pageSize: 100 }).pipe(
                catchError(() => of({ content: [] }))
            )
        ]).pipe(
            finalize(() => {
                this.loading = false;
            })
        ).subscribe(([mcpServers, pagedKeys]) => {
            this.mcpServers = mcpServers;
            this.apiKeys = pagedKeys.content || [];
        });
    }

    loadMcpServers(): void {
        this.loading = true;
        this.geboMcpServerUserControllerService.listAccessibleMcpServers().pipe(
            catchError(() => {
                this.showMessage("error", "Error", "Failed to load MCP servers.");
                return of([]);
            }),
            finalize(() => this.loading = false)
        ).subscribe(servers => {
            this.mcpServers = servers;
        });
    }

    loadApiKeys(): void {
        this.loading = true;
        this.generatedUserApiKeyControllerService.getUserGeneratedApiKeyPagedList({ page: 0, pageSize: 100 }).pipe(
            catchError(() => {
                this.showMessage("error", "Error", "Failed to load API keys.");
                return of({ content: [] });
            }),
            finalize(() => this.loading = false)
        ).subscribe(pagedKeys => {
            this.apiKeys = pagedKeys.content || [];
        });
    }

    getMcpEndpointUrl(mcpServer: UserAccessibleMcpServerView): string {
        let relativeUrl = mcpServer.exportedUniqueRelativeUrl || "";
        if (!relativeUrl) {
            return "";
        }
        if (!relativeUrl.startsWith("/")) {
            relativeUrl = "/" + relativeUrl;
        }
        return this.basePath + relativeUrl;
    }

    openCreateDialog(): void {
        this.formGroup.reset({
            description: "",
            expirationPreset: 30,
            customExpiration: null
        });
        this.showCreateDialog = true;
    }

    generateApiKey(): void {
        if (this.formGroup.invalid) {
            return;
        }

        const description = this.formGroup.value.description;
        const preset = this.formGroup.value.expirationPreset;
        let expirationDate: Date;

        if (preset === 0) {
            expirationDate = this.formGroup.value.customExpiration;
            if (!expirationDate) {
                this.showMessage("error", "Validation Error", "Please select a custom expiration date.");
                return;
            }
        } else {
            expirationDate = new Date();
            expirationDate.setDate(expirationDate.getDate() + preset);
        }

        this.loading = true;
        this.generatedUserApiKeyControllerService.generateUserGeneratedApiKey({
            description: description,
            expiration: expirationDate
        }).pipe(
            finalize(() => {
                this.loading = false;
                this.showCreateDialog = false;
            })
        ).subscribe({
            next: (newKey) => {
                this.generatedRawKey = newKey.apiKey;
                this.showRawKeyDialog = true;
                this.loadApiKeys();
                this.showMessage("success", "Success", "API Key generated successfully.");
            },
            error: () => {
                this.showMessage("error", "Error", "Failed to generate API Key.");
            }
        });
    }

    deleteApiKey(code: string): void {
        this.confirmationService.confirm({
            message: "Are you sure you want to delete this API key? Applications using this key will immediately lose access.",
            header: "Delete API Key",
            icon: "pi pi-exclamation-triangle",
            acceptButtonProps: { severity: "danger", label: "Delete", outlined: true },
            rejectButtonProps: { severity: "secondary", label: "Cancel", outlined: true },
            accept: () => {
                this.loading = true;
                this.generatedUserApiKeyControllerService.deleteUserGeneratedApiKey(code).pipe(
                    finalize(() => this.loading = false)
                ).subscribe({
                    next: () => {
                        this.showMessage("success", "Deleted", "API Key has been deleted.");
                        this.loadApiKeys();
                    },
                    error: () => {
                        this.showMessage("error", "Error", "Failed to delete API Key.");
                    }
                });
            }
        });
    }

    copyToClipboard(text: string, type: string): void {
        if (!text) return;
        
        if (navigator.clipboard && navigator.clipboard.writeText) {
            navigator.clipboard.writeText(text).then(() => {
                this.showMessage("success", "Copied", `${type} copied to clipboard.`);
            }).catch(err => {
                console.error("Could not copy text: ", err);
                this.fallbackCopyToClipboard(text, type);
            });
        } else {
            this.fallbackCopyToClipboard(text, type);
        }
    }

    private fallbackCopyToClipboard(text: string, type: string): void {
        const textarea = document.createElement("textarea");
        textarea.value = text;
        textarea.style.position = "fixed";  // Avoid scrolling to bottom
        document.body.appendChild(textarea);
        textarea.focus();
        textarea.select();
        try {
            document.execCommand("copy");
            this.showMessage("success", "Copied", `${type} copied to clipboard.`);
        } catch (err) {
            console.error("Fallback copy failed", err);
            this.showMessage("error", "Copy Failed", "Please select and copy manually.");
        }
        document.body.removeChild(textarea);
    }

    isExpired(expiration?: Date): boolean {
        if (!expiration) return false;
        return new Date(expiration).getTime() < Date.now();
    }

    private showMessage(severity: string, summary: string, detail?: string): void {
        this.userMessages = [{ severity, summary, detail }];
        // Automatically dismiss messages after 5 seconds
        setTimeout(() => {
            if (this.userMessages.length > 0 && this.userMessages[0].summary === summary) {
                this.userMessages = [];
            }
        }, 5000);
    }
}
