/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

import { Component, Injectable, OnInit } from "@angular/core";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { GeneratedAdminApiKeyControllerService, GeneratedApiKeyInfo, GeneratedApiKey, UserInfos, UsersAdminControllerService, DataPage, PagedModelGeneratedApiKeyInfo } from "@Gebo.ai/gebo-ai-rest-api";
import { BaseWizardSectionComponent, fieldHostComponentName, GEBO_AI_FIELD_HOST, SetupWizardComunicationService, AbstractStatusService } from "@Gebo.ai/reusable-ui";
import { ConfirmationService } from "primeng/api";
import { PaginatorState } from "primeng/paginator";
import { Observable } from "rxjs";

@Injectable({ providedIn: "root" })
export class GeneratedAdminApiKeyEnabledService extends AbstractStatusService {
    constructor(private apiKeyService: GeneratedAdminApiKeyControllerService) {
        super();
    }

    public override getBooleanStatus(): Observable<boolean> {
        return this.apiKeyService.isAdminGeneratedApiKeyGenerationAllowed();
    }
}

@Component({
    selector: "gebo-ai-generated-admin-api-key-wizard-component",
    templateUrl: "generated-admin-api-key-wizard.component.html",
    standalone: false,
    providers: [
        { provide: GEBO_AI_FIELD_HOST, multi: false, useValue: fieldHostComponentName("GeneratedAdminApiKeyWizardComponent") }
    ]
})
export class GeneratedAdminApiKeyWizardComponent extends BaseWizardSectionComponent implements OnInit {
    /** Paged list of API keys returned from the server */
    protected apiKeysPaged?: PagedModelGeneratedApiKeyInfo;
    
    /** List of all system users to populate the impersonate dropdown */
    protected users: UserInfos[] = [];
    
    /** Indicates if generating admin API keys is allowed by the server */
    protected isGenerationAllowed: boolean = true;
    
    /** Controls visibility of the key generation dialog */
    protected showCreateDialog: boolean = false;
    
    /** Holds the details of a newly generated API key, including the actual secret value */
    protected newGeneratedApiKey?: GeneratedApiKey;

    /** Pagination parameters */
    public page: DataPage = {
        page: 0,
        pageSize: 10
    };

    /** Form group for the API key generation fields */
    public formGroup: FormGroup = new FormGroup({
        description: new FormControl("", Validators.required),
        impersonatedUser: new FormControl("", Validators.required),
        expiration: new FormControl(this.getDefaultExpirationDate(), Validators.required)
    });

    constructor(
        setupWizardComunicationService: SetupWizardComunicationService,
        private apiKeyService: GeneratedAdminApiKeyControllerService,
        private usersController: UsersAdminControllerService,
        private confirmationService: ConfirmationService
    ) {
        super(setupWizardComunicationService);
    }

    /**
     * Component initialization hook.
     * Checks if API key generation is allowed, loads users, and refreshes the key list.
     */
    override ngOnInit(): void {
        super.ngOnInit();
        this.checkGenerationAllowed();
        this.loadUsers();
    }

    /**
     * Calculates a default expiration date (30 days from now).
     * @returns Date
     */
    protected getDefaultExpirationDate(): Date {
        const date = new Date();
        date.setDate(date.getDate() + 30);
        return date;
    }

    /**
     * Fetches all users from the user administration service to populate the dropdown.
     */
    private loadUsers() {
        this.usersController.getAllUsers().subscribe({
            next: (users: UserInfos[]) => {
                this.users = users;
            },
            error: (err: any) => {
                console.error("Error loading users", err);
            }
        });
    }

    /**
     * Checks if generating API keys is allowed in the current application configuration.
     */
    private checkGenerationAllowed() {
        this.apiKeyService.isAdminGeneratedApiKeyGenerationAllowed().subscribe({
            next: (allowed: boolean) => {
                this.isGenerationAllowed = allowed;
            },
            error: (err: any) => {
                console.error("Error checking generation allowance", err);
            }
        });
    }

