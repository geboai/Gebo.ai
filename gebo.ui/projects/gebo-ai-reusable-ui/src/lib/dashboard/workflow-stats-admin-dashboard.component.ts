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
  WorkflowStatsAdminLevelControllerService,
  WorkflowStatsDrillDownLevel,
  WorkflowStatsDrillDownResult
} from "@Gebo.ai/gebo-ai-rest-api";
import { Observable } from "rxjs";
import { GEBO_AI_FIELD_HOST, fieldHostComponentName, GEBO_AI_MODULE } from "../controls/field-host-component-iface/field-host-component-iface";
import { BaseWorkflowStatsDashboardComponent } from "./workflow-stats-dashboard.component";

@Component({
  selector: "gebo-ai-workflow-stats-admin-dashboard",
  templateUrl: "./workflow-stats-dashboard.component.html",
  styleUrls: ["./workflow-stats-dashboard.component.css"],
  providers: [
    { provide: GEBO_AI_MODULE, useValue: "GeboAIWorkflowStatsDashboardModule", multi: false },
    { provide: GEBO_AI_FIELD_HOST, multi: false, useValue: fieldHostComponentName("GeboAIWorkflowStatsAdminDashboardComponent") }
  ],
  standalone: false
})
export class GeboAIWorkflowStatsAdminDashboardComponent extends BaseWorkflowStatsDashboardComponent {
  constructor(private adminService: WorkflowStatsAdminLevelControllerService) {
    super();
  }

  executeDrillDown(filter: WorkflowStatsDrillDownLevel): Observable<WorkflowStatsDrillDownResult> {
    return this.adminService.workflowDrillDown(filter);
  }
}
