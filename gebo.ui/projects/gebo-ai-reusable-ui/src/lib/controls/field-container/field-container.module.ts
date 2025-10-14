import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { ReactiveFormsModule } from "@angular/forms";
import { GeboAIFieldContainerComponent } from "./field-container.component";
import { GeboAIFieldContainerDirective } from "./field-container.directive";
import { GeboAILabelDirective } from "./label.directive";
import { GeboAITranslationService } from "./gebo-translation.service";

@NgModule({
    imports:[CommonModule,ReactiveFormsModule],
    declarations:[GeboAIFieldContainerComponent,GeboAIFieldContainerDirective,GeboAILabelDirective],
    exports:[GeboAIFieldContainerComponent,GeboAIFieldContainerDirective,GeboAILabelDirective],
    providers:[GeboAITranslationService]
})
export class GeboAIFieldContainerModule {}