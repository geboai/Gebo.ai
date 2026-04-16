import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { GeboOauth2SecretComponent } from "./gebo-oauth2-secret.component";
import { BlockUIModule } from "primeng/blockui";
import { PanelModule } from "primeng/panel";
import { InputTextModule } from "primeng/inputtext";
import { EditableListboxModule } from "../editable-listbox-component/editable-listbox.module";
import { MultiSelectModule } from "primeng/multiselect";
import { FieldsetModule } from "primeng/fieldset";
import { GeboAIFieldTranslationContainerModule } from "../field-translation-container/field-container.module";
@NgModule({
    imports: [CommonModule, FormsModule, ReactiveFormsModule, BlockUIModule, PanelModule, InputTextModule, EditableListboxModule, MultiSelectModule, FieldsetModule, GeboAIFieldTranslationContainerModule],
    declarations: [GeboOauth2SecretComponent],
    exports: [GeboOauth2SecretComponent],
    providers: []
})
export class GeboOauth2SecretModule { }