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
import {
    PButtonLabelTarget, PFieldSetLabelTarget, PPanelLabelTarget, PDialogLabelTarget, PSidebarLabelTarget,
    PCardLabelTarget, PTabPanelLabelTarget, PAccordionTabLabelTarget, PToolbarLabelTarget,
    PBadgeLabelTarget, PTagLabelTarget, PChipLabelTarget,
    PCheckboxLabelTarget, PRadioLabelTarget, PToggleLabelTarget,
    PInputTextLabelTarget, PInputTextareaLabelTarget, PInputNumberLabelTarget,
    PDropdownLabelTarget, PMultiSelectLabelTarget, PListboxLabelTarget,
    PCalendarLabelTarget, PSliderLabelTarget, PProgressBarLabelTarget,
    PStepsLabelTarget, PGalleriaLabelTarget
} from "./primeng-components-multilanguage-adapters.directive";
import { PopoverModule } from 'primeng/popover';
import { POldTabPanelLabelTarget } from "./primeng-components-obsolete-multilanguage-adapters.directive";
@NgModule({
    imports: [CommonModule, ReactiveFormsModule, Button, SelectModule,PopoverModule],
    declarations: [GeboAIFieldContainerComponent, GeboAIFieldContainerDirective, GeboAILabelDirective, GeboAITextDirective, GeboAILanguageResourcesDownloadComponent, GeboAILanguageChoiceComponent, GeboAIMainLanguageChoiceComponent, PButtonLabelTarget, PFieldSetLabelTarget, PPanelLabelTarget, PDialogLabelTarget, PSidebarLabelTarget,
        PCardLabelTarget, PTabPanelLabelTarget, PAccordionTabLabelTarget, PToolbarLabelTarget,
        PBadgeLabelTarget, PTagLabelTarget, PChipLabelTarget,
        PCheckboxLabelTarget, PRadioLabelTarget, PToggleLabelTarget,
        PInputTextLabelTarget, PInputTextareaLabelTarget, PInputNumberLabelTarget,
        PDropdownLabelTarget, PMultiSelectLabelTarget, PListboxLabelTarget,
        PCalendarLabelTarget, PSliderLabelTarget, PProgressBarLabelTarget,
        PStepsLabelTarget, PGalleriaLabelTarget, POldTabPanelLabelTarget],
    exports: [GeboAIFieldContainerComponent, GeboAIFieldContainerDirective, GeboAILabelDirective, GeboAITextDirective, GeboAILanguageResourcesDownloadComponent, GeboAILanguageChoiceComponent, GeboAIMainLanguageChoiceComponent, PButtonLabelTarget, PFieldSetLabelTarget, PPanelLabelTarget, PDialogLabelTarget, PSidebarLabelTarget,
        PCardLabelTarget, PTabPanelLabelTarget, PAccordionTabLabelTarget, PToolbarLabelTarget,
        PBadgeLabelTarget, PTagLabelTarget, PChipLabelTarget,
        PCheckboxLabelTarget, PRadioLabelTarget, PToggleLabelTarget,
        PInputTextLabelTarget, PInputTextareaLabelTarget, PInputNumberLabelTarget,
        PDropdownLabelTarget, PMultiSelectLabelTarget, PListboxLabelTarget,
        PCalendarLabelTarget, PSliderLabelTarget, PProgressBarLabelTarget,
        PStepsLabelTarget, PGalleriaLabelTarget, POldTabPanelLabelTarget],
})
export class GeboAIFieldTranslationContainerModule {
    static forRoot(): ModuleWithProviders<GeboAIFieldTranslationContainerModule> {
        return {
            ngModule: GeboAIFieldTranslationContainerModule,
            providers: [GeboAITranslationService]
        };
    }
}