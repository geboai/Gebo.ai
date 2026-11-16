import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { GeboAIUserWorkflowsLandComponent } from "./user-workflows-land.component";
import { GeboAIUserWorkflowsStartComponent } from "./user-workflows-start.component";
import { Routes, RouterModule } from "@angular/router";
import { PanelModule } from "@openng/optimus-ui/panel";
import { InputTextModule } from "@openng/optimus-ui/inputtext";
import { ButtonModule } from "@openng/optimus-ui/button";
import { BlockUIModule } from "@openng/optimus-ui/blockui";
import { SelectModule } from "@openng/optimus-ui/select";
import { PasswordModule } from "@openng/optimus-ui/password";
import { GeboAIFieldTranslationContainerModule } from "../../controls/field-translation-container/field-container.module";
import { GeboAINotificationsModule } from "../../notifications/notifications.module";
import { GeboAICheckDeviceActivationComponent } from "./check-device-activation.component";

const routes: Routes = [{ path: "land", component: GeboAIUserWorkflowsLandComponent }, { path: "start", component: GeboAIUserWorkflowsStartComponent },{path:'check-device-activation',component:GeboAICheckDeviceActivationComponent}];
@NgModule({
    imports: [
        CommonModule,
        ReactiveFormsModule,
        FormsModule,
        RouterModule.forChild(routes),
        PanelModule,
        InputTextModule,
        ButtonModule,
        BlockUIModule,
        SelectModule,
        PasswordModule,
        GeboAIFieldTranslationContainerModule,
        GeboAINotificationsModule
    ],
    declarations: [GeboAIUserWorkflowsLandComponent, GeboAIUserWorkflowsStartComponent,GeboAICheckDeviceActivationComponent]
})
export class GeboAIUserWorkflowsModule { }