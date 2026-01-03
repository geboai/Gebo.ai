import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";

import { GeboAIChatModelUseComponent } from "./chat-model-use.component";

import { CheckboxModule } from 'primeng/checkbox';
@NgModule({
    imports: [CommonModule, ReactiveFormsModule, FormsModule, CheckboxModule],
    declarations: [GeboAIChatModelUseComponent],
    exports: [GeboAIChatModelUseComponent]
})
export class GeboAIChatModelUseModule {

}