    /**
     * Getter that returns the API key list content.
     */
    public get rows(): GeneratedApiKeyInfo[] {
        return this.apiKeysPaged?.content ? this.apiKeysPaged.content : [];
    }

    /**
     * Loads/reloads the page of API keys from the server.
     */
    public override reloadData(): void {
        this.loading = true;
        this.apiKeyService.getAdminGeneratedApiKeyPagedList(this.page).subscribe({
            next: (res: PagedModelGeneratedApiKeyInfo) => {
                this.apiKeysPaged = res;
                this.isSetupCompleted = (this.apiKeysPaged?.content && this.apiKeysPaged.content.length > 0) || false;
            },
            error: (err: any) => {
                console.error("Error fetching API keys", err);
                this.userMessages = [{ severity: "error", summary: "Error", detail: "Could not fetch API keys" }];
            },
            complete: () => {
                this.loading = false;
            }
        });
    }

    /**
     * Handles table page changes from the paginator.
     * @param evt PaginatorState containing the new page number and row count
     */
    public onPageChange(evt: PaginatorState) {
        this.page.page = evt.page;
        this.page.pageSize = evt.rows;
        this.reloadData();
    }

    /**
     * Prepares and opens the dialog to generate a new key.
     */
    protected openCreateDialog() {
        this.newGeneratedApiKey = undefined;
        this.formGroup.reset({
            description: "",
            impersonatedUser: "",
            expiration: this.getDefaultExpirationDate()
        });
        this.showCreateDialog = true;
    }

    /**
     * Generates a new API key based on user form inputs.
     */
    protected generateApiKey() {
        if (this.formGroup.invalid) {
            return;
        }
        this.loading = true;
        const formValue = this.formGroup.value;
        const param = {
            description: formValue.description,
            impersonatedUser: formValue.impersonatedUser,
            expiration: formValue.expiration
        };
        this.apiKeyService.generateAdminGeneratedApiKey(param).subscribe({
            next: (key: GeneratedApiKey) => {
                this.newGeneratedApiKey = key;
                this.reloadData();
            },
            error: (err: any) => {
                console.error("Error generating API key", err);
                this.userMessages = [{ severity: "error", summary: "Error", detail: "Could not generate API key" }];
                this.loading = false;
            },
            complete: () => {
                this.loading = false;
            }
        });
    }

    /**
     * Deletes a generated API key, prompting for confirmation first.
     * @param keyInfo Details of the key to delete
     */
    protected deleteApiKey(keyInfo: GeneratedApiKeyInfo) {
        if (!keyInfo.code) return;
        this.confirmationService.confirm({
            header: "Delete API key",
            message: `Are you sure you want to delete the API key for ${keyInfo.impersonatedUser}?`,
            accept: () => {
                this.loading = true;
                this.apiKeyService.deleteAdminGeneratedApiKey(keyInfo.code!).subscribe({
                    next: () => {
                        this.reloadData();
                        this.userMessages = [{ severity: "success", summary: "Deleted", detail: "API Key deleted successfully" }];
                    },
                    error: (err: any) => {
                        console.error("Error deleting API key", err);
                        this.userMessages = [{ severity: "error", summary: "Error", detail: "Could not delete API key" }];
                        this.loading = false;
                    },
                    complete: () => {
                        this.loading = false;
                    }
                });
            }
        });
    }

    /**
     * Copies a text value (the API key) to the system clipboard.
     * @param val Text to copy
     */
    protected copyToClipboard(val: string) {
        if (!val) return;
        navigator.clipboard.writeText(val).then(() => {
            this.userMessages = [{ severity: "info", summary: "Copied", detail: "API Key copied to clipboard" }];
        }).catch(err => {
            console.error("Could not copy text: ", err);
        });
    }

    /**
     * Closes the creation dialog and resets state.
     */
    protected closeDialog() {
        this.showCreateDialog = false;
        this.newGeneratedApiKey = undefined;
    }
}
