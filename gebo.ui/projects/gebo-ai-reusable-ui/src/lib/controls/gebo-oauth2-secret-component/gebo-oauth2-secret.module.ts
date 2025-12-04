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
import { fieldHostComponentName, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE } from "../field-host-component-iface/field-host-component-iface";
@NgModule({
    imports: [CommonModule, FormsModule, ReactiveFormsModule, BlockUIModule, PanelModule, InputTextModule, EditableListboxModule, MultiSelectModule, FieldsetModule, GeboAIFieldTranslationContainerModule],
    declarations: [GeboOauth2SecretComponent],
    exports: [GeboOauth2SecretComponent],
    providers: []
})
export class GeboOauth2SecretModule { }