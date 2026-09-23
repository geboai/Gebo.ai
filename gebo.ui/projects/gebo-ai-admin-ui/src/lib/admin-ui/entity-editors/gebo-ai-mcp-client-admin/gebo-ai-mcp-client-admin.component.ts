/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

import { Component, forwardRef, Injector, OnInit } from "@angular/core";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { McpClientConfigControllerService, MCPClientConfig, SecretInfo, SecretsControllerService, UsersAdminControllerService, UserInfos, UsersGroup, MCPTool, MCPResource, MCPPrompt } from "@Gebo.ai/gebo-ai-rest-api";
import { BaseEntityEditingComponent, GeboFormGroupsService, GeboUIActionRoutingService, GeboUIOutputForwardingService, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE } from "@Gebo.ai/reusable-ui";
import { ConfirmationService, ToastMessageOptions } from "primeng/api";
import { Observable, of } from "rxjs";
import { map } from "rxjs/operators";
import { newSecretActionRequest } from "../utils/gebo-ai-create-secret-action-request-factory";

/**
 * Component for creating and editing MCP Client configurations.
 * Supports STREAMABLE_HTTP, SSE_LEGACY, and STDIO transport mechanisms, 
 * validates connectivity via testAndDiscovery, and manages access controls 
 * for the server configuration and its individual tools, resources, and prompts.
 */
@Component({
    selector: "gebo-ai-mcp-client-admin-component",
    templateUrl: "gebo-ai-mcp-client-admin.component.html",
    standalone: false,
    providers: [
        { provide: GEBO_AI_MODULE, useValue: "GeboAIMcpClientAdminModule", multi: false },
        {
            provide: GEBO_AI_FIELD_HOST, useExisting: forwardRef(() => GeboAIMcpClientAdminComponent),
            multi: false
        }
    ]
})
export class GeboAIMcpClientAdminComponent extends BaseEntityEditingComponent<MCPClientConfig> implements OnInit {
    protected override entityName: string = "MCPClientConfig";

    override formGroup: FormGroup<any> = new FormGroup({
        code: new FormControl(),
        description: new FormControl(),
        userModified: new FormControl(),
        userCreated: new FormControl(),
        dateModified: new FormControl(),
        dateCreated: new FormControl(),
        baseUrl: new FormControl(),
        mcpEndpoint: new FormControl(),
        sseEndpoint: new FormControl(),
        secretCode: new FormControl(),
        oauth2AuthenticatorCode: new FormControl(),
        stdioCommand: new FormControl(),
        stdioArgsRaw: new FormControl(''),
        stdioEnvironmentRaw: new FormControl(''),
        transportType: new FormControl('SSE_LEGACY', Validators.required),
        authMode: new FormControl('NONE', Validators.required),
        exportingPrefix: new FormControl('mcp-', Validators.required),
        accessibleGroups: new FormControl(),
        accessibleUsers: new FormControl(),
        accessibleToAll: new FormControl(true),
        aclAliases: new FormControl(),
        tools: new FormControl([]),
        resources: new FormControl([]),
        prompts: new FormControl([])
    });

    // Transport Types and Auth Modes options
    public transportTypes = [
        { label: 'Streamable HTTP', value: 'STREAMABLE_HTTP' },
        { label: 'SSE Legacy', value: 'SSE_LEGACY' },
        { label: 'STDIO', value: 'STDIO' }
    ];

    public authModes = [
        { label: 'None', value: 'NONE' },
        { label: 'API Key', value: 'API_KEY' },
        { label: 'Static Bearer Token', value: 'STATIC_BEARER_TOKEN' },
        { label: 'OAuth2 Client Credentials', value: 'OAUTH2_CLIENT_CREDENTIALS' },
        { label: 'OAuth2 Authorization Code per User', value: 'OAUTH2_AUTHORIZATION_CODE_PER_USER' },
        { label: 'User Token Relay', value: 'USER_TOKEN_RELAY' },
        { label: 'Token Exchange', value: 'TOKEN_EXCHANGE' }
    ];

    // Secret Management
    private actualIdentityContext: string = "mcp-client";
    identitiesObservable = this.secretControllerService.getSecretsByContextCode(this.actualIdentityContext);
    public newSecretAction = newSecretActionRequest(this.actualIdentityContext, this.entityName, this.entity, ['TOKEN']);

    // Access control options
    public users: UserInfos[] = [];
    public groups: UsersGroup[] = [];

    // Exports collections
    public tools: MCPTool[] = [];
    public resources: MCPResource[] = [];
    public prompts: MCPPrompt[] = [];

    // Readonly property for the editor template
    public readonly: boolean = false;

