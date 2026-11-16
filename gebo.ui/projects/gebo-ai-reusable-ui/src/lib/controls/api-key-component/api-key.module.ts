import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { ReactiveFormsModule } from "@angular/forms";
import { FieldsetModule } from "@openng/optimus-ui/fieldset";
import { PanelModule } from "@openng/optimus-ui/panel";
import { EditableListboxModule } from "../editable-listbox-component/editable-listbox.module";
import { GeboAIFieldTranslationContainerModule } from "../field-translation-container/field-container.module";
import { GeboAIApiKeyComponent } from "./api-key.component";
import { SelectButtonModule } from "@openng/optimus-ui/selectbutton";
import { ButtonModule } from "@openng/optimus-ui/button";
import { BlockUIModule } from "@openng/optimus-ui/blockui";
import { InputTextModule } from "@openng/optimus-ui/inputtext";
import { SelectModule } from "@openng/optimus-ui/select";
import { TextareaModule } from "@openng/optimus-ui/textarea";
import { PasswordModule } from "@openng/optimus-ui/password";
import { GeboAINotificationsModule } from "../../notifications/notifications.module";
import { GeboBlockableContainerDirective } from "../../directives/blockable-container/blockable-container.directive";

@NgModule({
    imports:[CommonModule,ReactiveFormsModule,PanelModule,FieldsetModule,EditableListboxModule,GeboAIFieldTranslationContainerModule,SelectButtonModule,ButtonModule,BlockUIModule,InputTextModule,SelectModule,TextareaModule,PasswordModule,GeboAINotificationsModule,GeboBlockableContainerDirective],
    declarations:[GeboAIApiKeyComponent],
    exports:[GeboAIApiKeyComponent]
})
export class GeboAIApiKeyModule {}