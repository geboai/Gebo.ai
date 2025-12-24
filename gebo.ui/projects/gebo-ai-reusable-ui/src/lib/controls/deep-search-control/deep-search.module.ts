import { NgModule } from "@angular/core";
import { GeboAIDeepSearchComponent } from "./deep-search.component";
import { CommonModule } from "@angular/common";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { TabsModule } from "primeng/tabs";
import { ButtonModule } from "primeng/button";
import { FieldsetModule } from "primeng/fieldset";
import { PanelModule } from "primeng/panel";
import { BlockUIModule } from "primeng/blockui";
import { GeboAIStreamDeepSearchService } from "./stream-deep-search.service";
import { GeboAIFieldTranslationContainerModule } from "../field-translation-container/field-container.module";
import { ProgressBarModule } from 'primeng/progressbar';
@NgModule({
    imports:[CommonModule,ReactiveFormsModule,FormsModule,TabsModule,ButtonModule,FieldsetModule,PanelModule,BlockUIModule,GeboAIFieldTranslationContainerModule,ProgressBarModule],
    declarations:[GeboAIDeepSearchComponent],
    exports:[GeboAIDeepSearchComponent],
    providers:[GeboAIStreamDeepSearchService]
})
export class GeboAIDeepSearchModule {}