    // Permissions Dialog variables
    public showPermissionsDialog: boolean = false;
    public permissionsDialogHeader: string = '';
    public editingItemType: 'tool' | 'resource' | 'prompt' = 'tool';
    public editingItemIndex: number = -1;
    public editingItemPermissions: { accessibleToAll?: boolean, accessibleGroups?: string[], accessibleUsers?: string[] } = {};

    constructor(
        injector: Injector,
        geboFormGroupsService: GeboFormGroupsService,
        confirmationService: ConfirmationService,
        private service: McpClientConfigControllerService,
        private secretControllerService: SecretsControllerService,
        private userAdminControllerService: UsersAdminControllerService,
        geboUIActionRoutingService: GeboUIActionRoutingService,
        outputForwardingService?: GeboUIOutputForwardingService
    ) {
        super(injector, geboFormGroupsService, confirmationService, geboUIActionRoutingService, outputForwardingService);
        this.manageOperationStatus = true;

        // Dynamic Secret Action updates based on selected authMode
        this.formGroup.controls["authMode"].valueChanges.subscribe({
            next: (authMode) => {
                if (authMode) {
                    switch (authMode) {
                        case 'API_KEY':
                        case 'STATIC_BEARER_TOKEN':
                            this.newSecretAction = newSecretActionRequest(this.actualIdentityContext, this.entityName, this.entity, ['TOKEN']);
                            break;
                        case 'OAUTH2_CLIENT_CREDENTIALS':
                            this.newSecretAction = newSecretActionRequest(this.actualIdentityContext, this.entityName, this.entity, [SecretInfo.SecretTypeEnum.OAUTH2STANDARD]);
                            break;
                        default:
                            this.newSecretAction = newSecretActionRequest(this.actualIdentityContext, this.entityName, this.entity, ['TOKEN']);
                            break;
                    }
                }
            }
        });
    }

    override ngOnInit(): void {
        super.ngOnInit();
        this.userAdminControllerService.getAllUsers().subscribe(u => this.users = u || []);
        this.userAdminControllerService.getAllGroups().subscribe(g => this.groups = g || []);
    }

    override onLoadedPersistentData(actualValue: MCPClientConfig): void {
        this.tools = actualValue.tools || [];
        this.resources = actualValue.resources || [];
        this.prompts = actualValue.prompts || [];

        this.formGroup.patchValue({
            stdioArgsRaw: this.stringifyStdioArgs(actualValue.stdioArgs),
            stdioEnvironmentRaw: this.stringifyStdioEnv(actualValue.stdioEnvironment),
            tools: actualValue.tools || [],
            resources: actualValue.resources || [],
            prompts: actualValue.prompts || []
        }, { emitEvent: false });
    }

    override onNewData(actualValue: MCPClientConfig): void {
        this.tools = actualValue.tools || [];
        this.resources = actualValue.resources || [];
        this.prompts = actualValue.prompts || [];

        this.formGroup.patchValue({
            stdioArgsRaw: this.stringifyStdioArgs(actualValue.stdioArgs),
            stdioEnvironmentRaw: this.stringifyStdioEnv(actualValue.stdioEnvironment),
            tools: actualValue.tools || [],
            resources: actualValue.resources || [],
            prompts: actualValue.prompts || []
        }, { emitEvent: false });
    }

    // Parsing helpers for raw STDIO textareas
    private parseStdioArgs(raw: string | null | undefined): string[] {
        if (!raw) return [];
        return raw.split('\n').map(s => s.trim()).filter(s => s.length > 0);
    }

    private stringifyStdioArgs(args: string[] | null | undefined): string {
        if (!args) return '';
        return args.join('\n');
    }

    private parseStdioEnv(raw: string | null | undefined): { [key: string]: string } {
        if (!raw) return {};
        const env: { [key: string]: string } = {};
        raw.split('\n').forEach(line => {
            const index = line.indexOf('=');
            if (index !== -1) {
                const key = line.substring(0, index).trim();
                const value = line.substring(index + 1).trim();
                if (key) {
                    env[key] = value;
                }
            }
        });
        return env;
    }

    private stringifyStdioEnv(env: { [key: string]: string } | null | undefined): string {
        if (!env) return '';
        return Object.entries(env).map(([k, v]) => `${k}=${v}`).join('\n');
    }

