import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { GeboAIAclSettingsComponent } from "./acl-settings.component";
import { PanelModule } from "primeng/panel";
import { SelectButtonModule } from "primeng/selectbutton";
import { MultiSelectModule } from "primeng/multiselect";

@NgModule({
    imports:[CommonModule,ReactiveFormsModule,FormsModule,PanelModule,SelectButtonModule,MultiSelectModule],
    declarations:[GeboAIAclSettingsComponent],
    exports:[GeboAIAclSettingsComponent]
})
export class GeboAIAclSettingsModule {}