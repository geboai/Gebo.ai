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
import { A2AClientConfigControllerService, A2ARemoteAgentConfig, A2ARemoteSkill, SecretInfo, SecretsControllerService, UsersAdminControllerService, UserInfos, UsersGroup } from "@Gebo.ai/gebo-ai-rest-api";
import { BaseEntityEditingComponent, GeboFormGroupsService, GeboUIActionRoutingService, GeboUIOutputForwardingService, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE } from "@Gebo.ai/reusable-ui";
import { ConfirmationService } from "primeng/api";
import { Observable, of } from "rxjs";
import { map } from "rxjs/operators";
import { newSecretActionRequest } from "../utils/gebo-ai-create-secret-action-request-factory";

/**
 * Editor for a registered external A2A agent (the "import" side): connection,
 * transport, outbound authentication, and the skills discovered from the remote
 * Agent Card via testAndDiscovery. Mirrors the MCP client admin editor.
 */
@Component({
    selector: "gebo-ai-a2a-client-admin-component",
    templateUrl: "gebo-ai-a2a-client-admin.component.html",
    standalone: false,
    providers: [
        { provide: GEBO_AI_MODULE, useValue: "GeboAIA2AClientAdminModule", multi: false },
        { provide: GEBO_AI_FIELD_HOST, useExisting: forwardRef(() => GeboAIA2AClientAdminComponent), multi: false }
    ]
})
export class GeboAIA2AClientAdminComponent extends BaseEntityEditingComponent<A2ARemoteAgentConfig> implements OnInit {
    protected override entityName: string = "A2ARemoteAgentConfig";

    override formGroup: FormGroup<any> = new FormGroup({
        code: new FormControl(),
        description: new FormControl(),
        userModified: new FormControl(),
        userCreated: new FormControl(),
        dateModified: new FormControl(),
        dateCreated: new FormControl(),
        agentCardUrl: new FormControl('', Validators.required),
        rpcEndpoint: new FormControl(''),
        transportType: new FormControl('JSONRPC', Validators.required),
        authMode: new FormControl('NONE', Validators.required),
        secretCode: new FormControl(),
        oauth2AuthenticatorCode: new FormControl(),
        exportingPrefix: new FormControl('a2a-', Validators.required),
        enabled: new FormControl(false),
        accessibleGroups: new FormControl(),
        accessibleUsers: new FormControl(),
        accessibleToAll: new FormControl(true),
        aclAliases: new FormControl(),
        skills: new FormControl([])
    });