    /**
     * Triggers the testAndDiscovery service call.
     * Merges current form parameters, connects to the MCP Server, 
     * retrieves exported elements, and displays success or error notifications.
     */
    public testAndDiscoveryConnection(): void {
        const config: MCPClientConfig = {
            ...this.entity,
            ...this.formGroup.value,
            stdioArgs: this.parseStdioArgs(this.formGroup.value.stdioArgsRaw),
            stdioEnvironment: this.parseStdioEnv(this.formGroup.value.stdioEnvironmentRaw)
        };

        // Remove temp UI properties
        delete (config as any).stdioArgsRaw;
        delete (config as any).stdioEnvironmentRaw;

        this.loadingRelatedBackend = true;
        this.service.testAndDiscovery(config).subscribe({
            next: (status) => {
                this.updateLastOperationStatus(status);
                if (status && status.hasErrorMessages !== true && status.result) {
                    const res = status.result;
                    this.formGroup.patchValue({
                        tools: res.tools || [],
                        resources: res.resources || [],
                        prompts: res.prompts || []
                    });
                    this.tools = res.tools || [];
                    this.resources = res.resources || [];
                    this.prompts = res.prompts || [];
                    this.formGroup.markAsDirty();
                    this.userMessages = [{
                        severity: "success",
                        summary: "Connected",
                        detail: "MCP Server connection verified. Exported tools, resources, and prompts successfully discovered."
                    }];
                }
            },
            error: (err) => {
                this.loadingRelatedBackend = false;
            },
            complete: () => {
                this.loadingRelatedBackend = false;
            }
        });
    }

    // Export permissions editing
    public editItemPermissions(type: 'tool' | 'resource' | 'prompt', index: number, item: any): void {
        this.editingItemType = type;
        this.editingItemIndex = index;
        this.permissionsDialogHeader = `Edit Permissions for ${type}: ${item.name}`;
        this.editingItemPermissions = {
            accessibleToAll: item.accessibleToAll !== false,
            accessibleGroups: item.accessibleGroups ? [...item.accessibleGroups] : [],
            accessibleUsers: item.accessibleUsers ? [...item.accessibleUsers] : []
        };
        this.showPermissionsDialog = true;
    }

    public savePermissions(): void {
        if (this.editingItemIndex === -1) return;

        const targetList = this.editingItemType === 'tool' ? this.tools :
                           this.editingItemType === 'resource' ? this.resources :
                           this.prompts;

        if (targetList[this.editingItemIndex]) {
            targetList[this.editingItemIndex].accessibleToAll = this.editingItemPermissions.accessibleToAll;
            targetList[this.editingItemIndex].accessibleGroups = this.editingItemPermissions.accessibleGroups;
            targetList[this.editingItemIndex].accessibleUsers = this.editingItemPermissions.accessibleUsers;
        }

        const controlName = this.editingItemType === 'tool' ? 'tools' : 
                            this.editingItemType === 'resource' ? 'resources' : 'prompts';
        
        this.formGroup.controls[controlName].setValue([...targetList]);
        this.formGroup.markAsDirty();
        this.showPermissionsDialog = false;
    }

    // BaseEntityEditingComponent overrides
    override findByCode(code: string): Observable<MCPClientConfig | null> {
        return this.service.findMCPClientConfigByCode(code).pipe(map(r => r.result || null));
    }

    override save(value: any): Observable<MCPClientConfig> {
        const payload: MCPClientConfig = {
            ...value,
            stdioArgs: this.parseStdioArgs(value.stdioArgsRaw),
            stdioEnvironment: this.parseStdioEnv(value.stdioEnvironmentRaw)
        };
        delete (payload as any).stdioArgsRaw;
        delete (payload as any).stdioEnvironmentRaw;

        return this.service.updateMCPClientConfig(payload).pipe(map(r => {
            this.updateLastOperationStatus(r);
            return r.result ? r.result : value;
        }));
    }

    override insert(value: any): Observable<MCPClientConfig> {
        const payload: MCPClientConfig = {
            ...value,
            stdioArgs: this.parseStdioArgs(value.stdioArgsRaw),
            stdioEnvironment: this.parseStdioEnv(value.stdioEnvironmentRaw)
        };
        delete (payload as any).stdioArgsRaw;
        delete (payload as any).stdioEnvironmentRaw;

        return this.service.insertMCPClientConfig(payload).pipe(map(r => {
            this.updateLastOperationStatus(r);
            return r.result ? r.result : value;
        }));
    }

    override delete(value: MCPClientConfig): Observable<boolean> {
        return this.service.deleteMCPClientConfig(value).pipe(map(r => {
            this.assignBackendMessages(r.messages);
            return true;
        }));
    }

    override canBeDeleted(value: MCPClientConfig): Observable<{ canBeDeleted: boolean; message: string; }> {
        return of({ canBeDeleted: true, message: "" });
    }
}
