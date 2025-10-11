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

@NgModule({
    imports: [CommonModule, FormsModule, ReactiveFormsModule, BlockUIModule, PanelModule, Fieldset, Button, InputNumber, InputTextModule, EditableListboxModule],
    declarations:[GeboAIContentSelectionFilterComponent,GeboAISelectionFilterCriteriaComponent],
    exports:[GeboAIContentSelectionFilterComponent]
})
export class GeboAIContentSelectionFilterModule {

}