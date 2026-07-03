import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { GaboAIAclSettingsComponent } from "./acl-settings.component";
import { PanelModule } from "primeng/panel";
import { BlockUIModule } from "primeng/blockui";

@NgModule({
    imports:[CommonModule,ReactiveFormsModule,FormsModule,PanelModule,BlockUIModule],
    declarations:[GaboAIAclSettingsComponent],
    exports:[GaboAIAclSettingsComponent]
})
export class GeboAIAclSettingsModule {}