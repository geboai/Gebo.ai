import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { ReactiveFormsModule } from "@angular/forms";
import { FieldsetModule } from "primeng/fieldset";
import { PanelModule } from "primeng/panel";
import { EditableListboxModule } from "../editable-listbox-component/editable-listbox.module";
import { GeboAIFieldTranslationContainerModule } from "../field-translation-container/field-container.module";
import { GeboAIApiKeyComponent } from "./api-key.component";
import { SelectButtonModule } from "primeng/selectbutton";
import { ButtonModule } from "primeng/button";
import { BlockUIModule } from "primeng/blockui";
import { InputTextModule } from "primeng/inputtext";
import { GeboAINotificationsModule } from "../../notifications/notifications.module";

@NgModule({
    imports:[CommonModule,ReactiveFormsModule,PanelModule,FieldsetModule,EditableListboxModule,GeboAIFieldTranslationContainerModule,SelectButtonModule,ButtonModule,BlockUIModule,InputTextModule,GeboAINotificationsModule],
    declarations:[GeboAIApiKeyComponent],
    exports:[GeboAIApiKeyComponent]
})
export class GeboAIApiKeyModule {}