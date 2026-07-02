/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { FormsModule } from "@angular/forms";
import { TabsModule } from "primeng/tabs";
import { SelectModule } from "primeng/select";
import { ChartModule } from "primeng/chart";
import { ButtonModule } from "primeng/button";
import { BlockUIModule } from "primeng/blockui";
import { PanelModule } from "primeng/panel";

import { GeboAIWorkflowStatsAdminDashboardComponent } from "./workflow-stats-admin-dashboard.component";

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    TabsModule,
    SelectModule,
    ChartModule,
    ButtonModule,
    BlockUIModule,
    PanelModule
  ],
  declarations: [
    GeboAIWorkflowStatsAdminDashboardComponent
  ],
  exports: [
    GeboAIWorkflowStatsAdminDashboardComponent
  ]
})
export class GeboAIWorkflowStatsDashboardModule { }
