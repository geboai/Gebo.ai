/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

import { Component } from "@angular/core";
import { 
  LlmsUsageAdminLevelControllerService, 
  LLMUsageDrillDownLevel, 
  LLMUsageDrillDownResult 
} from "@Gebo.ai/gebo-ai-rest-api";
import { Observable } from "rxjs";
import { GEBO_AI_FIELD_HOST, fieldHostComponentName, GEBO_AI_MODULE } from "../controls/field-host-component-iface/field-host-component-iface";
import { BaseLLMSUsageDashboardComponent } from "./llms-usage-dashboard.component";

@Component({
  selector: "gebo-ai-llms-usage-admin-dashboard",
  templateUrl: "./llms-usage-dashboard.component.html",
  styleUrls: ["./llms-usage-dashboard.component.css"],
  providers: [
    { provide: GEBO_AI_MODULE, useValue: "GeboAILLMSUsageDashboardModule", multi: false },
    { provide: GEBO_AI_FIELD_HOST, multi: false, useValue: fieldHostComponentName("GeboAILLMSUsageAdminDashboardComponent") }
  ],
  standalone: false
})
export class GeboAILLMSUsageAdminDashboardComponent extends BaseLLMSUsageDashboardComponent {
  constructor(private adminService: LlmsUsageAdminLevelControllerService) {
    super();
  }

  executeDrillDown(filter: LLMUsageDrillDownLevel): Observable<LLMUsageDrillDownResult> {
    return this.adminService.drillDown1(filter);
  }
}
