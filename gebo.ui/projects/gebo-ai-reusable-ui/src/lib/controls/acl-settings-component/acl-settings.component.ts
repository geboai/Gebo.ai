/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

import { Component, forwardRef, Input, OnInit } from "@angular/core";
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from "@angular/forms";
import {
    AclGrantAssignment,
    AclOwnerRef,
    AclSelectableOwners,
    AclSettingsAdminControllerService,
    AclSystemMode
} from "@Gebo.ai/gebo-ai-rest-api";

type GrantType = AclGrantAssignment.GrantsEnum;
type AccessScope = "EVERYONE" | "SPECIFIC";

/** Key used to index the per-owner grants map. */
const EVERYONE_KEY = "EVERYONE";

/** A user option enriched with the "covered by a selected group" state. */
interface UserOptionView extends AclOwnerRef {
    viewLabel: string;
    disabled: boolean;
}

/** A row of the per-owner grant editor. */
interface OwnerGrantRow {
    key: string;
    label: string;
    type: AclOwnerRef.TypeEnum;
    code?: string;
}

/**
 * Reusable admin control that edits the ACL of an
 * {@code IAclGrantedResource}. Its {@link ControlValueAccessor} value is the
 * resource's {@code aclAliases} (a {@code number[]}); the control resolves those
 * aliases into everyone/group/user assignments through the backend and encodes
 * the edited assignments back into aliases before emitting.
 *
 * Collapse rules mirror the backend: choosing <em>Everyone</em> hides the
 * group/user pickers, and users already covered by a selected group are shown as
 * covered and excluded from the emitted ACL.
 */
@Component({
    selector: "gebo-ai-acl-settings-component",
    templateUrl: "acl-settings.component.html",
    standalone: false,
    providers: [
        {
            provide: NG_VALUE_ACCESSOR,
            useExisting: forwardRef(() => GeboAIAclSettingsComponent),
            multi: true
        }
    ]
})
export class GeboAIAclSettingsComponent implements OnInit, ControlValueAccessor {

    /** The grant types the placer allows to be assigned. Defaults to READ only. */
    @Input() allowableGrants: GrantType[] = [AclGrantAssignment.GrantsEnum.READ];

    protected loading: boolean = false;
    protected disabled: boolean = false;

    protected systemMode?: AclSystemMode;
    protected owners?: AclSelectableOwners;

    protected scope: AccessScope = "SPECIFIC";
    protected scopeOptions: { label: string, value: AccessScope }[] = [
        { label: "Everyone", value: "EVERYONE" },
        { label: "Specific groups / users", value: "SPECIFIC" }
    ];

    protected selectedGroupCodes: string[] = [];
    protected selectedUserCodes: string[] = [];

    /** Grants held by each active owner, keyed by {@link ownerKey}. */
    protected grantsByKey: { [key: string]: GrantType[] } = {};

    protected userOptionsView: UserOptionView[] = [];
    protected ownerRows: OwnerGrantRow[] = [];

    /** True while hydrating from writeValue so we don't echo back an onChange. */
    private hydrating: boolean = false;

    constructor(private aclService: AclSettingsAdminControllerService) { }

    ngOnInit(): void {
        this.loading = true;
        this.aclService.getSystemAclMode().subscribe({
            next: (mode) => { this.systemMode = mode; }
        });
        this.aclService.getSelectableOwners().subscribe({
            next: (owners) => {
                this.owners = owners;
                this.refreshDerived();
            },
            complete: () => { this.loading = false; }
        });
    }

    protected get aclEnabled(): boolean {
        return this.systemMode?.aclEnabled === true;
    }

    protected get grantChoiceVisible(): boolean {
        return (this.allowableGrants?.length ?? 0) > 1;
    }

    protected get grantOptions(): { label: string, value: GrantType }[] {
        return (this.allowableGrants ?? []).map(g => ({ label: this.grantLabel(g), value: g }));
    }

    private grantLabel(g: GrantType): string {
        return g.charAt(0) + g.slice(1).toLowerCase();
    }

    private ownerKey(type: AclOwnerRef.TypeEnum, code?: string): string {
        return type === AclOwnerRef.TypeEnum.EVERYONE ? EVERYONE_KEY : type + ":" + (code ?? "");
    }

    private defaultGrants(): GrantType[] {
        return [...(this.allowableGrants ?? [])];
    }

    // --- change handlers driven by the template -------------------------------

    protected onScopeChange(): void {
        this.ensureGrantsForActive();
        this.refreshDerived();
        this.emit();
    }

    protected onGroupsChange(): void {
        // Drop users now covered by a selected group.
        const covered = this.coveredUsernames();
        this.selectedUserCodes = this.selectedUserCodes.filter(u => !covered.has(u));
        this.ensureGrantsForActive();
        this.refreshDerived();
        this.emit();
    }

    protected onUsersChange(): void {
        this.ensureGrantsForActive();
        this.refreshDerived();
        this.emit();
    }

    protected onGrantsChange(): void {
        this.emit();
    }

    // --- derived view state ---------------------------------------------------

    private coveredUsernames(): Set<string> {
        const covered = new Set<string>();
        const groups = this.owners?.groups ?? [];
        for (const code of this.selectedGroupCodes) {
            const group = groups.find(g => g.code === code);
            (group?.memberUserIds ?? []).forEach(u => covered.add(u));
        }
        return covered;
    }

