import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";

import { GeboAIChatModelUseComponent } from "./chat-model-use.component";

import { CheckboxModule } from '@openng/optimus-ui/checkbox';
@NgModule({
    imports: [CommonModule, ReactiveFormsModule, FormsModule, CheckboxModule],
    declarations: [GeboAIChatModelUseComponent],
    exports: [GeboAIChatModelUseComponent]
})
export class GeboAIChatModelUseModule {

}