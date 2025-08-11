import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { ReactiveFormsModule, FormsModule } from "@angular/forms";
import { RouterModule, Routes } from "@angular/router";
import { BlockUIModule } from "primeng/blockui";
import { ButtonModule } from "primeng/button";
import { FieldsetModule } from "primeng/fieldset";
import { InputTextModule } from "primeng/inputtext";
import { MessagesModule } from "primeng/messages";
import { PanelModule } from "primeng/panel";
import { PasswordModule } from "primeng/password";
import { GeboAIReloadForwardComponent } from "./reload-forward.component";
import { LoginModule } from "@Gebo.ai/reusable-ui";
const routes: Routes = [{ path: 'ui/reloader', component: GeboAIReloadForwardComponent }];

@NgModule({
    imports: [CommonModule, PasswordModule, InputTextModule, ButtonModule,
        BlockUIModule,
        PanelModule,
        MessagesModule, ReactiveFormsModule, FormsModule, FieldsetModule, RouterModule.forRoot(routes),LoginModule],
    declarations: [GeboAIReloadForwardComponent],
    exports: [GeboAIReloadForwardComponent]
})
export class ReloadForwardModule { }