    private refreshDerived(): void {
        const covered = this.coveredUsernames();
        this.userOptionsView = (this.owners?.users ?? []).map(u => ({
            ...u,
            disabled: u.code != null && covered.has(u.code),
            viewLabel: (u.code != null && covered.has(u.code)) ? (u.label ?? u.code) + " — via group" : (u.label ?? u.code ?? "")
        }));
        this.ownerRows = this.buildActiveRows();
    }

    private buildActiveRows(): OwnerGrantRow[] {
        const rows: OwnerGrantRow[] = [];
        if (this.scope === "EVERYONE") {
            rows.push({ key: EVERYONE_KEY, label: "Everyone", type: AclOwnerRef.TypeEnum.EVERYONE });
            return rows;
        }
        const groups = this.owners?.groups ?? [];
        for (const code of this.selectedGroupCodes) {
            const group = groups.find(g => g.code === code);
            rows.push({
                key: this.ownerKey(AclOwnerRef.TypeEnum.GROUP, code),
                label: group?.label ?? (code ?? ""),
                type: AclOwnerRef.TypeEnum.GROUP,
                code
            });
        }
        const covered = this.coveredUsernames();
        for (const code of this.selectedUserCodes) {
            if (code != null && covered.has(code)) continue;
            const user = (this.owners?.users ?? []).find(u => u.code === code);
            rows.push({
                key: this.ownerKey(AclOwnerRef.TypeEnum.USER, code),
                label: user?.label ?? (code ?? ""),
                type: AclOwnerRef.TypeEnum.USER,
                code
            });
        }
        return rows;
    }

    /** Ensures every currently active owner has a grants entry (defaulting). */
    private ensureGrantsForActive(): void {
        const activeKeys = this.buildActiveRows().map(r => r.key);
        for (const key of activeKeys) {
            if (!this.grantsByKey[key] || this.grantsByKey[key].length === 0) {
                this.grantsByKey[key] = this.defaultGrants();
            }
        }
    }

    // --- assemble + emit ------------------------------------------------------

    private buildAssignments(): AclGrantAssignment[] {
        const rows = this.buildActiveRows();
        const allowed = new Set<GrantType>(this.allowableGrants ?? []);
        const assignments: AclGrantAssignment[] = [];
        for (const row of rows) {
            const grants = (this.grantsByKey[row.key] ?? this.defaultGrants()).filter(g => allowed.has(g));
            if (grants.length === 0) continue;
            assignments.push({
                ownerType: row.type as unknown as AclGrantAssignment.OwnerTypeEnum,
                ownerCode: row.code,
                grants
            });
        }
        return assignments;
    }

    private emit(): void {
        if (this.hydrating || this.disabled || !this.onChange) return;
        const assignments = this.buildAssignments();
        if (assignments.length === 0) {
            this.onChange([]);
            return;
        }
        this.aclService.encodeAssignments(assignments).subscribe({
            next: (aliases) => { if (this.onChange) this.onChange(aliases ?? []); }
        });
    }

    // --- ControlValueAccessor -------------------------------------------------

    writeValue(obj: any): void {
        const aliases: number[] = Array.isArray(obj) ? obj : [];
        this.hydrating = true;
        this.grantsByKey = {};
        this.selectedGroupCodes = [];
        this.selectedUserCodes = [];
        this.scope = "SPECIFIC";
        if (aliases.length === 0) {
            this.hydrating = false;
            this.refreshDerived();
            return;
        }
        this.aclService.resolveAliases(aliases).subscribe({
            next: (assignments) => this.applyAssignments(assignments ?? []),
            complete: () => {
                this.hydrating = false;
                this.refreshDerived();
            }
        });
    }

    private applyAssignments(assignments: AclGrantAssignment[]): void {
        const everyone = assignments.find(a => a.ownerType === AclGrantAssignment.OwnerTypeEnum.EVERYONE);
        if (everyone) {
            this.scope = "EVERYONE";
            this.grantsByKey[EVERYONE_KEY] = [...(everyone.grants ?? [])];
            return;
        }
        this.scope = "SPECIFIC";
        for (const a of assignments) {
            if (a.ownerType === AclGrantAssignment.OwnerTypeEnum.GROUP && a.ownerCode) {
                this.selectedGroupCodes.push(a.ownerCode);
                this.grantsByKey[this.ownerKey(AclOwnerRef.TypeEnum.GROUP, a.ownerCode)] = [...(a.grants ?? [])];
            } else if (a.ownerType === AclGrantAssignment.OwnerTypeEnum.USER && a.ownerCode) {
                this.selectedUserCodes.push(a.ownerCode);
                this.grantsByKey[this.ownerKey(AclOwnerRef.TypeEnum.USER, a.ownerCode)] = [...(a.grants ?? [])];
            }
        }
    }

    private onChange?: (v: any) => void;
    registerOnChange(fn: any): void { this.onChange = fn; }

    private onTouch?: (v: any) => void;
    registerOnTouched(fn: any): void { this.onTouch = fn; }

    setDisabledState(isDisabled: boolean): void {
        this.disabled = isDisabled;
    }
}
