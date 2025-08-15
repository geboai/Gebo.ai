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

@NgModule({
    imports:[CommonModule,FormsModule,ReactiveFormsModule,BlockUIModule,PanelModule,InputTextModule,EditableListboxModule,MultiSelectModule,FieldsetModule],
    declarations:[GeboOauth2SecretComponent],
    exports:[GeboOauth2SecretComponent]
})
export class GeboOauth2SecretModule {}