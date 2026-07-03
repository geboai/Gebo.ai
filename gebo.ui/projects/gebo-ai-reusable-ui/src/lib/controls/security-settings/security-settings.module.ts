/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { GeboAIAccessControlModule } from "../access-control-group/access-control-group.module";
import { GeboAIAclSettingsModule } from "../acl-settings-component/acl-settings.module";
import { GeboAISecuritySettingsComponent } from "./security-settings.component";

@NgModule({
    imports: [CommonModule, ReactiveFormsModule, FormsModule, GeboAIAccessControlModule, GeboAIAclSettingsModule],
    declarations: [GeboAISecuritySettingsComponent],
    exports: [GeboAISecuritySettingsComponent]
})
export class GeboAISecuritySettingsModule {}
