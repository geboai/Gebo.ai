import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { GeboAIContentSelectionFilterComponent } from "./content-selection-filter.component";
import { BlockUIModule } from "@openng/optimus-ui/blockui";
import { PanelModule } from "@openng/optimus-ui/panel";
import { GeboAISelectionFilterCriteriaComponent } from "./content-selection-filter-criteria.component";
import { Fieldset } from "@openng/optimus-ui/fieldset";
import { Button } from "@openng/optimus-ui/button";
import { InputNumber } from "@openng/optimus-ui/inputnumber";
import { InputTextModule } from "@openng/optimus-ui/inputtext";
import { EditableListboxModule } from "../editable-listbox-component/editable-listbox.module";
import { MultiSelect } from "@openng/optimus-ui/multiselect";
import { fieldHostComponentName, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE } from "../field-host-component-iface/field-host-component-iface";
import { GeboAIFieldTranslationContainerModule } from "../field-translation-container/field-container.module";


@NgModule({
    imports: [CommonModule, FormsModule, ReactiveFormsModule, BlockUIModule, PanelModule, Fieldset, Button, InputNumber, InputTextModule, EditableListboxModule, MultiSelect, GeboAIFieldTranslationContainerModule],
    declarations: [GeboAIContentSelectionFilterComponent, GeboAISelectionFilterCriteriaComponent],
    exports: [GeboAIContentSelectionFilterComponent],
    providers: [{
        provide: GEBO_AI_MODULE, useValue: "GeboAIContentSelectionFilterModule", multi: false
    }, {
        provide: GEBO_AI_FIELD_HOST, useValue: fieldHostComponentName("GeboAIContentSelectionFilterComponent"), multi: false
    }]
})
export class GeboAIContentSelectionFilterModule {

}