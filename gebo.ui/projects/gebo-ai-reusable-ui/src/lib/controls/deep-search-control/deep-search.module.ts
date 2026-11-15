import { NgModule } from "@angular/core";
import { GeboAIDeepSearchComponent } from "./deep-search.component";
import { CommonModule } from "@angular/common";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { TabsModule } from "@openng/optimus-ui/tabs";
import { ButtonModule } from "@openng/optimus-ui/button";
import { FieldsetModule } from "@openng/optimus-ui/fieldset";
import { PanelModule } from "@openng/optimus-ui/panel";
import { BlockUIModule } from "@openng/optimus-ui/blockui";

import { GeboAIFieldTranslationContainerModule } from "../field-translation-container/field-container.module";
import { GeboAINotificationsModule } from "../../notifications/notifications.module";
import { ProgressBarModule } from '@openng/optimus-ui/progressbar';
import { DialogModule } from "@openng/optimus-ui/dialog";
import { SelectButtonModule } from '@openng/optimus-ui/selectbutton';

import { MarkdownComponent } from "ngx-markdown";
import { AccordionModule } from "@openng/optimus-ui/accordion";
import { GeboAIDeepSearchSoucesChoiceComponent } from "./deep-search-sources-choice.component";
@NgModule({
    imports: [CommonModule, ReactiveFormsModule, FormsModule, TabsModule, ButtonModule, FieldsetModule, PanelModule, BlockUIModule, GeboAIFieldTranslationContainerModule, ProgressBarModule, DialogModule, SelectButtonModule, FieldsetModule, GeboAINotificationsModule, MarkdownComponent,AccordionModule],
    declarations:[GeboAIDeepSearchComponent,GeboAIDeepSearchSoucesChoiceComponent],
    exports:[GeboAIDeepSearchComponent,GeboAIDeepSearchSoucesChoiceComponent]
})
export class GeboAIDeepSearchModule {}