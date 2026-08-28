/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

import { Component, forwardRef, Injector } from "@angular/core";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { A2AExportedAgent, A2AServerConfig, GeboA2AServerAdminControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { BaseEntityEditingComponent, GeboFormGroupsService, GeboUIActionRoutingService, GeboUIOutputForwardingService, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE } from "@Gebo.ai/reusable-ui";
import { ConfirmationService } from "primeng/api";
import { map, Observable, of } from "rxjs";

/**
 * Editor for a published A2A server (the "export" side): the relative URL, the
 * enable flag, the list of exported agents/networks (each advertised as an A2A
 * skill), and the ACLs. Mirrors the MCP server admin editor.
 */
@Component({
    selector: "gebo-ai-a2a-server-admin-component",
    templateUrl: "gebo-ai-a2a-server-admin.component.html",
    standalone: false,
    providers: [
        { provide: GEBO_AI_MODULE, useValue: "GeboAIA2AServerAdminModule", multi: false },
        { provide: GEBO_AI_FIELD_HOST, useExisting: forwardRef(() => GeboAIA2AServerAdminComponent), multi: false }
    ]
})
export class GeboAIA2AServerAdminComponent extends BaseEntityEditingComponent<A2AServerConfig> {
    protected override entityName: string = "A2AServerConfig";

    override formGroup: FormGroup<any> = new FormGroup({
        code: new FormControl(),
        description: new FormControl(),
        userModified: new FormControl(),
        userCreated: new FormControl(),
        dateModified: new FormControl(),
        dateCreated: new FormControl(),
        exportedRelativeUrl: new FormControl('', Validators.required),
        enabled: new FormControl(false),
        securitySchemeName: new FormControl(),
        exportedAgents: new FormControl([]),
        accessibleGroups: new FormControl(),
        accessibleUsers: new FormControl(),
        accessibleToAll: new FormControl(true),
        aclAliases: new FormControl()
    });

    public kinds = [
        { label: 'Whole network of agents', value: 'NETWORK' },
        { label: 'Single agent', value: 'AGENT' }
    ];

    /** The exported agents/networks being edited. */
    public exportedAgents: A2AExportedAgent[] = [];

    /** New-entry form state. */
    public newEntry: A2AExportedAgent = this.blankEntry();

    public readonly: boolean = false;

    constructor(
        injector: Injector,
        geboFormGroupsService: GeboFormGroupsService,
        confirmationService: ConfirmationService,
        geboUIActionRoutingService: GeboUIActionRoutingService,
        outputForwardingService: GeboUIOutputForwardingService,
        private service: GeboA2AServerAdminControllerService
    ) {
        super(injector, geboFormGroupsService, confirmationService, geboUIActionRoutingService, outputForwardingService);
        this.manageOperationStatus = true;
    }

    protected override onLoadedPersistentData(actualValue: A2AServerConfig): void {
        this.initialize(actualValue);
    }

    protected override onNewData(actualValue: A2AServerConfig): void {
        this.initialize(actualValue);
    }

    private initialize(config: A2AServerConfig): void {
        this.exportedAgents = [...(config?.exportedAgents || [])];
        this.newEntry = this.blankEntry();
        this.formGroup.patchValue({ exportedAgents: this.exportedAgents }, { emitEvent: false });
    }

    private blankEntry(): A2AExportedAgent {
        return { kind: A2AExportedAgent.KindEnum.NETWORK, networkCode: '', agentConfigCode: '', skillName: '' };
    }

    public isNetwork(entry: A2AExportedAgent): boolean {
        return entry.kind === A2AExportedAgent.KindEnum.NETWORK;
    }

    /** Adds the new entry to the exported list after minimal validation. */
    public addEntry(): void {
        const entry = { ...this.newEntry };
        const code = this.isNetwork(entry) ? entry.networkCode : entry.agentConfigCode;
        if (!code || !code.trim()) {
            this.userMessages = [{ severity: "warn", summary: "Missing code", detail: "Enter the network or agent code to export." }];
            return;
        }
        if (!entry.skillName || !entry.skillName.trim()) {
            entry.skillName = code;
        }
        this.exportedAgents = [...this.exportedAgents, entry];
        this.formGroup.patchValue({ exportedAgents: this.exportedAgents });
        this.formGroup.markAsDirty();
        this.newEntry = this.blankEntry();
    }

    public removeEntry(index: number): void {
        this.exportedAgents = this.exportedAgents.filter((_, i) => i !== index);
        this.formGroup.patchValue({ exportedAgents: this.exportedAgents });
        this.formGroup.markAsDirty();
    }

    override findByCode(code: string): Observable<A2AServerConfig | null> {
        return this.service.findByCode1(code).pipe(map(r => r.result || null));
    }

    override save(value: any): Observable<A2AServerConfig> {
        return this.service.update1(value).pipe(map(r => {
            this.updateLastOperationStatus(r);
            return r.result ? r.result : value;
        }));
    }

    override insert(value: any): Observable<A2AServerConfig> {
        return this.service.insert(value).pipe(map(r => {
            this.updateLastOperationStatus(r);
            return r.result ? r.result : value;
        }));
    }

    override delete(value: A2AServerConfig): Observable<boolean> {
        return this.service._delete(value.code as string).pipe(map(r => {
            this.assignBackendMessages(r.messages);
            return true;
        }));
    }

    override canBeDeleted(value: A2AServerConfig): Observable<{ canBeDeleted: boolean; message: string; }> {
        return of({ canBeDeleted: true, message: "" });
    }
}
