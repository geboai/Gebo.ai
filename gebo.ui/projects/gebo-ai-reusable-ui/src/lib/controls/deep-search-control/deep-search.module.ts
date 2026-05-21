import { NgModule } from "@angular/core";
import { GeboAIDeepSearchComponent } from "./deep-search.component";
import { CommonModule } from "@angular/common";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { TabsModule } from "primeng/tabs";
import { ButtonModule } from "primeng/button";
import { FieldsetModule } from "primeng/fieldset";
import { PanelModule } from "primeng/panel";
import { BlockUIModule } from "primeng/blockui";

import { GeboAIFieldTranslationContainerModule } from "../field-translation-container/field-container.module";
import { GeboAINotificationsModule } from "../../notifications/notifications.module";
import { ProgressBarModule } from 'primeng/progressbar';
import { DialogModule } from "primeng/dialog";
import { SelectButtonModule } from 'primeng/selectbutton';

import { MarkdownComponent } from "ngx-markdown";
import { AccordionModule } from "primeng/accordion";
import { GeboAIDeepSearchSoucesChoiceComponent } from "./deep-search-sources-choice.component";
@NgModule({
    imports: [CommonModule, ReactiveFormsModule, FormsModule, TabsModule, ButtonModule, FieldsetModule, PanelModule, BlockUIModule, GeboAIFieldTranslationContainerModule, ProgressBarModule, DialogModule, SelectButtonModule, FieldsetModule, GeboAINotificationsModule, MarkdownComponent,AccordionModule],
    declarations:[GeboAIDeepSearchComponent,GeboAIDeepSearchSoucesChoiceComponent],
    exports:[GeboAIDeepSearchComponent,GeboAIDeepSearchSoucesChoiceComponent]
})
export class GeboAIDeepSearchModule {}