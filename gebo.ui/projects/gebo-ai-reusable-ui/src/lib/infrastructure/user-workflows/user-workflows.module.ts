import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { GeboAIUserWorkflowsLandComponent } from "./user-workflows-land.component";
import { GeboAIUserWorkflowsStartComponent } from "./user-workflows-start.component";
import { Routes, RouterModule } from "@angular/router";
import { PanelModule } from "primeng/panel";
import { InputTextModule } from "primeng/inputtext";
import { ButtonModule } from "primeng/button";
import { BlockUIModule } from "primeng/blockui";
import { SelectModule } from "primeng/select";
import { PasswordModule } from "primeng/password";
import { GeboAIFieldTranslationContainerModule } from "../../controls/field-translation-container/field-container.module";
import { GeboAINotificationsModule } from "../../notifications/notifications.module";

const routes: Routes = [{ path: "land", component: GeboAIUserWorkflowsLandComponent }, { path: "start", component: GeboAIUserWorkflowsStartComponent }];
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
    declarations: [GeboAIUserWorkflowsLandComponent, GeboAIUserWorkflowsStartComponent]
})
export class GeboAIUserWorkflowsModule { }