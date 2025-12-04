/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */




/**
 * @file content-reindex.module.ts
 * AI generated comments
 * 
 * This module provides components for scheduling content reindexing operations.
 * It imports various UI modules from PrimeNG and Angular and declares the scheduling
 * related components used for timing and schedule management in the application.
 */

import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { BlockUIModule } from "primeng/blockui";
import { ButtonModule } from "primeng/button";
import { ChipModule } from "primeng/chip";
import { DialogModule } from "primeng/dialog";
import { PanelModule } from "primeng/panel";
import { TimeSetComponent } from "./time-set.component";
import { GeboAIPeriodsSchedulingBaseComponent } from "./periods-base.component";
import { GeboAIContentReindexScheduleComponent } from "./content-reindex-schedule.component";

import { CalendarModule } from "primeng/calendar";
import { InputNumberModule } from "primeng/inputnumber";
import { FieldsetModule } from "primeng/fieldset";
import { SelectModule } from 'primeng/select';
import { GEBO_AI_MODULE} from "../field-host-component-iface/field-host-component-iface";
import { GeboAIFieldTranslationContainerModule } from "../field-translation-container/field-container.module";

/**
 * NgModule for content reindexing functionality.
 * 
 * This module encapsulates all components needed for the content reindexing feature.
 * It imports necessary UI modules from PrimeNG like calendar, buttons, dialogs, etc.,
 * and Angular's form handling modules.
 * 
 * The module declares three components:
 * - GeboAIPeriodsSchedulingBaseComponent: Base component for period scheduling
 * - TimeSetComponent: Component for setting specific times
 * - GeboAIContentReindexScheduleComponent: Main component for content reindexing schedule
 * 
 * Only the GeboAIContentReindexScheduleComponent is exported for use in other modules.
 */
@NgModule({
    imports: [CommonModule, ReactiveFormsModule, FormsModule, DialogModule, PanelModule, BlockUIModule, ChipModule, ButtonModule, SelectModule, CalendarModule, InputNumberModule, FieldsetModule,GeboAIFieldTranslationContainerModule],
    declarations: [GeboAIPeriodsSchedulingBaseComponent, TimeSetComponent, GeboAIContentReindexScheduleComponent],
    exports: [GeboAIContentReindexScheduleComponent],
    providers: [{ provide: GEBO_AI_MODULE, useValue: "GeboAIContentReindexModule", multi: false }]

})
export class GeboAIContentReindexModule { }