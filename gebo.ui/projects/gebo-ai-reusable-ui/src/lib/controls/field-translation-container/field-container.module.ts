import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { ReactiveFormsModule } from "@angular/forms";
import { GeboAIFieldContainerComponent } from "./field-container.component";
import { GeboAIFieldContainerDirective } from "./field-container.directive";
import { GeboAILabelDirective } from "./label.directive";
import { GeboAITranslationService } from "./gebo-translation.service";
import { GeboAITextDirective } from "./text.directive";
import { GeboAILanguageResourcesDownloadComponent } from "./language-resources-download.component";
import { Button } from "primeng/button";

@NgModule({
    imports: [CommonModule, ReactiveFormsModule, Button],
    declarations:[GeboAIFieldContainerComponent,GeboAIFieldContainerDirective,GeboAILabelDirective,GeboAITextDirective,GeboAILanguageResourcesDownloadComponent],
    exports:[GeboAIFieldContainerComponent,GeboAIFieldContainerDirective,GeboAILabelDirective,GeboAITextDirective,GeboAILanguageResourcesDownloadComponent],
    providers:[GeboAITranslationService]
})
export class GeboAIFieldTransationContainerModule {}