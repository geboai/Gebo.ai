/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

import { Component, Input, OnInit } from "@angular/core";
import { FormGroup } from "@angular/forms";
import { AclGrantAssignment, AclSettingsAdminControllerService } from "@Gebo.ai/gebo-ai-rest-api";

/**
 * Wrapper security-settings control bound to an {@link FormGroup}. When the
 * installation runs in ACL mode (discovered through the ACL controller) and the
 * bound form group exposes an {@code aclAliases} child control, it edits the ACL
 * through the {@code gebo-ai-acl-settings-component}; otherwise it falls back to
 * the classic {@code gebo-ai-access-control} (accessible groups/users/everyone).
 */
@Component({
    selector: "gebo-ai-security-settings",
    templateUrl: "security-settings.component.html",
    standalone: false
})
export class GeboAISecuritySettingsComponent implements OnInit {

    /** The form group whose access settings are edited. */
    @Input() formGroup?: FormGroup;

    /** Grants the ACL editor is allowed to assign (forwarded to the ACL control). */
    @Input() allowableGrants: AclGrantAssignment.GrantsEnum[] = [AclGrantAssignment.GrantsEnum.READ];

    /** Whether the installation enforces ACLs; resolved from the backend. */
    protected aclEnabled: boolean = false;

    /** True once the system ACL mode has been resolved, to avoid flicker. */
    protected modeLoaded: boolean = false;

    constructor(private aclService: AclSettingsAdminControllerService) { }

    ngOnInit(): void {
        this.aclService.getSystemAclMode().subscribe({
            next: (mode) => { this.aclEnabled = mode?.aclEnabled === true; },
            complete: () => { this.modeLoaded = true; }
        });
    }

    /**
     * The ACL editor is shown only when the system is in ACL mode and the bound
     * form group actually carries an {@code aclAliases} control to edit.
     */
    protected get useAclControl(): boolean {
        return this.aclEnabled === true && !!this.formGroup?.controls["aclAliases"];
    }
}
