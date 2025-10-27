import { CommonModule } from "@angular/common";
import { ModuleWithProviders, NgModule } from "@angular/core";
import { ReactiveFormsModule } from "@angular/forms";
import { GeboAIFieldContainerComponent } from "./field-container.component";
import { GeboAIFieldContainerDirective } from "./field-container.directive";
import { GeboAILabelDirective } from "./label.directive";
import { GeboAITranslationService } from "./gebo-translation.service";
import { GeboAITextDirective } from "./text.directive";
import { GeboAILanguageResourcesDownloadComponent } from "./language-resources-download.component";
import { Button } from "primeng/button";
import { GeboAILanguageChoiceComponent } from "./language-choice.component";
import { SelectModule } from 'primeng/select';
import { GeboAIMainLanguageChoiceComponent } from "./main-language-choice.component";
@NgModule({
    imports: [CommonModule, ReactiveFormsModule, Button, SelectModule],
    declarations: [GeboAIFieldContainerComponent, GeboAIFieldContainerDirective, GeboAILabelDirective, GeboAITextDirective, GeboAILanguageResourcesDownloadComponent, GeboAILanguageChoiceComponent, GeboAIMainLanguageChoiceComponent],
    exports: [GeboAIFieldContainerComponent, GeboAIFieldContainerDirective, GeboAILabelDirective, GeboAITextDirective, GeboAILanguageResourcesDownloadComponent, GeboAILanguageChoiceComponent, GeboAIMainLanguageChoiceComponent],
    providers: [GeboAITranslationService]
})
export class GeboAIFieldTransationContainerModule {
    static forRoot(): ModuleWithProviders<GeboAIFieldTransationContainerModule> {
        return {
            ngModule: GeboAIFieldTransationContainerModule,
            providers: [GeboAITranslationService] 
        };
    }
}