    public transportTypes = [
        { label: 'JSON-RPC', value: 'JSONRPC' },
        { label: 'REST', value: 'REST' },
        { label: 'gRPC', value: 'GRPC' }
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

    private actualIdentityContext: string = "a2a-client";
    identitiesObservable = this.secretControllerService.getSecretsByContextCode(this.actualIdentityContext);
    public newSecretAction = newSecretActionRequest(this.actualIdentityContext, this.entityName, this.entity, ['TOKEN']);

    public users: UserInfos[] = [];
    public groups: UsersGroup[] = [];
    public skills: A2ARemoteSkill[] = [];
    public readonly: boolean = false;

    // Auth-mode helpers driving the conditional credential UI.
    get authModeValue(): string { return this.formGroup.get('authMode')?.value; }
    /** Modes that use a stored secret (API key / bearer): the secret is required. */
    get isStaticSecretMode(): boolean {
        return this.authModeValue === 'API_KEY' || this.authModeValue === 'STATIC_BEARER_TOKEN';
    }
    /** Per-user delegation modes: a stored secret is optional (empty ⇒ token exchange). */
    get isPerUserRelayMode(): boolean {
        return this.authModeValue === 'OAUTH2_AUTHORIZATION_CODE_PER_USER' || this.authModeValue === 'USER_TOKEN_RELAY';
    }
    /** Token exchange always relays the caller's own identity: no secret is used. */
    get isTokenExchangeMode(): boolean {
        return this.authModeValue === 'TOKEN_EXCHANGE';
    }

    constructor(
        injector: Injector,
        geboFormGroupsService: GeboFormGroupsService,
        confirmationService: ConfirmationService,
        private service: A2AClientConfigControllerService,
        private secretControllerService: SecretsControllerService,
        private userAdminControllerService: UsersAdminControllerService,
        geboUIActionRoutingService: GeboUIActionRoutingService,
        outputForwardingService?: GeboUIOutputForwardingService
    ) {
        super(injector, geboFormGroupsService, confirmationService, geboUIActionRoutingService, outputForwardingService);
        this.manageOperationStatus = true;

        this.formGroup.controls["authMode"].valueChanges.subscribe({
            next: (authMode) => {
                if (!authMode) { return; }
                switch (authMode) {
                    case 'OAUTH2_CLIENT_CREDENTIALS':
                        this.newSecretAction = newSecretActionRequest(this.actualIdentityContext, this.entityName, this.entity, [SecretInfo.SecretTypeEnum.OAUTH2STANDARD]);
                        break;
                    default:
                        this.newSecretAction = newSecretActionRequest(this.actualIdentityContext, this.entityName, this.entity, ['TOKEN']);
                        break;
                }
            }
        });
    }

    override ngOnInit(): void {
        super.ngOnInit();
        this.userAdminControllerService.getAllUsers().subscribe(u => this.users = u || []);
        this.userAdminControllerService.getAllGroups().subscribe(g => this.groups = g || []);
    }

    override onLoadedPersistentData(actualValue: A2ARemoteAgentConfig): void {
        this.skills = actualValue.skills || [];
        this.formGroup.patchValue({ skills: this.skills }, { emitEvent: false });
    }

    override onNewData(actualValue: A2ARemoteAgentConfig): void {
        this.skills = actualValue.skills || [];
        this.formGroup.patchValue({ skills: this.skills }, { emitEvent: false });
    }

    /** Fetches the remote Agent Card and reconciles the discovered skills. */
    public testAndDiscoveryConnection(): void {
        const config: A2ARemoteAgentConfig = { ...this.entity, ...this.formGroup.value };
        this.loadingRelatedBackend = true;
        this.service.testAndDiscovery1(config).subscribe({
            next: (status) => {
                this.updateLastOperationStatus(status);
                if (status && status.hasErrorMessages !== true && status.result) {
                    this.skills = status.result.skills || [];
                    this.formGroup.patchValue({ skills: this.skills });
                    this.formGroup.markAsDirty();
                    this.userMessages = [{
                        severity: "success",
                        summary: "Connected",
                        detail: "A2A agent reachable. Discovered " + this.skills.length + " skill(s)."
                    }];
                }
            },
            error: () => { this.loadingRelatedBackend = false; },
            complete: () => { this.loadingRelatedBackend = false; }
        });
    }

    override findByCode(code: string): Observable<A2ARemoteAgentConfig | null> {
        return this.service.findByCode2(code).pipe(map(r => r.result || null));
    }

    override save(value: any): Observable<A2ARemoteAgentConfig> {
        return this.service.update2(value).pipe(map(r => {
            this.updateLastOperationStatus(r);
            return r.result ? r.result : value;
        }));
    }

    override insert(value: any): Observable<A2ARemoteAgentConfig> {
        return this.service.insert1(value).pipe(map(r => {
            this.updateLastOperationStatus(r);
            return r.result ? r.result : value;
        }));
    }

    override delete(value: A2ARemoteAgentConfig): Observable<boolean> {
        return this.service.delete1(value).pipe(map(r => {
            this.assignBackendMessages(r.messages);
            return true;
        }));
    }

    override canBeDeleted(value: A2ARemoteAgentConfig): Observable<{ canBeDeleted: boolean; message: string; }> {
        return of({ canBeDeleted: true, message: "" });
    }
}
