import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { GeboAIContentSelectionFilterComponent } from "./content-selection-filter.component";
import { BlockUIModule } from "primeng/blockui";
import { PanelModule } from "primeng/panel";
import { GeboAISelectionFilterCriteriaComponent } from "./content-selection-filter-criteria.component";
import { Fieldset } from "primeng/fieldset";
import { Button } from "primeng/button";
import { InputNumber } from "primeng/inputnumber";
import { InputTextModule } from "primeng/inputtext";
import { EditableListboxModule } from "../editable-listbox-component/editable-listbox.module";
import { MultiSelect } from "primeng/multiselect";
import { fieldHostComponentName, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE } from "../field-host-component-iface/field-host-component-iface";
import { GeboAIFieldTransationContainerModule } from "../field-translation-container/field-container.module";


@NgModule({
    imports: [CommonModule, FormsModule, ReactiveFormsModule, BlockUIModule, PanelModule, Fieldset, Button, InputNumber, InputTextModule, EditableListboxModule, MultiSelect, GeboAIFieldTransationContainerModule],
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