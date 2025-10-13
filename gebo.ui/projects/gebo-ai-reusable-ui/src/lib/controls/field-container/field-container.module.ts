import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { ReactiveFormsModule } from "@angular/forms";
import { GeboAIFieldContainerComponent } from "./field-container.component";
import { GeboAIFieldContainerDirective } from "./field-container.directive";

@NgModule({
    imports:[CommonModule,ReactiveFormsModule],
    declarations:[GeboAIFieldContainerComponent,GeboAIFieldContainerDirective],
    exports:[GeboAIFieldContainerComponent,GeboAIFieldContainerDirective]
})
export class GeboAIFieldContainerModule {}