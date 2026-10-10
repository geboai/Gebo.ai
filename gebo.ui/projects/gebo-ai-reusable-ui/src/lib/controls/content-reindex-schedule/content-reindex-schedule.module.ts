/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */




/**
 * @file content-reindex-schedule.module.ts
 *
 * The editor of the schedule on which a data source is re-checked for contents to ingest.
 *
 * Only GeboAIContentReindexScheduleComponent is exported: the other three components are the
 * pieces it is drawn from (one rule as a card, one rule said in words, the week at a glance).
 */

import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { ButtonModule } from "primeng/button";
import { DatePickerModule } from "primeng/datepicker";
import { DialogModule } from "primeng/dialog";
import { InputNumberModule } from "primeng/inputnumber";
import { GEBO_AI_MODULE } from "../field-host-component-iface/field-host-component-iface";
import { GeboAIFieldTranslationContainerModule } from "../field-translation-container/field-container.module";
import { GeboAIContentReindexScheduleComponent } from "./content-reindex-schedule.component";
import { GeboAIScheduleRuleCardComponent } from "./schedule-rule-card.component";
import { GeboAIScheduleSentenceComponent } from "./schedule-sentence.component";
import { GeboAIScheduleWeekGridComponent } from "./schedule-week-grid.component";

@NgModule({
    imports: [CommonModule, ReactiveFormsModule, FormsModule, DialogModule, ButtonModule,
        DatePickerModule, InputNumberModule, GeboAIFieldTranslationContainerModule],
    declarations: [GeboAIScheduleSentenceComponent, GeboAIScheduleWeekGridComponent,
        GeboAIScheduleRuleCardComponent, GeboAIContentReindexScheduleComponent],
    exports: [GeboAIContentReindexScheduleComponent],
    providers: [{ provide: GEBO_AI_MODULE, useValue: "GeboAIContentReindexModule", multi: false }]
})
export class GeboAIContentReindexModule { }
